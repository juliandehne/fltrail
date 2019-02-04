let userName;
let projectName;

$(document).ready(function () {
    checkAssessementPhase();
    userName = $('#userEmail').html().trim();
    projectName = $('#projectName').html().trim();

    Survey
        .StylesManager
        .applyTheme("default");

    projectId = getQueryVariable("projectName");
    // getting the survey items from the server
    let requ = new RequestObj(1, "/survey", "/evaluation/project/?", [projectName], []);
    serverSide(requ, "GET", function (surveyJSON) {
        let survey = new Survey.Model(surveyJSON);
        survey.locale = "de";


        $("#surveyContainer").Survey({
            model: survey,
            onComplete: sendDataToServer
        });
    });

    function sendDataToServer(survey) {
        //var resultAsString = JSON.stringify(survey.data);
        //alert(resultAsString); //send Ajax request to your web server.

        let dataReq = new RequestObj(1, "/survey", "/save/projects/?", [projectName], [], survey.data);
        userEmail = survey.data.EMAIL1;
        serverSide(dataReq, "POST", function () {
            //log.warn(a);
            location.href = "../project/tasks-student.jsp?projectName=" + projectName;
        })
    }

    $('#notAllRated').hide();
    /*
    $(".carousel").carousel({
        interval: false
    });
    getWorkProperty(projectName, buildTablesForPeers);

    $('#assessThePeer').on("click", function () {
        assessPeer();
    });*/
});


function buildTablesForPeers(properties) {
    $.ajax({
        url: '../rest/group/project/' + projectName + '/student/' + userName,
        type: 'GET',
        headers: {
            "Content-Type": "text/javascript",
            "Cache-Control": "no-cache"
        },
        properties: properties,
        success: function (peers) {
            let div = document.getElementById('peerTable');
            for (let peer = 0; peer < peers.length; peer++) {
                let peerId = peers[peer].replace("@", "").replace(".", "");
                let tmplObject = {
                    peerId: peerId,
                    first: null,
                    properties: properties
                };
                if (peer === 0)
                    tmplObject.first = 1;
                $('#peerTemplate').tmpl(tmplObject).appendTo(div);
                $('#btnJournal' + peerId).on("click", function () {
                    $('#eJournal' + peerId).toggle();
                });
            }},
        error: function () {
        }
        }
    );
}

function assessPeer() {
    let peerStudents = $('.peerStudent');
    ///////initialize variables///////
    let dataP = new Array(peerStudents.length);
    let rateThis = ['responsibility', 'partOfWork', 'cooperation', 'communication', 'autonomous'];

    ///////read values from html///////
    for (let peer = 0; peer < peerStudents.length; peer++) {
        let workRating = {};
        let peerRating = {
            "fromPeer": $('#user').html().trim(),
            "toPeer": peerStudents[peer].id,
            "workRating": {}
        };
        for (let rate = 0; rate < rateThis.length; rate++) {
            let category = rateThis[rate];
            workRating[category] = ($('input[name=' + rateThis[rate] + peerStudents[peer].id + ']:checked').val());
        }

        peerRating.workRating = workRating;
        //////write values in Post-Variable
        dataP[peer] = peerRating;
    }
    for (let peer = 0; peer < dataP.length; peer++) {
        for (let workRating = 0; workRating < rateThis.length; workRating++) {
            if (dataP[peer].workRating[rateThis[workRating]] === undefined) {
                $('#notAllRated').show();
                return;
            }
        }
    }
    let projectName = $('#projectName').html().trim();
    $.ajax({
        url: '../rest/assessments/peerRating/project/' + projectName,
        type: 'POST',
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        data: JSON.stringify(dataP),
        success: function () {
            location.href = "take-quiz.jsp?projectName=" + projectName;
        },
        error: function (a, b, c) {

        }
    });
}

function getWorkProperty(projectName, callback) {
    $.ajax({
        url: '../rest/survey/evaluation/project/'+projectName,
        type: 'GET',
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        success: function (response) {
            callback(response)
        },
        error: function (a) {

        }
    });
}
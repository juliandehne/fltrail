$(document).ready(function () {
    checkAssessementPhase();


    $('#notAllRated').hide();
    $(".carousel").carousel({
        interval: false
    });
    buildTablesForPeers();

    $('#assessThePeer').on("click", function () {
        assessPeer();
    });
});


function buildTablesForPeers() {
    let userName = $('#userEmail').html().trim();
    let projectName = $('#projectName').html().trim();
    $.ajax({
            url: '../rest/group/project/' + projectName + '/student/' + userName,
            type: 'GET',
            headers: {
                "Content-Type": "text/javascript",
                "Cache-Control": "no-cache"
            },
            success: function (peers) {
                let div = document.getElementById('peerTable');
                for (let peer = 0; peer < peers.length; peer++) {
                    let peerId = peers[peer].replace("@", "").replace(".", "");
                    let tmplObject = {
                        peerId: peerId,
                        first: null
                    };
                    if (peer === 0)
                        tmplObject.first = 1;
                    $('#peerTemplate').tmpl(tmplObject).appendTo(div);
                    $('#btnJournal' + peerId).on("click", function () {
                        $('#eJournal' + peerId).toggle();
                    });
                }
            },
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

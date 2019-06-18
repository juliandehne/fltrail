let projectName = "";
let userEmail;
let userFeedbacked;

$(document).ready(function () {
    Survey
        .StylesManager
        .applyTheme("default");

    projectName = getQueryVariable("projectName");

    // get the user to feedback
    getWhomToRate(projectName);


    // getting the survey items from the server
    let requ = new RequestObj(1, "/assessment", "/data/project/?", [projectName], []);
    serverSide(requ, "GET", function (surveyJSON) {
        let survey = new Survey.Model(surveyJSON);
        survey.locale = "de";


        $("#surveyContainer").Survey({
            model: survey,
            onComplete: sendDataToServer
        });
    });
});

function sendDataToServer(survey) {
    //var resultAsString = JSON.stringify(survey.data);
    //alert(resultAsString); //send Ajax request to your web server.
    let dataReq = new RequestObj(1, "/assessment", "/save/projects/?/context/FL/user/?", [projectName, userFeedbacked.email], [], survey.data);
    serverSide(dataReq, "POST", function () {
        //log.warn(a);
        // bestätigung, dass es gut funktioniert hat und dann zurückbutton anbieten
        // this here is a dummy
        location.href = "../project/tasks-student.jsp?projectName=" + projectName;
    })
}

function getWhomToRate(projectName) {
    let dataReq = new RequestObj(1, "/assessment", "/nextGroupMemberToRate/projects/?", [projectName], [], []);
    serverSide(dataReq, "GET", function (response) {
        userFeedbacked = response;
        // dynamic data
        $('#taskTemplate').tmpl(userFeedbacked).appendTo('#taskTemplateDiv');
    });
}
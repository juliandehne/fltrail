let projectId = "";
let userEmail;
let userFeedbacked;

$(document).ready(function () {
    Survey
        .StylesManager
        .applyTheme("default");

    projectId = getQueryVariable("projectName");
    // getting the survey items from the server
    let requ = new RequestObj(1, "/assessment", "/data/project/?", [projectId], []);
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
        let dataReq = new RequestObj(1, "/assessment", "/save/projects/?", [projectId, userFeedbacked], [], survey.data);
        serverSide(dataReq, "POST", function () {
            //log.warn(a);
            location.href = "../project/tasks-student.jsp?projectName=" + projectId;
        })
    }
});
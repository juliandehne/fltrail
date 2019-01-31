let projectId="";
$(document).ready(function () {
    Survey
        .StylesManager
        .applyTheme("default");


    let projq = new RequestObj(1, "/survey", "/project/name/?", ["FL"], []);
    serverSide(projq, "GET", function (response) {

        projectId = response.name;
        // getting the survey items from the server
        let requ = new RequestObj(1, "/survey", "/data/project/?", [projectId], []);
        serverSide(requ, "GET", function (surveyJSON) {
            let survey = new Survey.Model(surveyJSON);
            survey.locale = "en";


            $("#surveyContainer").Survey({
                model: survey,
                onComplete: sendDataToServer
            });
        });
    });

    function sendDataToServer(survey) {
        //var resultAsString = JSON.stringify(survey.data);
        //alert(resultAsString); //send Ajax request to your web server.

        let dataReq = new RequestObj(1, "/survey", "/save/projects/?", [projectId], [], survey.data);
        userEmail = survey.data.EMAIL1;
        serverSide(dataReq, "POST", function () {
            //log.warn(a);
            location.href = "../project/tasks-student.jsp?projectName="+projectId;
        })
    }
});
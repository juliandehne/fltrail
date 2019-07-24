let projectName = "";

$(document).ready(function () {
    Survey
        .StylesManager
        .applyTheme("default");

    projectName = getQueryVariable("projectName");

    // getting the survey items from the server
    let requ = new RequestObj(1, "/evaluation", "/sus/data/project/?", [projectName], []);
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
    let req = new RequestObj(1,"/evaluation" , "/sus/data/project/?/send", [projectName], [], survey.data );
    serverSide(req, "POST", function (response) {
        console.log(response);
    });
}


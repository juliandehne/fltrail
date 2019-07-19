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
    
}

function sendDataToserver(survey) {
    // TODO implement this next
}
$(document).ready(function () {
    // creating the survey element
    Survey
        .StylesManager
        .applyTheme("default");

    let language = getQueryVariable("language");

    // getting the survey items from the server
    let requ= new RequestObj(1, "/survey", "/data/project/?", ["d1_test"], []);
    serverSide(requ, "GET", function (surveyJSON) {
        var survey = new Survey.Model(surveyJSON);
        survey.locale = "en";
        if (language) {
            survey.locale = language;
        }

        $("#surveyContainer").Survey({
            model: survey,
            onComplete: sendDataToServer
        });
    });

    function sendDataToServer(survey) {
        var resultAsString = JSON.stringify(survey.data);
        alert(resultAsString); //send Ajax request to your web server.
    }

});

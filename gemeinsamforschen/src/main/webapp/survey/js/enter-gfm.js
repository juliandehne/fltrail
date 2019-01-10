$(document).ready(function () {
    // creating the survey element

    $("#messageHolder").hide();

    Survey
        .StylesManager
        .applyTheme("default");

    let language = getQueryVariable("language");
    let context = getQueryVariable("context");

    if (!language || !project){
        $("#messageHolder").show();
    }

    // TODO get projectname from backend based on context

    let projq = new RequestObj(1, "/survey", "/project/name/?", [context],[]);
    serverSide(projq, "GET", function (project) {
        // getting the survey items from the server
        let requ= new RequestObj(1, "/survey", "/data/project/?", [project], []);
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
    });



    function sendDataToServer(survey) {
        //var resultAsString = JSON.stringify(survey.data);
        //alert(resultAsString); //send Ajax request to your web server.

        let dataReq = new RequestObj(1, "/survey", "/save/projects/?", [project], [], survey.data);

        serverSide(dataReq, "POST", function (a) {
            //log.warn(a);
            // TODO display link for the groups that were created
        })
    }

});

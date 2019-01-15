let projectId = "";

$(document).ready(function () {
    // creating the survey element
    $('#navTextPage').on('click', function(){
        preparePageToggle();
        $('#theTextPage').toggleClass("in");
        $('#navLiTextPage').toggleClass("active");
        document.getElementById('navBtnPrev').className="page-item disabled";
    });
    $('#navGroupView').on('click',function(){
        preparePageToggle();
        $('#theGroupView').toggleClass("in");
        $('#navLiGroupView').toggleClass("active");
        document.getElementById("navBtnNext").className="page-item disabled";
    });
    $('#navSurvey').on('click',function(){
        preparePageToggle();
        $('#theSurvey').toggleClass("in");
        $('#navLiSurvey').toggleClass("active");
    });
    $('#btnPrev').on('click', function(){
        let activeDiv = $('.collapse.in')[0];
        preparePageToggle();
        if ($(activeDiv).attr("id")==="theGroupView"){
            $('#theSurvey').toggleClass("in");
            $('#navLiSurvey').toggleClass("active");
        }else{
            $('#theTextPage').toggleClass("in");
            $('#navLiTextPage').toggleClass("active");
            document.getElementById('navBtnPrev').className="page-item disabled";
        }
    });
    $('#btnNext').on('click',function(){
        let activeDiv = $('.collapse.in')[0];
        preparePageToggle();
        if ($(activeDiv).attr("id")==="theTextPage"){
            $('#theSurvey').toggleClass("in");
            $('#navLiSurvey').toggleClass("active");
        }else{
            $('#theGroupView').toggleClass("in");
            $('#navLiGroupView').toggleClass("active");
            document.getElementById("navBtnNext").className="page-item disabled";
        }
    });

    $("#messageHolder").hide();

    Survey
        .StylesManager
        .applyTheme("default");

    let language = getQueryVariable("language");
    let context = getQueryVariable("context");

    if (!language || !context){
        $("#messageHolder").show();
    }

    // TODO get projectname from backend based on context

    let projq = new RequestObj(1, "/survey", "/project/name/?", [context],[]);
    serverSide(projq, "GET", function (response) {

        projectId = response.name;
        // getting the survey items from the server
        let requ= new RequestObj(1, "/survey", "/data/project/?", [projectId], []);
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
            $("#resultLink").append("the groups can be viewed with group id " +  projectId);
        })
    }

});


function preparePageToggle(){
    let collapsable = $('.collapse');
    let navi = $('.page-item');
    collapsable.each(function(){
        this.className="collapse";
    });
    navi.each(function(){
        this.className="page-item";
    });
}

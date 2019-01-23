let projectId = "";

$(document).ready(function () {
    let messageHolder = $('#messageHolder');
    messageHolder.hide();
    $('#ifNoUserIsSet').hide();
    $('#ifUserIsSet').hide();
    $('#noGroupsYet').hide();
    $('#bisherKeineGruppen').hide();


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
        authenticate("", function(loggedin){
            if (!loggedin){
                $('#ifUserIsSet').hide();
                $('#ifNoUserIsSet').show();
            } else{
                $('#ifNoUserIsSet').hide();
                $('#ifUserIsSet').show();
                getAllGroups(function (allGroups) {
                    if(allGroups.length === 0){
                        if (language==="en"){
                            $('#noGroupsYet').show();
                        }else{
                            $('#bisherKeineGruppen').show();
                        }
                    }else{
                        groupsToTemplate(allGroups, function (done) {
                            selectableButtons(done);
                        });
                    }
                });
            }
        });
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
            authenticate("", function(loggedin){
                if (!loggedin){
                    $('#ifUserIsSet').hide();
                    $('#ifNoUserIsSet').show();
                } else{
                    $('#ifNoUserIsSet').hide();
                    $('#ifUserIsSet').show();
                    getAllGroups(function (allGroups) {
                        if(allGroups.length === 0){
                            if (language==="en"){
                                $('#noGroupsYet').show();
                            }else{
                                $('#bisherKeineGruppen').show();
                            }
                        }else{
                            groupsToTemplate(allGroups, function (done) {
                                selectableButtons(done);
                            });
                        }
                    });
                }
            });
            document.getElementById("navBtnNext").className="page-item disabled";
        }
    });

    $('#btnSetUserEmail').on('click',function(){
        let userEmail = $('#userEmailGroupView').val();
        authenticate(userEmail, function(){
            $('#ifNoUserIsSet').hide();
            $('#ifUserIsSet').show();
            getAllGroups(function (allGroups) {
                if(allGroups.length === 0){
                    if (language==="en"){
                        $('#noGroupsYet').show();
                    }else{
                        $('#bisherKeineGruppen').show();
                    }
                }else{
                    groupsToTemplate(allGroups, function (done) {
                        selectableButtons(done);
                    });
                }
            });
        });
    });


    Survey
        .StylesManager
        .applyTheme("default");

    let language = getQueryVariable("language");
    let context = getQueryVariable("context");

    if (!language || !context){
        messageHolder.show();
    }

    let projq = new RequestObj(1, "/survey", "/project/name/?", [context],[]);
    serverSide(projq, "GET", function (response) {

        projectId = response.name;
        // getting the survey items from the server
        let requ= new RequestObj(1, "/survey", "/data/project/?", [projectId], []);
        serverSide(requ, "GET", function (surveyJSON) {
            let survey = new Survey.Model(surveyJSON);
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

        let dataReq = new RequestObj(1, "/survey", "/save/projects/?", [projectId], [], survey.data);

        serverSide(dataReq, "POST", function () {
            //log.warn(a);
            $("#resultLink").append("the groups can be viewed on next page.");
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

function getAllGroups(callback) {
    $.ajax({
        url: "../rest/group/get/context/"+getQueryVariable("context"),
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (response) {
            callback(response.groups);
        },
        error: function (a) {
        }
    });
}

function groupsToTemplate(allGroups, callback) {
    let groupTmplObject = [];
    $('#groupsInProject').html("");
    for (let group = 0; group < allGroups.length; group++) {
        groupTmplObject.push({
            groupName: "group" + group,
            groupMember: allGroups[group].members,
            chatRoomId: allGroups[group].chatRoomId,
        });
    }
    $('#groupTemplate').tmpl(groupTmplObject).appendTo('#groupsInProject');
    let done = true;
    callback(done);
}

function selectableButtons(done) {
    if (done) {
        $(".student-button").click(function () {
            $(this).toggleClass('active');
        });
        $(".group-button").click(function () {
            $(".group-button.active").toggleClass('active');
            $(this).toggleClass('active');
        });
    }
}

function authenticate(userEmail, callback){
    $.ajax({
        url: "../rest/survey/user/"+userEmail,
        headers: {
            "Content-Type": "text/html",
            "Cache-Control": "no-cache"
        },
        type: 'POST',
        success: function (response) {
                        //Session.setAttribute(userEmail) happens on serverSide
            if (response==="userEmail set"){
                callback(response);
            }else{
                return callback(response === "userEmail set");
            }
        },
        error: function (a) {
        }
    });
}
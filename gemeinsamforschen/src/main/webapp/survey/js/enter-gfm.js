let projectId = "";

$(document).ready(function () {
    let messageHolder = $('#messageHolder');
    let language = getQueryVariable("language");
    let context = getQueryVariable("context");
    let userEmail = getQueryVariable("userEmail");

    if (!language || !context){
        messageHolder.show();
    }

    if (!language){
        language='en';
    }

    if (language==='en'){
        $('#theTextPageEn').toggleClass("in");
        $('#navGroupView').html("groups");
        $('#navTextPage').html("introduction");
        $('#navSurvey').html("survey");
    }else{
        if(language==='de'){
            $('#theTextPageGer').toggleClass("in");
            $('#navGroupView').html("Gruppen");
            $('#navTextPage').html("Einleitung");
            $('#navSurvey').html("Umfrage");
        }
    }

    if (userEmail){
        preparePageToggle();
        $('#naviPagi').hide();
        $('#theGroupView').toggleClass("in");
        authenticate(userEmail, function(){
            $('#ifNoUserIsSetGer').hide();
            $('#ifNoUserIsSetEn').hide();
            $('#ifUserIsSet').show();
            getAllGroups(function (allGroups) {
                if(allGroups.length === 0){
                    if (language==="en"){
                        $('#noGroupsYet').show();
                        $('#groupsHeadline').show();
                        $('#bisherKeineGruppen').hide();
                        $('#Gruppeneinteilung').hide();
                    }else{
                        $('#bisherKeineGruppen').show();
                        $('#Gruppeneinteilung').show();
                        $('#noGroupsYet').hide();
                        $('#groupsHeadline').hide();
                    }
                }else{
                    groupsToTemplate(allGroups, function (done) {
                        selectableButtons(done);
                    });
                }
            });
        });
    }


    messageHolder.hide();
    $('#ifNoUserIsSet').hide();
    $('#ifUserIsSet').hide();
    $('#noGroupsYet').hide();
    if (language==='en'){
        $('#groupsHeadline').show();
        $('#Gruppeneinteilung').hide();
    }else{
        if(language ==='de'){
            $('#groupsHeadline').hide();
            $('#Gruppeneinteilung').show();
        }
    }
    $('#bisherKeineGruppen').hide();


    // creating the survey element
    $('#navTextPage').on('click', function(){
        preparePageToggle();
        if(language==='en'){
            $('#theTextPageEn').toggleClass("in");
        }else{
            if(language==='de'){
                $('#theTextPageGer').toggleClass("in");
            }
        }
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
                if (language==='en'){
                    $('#ifNoUserIsSetEn').show();
                    $('#ifNoUserIsSetGer').hide();
                }else{
                    if(language==='de'){
                        $('#ifNoUserIsSetGer').show();
                        $('#ifNoUserIsSetEn').hide();
                    }
                }
            } else{
                $('#ifNoUserIsSetGer').hide();
                $('#ifNoUserIsSetEn').hide();
                $('#ifUserIsSet').show();
                getAllGroups(function (allGroups) {
                    if(allGroups.length === 0){
                        if (language==="en"){
                            $('#noGroupsYet').show();
                            $('#groupsHeadline').show();
                        }else{
                            $('#bisherKeineGruppen').show();
                            $('#Gruppeneinteilung').show();
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
            if(language==='en'){
                $('#theTextPageEn').toggleClass("in");
            }else{
                if(language==='de'){
                    $('#theTextPageGer').toggleClass("in");
                }
            }
            $('#navLiTextPage').toggleClass("active");
            document.getElementById('navBtnPrev').className="page-item disabled";
        }
    });
    $('#btnNext').on('click',function(){
        let activeDiv = $('.collapse.in')[0];
        preparePageToggle();
        if ($(activeDiv).attr("id")==="theTextPageGer" || $(activeDiv).attr("id")==="theTextPageEn"){
            $('#theSurvey').toggleClass("in");
            $('#navLiSurvey').toggleClass("active");
        }else{
            $('#theGroupView').toggleClass("in");
            $('#navLiGroupView').toggleClass("active");
            authenticate("", function(loggedin){
                if (!loggedin){
                    $('#ifUserIsSet').hide();
                    if (language==='en'){
                        $('#ifNoUserIsSetEn').show();
                        $('#ifNoUserIsSetGer').hide();
                    }else{
                        if(language==='de'){
                            $('#ifNoUserIsSetGer').show();
                            $('#ifNoUserIsSetEn').hide();
                        }
                    }
                    $('#ifNoUserIsSet').show();
                } else{
                    $('#ifNoUserIsSet').hide();
                    $('#ifUserIsSet').show();
                    getAllGroups(function (allGroups) {
                        if(allGroups.length === 0){
                            if (language==="en"){
                                $('#noGroupsYet').show();
                                $('#groupsHeadline').show();
                                $('#bisherKeineGruppen').hide();
                                $('#Gruppeneinteilung').hide();
                            }else{
                                $('#bisherKeineGruppen').show();
                                $('#Gruppeneinteilung').show();
                                $('#noGroupsYet').hide();
                                $('#groupsHeadline').hide();
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

    $('#btnSetUserEmailGer').on('click',function(){
        btnSetUserEmail();
    });
    $('#btnSetUserEmailEn').on('click',function(){
        btnSetUserEmail();
    });

    Survey
        .StylesManager
        .applyTheme("default");


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
        userEmail=survey.data.EMAIL1;
        serverSide(dataReq, "POST", function () {
            //log.warn(a);
            location.href=window.location.href +"&userEmail="+userEmail;//todo: get userEmail
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

function btnSetUserEmail(){
    let userEmail;
    if (language==='en'){
        userEmail = $('#userEmailGroupViewEn').val();
    }else{
        if(language==='de'){
            userEmail = $('#userEmailGroupViewGer').val();
        }
    }
    authenticate(userEmail, function(){
        $('#ifNoUserIsSet').hide();
        $('#ifUserIsSet').show();
        getAllGroups(function (allGroups) {
            if(allGroups.length === 0){
                if (language==="en"){
                    $('#noGroupsYet').show();
                    $('#groupsHeadline').show();
                    $('#bisherKeineGruppen').hide();
                    $('#Gruppeneinteilung').hide();
                }else{
                    $('#bisherKeineGruppen').show();
                    $('#Gruppeneinteilung').show();
                    $('#noGroupsYet').hide();
                    $('#groupsHeadline').hide();
                }
            }else{
                groupsToTemplate(allGroups, function (done) {
                    selectableButtons(done);
                });
            }
        });
    });
}
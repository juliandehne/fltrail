let projectId = "";
let language = "";
let userEmail;
$(document).ready(function () {
    authenticate("", function (loggedin) {
        if (loggedin) {
            $('#navLiSurvey').toggleClass("disabled");
            $('#navSurvey').toggleClass("disabled");
        }
    });

    // todo why do we need this?
    $('#naviPagi').show();

    let messageHolder = $('#messageHolder');
    language = getQueryVariable("language");
    let context = getQueryVariable("context");
    userEmail = getQueryVariable("userEmail");

    let navTextPage = $('#navTextPage');
    let navGroupView = $('#navGroupView');
    let navSurvey = $('#navSurvey');

    if (!context) {
        messageHolder.show();
        context = "fl_survey";
    }

    if (!language) {
        messageHolder.show();
        language = 'en';
    }

    if (language === 'en') {
        $('#theTextPageEn').toggleClass("in");
        navGroupView.html("groups");
        navTextPage.html("introduction");
        navSurvey.html("survey");
    } else {
        if (language === 'de') {
            $('#theTextPageGer').toggleClass("in");
            navGroupView.html("Gruppen");
            navTextPage.html("Einleitung");
            navSurvey.html("Umfrage");
        }
    }
    $('#buildGroupsLink').on('click', function () {
        location.href = "saveGroups.jsp?context=" + context + "&language=" + language;
    });
    if (userEmail) {
        let correctEmail = "";
        let backToChar = "";
        for (let i = 0; i < userEmail.length; i++) {
            if (userEmail[i] !== "-") {
                backToChar += userEmail[i];
            } else {
                correctEmail += String.fromCharCode(backToChar);
                backToChar = "";
            }
        }
        if (userEmail[userEmail.length - 1] !== "-") {
            correctEmail += String.fromCharCode(backToChar);
        }
        userEmail = correctEmail;
        preparePageToggle();
        //$('#naviPagi').hide();
        openGroupView(userEmail);
    }
    messageHolder.hide();
    $('#ifNoUserIsSet').hide();
    $('#ifUserIsSet').hide();
    $('#noGroupsYet').hide();
    if (language === 'en') {
        $('#groupsHeadline').show();
        $('#Gruppeneinteilung').hide();
    } else {
        if (language === 'de') {
            $('#groupsHeadline').hide();
            $('#Gruppeneinteilung').show();
        }
    }
    $('#bisherKeineGruppen').hide();


    // creating the survey element
    navTextPage.on('click', function () {
        preparePageToggle();
        if (language === 'en') {
            $('#theTextPageEn').toggleClass("in");
        } else {
            if (language === 'de') {
                $('#theTextPageGer').toggleClass("in");
            }
        }
        $('#navLiTextPage').toggleClass("active");
        document.getElementById('navBtnPrev').className = "page-item disabled";
    });
    navGroupView.on('click', function () {
        preparePageToggle();
        openGroupView("");
        document.getElementById("navBtnNext").className = "page-item disabled";
    });
    navSurvey.on('click', function () {
        preparePageToggle();
        $('#theSurvey').toggleClass("in");
        $('#navLiSurvey').toggleClass("active");
    });
    $('#btnPrev').on('click', function () {
        let activeDiv = $('.collapse.in')[0];
        preparePageToggle();
        if ($(activeDiv).attr("id") === "theGroupView") {
            $('#theSurvey').toggleClass("in");
            $('#navLiSurvey').toggleClass("active");
        } else {
            $('#theTextPage').toggleClass("in");
            if (language === 'en') {
                $('#theTextPageEn').toggleClass("in");
            } else {
                if (language === 'de') {
                    $('#theTextPageGer').toggleClass("in");
                }
            }
            $('#navLiTextPage').toggleClass("active");
            document.getElementById('navBtnPrev').className = "page-item disabled";
        }
    });
    $('#btnNext').on('click', function () {
        let activeDiv = $('.collapse.in')[0];
        preparePageToggle();
        if ($(activeDiv).attr("id") === "theTextPageGer" || $(activeDiv).attr("id") === "theTextPageEn") {
            $('#theSurvey').toggleClass("in");
            $('#navLiSurvey').toggleClass("active");
        } else {
            openGroupView("");
            document.getElementById("navBtnNext").className = "page-item disabled";
        }
    });

    $('#btnSetUserEmailGer').on('click', function () {
        btnSetUserEmail();
    });
    $('#btnSetUserEmailEn').on('click', function () {
        btnSetUserEmail();
    });

    Survey
        .StylesManager
        .applyTheme("default");

    let projq = new RequestObj(1, "/survey", "/project/name/?", [context], []);
    serverSide(projq, "GET", function (response) {

        projectId = response.name;
        // getting the survey items from the server
        let requ = new RequestObj(1, "/survey", "/data/project/?", [projectId], []);
        serverSide(requ, "GET", function (surveyJSON) {
            for (let page = 0; page < surveyJSON.pages.length; page++) {
                for (let question = 0; question < surveyJSON.pages[page].questions.length; question++) {
                    let questionText = surveyJSON.pages[page].questions[question].title[language];
                    if (questionText.includes('(optional)')) {
                        surveyJSON.pages[page].questions[question].isRequired = false;
                    }
                    if (questionText.includes('email') ||
                        questionText.includes('E-Mail')) {
                        surveyJSON.pages[page].questions[question].validators = [{
                            type: "email"
                        }]
                    }
                }
            }
            let survey = new Survey.Model(surveyJSON);
            survey.locale = "en";
            if (language) {
                survey.locale = language;
            }

            $("#surveyContainer").Survey({
                model: survey,
                onComplete: sendDataToServer,
                onValidateQuestion: validateEmails
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
            let asciiMail = "";
            for (let i = 0; i < userEmail.length; i++) {
                asciiMail += userEmail[i].charCodeAt(0) + "-";
            }
            asciiMail = asciiMail.substring(0, asciiMail.length - 1);
            location.href = window.location.href + "&userEmail=" + asciiMail;
        })
    }

});


function preparePageToggle() {
    let collapsable = $('.collapse');
    let navi = $('.page-item');
    collapsable.each(function () {
        this.className = "collapse";
    });
    navi.each(function () {
        this.className = "page-item";
    });
}

function getAllGroups(callback) {
    $.ajax({
        url: "../rest/group/get/context/" + getQueryVariable("context"),
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

function authenticate(userEmail, callback) {
    if (userEmail.trim() == "") {
        callback(false);
    } else {
        $.ajax({
            url: "../rest/survey/user/" + userEmail,
            headers: {
                "Content-Type": "text/html",
                "Cache-Control": "no-cache"
            },
            type: 'POST',
            success: function (response) {
                //Session.setAttribute(userEmail) happens on serverSide
                if (response === "userEmail set") {
                    callback(response);
                } else {
                    callback(response === "userEmail set");
                }
            },
            error: function (a) {
                console.log("user Email existiert nicht");
                callback(false);
            }
        });
    }
}

function btnSetUserEmail() {
    let userEmail;
    if (language === 'en') {
        userEmail = $('#userEmailGroupViewEn').val();
    } else {
        if (language === 'de') {
            userEmail = $('#userEmailGroupViewGer').val();
        }
    }
    authenticate(userEmail, function (loggedIn) {
        if (!loggedIn) {
            $("#emailDoesNotExistWarning").show();
        } else {
            $("#emailDoesNotExistWarning").hide();

            $('#ifNoUserIsSet').hide();
            $('#ifNoUserIsSetEn').hide();
            $('#ifUserIsSet').show();
            getAllGroups(function (allGroups) {
                if (allGroups.length === 0) {
                    if (language === "en") {
                        $('#noGroupsYet').show();
                        $('#groupsHeadline').show();
                        $('#bisherKeineGruppen').hide();
                        $('#Gruppeneinteilung').hide();
                    } else {
                        $('#bisherKeineGruppen').show();
                        $('#Gruppeneinteilung').show();
                        $('#noGroupsYet').hide();
                        $('#groupsHeadline').hide();
                    }
                } else {
                    groupsToTemplate(allGroups, function (done) {
                        selectableButtons(done);
                    });
                }
            });
        }
    });
}

function openGroupView(userEmail) {
    authenticate(userEmail, function (loggedin) {

        if (!loggedin) {
            $('#ifUserIsSet').hide();
            if (language === 'en') {
                $('#ifNoUserIsSetEn').show();
                $('#ifNoUserIsSetGer').hide();
            } else {
                if (language === 'de') {
                    $('#ifNoUserIsSetGer').show();
                    $('#ifNoUserIsSetEn').hide();
                }
            }
            $('#ifNoUserIsSet').show();
        } else {
            $('#ifNoUserIsSetEn').hide();
            $('#ifNoUserIsSetGer').hide();
            let clpText = document.getElementsByName('clpText');
            clpText[0].value = document.URL;
            clpText[1].value = document.URL;
            $('#ifNoUserIsSet').hide();
            $('#ifUserIsSet').show();
            $.ajax({
                url: "../rest/survey/participantCount/project/" + projectId,
                headers: {
                    "Content-Type": "text/html",
                    "Cache-Control": "no-cache"
                },
                type: 'GET',
                success: function (response) {
                    let participantsNeeded = 30 - response;
                    if (language === "en") {
                        $('#participantsMissing').html("They are still " + participantsNeeded + " participants missing to form groups.")
                    } else {
                        $('#teilnehmerFehlend').html("Es fehlen noch " + participantsNeeded + " Teilnehmer um die Gruppen zu bilden.")
                    }
                },
                error: function () {

                }
            });
            getAllGroups(function (allGroups) {
                if (allGroups.length === 0) {
                    if (language === "en") {
                        $('#noGroupsYet').show();
                        $('#groupsHeadline').show();
                        $('#bisherKeineGruppen').hide();
                        $('#Gruppeneinteilung').hide();
                    } else {
                        $('#bisherKeineGruppen').show();
                        $('#Gruppeneinteilung').show();
                        $('#noGroupsYet').hide();
                        $('#groupsHeadline').hide();
                    }
                } else {
                    groupsToTemplate(allGroups, function (done) {
                        selectableButtons(done);
                    });
                }
            });
        }
    });
}

function validateEmails(survey, options) {
    if (options.name === "EMAIL1") {
        userEmail = options.value;
        let context = getQueryVariable("context");
        checkExistence(userEmail, context, function (exists) {
            if (exists === "true") {
                options.error = "You already participated in a survey.";
            }
        });
    }
    if (options.name === "EMAIL2") {
        if (userEmail !== options.value) {
            options.error = "The Email is not the same as the first one.";
        }
    }
}

function checkExistence(userEmail, context, callback) {
    $.ajax({
        url: "../rest/survey/checkDoubleParticipation/user/" + userEmail,
        headers: {
            "Content-Type": "text/html",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        async: false,
        success: function (response) {
            callback(response);
        }
    });
}
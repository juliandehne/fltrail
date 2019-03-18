// context variables
let language = "";
let userEmail;
let context;
let projectName;
let alreadyParticipatedMessage;
let mailsDontMatch;


// ##############################################################################
// ####################### LOGIC ################################################
// ##############################################################################

$(document).ready(function () {

    // ############## Context ##################################
    language = getQueryVariable("language");
    context = getQueryVariable("context");
    let email = getQueryVariable("userEmail");
    if (email) {
        userEmail = convertEncodedEmail(email);
    }

    // set defaults
    if (!context) {
        context = "dota_test";
    }

    if (!language) {
        language = 'en';
    }

    // ############## Translations ###############################
    let welcomeObject;
    let naviObject;
    let groupViewLoginObject;
    let titleObject;


    if (language == 'en') {
        welcomeObject = [{
            welcomeTitle: welcomeTitleEN,
            welcomeText: welcomeTextEN
        }];
        naviObject = navEN;
        groupViewLoginObject = groupViewLoginEN;
        titleObject = computedGroupsEN;
        alreadyParticipatedMessage = alreadyParticipatedMessageEN;
        mailsDontMatch = mailsDontMatchEN;
    } else {
        welcomeObject = [{
            welcomeTitle: welcomeTitleDE,
            welcomeText: welcomeTextDE
        }];
        naviObject = navDE;
        groupViewLoginObject = groupViewLoginDE;
        titleObject = computedGroupsDE;
        alreadyParticipatedMessage = alreadyParticipatedMessageDE;
        mailsDontMatch = mailsDontMatchDE;
    }

    getProjectNameByContext(context, function (surveyName) {
        projectName = surveyName;

        // print templates
        printWelcomeText(welcomeObject);
        printNavi(naviObject);
        printGroupView(groupViewLoginObject, titleObject);

        // ############## Navigation ###############################
        // activate the group link
        $('#buildGroupsLink').on('click', function () {
            location.href = "saveGroups.jsp?context=" + context + "&language=" + language;
        });
        // the button for next page
        let navTextPage = $('#navTextPage');
        // the button for group view
        let navGroupView = $('#navGroupView');
        // the button for survey view
        let navSurvey = $('#navSurvey');

        navTextPage.on('click', showIntroduction);
        navGroupView.on('click', showGroupView);
        navSurvey.on('click', showSurveyView);

        $('#btnPrev').on('click', prevView);
        $('#btnNext').on('click', nextView);
        $("#logout").on('click', logout);


        // survey tab
        if (!disableSurveyTab()) {
            prepareSurvey();
        }
        prepareGroupTab();

        // ############## REDIRECTS #################################
        checkUserEmailForDirectLink();
    })
});


// ##############################################################################
// ####################### FUNCTIONS ############################################
// ##############################################################################


// template functions
function printWelcomeText(welcomeObject) {
    $('#welcomeTextTemplate').tmpl(welcomeObject).appendTo('#welcomeTextHolder');
}


function printNavi(naviObject) {
    $('#navigationTemplate').tmpl(naviObject).appendTo('#navigationTemplateHolder');
}

function printGroupView(groupViewLoginObject, titleObject) {
    $('#groupViewTemplate').tmpl(groupViewLoginObject).appendTo('#groupViewLoginTemplateHolder');
    $('#emailDoesNotExistWarning').hide();
    $('#titleTemplate').tmpl(titleObject).appendTo('#titleHolder');
    $('#titleHolder').hide();

    // paint the participant needed box
    getParticipantsNeeded1(function (projectStatus) {
        paintGroupsOrMessage(projectStatus);
    });
}

// navigation functions

function redirectToGroupView(email) {
    showGroupView()
}


function logout() {
    location.href = "./enterGFM.jsp?context=" + context + "&language=" + language;
}

function resetNaviSelections() {
    let collapsable = $('.collapse');
    let navi = $('.page-item');
    collapsable.each(function () {
        this.className = "collapse";
    });
    navi.each(function () {
        this.className = "page-item";
    });
    disableSurveyTab();
}

/**
 * displays the introduction
 */
function showIntroduction() {
    // make navigation toggle-able
    resetNaviSelections();
    $('#welcomeTextHolder').toggleClass("in");
    $('#navLiTextPage').toggleClass("active");
    $('#navBtnPrev').attr('class', 'page-item disabled');
}

/**
 * displays the groups tab
 */
function showGroupView() {
    // make navigation toggle-able
    resetNaviSelections();
    $('#theGroupView').toggleClass("in");
    $('#navLiGroupView').toggleClass("active");
    $('#navBtnNext').attr('class', 'page-item disabled');
}

// displays the survey tab
function showSurveyView() {
    // make navigation toggle-able
    resetNaviSelections();
    $('#theSurvey').toggleClass("in");
    $('#navLiSurvey').toggleClass("active");
}

function prevView() {
    let activeDiv = $('.collapse.in')[0];
    if ($(activeDiv).attr("id") === "theGroupView") {
        showSurveyView();
    } else {
        showIntroduction();
    }
}

function nextView() {
    let activeDiv = $('.collapse.in')[0];
    let navId = $(activeDiv).attr("id");
    if (navId === "theSurvey") {
        showGroupView();
    }
    if (navId === "welcomeTextHolder") {
        showSurveyView();
    }
}

function convertEncodedEmail(encodedEmail) {
    if (encodedEmail) {
        let correctEmail = "";
        let backToChar = "";
        for (let i = 0; i < encodedEmail.length; i++) {
            if (encodedEmail[i] !== "-") {
                backToChar += encodedEmail[i];
            } else {
                correctEmail += String.fromCharCode(backToChar);
                backToChar = "";
            }
        }
        if (encodedEmail[encodedEmail.length - 1] !== "-") {
            correctEmail += String.fromCharCode(backToChar);
        }
        return correctEmail;
    } else {
        return false;
    }
}

function checkUserEmailForDirectLink() {
    authenticate(userEmail, function (exists) {
        if (exists) {
            showGroupView();
        }
        showErrorMessageOrGroupView(exists);
    })
}

function disableSurveyTab() {
    if (userEmail) {
        //disable navigation to survey
        $('#navLiSurvey').attr('class', 'page-item disabled');
        $('#navSurvey').attr('class', 'page-item disabled');
        return true;
    } else {
        return false;
    }
}


// ################# Survey Functions #############################

function prepareSurvey() {

    $('#noSurveyContextMessage').hide();
    if (!context) {
        $('#noSurveyContextMessage').show();
    }
    Survey
        .StylesManager
        .applyTheme("default");

    getSurveyPages1(loadSurvey);
}


function loadSurvey(surveyJSON) {
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
}

function validateEmails(survey, options) {
    if (options.name === "EMAIL1") {
        userEmail = options.value;
        let context = getQueryVariable("context");
        checkExistence(userEmail, context, function (exists) {
            if (exists === "true") {
                options.error = alreadyParticipatedMessage;
            }
        });
    }
    if (options.name === "EMAIL2") {
        if (userEmail !== options.value) {
            options.error = mailsDontMatch;
        }
    }
}


// #################### Group Functions #############################

function paintGroupsOrMessage(projectStatus) {
    let groupsFormed = projectStatus.groupsFormed;
    let isAutomated = projectStatus.automated;
    if (groupsFormed) {
        getAllGroups(groupsToTemplate);
    } else {
        let noGroupsMessageObject;
        let participantsMissing = projectStatus.participantsNeeded - projectStatus.participants;
        if (language == "en") {
            noGroupsMessageObject = noGroupsMessageEN(participantsMissing);
        } else {
            noGroupsMessageObject = noGroupsMessageDE(participantsMissing);
        }
        if (!isAutomated) {
            noGroupsMessageObject[0].participantsMissing = "";
        }
        // render the message
        $('#noGroupTemplate').tmpl(noGroupsMessageObject).appendTo('#noGroupMessageHolder');

        let clpText = document.getElementById('clpText');
        clpText.value = document.URL;
        //clpText[1].value = document.URL;
    }
    if (isAutomated) {
        let isParticipantMissing = projectStatus.participantsNeeded > projectStatus.participants;
        let participantsMissing = projectStatus.participantsNeeded - projectStatus.participants;
        if (!isParticipantMissing) {
            getAllGroups(groupsToTemplate);
        } else {
            let noGroupsMessageObject;
            if (language == "en") {
                noGroupsMessageObject = noGroupsMessageEN(participantsMissing);
            } else {
                noGroupsMessageObject = noGroupsMessageDE(participantsMissing);
            }
            // if the context has manual group formation don't show the missing participants
            if (isParticipantMissing) {
                noGroupsMessageObject.participantsMissing = "";
            }
            // render the message
            $('#noGroupTemplate').tmpl(noGroupsMessageObject).appendTo('#noGroupMessageHolder');

            let clpText = document.getElementById('clpText');
            clpText.value = document.URL;
            //clpText[1].value = document.URL;
        }
    } else {
        if (groupsFormed) {
            getAllGroups(groupsToTemplate);
        }
    }

}

function groupsToTemplate(allGroups) {
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
}


function prepareGroupTab() {
    // only display email
    toggleLoginWithGroups();

    //if email is set in url
    if (userEmail) {
        authenticate(userEmail, function (exists) {
            showErrorMessageOrGroupView(exists);
        });
    }

    // if email is not set in url
    $('#btnSetUserEmail').on('click', function () {
        userEmail = $('#userEmailGroupView').val();
        authenticate(userEmail, function (exists) {
            showErrorMessageOrGroupView(exists);
        })
    })
}

/**
 * if use does not exist, show message
 * if he does toggle the login field and display groups
 * @param exists
 */
function showErrorMessageOrGroupView(exists = true) {
    if (!exists) {
        if (userEmail && userEmail.trim() !== "") {
            $('#emailDoesNotExistWarning').show();
        }
    } else {
        $('#emailDoesNotExistWarning').hide();
        toggleLoginWithGroups(false);
    }
}

/**
 * on the page either the login field or the group information
 * is displayed
 */
function toggleLoginWithGroups(loginActive = true) {
    if (loginActive) {
        $('#groupsOrNoParticipantsMessage').hide();
        $('#groupViewLoginTemplateHolder').show();
    } else {
        $('#groupsOrNoParticipantsMessage').show();
        $('#groupViewLoginTemplateHolder').hide();
        /*getParticipantsNeeded1(context, function (participantsNeededObj) {
            participantsMissing = participantsNeededObj.participantsNeeded - participantsNeededObj.participants;
            toggleGroup(participantsMissing <= 0);
        });*/
        getAllGroups(toggleGroup);
    }
}

/**
 * if the groups are calculated, they are displayed,
 * else: the current number of participants
 */
function toggleGroup(groups) {
    if (groups.length > 0) {
        $('#groupsInProject').show();
        $('#noGroupMessageHolder').hide();
    } else {
        $('#groupsInProject').hide();
        $('#noGroupMessageHolder').show();
    }
}
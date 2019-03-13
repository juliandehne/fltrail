// context variables
let language = "";
let userEmail;
let context;

// placeholders
let participantsMissing = 30;


// ##############################################################################
// ####################### LOGIC ################################################
// ##############################################################################

$(document).ready(function () {

    // ############## Context ##################################
    language = getQueryVariable("language");
    context = getQueryVariable("context");
    userEmail = getQueryVariable("userEmail");

    // ############## REDIRECTS #################################
    checkUserEmailForDirectLink(userEmail);

    // set defaults
    if (!context) {
        context = "fl_test";
    }

    if (!language) {
        language = 'en';
    }

    // ############## Translations ###############################
    let welcomeObject;
    let naviObject;
    let groupViewLoginObject;
    let titleObject;
    let noGroupObject;



    if (language == 'en') {
        welcomeObject = [{
            welcomeTitle: welcomeTitleEN,
            welcomeText: welcomeTextEN
        }];
        naviObject = navEN;
        groupViewLoginObject = groupViewLoginEN;
        titleObject = computedGroupsEN;
        noGroupObject = noGroupsMessageEN(participantsMissing);
    } else {
        welcomeObject = [{
            welcomeTitle: welcomeTitleDE,
            welcomeText: welcomeTextDE
        }];
        naviObject = navDE;
        groupViewLoginObject = groupViewLoginDE;
        titleObject = computedGroupsDE;
        noGroupObject = noGroupsMessageDE(participantsMissing);
    }

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


    // survey tab
    prepareSurvey();
    prepareGroupTab();

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

function printGroupView(groupViewLoginObject, titleObject){
    // calculate missing participants

    $('#groupViewTemplate').tmpl(groupViewLoginObject).appendTo('#groupViewLoginTemplateHolder');
    $('#emailDoesNotExistWarning').hide();
    $('#titleTemplate').tmpl(titleObject).appendTo('#titleHolder');
    $('#titleHolder').hide();
}

// navigation functions
function resetNaviSelections() {
    let collapsable = $('.collapse');
    let navi = $('.page-item');
    collapsable.each(function () {
        this.className = "collapse";
    });
    navi.each(function () {
        this.className = "page-item";
    });
}

/**
 * displays the introduction
 */
function showIntroduction() {
    // make navigation toggle-able
    resetNaviSelections();
    $('#welcomeTextHolder').toggleClass("in");
    $('#navLiTextPage').toggleClass("active");
    $('#navBtnPrev').attr('className', 'page-item disabled');
}

/**
 * displays the groups tab
 */
function showGroupView() {
    // make navigation toggle-able
    resetNaviSelections();
    $('#theGroupView').toggleClass("in");
    $('#navLiGroupView').toggleClass("active");
    $('#navBtnNext').attr('className', 'page-item disabled');
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
    preparePageToggle();
    if ($(activeDiv).attr("id") === "theTextPageGer" || $(activeDiv).attr("id") === "theTextPageEn") {
        $('#theSurvey').toggleClass("in");
        $('#navLiSurvey').toggleClass("active");
    } else {
        openGroupView("");
        document.getElementById("navBtnNext").className = "page-item disabled";
    }
}

function checkUserEmailForDirectLink(encodedEmail) {
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
        userEmail = correctEmail;
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

    getSurveyPages1(context, loadSurvey);
}

function loadSurvey(surveyJSON){
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


// #################### Group Functions #############################

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


function prepareGroupTab(){
    // only display email
    toggleLoginWithGroups();

    //if email is set in url
    if (userEmail){
        authenticate(userEmail, function (exists) {
            showErrorMessageOrGroupView(exists);
        });
    }

    // if email is not set in url
    $('#btnSetUserEmail').on('click', function(){
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
        $('#emailDoesNotExistWarning').show();
    } else {
        $('#emailDoesNotExistWarning').hide();
        toggleLoginWithGroups(false);
    }
}

/**
 * on the page either the login field or the group information
 * is displayed
 */
function toggleLoginWithGroups(loginActive = true){
    if (loginActive) {
        $('#groupsOrNoParticipantsMessage').hide();
        $('#groupViewLoginTemplateHolder').show();
    } else {
        $('#groupsOrNoParticipantsMessage').show();
        $('#groupViewLoginTemplateHolder').hide();
        getParticipantsNeeded1(context, function(participantsMissing){
            toggleGroup(participantsMissing===0);
        });
    }
}

/**
 * if the groups are calculated, they are displayed,
 * else: the current number of participants
 */
function toggleGroup(groupsAreCalculated = false) {
    if (groupsAreCalculated) {
        $('#groupsInProject').show();
        $('#noGroupMessageHolder').hide();
        getAllGroups(groupsToTemplate);
    } else {
        $('#groupsInProject').hide();
        $('#noGroupMessageHolder').show();
    }
}
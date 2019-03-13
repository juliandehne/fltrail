let language = "";
let userEmail;
let context;

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
        context = "fl_survey_test";
    }

    if (!language) {
        language = 'en';
    }

    // ############## Translations ###############################
    let welcomeObject;
    let naviObject;

    if (language == 'en') {
        welcomeObject = [{
            welcomeTitle: welcomeTitleEN,
            welcomeText: welcomeTextEN
        }]
        naviObject = navEN;
    } else {
        welcomeObject = [{
            welcomeTitle: welcomeTitleDE,
            welcomeText: welcomeTextDE
        }]
        naviObject = navDE;
    }

    // print templates
    printWelcomeText(welcomeObject);
    printNavi(naviObject);

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
    document.getElementById('navBtnPrev').className = "page-item disabled";
}

/**
 * displays the groups tab
 */
function showGroupView() {
    // make navigation toggle-able
    resetNaviSelections();
    $('#theGroupView').toggleClass("in");
    $('#navLiGroupView').toggleClass("active");
    openGroupView("");
    document.getElementById("navBtnNext").className = "page-item disabled";
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

function checkUserEmailForDirectLink(userEmail) {
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
        //$('#naviPagi').hide();
        openGroupView(userEmail);
    }
}


// ################# Survey Functions #############################

function prepareSurvey() {
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


// #################### Group Functions #############################

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

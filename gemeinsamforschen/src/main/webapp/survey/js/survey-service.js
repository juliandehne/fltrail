

// ############## survey functions ############################

/**
 * send the survey data to the server
 * @param survey
 */
function sendDataToServer(survey) {
    //var resultAsString = JSON.stringify(survey.data);
    //alert(resultAsString); //send Ajax request to your web server.
    let dataReq = new RequestObj(1, "/survey", "/save/projects/?/context/?", [projectName, context], [], survey.data);
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


function getSurveyPages1(callback) {
    let requ = new RequestObj(1, "/survey", "/data/project/?", [projectName], []);
    serverSide(requ, "GET", function (surveyJSON) {
        for (let page = 0; page < surveyJSON.pages.length; page++) {
            for (let question = 0; question < surveyJSON.pages[page].questions.length; question++) {
                let questionText = surveyJSON.pages[page].questions[question].title[language];
                if (questionText.includes('(optional)')) {
                    surveyJSON.pages[page].questions[question].isRequired = false;
                }
                if (questionText.toLowerCase().includes('email')||
                    questionText.toLowerCase().includes('e-mail')
                ) {
                    surveyJSON.pages[page].questions[question].validators = [{
                        type: "email"
                    }]
                }
            }
        }
        //
        surveyJSON.title.de = surveyJSTitleDE;
        surveyJSON.title.en = surveyJSTitleEN;

        callback(surveyJSON);
    });
}


// ###################### Group functions ##########################################

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

// fetches the initialized groups from the backend
function initializeOrGetGroups(projectName, callback) {
    $.ajax({
        url: "../rest/survey/projects/" + projectName + "/buildGroups",
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'POST',
        success: function (groups) {
            callback(groups);
        },
        error: function (a) {
            callback([]);
        }
    });
}



function getParticipantsNeeded1(callback){
    $.ajax({
        url: "../rest/survey/participantCountNeeded/project/" + projectName + "/context/"+context,
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (projectStatus) {
            callback(projectStatus);
        },
        error: function () {
            callback(false);
        }
    });

}

// ###################### User Management ############################################


function authenticate(userEmail, callback) {
    if (!userEmail || userEmail.trim() == "") {
        callback(false);
    } else {
        $.ajax({
            url: "../rest/survey/user/" + userEmail + "/context/"+context,
            headers: {
                "Content-Type": "text/html",
                "Cache-Control": "no-cache"
            },
            type: 'POST',
            success: function (response) {
                //Session.setAttribute(userEmail) happens on serverSide
                callback(response);
            },
            error: function (a) {
                console.log("user Email existiert nicht");
                callback(false);
            }
        });
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


// ########################### project functions #################################

function getProjectNameByContext(context, callback) {
    let tmpEmail="UNKNOWN";
    if (userEmail){
        tmpEmail=userEmail;
    }
    $.ajax({
        url: "../rest/survey/project/name/" + context+"/email/"+tmpEmail,
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (project) {
            callback(project);
        },
        error: function (a) {
        }
    });
}

function getContextType(callback) {
    $.ajax({
        url: "../rest/survey/context/"+context +"/isAutomated",
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (isAutomated) {
            callback(isAutomated);
        },
        error: function (a) {
        }
    });
}

/*
function groupsToTemplate(allGroups, callback) {
    let groupTmplObject = [];
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
*/


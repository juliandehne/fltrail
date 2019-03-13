

/**
 * send the survey data to the server
 * @param survey
 */
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


/**
 * get survey pages for context
 * @param context
 * @param callback
 */
function getSurveyPages1(context, callback) {
    let projq = new RequestObj(1, "/survey", "/project/name/?", [context], []);
    serverSide(projq, "GET", function (response) {
        projectId = response.name;
        getSurveyPages2(projectId, callback);
    });
}


function getSurveyPages2(projectId, callback) {
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
        callback(surveyJSON);
    });
}


// ###################### Group functions ##########################################

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


function getParticipantsNeeded1(context, callback) {
    let projq = new RequestObj(1, "/survey", "/project/name/?", [context], []);
    serverSide(projq, "GET", function (response) {
        projectId = response.name;
        getParticipantsNeeded2(projectId, callback);
    });
}

function getParticipantsNeeded2(projectId, callback){
    $.ajax({
        url: "../rest/survey/participantCount/project/" + projectId,
        headers: {
            "Content-Type": "text/html",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (numberOfParticipants) {
            let result = 0;
            if (30 - numberOfParticipants < 0) {
                participantsMissing = result;
            } else {
                participantsMissing = result;
            }
            callback(participantsMissing);
        },
        error: function () {
            participantsMissing = 0;
        }
    });

}

// ###################### User Management ############################################


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


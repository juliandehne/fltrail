$(document).ready(function () {
    $('#headLineProject').html($('#projectName').html());
    $('#taskCompleted').hide();
    $('#logout').click(function () {

        // logout rocket chat
        //Meteor.logout();
        //
        $.ajax({
            url: '../rest/logout/user',
            type: 'POST',
            headers: {
                "Content-Type": "application/json",
                "Cache-Control": "no-cache"
            },
            success: function () {
                let context = getQueryVariable("context");
                if (!context) {
                    let target = "index.jsp";
                    document.location = changeLocationTo(target);
                } else if (context !== "fl") {
                    //document.location.reload();
                    updateURLParameter(document.location.href, "userEmail", "");
                } else {
                    document.location.reload();
                }
            },
            error: function (a) {
                console.log(a);
            }
        });
    });

    $('#footerBack').click(function () {
        goBack();
    });
    $('#backToTasks').on('click', function () {
        changeLocation();
    });
    $('#readMe').on('click', function () {
        let whatRole = $('#isStudent').val();
        if (whatRole === "isDocent") {
            location.href = "../project/readMe-docent.jsp";
        } else {
            location.href = "../project/readMe-student.jsp";
        }
    });
    $('#readMeGruppenbildung').on('click', function () {
        let whatRole = $('#isStudent').val();
        if (whatRole === "isDocent") {
            location.href = "../project/readMe-docent.jsp#Gruppenbildung";
        } else {
            location.href = "../project/readMe-student.jsp#Gruppenbildung";
        }
    });
    $('#readMeEntwurf').on('click', function () {
        let whatRole = $('#isStudent').val();
        if (whatRole === "isDocent") {
            location.href = "../project/readMe-docent.jsp#Entwurf";
        } else {
            location.href = "../project/readMe-student.jsp#Entwurf";
        }
    });
    $('#readMeDurchfuhrung').on('click', function () {
        let whatRole = $('#isStudent').val();
        if (whatRole === "isDocent") {
            location.href = "../project/readMe-docent.jsp#Durchfuhrung";
        } else {
            location.href = "../project/readMe-student.jsp#Durchfuhrung";
        }
    });
    $('#readMeBewertung').on('click', function () {
        let whatRole = $('#isStudent').val();
        if (whatRole === "isDocent") {
            location.href = "../project/readMe-docent.jsp#Bewertung";
        } else {
            location.href = "../project/readMe-student.jsp#Bewertung";
        }
    });
    $('#readMeProjektabschluss').on('click', function () {
        let whatRole = $('#isStudent').val();
        if (whatRole === "isDocent") {
            location.href = "../project/readMe-docent.jsp#Projektabschluss";
        } else {
            location.href = "../project/readMe-student.jsp#Projektabschluss";
        }
    });
});


/**
 * http://stackoverflow.com/a/10997390/11236
 */
function updateURLParameter(url, param, paramVal) {
    let newAdditionalURL = "";
    let tempArray = url.split("?");
    let baseURL = tempArray[0];
    let additionalURL = tempArray[1];
    let temp = "";
    if (additionalURL) {
        tempArray = additionalURL.split("&");
        for (let i = 0; i < tempArray.length; i++) {
            if (tempArray[i].split('=')[0] != param) {
                newAdditionalURL += temp + tempArray[i];
                temp = "&";
            }
        }
    }

    let rows_txt = temp + "" + param + "=" + paramVal;
    return baseURL + "?" + newAdditionalURL + rows_txt;
}

function changeLocationTo(target) {
    let level = $('#hierarchyLevel').html().trim();
    return level + target;
}


function goBack() {
    window.history.back();
}

function getUserEmail() {
    return $('#userEmail').html().trim();
}


function getProjectName() {
    return getQueryVariable("projectName");
}

function getQueryVariable(variable) {
    let query = window.location.search.substring(1);
    let vars = query.split("&");
    for (let i = 0; i < vars.length; i++) {
        let pair = vars[i].split("=");
        if (pair[0] === variable) {
            return pair[1];
        }
    }
    return (false);
}


/**
 *
 * @param hierachyLevel the level the hierachy has
 * @param modulePath i.e. project or annotatoin
 * @param methodPath i.e. /create/{id} in this case /create/?/another/?
 * @param pathParams what is filling in for the ?
 * @param queryParams i.e. &projectName=something&submissionId=anotherthing
 * @param entity the json obj to send in put
 * @constructor
 */
function RequestObj(hierachyLevel, modulePath, methodPath, pathParams, queryParams, entity) {
    this.hierarchyLevel = hierachyLevel;
    this.modulePath = modulePath;
    this.methodPath = methodPath;
    this.pathParams = pathParams;
    this.queryParams = queryParams;
    this.entity = entity;
}


function serverSide(requestObj, method, callback) {
    serverSideWithType(requestObj, method, callback, 'application/json');
}

/**
 * send a request to the server
 * @param requestObj the data specific to the reqeuest
 * @param method GET, POST, DELETE or PUT
 * @param callback
 * @param contentType typically 'application/json'
 */
function serverSideWithType(requestObj, method, callback, contentType) {
    let relativPath = calculateHierachy(requestObj.hierarchyLevel);
    let methodPath = requestObj.methodPath;
    requestObj.pathParams.forEach(function (e) {
        methodPath = methodPath.replace("?", e);
    });

    let localurl = relativPath + "rest" + requestObj.modulePath + methodPath;

    if (requestObj.queryParams) {
        localurl = localurl + requestObj.queryParams;
    }


    if (method === "PUT") {
        $.ajax({
            url: localurl,
            contentType: contentType,
            type: 'PUT',
            data: JSON.stringify(requestObj.entity),
            success: function (response) {
                if (callback) {
                    callback(response);
                }
            },
            error: function (a) {
                console.log(a);
            }
        });
    }
    if (method === "POST" || method === "DELETE") {
        $.ajax({
            url: localurl,
            contentType: contentType,
            type: method,
            data: requestObj.entity == null ? {} : JSON.stringify(requestObj.entity),
            success: function (response) {
                if (callback) {
                    callback(response);
                }
            },
            error: function (a) {
                console.log(a);
            }
        });
    }
    if (method === "GET") {
        $.getJSON({
            url: localurl,
            contentType: contentType,
            type: 'GET',
            success: function (response) {
                if (callback) {
                    callback(response);
                }
            },
            error: function (a) {
                console.log(a);
            }
        });
    }
}

function clpSet() {
    let clpText = document.getElementById('clpText');
    clpText.select();
    document.execCommand('copy');
}


function getMyGroupId(callback) {
    let projectName = $('#projectName').html().trim();
    $.ajax({
        url: '../rest/group/get/groupId/projects/' + projectName,
        type: 'GET',
        headers: {
            "Cache-Control": "no-cache"
        },
        success: function (response) {
            groupId = response;
            callback(response);
        },
        error: function () {

        }
    })
}

function getFullSubmissionOfGroup(groupId, version) {
    let projectName = $('#projectName').html().trim();
    let fileRole = $('#fileRole').html().trim();
    $.ajax({
        url: '../rest/submissions/full/groupId/' + groupId +
            '/project/' + projectName +
            '/fileRole/' + fileRole.toUpperCase() +
            '?version=' + version,
        type: 'GET',
        headers: {
            "Cache-Control": "no-cache"
        },
        success: function (fullSubmission) {
            setQuillContentFromFullSubmission(fullSubmission);
            setHeader(fullSubmission.header);
            fullSubmissionId = fullSubmission.id;
        },
        error: function (a, b, c) {
            if (c === "Unauthorized") {
                let fullSubmission = a.responseJSON;
                setQuillContentFromFullSubmission(fullSubmission);
                setHeader(fullSubmission.header);
                $("main").addClass("block");
                $('#unauthorized').attr("hidden", false);
            }
        }
    })
}

function setQuillContentFromFullSubmission(fullSubmission) {
    let text = fullSubmission.text;
    let content = JSON.parse(text);
    quill.setContents(content);
   /* if (/^[\],:{}\s]*$/.test(text.replace(/\\["\\\/bfnrtu]/g, '@').
    replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']').
    replace(/(?:^|:|,)(?:\s*\[)+/g, ''))) {
        //the json is ok
        let content = JSON.parse(text);
    quill.setContents(content);
   /* }else{
        quill.setText(text);
    }*/

}

function calculateHierachy(level) {

    if (level === 0) {

        return "";

    } else {

        return calculateHierachy(level - 1) + "../";

    }
}

/**
 * Use this function when page has a div with ID "taskCompleted"
 */
function taskCompleted() {
    $('#taskCompleted').show();
    setTimeout(function () {
        changeLocation();
    }, 1000);
}

function changeLocation() {
    let whatRole = $('#isStudent').val();
    let currentProjectName = $('#projectName').html().trim();
    let fileRole = $('#fileRole');
    if (fileRole.length) {
        fileRole = fileRole.html().trim();
    } else {
        fileRole = "";
    }
    if (typeof fileRole !== "undefined" && fileRole.toUpperCase() === "PORTFOLIO_ENTRY") {
        if (whatRole === "isDocent") {
            if (personal) {
                location.href = `../portfolio/show-portfolio-docent.jsp?projectName=${currentProjectName}`
            } else {
                location.href = `../project/tasks-docent.jsp?projectName=${currentProjectName}`;
            }
        } else {
            if (personal) {
                location.href = `../portfolio/show-portfolio-student.jsp?projectName=${currentProjectName}`
            } else {
                location.href = `../project/tasks-student.jsp?projectName=${currentProjectName}`;
            }
        }
    } else {
        if (whatRole === "isDocent") {
            location.href = `../project/tasks-docent.jsp?projectName=${currentProjectName}`;
        } else {
            location.href = `../project/tasks-student.jsp?projectName=${currentProjectName}`;
        }
    }
}

function loaderStart() {
    document.getElementById('loader').className = "loader";
}

function loaderStop() {
    document.getElementById('loader').className = "loader-inactive";
}

String.prototype.hashCode = function () {
    let hash = 0;
    for (let i = 0; i < this.length; i++) {
        let character = this.charCodeAt(i);
        hash = ((hash << 5) - hash) + character;
        hash = hash & hash; // Convert to 32bit integer
    }
    return hash;
};

function setHeader(header) {
    let ownTitle = $('#ownTitle');
    if (ownTitle.is("input")) {
        ownTitle.val(header);
    } else {
        ownTitle.html(header);
    }
}

//expects a <main> in the html and a <div id="unauthorized"> with an explanation.
function handleLocker(taskName) {
    $.ajax({
        url: '../rest/fileStorage/isOccupied/project/' + $('#projectName').text().trim() + '/task/' + taskName,
        type: 'GET',
        headers: {
            "Cache-Control": "no-cache"
        },
        success: function () {

        },
        error: function (a, b, c) {
            if (c === "Unauthorized") {
                $("main").addClass("block");
                $('#unauthorized').attr("hidden", false);
            }
        }
    });
}
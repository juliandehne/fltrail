$(document).ready(function () {
    $('#headLineProject').html($('#projectName').html());
    $('#logout').click(function () {
        $.ajax({
            url: '../rest/logout/user',
            type: 'POST',
            headers: {
                "Content-Type": "application/json",
                "Cache-Control": "no-cache"
            },
            success:function(){
                let context = getQueryVariable("context");
                if (context!=="fl"){
                    //document.location.reload();
                    updateURLParameter(document.location.href, "userEmail", "");

                }else{
                    let target = "index.jsp";
                    document.location = changeLocationTo(target);
                }
            },
            error: function(a){
                console.log(a);
            }
        });
    });

    $('#assessment').click(function () {
        checkAssessementPhase();
    });
    $('#footerBack').click(function () {
        goBack();
    });
});




/**
 * http://stackoverflow.com/a/10997390/11236
 */
function updateURLParameter(url, param, paramVal){
    var newAdditionalURL = "";
    var tempArray = url.split("?");
    var baseURL = tempArray[0];
    var additionalURL = tempArray[1];
    var temp = "";
    if (additionalURL) {
        tempArray = additionalURL.split("&");
        for (var i=0; i<tempArray.length; i++){
            if(tempArray[i].split('=')[0] != param){
                newAdditionalURL += temp + tempArray[i];
                temp = "&";
            }
        }
    }

    var rows_txt = temp + "" + param + "=" + paramVal;
    return baseURL + "?" + newAdditionalURL + rows_txt;
}

function changeLocationTo(target) {
    let level = $('#hierarchyLevel').html().trim();
    return calculateHierachy(level) + target;
}


function goBack() {
    window.history.back();
}

function checkAssessementPhase() {
    let userName = $('#userEmail').html().trim();
    let projectName = $('#projectName').html().trim();
    $.ajax({
        url: '../rest/assessments/whatToRate/project/' + projectName + '/student/' + userName,
        type: 'GET',
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        success: function (phase) {
            switch (phase) {
                case "workRating": {
                    changeLocationTo("finalAssessment.jsp");
                    break;
                }
                case "quiz": {
                    changeLocationTo("take-quiz.jsp");
                    break;
                }
                case "contributionRating": {
                    changeLocationTo("rate-contribution.jsp");
                    break;
                }
                case "done": {
                    changeLocationTo("projects-student.jsp");
                    break;
                }
            }
        },
        error: function (a) {
        }
    });
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


function calculateHierachy(level) {

    if (level === 0) {

        return "";

    } else {

        return calculateHierachy(level - 1) + "../";

    }
}

function clpSet(){
    let clpText = document.getElementsByName('clpText');
    clpText[0].select();
    document.execCommand('copy');
}
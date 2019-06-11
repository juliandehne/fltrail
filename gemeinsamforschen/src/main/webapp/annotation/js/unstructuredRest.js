/**
 * POST: Save an full submission in the database
 *
 * @param fullSubmissionPostRequest The post request
 * @param responseHandler The response handler
 */

const baseUrl = "../rest/submissions/";

function createFullSubmission(fullSubmissionPostRequest, responseHandler) {
    let url = baseUrl + "full";
    let json = JSON.stringify(fullSubmissionPostRequest);
    $.ajax({
        url: url,
        type: "POST",
        data: json,
        contentType: "application/json",
        dataType: "json",
        success: function (response) {
            responseHandler(response);
        }
    });
}

/**
 * GET: Get a specific full submission for a given id
 *
 * @param id The id of the full submission
 * @param contributionCategory
 * @param responseHandler The response handler
 * @param errorHandler The error handler
 */
function getFullSubmission(id, responseHandler, errorHandler) {
    let url = baseUrl + "full/" + id;
    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        success: function (response) {
            // handle the response
            responseHandler(response);
        },
        error: function () {
            // handle the error
            errorHandler();
        }
    })
}

/**
 * POST: Save an submission part in the database
 *
 * @param submissionPartPostRequest The post request
 * @param responseHandler The response handler
 * @returns A promise object
 */
function createSubmissionPart(submissionPartPostRequest, responseHandler) {
    let url = baseUrl + "part/";
    let json = JSON.stringify(submissionPartPostRequest);
    return $.ajax({
        url: url,
        type: "POST",
        data: json,
        contentType: "application/json",
        dataType: "json",
        success: function (response) {
            responseHandler(response);
        },
        error: function (e) {
            console.log(e);
        }
    });
}

/**
 * GET: Get a specific submission part for a given full submission id and its category
 *
 * @param id The id of the full submission
 * @param category The category of the submission part
 * @param responseHandler The response handler
 * @param errorHandler The error handler
 */
function getSubmissionPart(id, category, responseHandler, errorHandler) {
    if (!category) {
        responseHandler(false);
        return false;
    }
    let url = baseUrl + "full/" + id + "/category/" + category;
    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        success: function (response) {
            // handle the response
            responseHandler(response);
        },
        error: function () {
            // handle the error
            errorHandler();
        }
    })
}

/**
 * GET: Get all submission parts for a given full submission id
 *
 * @param id The id of the full submission
 * @param responseHandler The response handler
 */
function getAllSubmissionParts(id, responseHandler) {
    let url = baseUrl + "full/" + id + "/parts";
    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        success: function (response) {
            // handle the response
            responseHandler(response);
        },
        error: function () {
            console.log("no parts");
        }
    })
}

function getAnnotationCategories(callback) {
    let url = "../rest/submissions/categories/project/" + $('#projectName').html().trim();
    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        success: function (response) {
            // handle the response
            callback(response);
        },
        error: function () {
            console.log("error loading annotation categories");
        }
    })
}

function getVisibilities(callback) {
    if (personal == null) {
        personal = false;
    }
    let url = baseUrl + "visibilities/personal/" + $('#personal').html().trim();
    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        success: function (response) {
            // handle the response
            callback(response);
        },
        error: function () {
            console.log("error loading visibilities");
        }
    })
}
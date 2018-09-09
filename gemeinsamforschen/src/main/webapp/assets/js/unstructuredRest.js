/**
 * POST: Save an full submission in the database
 *
 * @param fullSubmissionPostRequest The post request
 * @param responseHandler The response handler
 */
function createFullSubmission(fullSubmissionPostRequest, responseHandler) {
    var url = "../rest/submissions/full/";
    var json = JSON.stringify(fullSubmissionPostRequest);
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
 * @param responseHandler The response handler
 * @param errorHandler The error handler
 */
function getFullSubmission(id, responseHandler, errorHandler) {
    var url = "../rest/submissions/full/" + id;
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
    var url = "../rest/submissions/part/";
    var json = JSON.stringify(submissionPartPostRequest);
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
    var url = "../rest/submissions/full/" + id + "/category/" + category;
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
    var url = "../rest/submissions/full/" + id + "/parts";
    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        success: function (response) {
            // handle the response
            responseHandler(response);
        }
    })
}
/**
 * POST: Save an full submission in the database
 *
 * @param fullSubmissionPostRequest The post request
 * @param responseHandler The response handler
 */

const baseSubmissionUrl = "../rest/submissions/";

function createFullSubmission(fullSubmissionPostRequest, responseHandler) {
    let url = baseSubmissionUrl + "full";
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
 * POST: Save an full submission in the database
 *
 * @param fullSubmissionId Id of submission that is about to get updated
 * @param fullSubmissionPostRequest The post request
 * @param responseHandler The response handler
 * @param finalize If "true" group ends dossier reediting. While false, fullSubmission can be reedited
 */
function updateFullSubmission(fullSubmissionPostRequest, finalize, responseHandler) {
    let url = "../rest/submissions/full/update?finalize=" + finalize;
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
 * @param responseHandler The response handler
 * @param errorHandler The error handler
 */
function getFullSubmission(id, responseHandler, errorHandler) {
    let url = `../rest/submissions/full/${id}`;
    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        success: function (response) {
            // handle the response
            responseHandler(response);
        },
        error: function (a, b, c) {
            // handle the error
            if (c === "Unauthorized") {
                let fullSubmission = a.responseJSON;
                setQuillContentFromFullSubmission(fullSubmission);
                setHeader(fullSubmission.header);
                $("main").addClass("block");
                $('#unauthorized').attr("hidden", false);
            }
            if (errorHandler) {
                errorHandler();
            }
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
    let url = baseSubmissionUrl + "part/";
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
    let url = baseSubmissionUrl + "full/" + id + "/category/" + category;
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
    let url = baseSubmissionUrl + "full/" + id + "/parts";
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

function buildAnnotationList(categories) {
    let data = {categories: []};
    categories.forEach(function (category) {
        data.categories.push({name: category, nameLower: category.toLowerCase()})
    });
    let tmpl = $.templates("#annotationTemplate");
    let html = tmpl.render(data);
    $("#annotations").html(html);
}

function getVisibilities(personal, callback) {
    let url = baseSubmissionUrl + "visibilities/personal/" + personal;
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

function getPortfolioSubmissions(queryParams, callback) {
    Object.keys(queryParams).forEach(key => {
        if (queryParams[key] == null || queryParams === "") {
            delete queryParams[key];
        }
    });
    let url = baseSubmissionUrl + "portfolio?" + $.param(queryParams);

    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        success: function (response) {
            // handle the response
            callback(response);
        },
        error: function () {
            let response = [];
            callback(response);
        }
    })
}

function updatePortfolioSubmission(fullSubmissionPostRequest, callback) {
    let url = `../rest/submissions/portfolio/${fullSubmissionPostRequest.id}`;
    let data = JSON.stringify(fullSubmissionPostRequest);
    $.ajax({
        url: url,
        type: "PUT",
        data: data,
        contentType: "application/json",
        success: function (response) {
            // handle the response
            callback(response);
        },
        error: function (response) {
            console.error(`Error while updating portfolio entry! ${response}`);
        }
    })
}
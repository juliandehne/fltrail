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
 */
function getAnnotation(id, responseHandler) {
    var url = "../rest/submissions/full/" + id;
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
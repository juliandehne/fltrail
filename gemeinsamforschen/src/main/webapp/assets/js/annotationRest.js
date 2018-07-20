/**
 * POST: Save an annotation in the database
 *
 * @param annotationPostRequest The post request
 * @param responseHandler The response handler
 */
function createAnnotation(annotationPostRequest, responseHandler) {
    var url = "../rest/annotations/";
    var json = JSON.stringify(annotationPostRequest);
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
 * PATCH: Alter an annotation in database
 *
 * @param id The annotation id
 * @param annotationPatchRequest The patch request
 * @param responseHandler The response handler
 */
function alterAnnotation(id, annotationPatchRequest, responseHandler) {
    var url = "../rest/annotations/" + id;
    var json = JSON.stringify(annotationPatchRequest);
    $.ajax({
        url: url,
        type: "PATCH",
        data: json,
        contentType: "application/json",
        dataType: "json",
        success: function (response) {
            responseHandler(response);
        }
    });
}

/**
 * DELETE: Delete an annotation from database
 *
 * @param id The annotation id
 */
function deleteAnnotation(id, responseHandler) {
    var url = "../rest/annotations/" + id;
    $.ajax({
        url: url,
        type: "DELETE",
        dataType: "json",
        success: function (response) {
            responseHandler(response)
        }
    });
}

/**
 * GET: Get all annotations from database for a specific target
 *
 *
 * @param targetId The target id
 * @param responseHandler The response handler
 */
function getAnnotations(targetId, responseHandler) {
    var url = "../rest/annotations/target/" + targetId;
    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        success: function (response) {
            // sort the responding annotations by timestamp (DESC)
            response.sort(function (a, b) {
                return a.timestamp - b.timestamp;
            });
            // handle the response
            responseHandler(response);
        }
    });
}
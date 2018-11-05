/**
 * POST: Save an annotation in the database
 *
 * @param annotationPostRequest The post request
 * @param responseHandler The response handler
 */
function createAnnotation(annotationPostRequest, responseHandler) {
    let url = "../rest/annotations/";
    let json = JSON.stringify(annotationPostRequest);
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
    let url = "../rest/annotations/" + id;
    let json = JSON.stringify(annotationPatchRequest);
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
    let url = "../rest/annotations/" + id;
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
 * GET: Get a specific annotation for a given id
 *
 * @param id The id of the annotation
 * @param responseHandler The response handler
 */
function getAnnotation(id, responseHandler) {
    let url = "../rest/annotations/" + id;
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

/**
 * GET: Get all annotations from database for a specific target
 *
 * @param targetId The target id
 * @param targetCategory The category of the target
 * @param responseHandler The response handler
 */
function getAnnotations(targetId, targetCategory, responseHandler) {
    let url = "../rest/annotations/targetid/" + targetId + "/targetcategory/" + targetCategory;
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

function finalize(){
    $.ajax({
        url: "../rest/annotations/finalize/projectName/" +
            ""+getProjectName()+"/taskName/GIVE_FEEDBACK",
        type: "GET",
        dataType: "application/json",
        contentType: "application/json",
        success:function(response){
            location.href = "../project/tasks-student.jsp?projectName=" + getProjectName()
        },
        error: function(a){
        }
    });
}
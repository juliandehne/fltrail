const baseUrl = "../rest/contributionfeedback";

/**
 * POST: Save an annotation in the database
 *
 * @param contributionFeedback
 * @param responseHandler The response handler
 */
function createContributionFeedback(contributionFeedback, responseHandler) {
    let url = baseUrl + "/fullSubmissionId/" + contributionFeedback.fullSubmissionId;
    let json = JSON.stringify(contributionFeedback);
    $.ajax({
        url: url,
        type: "POST",
        data: json,
        contentType: "application/json",
        dataType: "json",
        success: function (response) {
            responseHandler(response);
        },
        error: function () {
            console.error("Error while creating contributionFeedback");
        }
    });
}

/**
 * PATCH: Alter an annotation in database
 *
 * @param id The annotation id
 * @param contributionFeedback
 * @param responseHandler The response handler
 */
function updateContributionFeedback(id, contributionFeedback, responseHandler) {
    let contributionFeedbackRequest = $.extend(true, {}, contributionFeedback);
    contributionFeedbackRequest.text = JSON.stringify(contributionFeedbackRequest.text);
    let url = baseUrl + "/" + id;
    let json = JSON.stringify(contributionFeedbackRequest);
    $.ajax({
        url: url,
        type: "PUT",
        data: json,
        contentType: "application/json",
        dataType: "json",
        success: function (response) {
            responseHandler(response);
        }
    });
}

/**
 * GET: Get a specific annotation for a given id
 *
 * @param id The id of the annotation
 * @param responseHandler The response handler
 */
function getContributionFeedbackById(id, responseHandler) {
    let url = baseUrl + "/" + id;
    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        success: function (response) {
            response.text = JSON.parse(response.text);
            // handle the response
            responseHandler(response);
        }
    })
}

/**
 * GET: Get all annotations from database for a specific target
 *
 * @param fullSubmissionId
 * @param fullSubmissionPartCategory
 * @param groupId
 * @param responseHandler The response handler
 */
function getContributionFeedback(fullSubmissionId, fullSubmissionPartCategory, groupId, responseHandler) {
    let url = baseUrl + '/id/' + fullSubmissionId + '/category/' + fullSubmissionPartCategory;
    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        success: function (response) {
            if (response) {
                response.text = JSON.parse(response.text);
                responseHandler(response);
            }
        }
    });
}

async function getAllContributionFeedback(fullSubmissionId) {
    let url = baseUrl + '/fullSubmissionId/' + fullSubmissionId;
    let contributionFeedbacks;
    try {
        contributionFeedbacks = await $.ajax({
            url: url,
            type: "GET",
            dataType: "json",
        });
    } catch (error) {
    }
    return contributionFeedbacks;

}


function finalize() {
    getMyGroupId(function (groupId) {
        let projectName = decodeURI(getProjectName());
        let requObj = new RequestObj(1, "/contributionfeedback", "/finalize/projects/?/groups/?", [projectName, groupId], [])
        serverSide(requObj, "POST", function () {
            location.href = "../project/tasks-student.jsp?projectName=" + getProjectName()
        })
    });
}

function getFeedbackedGroup(responseHandler) {
    $.ajax({
        url: "../rest/contributionfeedback/feedbackTarget/projectName/" + $('#projectName').html().trim(),
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (response) {
            responseHandler(response);
        }
    });
}
$(document).ready(function () {
    // fetch all submission part project representations from database
    getSubmissionPartsByProjectId(getQueryVariable("projectId"), function (response) {

        // iterate over response and display each element
        for (let i = 0; i < response.length; i++) {
            displaySubmission(response[i].user, response[i].category, response[i].fullSubmissionId);
        }

        // add click listener to feedback buttons
        $('.annotationview').click(function () {
            let fullSubmissionId = $(this).closest("li").data("fullSubmissionId");
            let category = $(this).closest("li").data("category");
            location.href = "annotation/annotation-document.jsp?token=" + getUserTokenFromUrl() +
                "&projectId=" + getQueryVariable("projectId") +
                "&fullSubmissionId=" + fullSubmissionId +
                "&category=" + category;
        });

    }, function () {
        // display empty view
        displayEmptyView()
    });

    /*
    var memberTable = $('#myGroupMembers');
    memberTable.hide();
    $('#nextPhase').on('click',function(){
        memberTable.show();
    });
    */
    $('.givefeedback').click(function () {
        location.href = "feedback/give-feedback.jsp?token=" + getUserTokenFromUrl();
    });
    $('.viewfeedback').click(function () {
        location.href = "feedback/view-feedback.jsp?token=" + getUserTokenFromUrl();
    });

    $('.annotationview').click(function () {
        location.href = "annotation/annotation-document.jsp?token=" + getUserTokenFromUrl();
    });

    $('.viewprojectstudent').click(function () {
        location.href = "project-student.jsp?token=" + getUserTokenFromUrl();
    })
});

/**
 * Display category of submission part in list
 *
 * @param user The user of the submission part
 * @param category The category of the submission part
 * @param fullSubmissionId The id of the full submission the submission part belongs to
 */
function displaySubmission(user, category, fullSubmissionId) {
    // build link
    $('#submissionUpload').append(
        $('<li>')
            .append($('<span>').append(category.toUpperCase() + " eingereicht"))
            .append($('<a>').attr("class", "annotationview").attr("role", "button")
                .append($('<label>').css("font-size", "10px")
                    .append($('<i>').attr("class", "far fa-comments").css("font-size", "15px"))
                    .append("feedback")
                )
            )
            // add data to link
            .data("fullSubmissionId", fullSubmissionId)
            .data("category", category)
    );

}

/**
 * Display a not found message if there are no submission parts in the database (or on error)
 */
function displayEmptyView() {
    // build link
    $('#submissionUpload').append(
        $('<li>')
            .append($('<span>').append("keine Daten gefunden"))
    );
}

/**
 * GET: Get all representations of a submission part for a given project id
 *
 * @param projectId The id of the project
 * @param responseHandler The response handler
 * @param errorHandler The error handler
 */
function getSubmissionPartsByProjectId(projectId, responseHandler, errorHandler) {
    let url = "rest/submissions/project/" + projectId;
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
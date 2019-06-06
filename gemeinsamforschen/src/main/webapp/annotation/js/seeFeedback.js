let categories = [];


$(document).ready(function () {
    getAnnotationCategories(function (response) {
        categories = response;
    });
    let fullSubmissionId = getQueryVariable("fullSubmissionId");
    let category = getQueryVariable("category");
    $('#categoryHeadline').html("Kategorie: " + category);
    let contribution = getQueryVariable("contribution");
    $('.contributionCategory').each(function () {
        $(this).html(contribution);
    });
    prepareFeedbackMenu(category);
    getFeedbackFor(fullSubmissionId, category);
    let btnBack = $('#btnBack');
    btnBack.click(handleBackButtonClick);
    if (category.toUpperCase() === "TITEL") {
        //btnBack.css('visibility', 'hidden');
        btnBack.hide()
    }

    let btnContinue = $('#btnContinue');
    btnContinue.click(handleContinueButtonClick);


    let btnFinalize = $('#finalize');
    btnFinalize.hide();
    btnFinalize.on("click", function () {
        location.href = "../project/tasks-student.jsp?projectName=" + $('#projectName').html().trim();
    });
    if (category.toUpperCase() === "AUSWERTUNG") {
        btnFinalize.show();
        btnContinue.hide();
    }
});

function getFeedbackFor(fullSubmissionId, category) {   //if groupId == 0, you get the feedback for this category
    getContributionFeedback(fullSubmissionId, category, 0, function (response) {
        contributionFeedback = response;
        quillFeedback.setContents(contributionFeedback.text);
    });
}

function handleBackButtonClick() {
    let submissionId = getQueryVariable("fullSubmissionId");
    let category = getQueryVariable("category");
    let nextCategory = calculateLastCategory(category);

    if (!nextCategory) {

    } else {
        location.href = "../annotation/see-feedback.jsp?" +
            "projectName=" + getProjectName() +
            "&fullSubmissionId=" + submissionId +
            "&category=" + nextCategory +
            "&contribution=" + getQueryVariable("contribution");
    }
}

function handleContinueButtonClick() {
    let submissionId = getQueryVariable("fullSubmissionId");
    let category = getQueryVariable("category");
    let nextCategory = calculateNextCategory(category);

    if (!nextCategory) {

    } else {
        location.href = "../annotation/see-feedback.jsp?" +
            "projectName=" + getProjectName() +
            "&fullSubmissionId=" + submissionId +
            "&category=" + nextCategory +
            "&contribution=" + getQueryVariable("contribution");
    }
}
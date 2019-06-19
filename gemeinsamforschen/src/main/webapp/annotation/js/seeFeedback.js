let categories = [];


$(document).ready(function () {
    getAnnotationCategories(function (response) {
        categories = response;
        if (category.toUpperCase() === categories[categories.length - 1]) {
            btnFinalize.show();
            btnContinue.hide();
        }
        if (category.toUpperCase() === categories[0]) {
            //btnBack.css('visibility', 'hidden');
            btnBack.hide()
        }
    });
    let fullSubmissionId = getQueryVariable("fullSubmissionId");
    let category = getQueryVariable("category");
    $('#categoryHeadline').html("Kategorie: " + category);
    let contribution = getQueryVariable("contribution");
    $('.fileRole').each(function () {
        $(this).html(contribution[0] + contribution.substring(1, contribution.length).toLowerCase());
    });
    prepareFeedbackMenu(category);
    getFeedbackFor(fullSubmissionId, category);
    let btnBack = $('#btnBack');
    btnBack.click(handleBackButtonClick);

    let btnContinue = $('#btnContinue');
    btnContinue.click(handleContinueButtonClick);


    let btnFinalize = $('#finalize');
    btnFinalize.hide();
    btnFinalize.on("click", function () {
        location.href = "../project/tasks-student.jsp?projectName=" + $('#projectName').html().trim();
    });
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
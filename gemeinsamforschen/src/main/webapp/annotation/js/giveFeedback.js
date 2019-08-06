let categories = [];
/**
 * This function will fire when the DOM is ready
 */
let contributionFeedback = undefined;

$(document).ready(function () {
    handleLocker("GIVE_FEEDBACK");
    let btnBack = $('#btnBack');
    btnBack.click(handleBackButtonClick);

    let btnContinue = $('#btnContinue');
    btnContinue.click(handleContinueButtonClick);
    getAnnotationCategories(function (response) {
        categories = response;
        let btnContinueBot = $('#btnContinueBot');
        btnContinueBot.click(handleContinueButtonClick);
        if (category.toUpperCase() === categories[categories.length - 1].toUpperCase()) {
            btnFinalize.show();
            btnContinue.hide();
            btnContinueBot.on("click", function () {
                saveContributionFeedback(function () {
                    finalize();
                });
            });
        }
        if (category.toUpperCase() === categories[0]) {
            //btnBack.css('visibility', 'hidden');
            btnBack.hide()
        }
    });

    let fullSubmissionId = getQueryVariable("fullSubmissionId");
    let category = getQueryVariable("category");
    $('#categoryHeadline').html(category);
    /*getFeedbackedGroup(function (response) {
    });*/
    prepareFeedbackMenu(category);

    let btnFinalize = $('#finalize');
    btnFinalize.hide();
    btnFinalize.on("click", function () {
        saveContributionFeedback(function () {
            finalize();
        });
    });

    addExistingContributionFeedback(fullSubmissionId, category);
    // connect to websocket on page ready
    connect(fullSubmissionId, category);
    quillFeedback.focus();
});

function addExistingContributionFeedback(fullSubmissionId, category) {
    getMyGroupId(function (groupId) {
        getContributionFeedback(fullSubmissionId, category, groupId, function (response) {
            contributionFeedback = response;
            quillFeedback.setContents(contributionFeedback.text);
        });
    });
}


function saveContributionFeedback(callback) {

    // initialize target
    let fullSubmissionId = getQueryVariable("fullSubmissionId");
    let fullSubmissionPartCategory = getQueryVariable("category").toUpperCase();
    let content = quillFeedback.getContents();

    if (contributionFeedback) {
        let contributionFeedbackRequest = contributionFeedback;
        contributionFeedbackRequest.text = content;
        updateContributionFeedback(contributionFeedbackRequest.id, contributionFeedbackRequest, function () {
            callback();
        });
    } else {
        getMyGroupId(function (groupId) {
            let contributionFeedbackRequest = {
                userEmail: $('#userEmail').html().trim(),
                groupId: groupId,
                fullSubmissionId: fullSubmissionId,
                fullSubmissionPartCategory: fullSubmissionPartCategory,
                text: JSON.stringify(content),
            };

            // send new annotation to back-end and display it in list
            createContributionFeedback(contributionFeedbackRequest, function (response) {
                // send new annotation to websocket
                //send("CREATE", response.id);
                console.log(response);
                callback();
            });
        });
    }
}


function handleContinueButtonClick() {
    let submissionId = getQueryVariable("fullSubmissionId");
    let category = getQueryVariable("category");
    let nextCategory = calculateNextCategory(category);

    if (nextCategory) {
        saveContributionFeedback(function () {
            location.href = "../annotation/give-feedback.jsp?" +
                "projectName=" + getProjectName() +
                "&fullSubmissionId=" + submissionId +
                "&category=" + nextCategory;
        });
    }
}


function handleBackButtonClick() {
    let submissionId = getQueryVariable("fullSubmissionId");
    let category = getQueryVariable("category");
    let nextCategory = calculateLastCategory(category);

    if (!nextCategory) {

    } else {
        saveContributionFeedback(function () {
            location.href = "../annotation/give-feedback.jsp?" +
                "projectName=" + getProjectName() +
                "&fullSubmissionId=" + submissionId +
                "&category=" + nextCategory;
        });
    }
}
String.prototype.hashCode = function () {
    let hash = 0, i, chr;
    if (this.length === 0) return hash;
    for (i = 0; i < this.length; i++) {
        chr = this.charCodeAt(i);
        hash = ((hash << 5) - hash) + chr;
        hash |= 0; // Convert to 32bit integer
    }
    return hash;
};
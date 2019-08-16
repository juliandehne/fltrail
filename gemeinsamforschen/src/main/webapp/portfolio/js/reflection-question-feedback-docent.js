let projectName;
let currentVisibleButtonText;
let possibleVisibleButtons = [];
let sortedPortfolioEntries = [];
let templateData = {};
let userEmail;

$(document).ready(function () {
    projectName = $('#projectName').html().trim();
    userEmail = $('#userEmail').html().trim();
    templateData.pageTitle = "Feedback zu Antworten auf Reflexionsfragen";
    setupVisibilityButton();
});

function setupVisibilityButton() {
    getAnsweredReflectionQuestions(projectName, async function (reflectionQuestionsWithAnswers) {
        if (reflectionQuestionsWithAnswers.length === 0) {
            templateData.possibleButtons = [];
            renderTemplate(templateData);
        } else {
            let fullSubmissionList = convertObjectsToFullSubmissions(reflectionQuestionsWithAnswers);
            fillLocalSubmissionStorage(fullSubmissionList);
            templateData.possibleButtons = createButtonData();
            await fillSubmissions(templateData);
        }
    });
}

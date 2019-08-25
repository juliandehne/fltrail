let projectName;
let userEmail;
let currentPortfolioEntries;
let currentPortfolioTemplateData;
let quillNewComment;
let lastActiveReflectionQuestionIndex = -1;

$(document).ready(async function () {
    projectName = $('#projectName').html().trim();
    userEmail = $('#userEmail').html().trim();
    await fillPortfolioEntriesAndFeedback();
});

async function fillPortfolioEntriesAndFeedback() {
    getAnsweredReflectionQuestions(projectName, async function (response) {
        currentPortfolioEntries = [];
        let fullSubmissions = [];
        if (response.length !== 0) {
            fullSubmissions = convertObjectsToFullSubmissions(response);
        } else {
            fullSubmissions.error = true;
        }
        await fillTemplateData(fullSubmissions);
    });
}

async function fillTemplateData(fullSubmissions) {
    let data = {};
    for (let fullSubmission of fullSubmissions) {
        fillWithExtraTemplateData(fullSubmission, 0, userEmail, false);
        await addContributionFeedback(fullSubmission, 0);
        currentPortfolioEntries.push(fullSubmission);
    }
    data.submissionList = currentPortfolioEntries;
    data.error = fullSubmissions.error;
    fillWithTemplateMetadata(data);
    currentPortfolioTemplateData = data;
    renderPortfolioContent(data);
}


function renderPortfolioContent(data) {
    let tmpl = $.templates("#portfolioTemplate");
    let html = tmpl.render(data);
    $("#portfolioTemplateResult").html(html);
}

function saveComment(index) {
    let contents = quillNewComment[index].getContents();
    let fullSubmissionId = currentPortfolioEntries[index].id;
    if (lastActiveReflectionQuestionIndex !== -1) {
        currentPortfolioEntries[lastActiveReflectionQuestionIndex].active = false;
        lastActiveReflectionQuestionIndex = index;
    }
    currentPortfolioEntries[index].active = true;
    getMyGroupId(function (groupId) {
        let contributionFeedbackRequest = {
            userEmail: userEmail,
            fullSubmissionId: fullSubmissionId,
            text: JSON.stringify(contents),
            groupId: groupId
        };
        createContributionFeedback(contributionFeedbackRequest, async function () {
            currentPortfolioEntries[index].contributionFeedback = await getContributionFeedbackFromSubmission(fullSubmissionId);
            renderPortfolioContent(currentPortfolioTemplateData);
        });

    });
}
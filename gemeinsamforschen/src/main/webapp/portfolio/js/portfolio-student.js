let projectName;
let possibleVisibilities = [];
let currentVisibleButton;
let userEmail;
let currentPortfolioEntries;
let currentTemplateData;
let quillNewComment;

$(document).ready(async function () {
    projectName = $('#projectName').html().trim();
    userEmail = $('#userEmail').html().trim();
    setupVisibilityButton();
});

function setupVisibilityButton() {
    getVisibilities(true, async function (response) {
        Object.entries(response).forEach(([name, buttonText]) => {
            possibleVisibilities[name] = {name: name, buttonText: buttonText};
        });
        currentVisibleButton = possibleVisibilities['PERSONAL'];
        let data = {};
        data.possibleVisibilities = Object.values(possibleVisibilities);
        data.currentVisibility = currentVisibleButton;
        let tmpl = $.templates("#visibilityTemplate");
        let html = tmpl.render(data);
        $("#visibilityTemplateResult").html(html);
        await fillPortfolioEntriesAndFeedback();
    });
}

async function fillPortfolioEntriesAndFeedback() {
    let queryParams = {
        projectName: projectName,
        visibility: currentVisibleButton.name
    };
    getPortfolioSubmissions(queryParams, function (response) {
        currentPortfolioEntries = [];
        let data = {};
        getMyGroupId(async function (groupId) {
            for (let fullSubmission of response) {
                fillWithExtraTemplateData(fullSubmission, groupId, userEmail);
                await addContributionFeedback(fullSubmission, groupId);
                currentPortfolioEntries[fullSubmission.id] = fullSubmission;
            }
            data.submissionList = Object.values(currentPortfolioEntries);
            data.error = response.error;
            currentTemplateData = data;
            renderPortfolioContent(data);
        });
    });
}


function renderPortfolioContent(data) {
    let tmpl = $.templates("#portfolioTemplate");
    let html = tmpl.render(data);
    $("#portfolioTemplateResult").html(html);
}

function visibilityButtonPressed(pressedButton) {
    changeButtonText(pressedButton, fillPortfolioEntriesAndFeedback);
}

function changeButtonText(clickedItem, callback) {
    let dropBtn = $('.dropbtn');
    let oldText = dropBtn.html();
    let oldVisibility = currentVisibleButton;
    currentVisibleButton = possibleVisibilities[clickedItem];
    let newText = oldText.replace(oldVisibility.buttonText, currentVisibleButton.buttonText);
    dropBtn.html(newText);
    if (callback) {
        callback();
    }
}

function clickedWantToComment(fullSubmissionId) {
    currentPortfolioEntries[fullSubmissionId].wantToComment = true;
    renderPortfolioContent(currentTemplateData);
}

function saveComment(fullSubmissionId) {
    let contents = quillNewComment.getContents();
    getMyGroupId(function (groupId) {
        let contributionFeedbackRequest = {
            userEmail: userEmail,
            fullSubmissionId: fullSubmissionId,
            text: JSON.stringify(contents),
            groupId: groupId
        };
        createContributionFeedback(contributionFeedbackRequest, async function () {
            currentPortfolioEntries[fullSubmissionId].wantToComment = false;
            currentPortfolioEntries[fullSubmissionId].contributionFeedback = await getContributionFeedbackFromSubmission(fullSubmissionId);
            renderPortfolioContent(currentTemplateData);
        });

    });
}

function clickedCreatePrivatePortfolio() {
    location.href = `../annotation/upload-unstructured-dossier.jsp?projectName=${projectName}&fileRole=Portfolio_Entry&personal=true`;
}
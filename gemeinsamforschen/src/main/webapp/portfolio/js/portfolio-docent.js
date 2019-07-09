let projectName;
let currentVisibleButtonText;
let possibleVisibleButtons = [];
let sortedPortfolioEntries = [];
let templateData = {};
let userEmail;
$(document).ready(function () {
    projectName = $('#projectName').html().trim();
    userEmail = $('#userEmail').html().trim();
    setupVisibilityButton();
});

function setupVisibilityButton() {
    let queryParams = {
        projectName: projectName,
        visibility: 'DOCENT'
    };
    getPortfolioSubmissions(queryParams, async function (response) {
        if (response.error) {
            templateData.possibleButtons = response;
            renderTemplate(templateData);
        } else {
            fillLocalPortfolioStorage(response);
            templateData.possibleButtons = createButtonData();
            await fillPortfolioEntries(templateData);
        }
    });
}

function fillLocalPortfolioStorage(fullSubmissions) {
    for (let element of fullSubmissions) {
        let key = element.userEmail ? 'Student: ' + element.userEmail : 'Gruppe: ' + element.groupId;
        if (!sortedPortfolioEntries[key]) {
            sortedPortfolioEntries[key] = [];
        }
        sortedPortfolioEntries[key][element.id] = element;
    }
}

function createButtonData() {
    possibleVisibleButtons = Object.keys(sortedPortfolioEntries);
    currentVisibleButtonText = possibleVisibleButtons[0];
    templateData.currentVisibleButtonText = currentVisibleButtonText;
    let buttonData = [];
    for (name of possibleVisibleButtons) {
        buttonData.push({name: name});
    }
    return buttonData;
}

async function fillPortfolioEntries(templateData) {
    templateData.currentVisibleButtonText = currentVisibleButtonText;

    let portfolioEntries = Object.values(sortedPortfolioEntries[currentVisibleButtonText]);
    for (let fullSubmission of portfolioEntries) {
        fillWithExtraTemplateData(fullSubmission, 0, userEmail);
        await addContributionFeedback(fullSubmission, 0);

    }
    templateData.submissionList = portfolioEntries;
    renderTemplate(templateData);
}

function renderTemplate(templateTempData) {
    let tmpl = $.templates("#portfolioTemplate");
    let html = tmpl.render(templateTempData);
    $("#portfolioTemplateResult").html(html);
}


async function visibilityButtonPressed(pressedButton) {
    await changeButtonText(pressedButton);
    await fillPortfolioEntries(templateData)
}

function clickedWantToComment(fullSubmissionId) {
    let portfolioEntries = sortedPortfolioEntries[currentVisibleButtonText];
    portfolioEntries[fullSubmissionId].wantToComment = true;
    sortedPortfolioEntries[currentVisibleButtonText] = portfolioEntries;
    templateData.submissionList = Object.values(portfolioEntries);
    renderTemplate(templateData);
}

function saveComment(fullSubmissionId) {
    let contents = quillNewComment.getContents();

    let contributionFeedbackRequest = {
        userEmail: userEmail,
        fullSubmissionId: fullSubmissionId,
        text: JSON.stringify(contents),
        groupId: 0
    };
    createContributionFeedback(contributionFeedbackRequest, async function () {
        let portfolioEntry = sortedPortfolioEntries[currentVisibleButtonText][fullSubmissionId];
        portfolioEntry.wantToComment = false;
        portfolioEntry.contributionFeedback = await getContributionFeedbackFromSubmission(fullSubmissionId);
        sortedPortfolioEntries[currentVisibleButtonText][fullSubmissionId] = portfolioEntry;
        templateData.submissionList = Object.values(sortedPortfolioEntries[currentVisibleButtonText]);
        renderTemplate(templateData);
    });

}

async function changeButtonText(clickedItem) {
    let dropBtn = $('.dropbtn');
    let oldText = dropBtn.html();
    let oldButtonText = currentVisibleButtonText;
    currentVisibleButtonText = clickedItem;
    let newText = oldText.replace(oldButtonText, currentVisibleButtonText);
    dropBtn.html(newText);
}
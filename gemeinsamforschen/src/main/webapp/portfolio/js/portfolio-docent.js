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
    let queryParamsDocent = {
        projectName: projectName,
        visibility: 'DOCENT'
    };
    let queryParamsPublic = {
        projectName: projectName,
        visibility: 'PUBLIC'
    }
    getPortfolioSubmissions(queryParamsDocent, async function (portfolioEntriesDocent) {
        getPortfolioSubmissions(queryParamsPublic, async function (portfolioEntriesPublic) {
            if (portfolioEntriesDocent.length === 0 && portfolioEntriesPublic.length === 0) {
                templateData.possibleButtons = [];
                renderTemplate(templateData);
            } else {
                let allSubmissions = portfolioEntriesDocent.concat(portfolioEntriesPublic);
                fillLocalPortfolioStorage(allSubmissions);
                templateData.possibleButtons = createButtonData();
                await fillPortfolioEntries(templateData);
            }
        })

    });
}

function fillLocalPortfolioStorage(fullSubmissions) {
    for (let element of fullSubmissions) {
        let key = element.userEmail ? 'Student: ' + element.userEmail : 'Gruppe: ' + element.groupId;
        if (!sortedPortfolioEntries[key]) {
            sortedPortfolioEntries[key] = [];
        }
        sortedPortfolioEntries[key].push(element);
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
    fillWithTemplateMetadata(templateData);
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

function saveComment(index) {
    let contents = quillNewComment[index].getContents();
    let fullSubmissionId = sortedPortfolioEntries[currentVisibleButtonText][index].id;
    let contributionFeedbackRequest = {
        userEmail: userEmail,
        fullSubmissionId: fullSubmissionId,
        text: JSON.stringify(contents),
        groupId: 0
    };
    createContributionFeedback(contributionFeedbackRequest, async function () {
        let portfolioEntry = sortedPortfolioEntries[currentVisibleButtonText][index];
        portfolioEntry.wantToComment = false;
        portfolioEntry.contributionFeedback = await getContributionFeedbackFromSubmission(fullSubmissionId);
        sortedPortfolioEntries[currentVisibleButtonText][index] = portfolioEntry;
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
let lastActiveEntryIndex = -1;

function fillLocalSubmissionStorage(fullSubmissions) {
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

async function fillSubmissions(templateData) {
    templateData.currentVisibleButtonText = currentVisibleButtonText;

    let portfolioEntries = Object.values(sortedPortfolioEntries[currentVisibleButtonText]);
    for (let fullSubmission of portfolioEntries) {
        fillWithExtraTemplateData(fullSubmission, 0, userEmail, false);
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
    await fillSubmissions(templateData)
}

function saveComment(index) {
    // noinspection JSUnresolvedFunction
    let contents = quillNewComment[index].getContents();
    if (lastActiveEntryIndex !== -1) {
        sortedPortfolioEntries[currentVisibleButtonText][lastActiveEntryIndex].active = false;
        lastActiveEntryIndex = index;
    }
    sortedPortfolioEntries[currentVisibleButtonText][index].active = true;
    let fullSubmissionId = sortedPortfolioEntries[currentVisibleButtonText][index].id;
    let contributionFeedbackRequest = {
        userEmail: userEmail,
        fullSubmissionId: fullSubmissionId,
        text: JSON.stringify(contents),
        groupId: 0
    };
    createContributionFeedback(contributionFeedbackRequest, async function () {
        let portfolioEntry = sortedPortfolioEntries[currentVisibleButtonText][index];
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
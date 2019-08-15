let projectName;
let userEmail;
let currentPortfolioEntries;
let currentPortfolioTemplateData;
let quillNewComment;
let visibilityButtonTemplateData = {};
const nameForAllEntries = "KEIN FILTER";

$(document).ready(async function () {
    projectName = $('#projectName').html().trim();
    userEmail = $('#userEmail').html().trim();
    setupVisibilityButton();
});

function setupVisibilityButton() {
    getVisibilities(true, async function (response) {

        let possibleVisibilities = [];
        possibleVisibilities.push({name: nameForAllEntries, buttonText: "Alle Einträge"});
        Object.entries(response).forEach(([name, buttonText]) => {
            possibleVisibilities.push({name: name, buttonText: buttonText});
        });

        visibilityButtonTemplateData.possibleVisibilities = possibleVisibilities;
        visibilityButtonTemplateData.currentVisibility = possibleVisibilities[0];
        let tmpl = $.templates("#visibilityTemplate");
        let html = tmpl.render(visibilityButtonTemplateData);
        $("#visibilityTemplateResult").html(html);
        await fillPortfolioEntriesAndFeedback();
    });
}

async function fillPortfolioEntriesAndFeedback() {
    let visibility = visibilityButtonTemplateData.currentVisibility.name;
    let queryParams = {
        projectName: projectName,
        visibility: visibility === nameForAllEntries ? null : visibility
    };
    getPortfolioSubmissions(queryParams, function (response) {
        currentPortfolioEntries = [];
        let data = {};
        getMyGroupId(async function (groupId) {
            for (let fullSubmission of response) {
                fillWithExtraTemplateData(fullSubmission, groupId, userEmail, true);
                await addContributionFeedback(fullSubmission, groupId);
                currentPortfolioEntries.push(fullSubmission);
            }
            data.submissionList = currentPortfolioEntries;
            data.error = response.error;
            fillWithTemplateMetadata(data);
            currentPortfolioTemplateData = data;
            renderPortfolioContent(data);
        });
    });
}


function renderPortfolioContent(data) {
    let tmpl = $.templates("#portfolioTemplate");
    let html = tmpl.render(data);
    $("#portfolioTemplateResult").html(html);
}

function visibilityButtonPressed(index) {
    changeButtonText(index, fillPortfolioEntriesAndFeedback);
}

function changeButtonText(index, callback) {
    let dropBtn = $('.dropbtn');
    let oldText = dropBtn.html();
    let oldVisibility = visibilityButtonTemplateData.currentVisibility;
    visibilityButtonTemplateData.currentVisibility = visibilityButtonTemplateData.possibleVisibilities[index];
    let newText = oldText.replace(oldVisibility.buttonText, visibilityButtonTemplateData.currentVisibility.buttonText);
    dropBtn.html(newText);
    if (callback) {
        callback();
    }
}

function clickedWantToComment(index) {
    currentPortfolioEntries[index].wantToComment = true;
    renderPortfolioContent(currentPortfolioTemplateData);
}

function saveComment(index) {
    let contents = quillNewComment[index].getContents();
    let fullSubmissionId = currentPortfolioEntries[index].id;
    getMyGroupId(function (groupId) {
        let contributionFeedbackRequest = {
            userEmail: userEmail,
            fullSubmissionId: fullSubmissionId,
            text: JSON.stringify(contents),
            groupId: groupId
        };
        createContributionFeedback(contributionFeedbackRequest, async function () {
            currentPortfolioEntries[index].wantToComment = false;
            currentPortfolioEntries[index].contributionFeedback = await getContributionFeedbackFromSubmission(fullSubmissionId);
            renderPortfolioContent(currentPortfolioTemplateData);
        });

    });
}

function clickedCreatePrivatePortfolio() {
    location.href = `../annotation/upload-unstructured-dossier.jsp?projectName=${projectName}&fileRole=Portfolio_Entry&personal=true`;
}

function editButtonPressed(index) {
    let fullSubmissionId = currentPortfolioEntries[index].id;
    location.href = `../annotation/upload-unstructured-dossier.jsp?projectName=${projectName}&fullSubmissionId=${fullSubmissionId}&fileRole=Portfolio_Entry&personal=true`
}
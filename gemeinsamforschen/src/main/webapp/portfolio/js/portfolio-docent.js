let projectName;
let currentVisibleButtonText;
let possibleVisibleButtons = [];
let sortedPortfolioEntries = [];
let templateData = {};
let userEmail;

$(document).ready(function () {
    projectName = $('#projectName').html().trim();
    userEmail = $('#userEmail').html().trim();
    templateData.pageTitle = "E-Portfolio";
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
    };
    getPortfolioSubmissions(queryParamsDocent, async function (portfolioEntriesDocent) {
        getPortfolioSubmissions(queryParamsPublic, async function (portfolioEntriesPublic) {
            if (portfolioEntriesDocent.length === 0 && portfolioEntriesPublic.length === 0) {
                templateData.possibleButtons = [];
                renderTemplate(templateData);
            } else {
                let allSubmissions = portfolioEntriesDocent.concat(portfolioEntriesPublic);
                fillLocalSubmissionStorage(allSubmissions);
                templateData.possibleButtons = createButtonData();
                await fillSubmissions(templateData);
            }
        })

    });
}
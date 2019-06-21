let projectName;
let possibleVisibilities = [];
let currentVisibleButton;
let userEmail;
$(document).ready(function () {
    projectName = $('#projectName').html().trim();
    userEmail = $('#userEmail').html().trim();
    setupVisibilityButton();
});

function setupVisibilityButton() {
    getVisibilities(true, function (response) {
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
        fillPortfolioEntries();
    });
}

function fillPortfolioEntries() {
    let queryParams = {
        projectName: projectName,
        visibility: currentVisibleButton.name
    };
    getPortfolioSubmissions(queryParams, function (response) {
        let data = {};
        data.scriptBegin = '<script>';
        data.scriptEnd = '</script>';
        getMyGroupId(function (groupId) {
            for (let element of response) {
                element.scriptBegin = data.scriptBegin;
                element.scriptEnd = data.scriptEnd;
                element.timestampDateTimeFormat = new Date(element.timestamp).toLocaleString();
                element.editable = element.userEmail === userEmail || element.userEmail == null && element.groupId === groupId;
            }
            data.submissionList = response;
            data.error = response.error;
            let tmpl = $.templates("#portfolioTemplate");
            let html = tmpl.render(data);
            $("#portfolioTemplateResult").html(html);

        });
    });
}

function visibilityButtonPressed(pressedButton) {
    changeButtonText(pressedButton, fillPortfolioEntries);
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

function clickedCreatePrivatePortfolio() {
    location.href = `../annotation/upload-unstructured-dossier.jsp?projectName=${projectName}&fileRole=Portfolio&personal=true`;
}
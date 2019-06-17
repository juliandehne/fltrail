let projectName;
let currentVisibleButtonText;
let possibleVisibleButtons = [];
let sortedPortfolioEntries = [];
let templateData = {};
$(document).ready(function () {
    projectName = $('#projectName').html().trim();
    setupVisibilityButton();
});

function setupVisibilityButton() {
    let queryParams = {
        projectName: projectName,
        visibility: 'DOCENT'
    };
    getPortfolioSubmissions(queryParams, function (response) {
        if (response.error) {
            templateData.possibleButtons = response;
        } else {
            for (let element of response) {
                let key = element.userEmail ? 'Student: ' + element.userEmail : 'Gruppe: ' + element.groupId;
                if (!sortedPortfolioEntries[key]) {
                    sortedPortfolioEntries[key] = [];
                }
                sortedPortfolioEntries[key].push(element)
            }
            possibleVisibleButtons = Object.keys(sortedPortfolioEntries);
            currentVisibleButtonText = possibleVisibleButtons[0];
            let buttonData = [];
            for (name of possibleVisibleButtons) {
                buttonData.push({name: name});
            }
            templateData.possibleButtons = buttonData;
            fillPortfolioEntries();
        }
    });
}

function fillPortfolioEntries() {
    templateData.currentVisibleButtonText = currentVisibleButtonText;

    let portfolioEntries = sortedPortfolioEntries[currentVisibleButtonText];
    for (let element of portfolioEntries) {
        element.scriptBegin = '<script>';
        element.scriptEnd = '</script>';

        if (element.userEmail) {
            element.creator = element.userEmail;
        } else {
            element.creator = "Gruppe " + element.groupId;
        }
        element.timestampDateTimeFormat = new Date(element.timestamp).toLocaleString();
    }
    templateData.submissionList = portfolioEntries;
    let tmpl = $.templates("#portfolioTemplate");
    let html = tmpl.render(templateData);
    $("#portfolioTemplateResult").html(html);


}

function visibilityButtonPressed(pressedButton) {
    changeButtonText(pressedButton, fillPortfolioEntries);

}

function changeButtonText(clickedItem, callback) {
    let dropBtn = $('.dropbtn');
    let oldText = dropBtn.html();
    let oldButtonText = currentVisibleButtonText;
    currentVisibleButtonText = clickedItem;
    let newText = oldText.replace(oldButtonText, currentVisibleButtonText);
    dropBtn.html(newText);
    if (callback) {
        callback();
    }
}
let projectName;
let possibleVisibilities = [];
let currentVisibility;
$(document).ready(function () {
    projectName = $('#projectName').html().trim();
    setupVisibilityButton();
});

function setupVisibilityButton() {
    getVisibilities(true, function (response) {
        Object.entries(response).forEach(([name, buttonText]) => {
            possibleVisibilities[name] = {name: name, buttonText: buttonText};
        });
        currentVisibility = possibleVisibilities['PERSONAL'];
        let data = {};
        data.possibleVisibilities = Object.values(possibleVisibilities);
        data.currentVisibility = currentVisibility;
        let tmpl = $.templates("#visibilityTemplate");
        let html = tmpl.render(data);
        $("#visibilityTemplateResult").html(html);
        fillPortfolioEntries();
    });
}

function fillPortfolioEntries() {
    let queryParams = {
        projectName: projectName,
        visibility: currentVisibility.name
    };
    getPortfolioSubmissions(queryParams, function (response) {
        let data = {};

        data.scriptBegin = '<script>';
        data.scriptEnd = '</script>';
        for (let element of response) {
            element.scriptBegin = data.scriptBegin;
            element.scriptEnd = data.scriptEnd;
            element.timestamp = new Date(element.timestamp).toLocaleString();
        }
        data.submissionList = response;
        data.error = response.error;
        let tmpl = $.templates("#portfolioTemplate");
        let html = tmpl.render(data);
        $("#portfolioTemplateResult").html(html);

    });
}

function visibilityButtonPressed(pressedButton) {
    changeButtonText(pressedButton, fillPortfolioEntries);

}
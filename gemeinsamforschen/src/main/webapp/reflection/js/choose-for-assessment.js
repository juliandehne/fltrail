let templateData = {};

$(document).ready(function () {
    getGroupPortfolioEntries(function (response) {
        templateData.data = response;
        templateData.extraData = {
            scriptBegin: "<script>",
            scriptEnd: "</script>",
            buttonText: response.length === 0 ? "Überspringen" : "Speichern"
        };
        let tmpl = $.templates("#assessmentTemplate");
        let html = tmpl.render(templateData);
        $("#assessmentTemplateResult").html(html);
    })
});

function clickItem(entryIndex) {
    $(`#list-item-${entryIndex}`).toggleClass('active');
    templateData.data[entryIndex].chosen = true;
}

function save() {
    let chosenPortfolioEntries = [];
    templateData.data.forEach(function (portfolioEntry) {
        if (portfolioEntry.chosen) {
            let copy = $.extend({}, portfolioEntry);
            delete copy.chosen;
            chosenPortfolioEntries.push(copy);
        }
    });

    if (chosenPortfolioEntries.length > 0 || chosenPortfolioEntries.length === 0 && templateData.data.length === 0) {
        selectGroupPortfolioEntries(chosenPortfolioEntries, function () {
            changeLocation();
        });
    } else {
        alert('Sie müssen mindestens eine Reflexionsfrage zur Bewertung abgeben.')
    }
}
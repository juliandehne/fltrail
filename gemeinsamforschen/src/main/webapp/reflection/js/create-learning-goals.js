let learningGoals = [];
let reflectionQuestions = [];
let selectedReflectionQuestions = [];
let modalTemplateData = {};

$(document).ready(function () {
    projectName = $('#projectName').html().trim();
    resetModalTemplateData();
    $('#myModal').on('hide.bs.modal', resetModalTemplateData);
    renderSiteTemplate();
    //setupLearningGoalButton();
});

function resetModalTemplateData() {
    modalTemplateData = {};
}

function setupModal() {
    modalTemplateData.step = 1;

    getLearningGoalsFromStore(function (learningGoalsResponse) {
        modalTemplateData.learningGoals = learningGoalsResponse.sort(sortAlphabetically);
        modalTemplateData.stepTitle = "Lernziel auswählen";
        getExisistingLearningGoals(projectName, function (existingLearningGoals) {
            let existingTexts = existingLearningGoals.map(getTextParam);
            let newGoals = learningGoalsResponse.filter(learningGoal => !existingTexts.includes(learningGoal.text));
            modalTemplateData.learningGoals = newGoals.sort(sortAlphabetically);
            renderModalTemplate();
        }, renderModalTemplate);
    });
}

//--------------Helper and Buttons--------------//

function getTextParam(item) {
    return item.text;
}

function sortAlphabetically(firstElement, secondElement) {
    return firstElement.text.localeCompare(secondElement.text, 'de-DE');
}

function renderModalTemplate() {
    let tmpl = $.templates("#modalTemplate");
    let html = tmpl.render(modalTemplateData);
    $("#modalTemplateResult").html(html);
}

function clickCancelButton() {
    resetModalTemplateData();
}

function clickNextButton() {
    switch (modalTemplateData.step++) {
        case 1:
            modalTemplateData.stepTitle = "Reflexionsfragen auswählen";
            getSpecificStoreReflectionQuestions(modalTemplateData.selectedLearningGoal.text, saveReflectionQuestionsAndRender,
                function () {
                    getAllStoreReflectionQuestions(saveReflectionQuestionsAndRender);
                });
            break;
        case 2:
            renderModalTemplate();
    }
}

function clickBackButton() {
    switch (modalTemplateData.step--) {
        case 2:
            renderModalTemplate();
            toggleButtonHighlight(`list-${modalTemplateData.lastIndex}`);
            break;
        case 3:
            renderModalTemplate();
            break;
    }
}

function toggleButtonHighlight(id) {
    $(`#${id}`).toggleClass("active");
}

//--------------Step 1----------------------//

function learningGoalChosen(index) {
    if (modalTemplateData.selectedLearningGoal) {
        if (modalTemplateData.lastIndex === index) {
            return;
        }
        toggleButtonHighlight(`list-${modalTemplateData.lastIndex}`);
    }
    modalTemplateData.selectedLearningGoal = modalTemplateData.learningGoals[index];
    modalTemplateData.lastIndex = index;
    toggleButtonHighlight(`list-${index}`);
    selectedReflectionQuestions = [];
}

function addCustomLearningGoal() {
    let learningGoalText = $('#customLearningGoalField').val();
    if (learningGoalText.length === 0) {
        return;
    }
    modalTemplateData.selectedLearningGoal = {text: learningGoalText};
    modalTemplateData.learningGoals.push(modalTemplateData.selectedLearningGoal);
    modalTemplateData.lastIndex = modalTemplateData.learningGoals.length - 1;
    renderModalTemplate();
    toggleButtonHighlight(`list-${modalTemplateData.lastIndex}`);
}

//--------------Step 2----------------------//

function saveReflectionQuestionsAndRender(reflectionQuestionsResponse) {
    if (Object.values(selectedReflectionQuestions).length === 0) {
        modalTemplateData.reflectionQuestions = reflectionQuestionsResponse;
    }
    renderModalTemplate();
    Object.values(selectedReflectionQuestions).forEach(selectedQuestion => toggleButtonHighlight(`list-${selectedQuestion.index}`));
}

function reflectionQuestionChosen(index) {
    let reflectionQuestion = modalTemplateData.reflectionQuestions[index];
    if (selectedReflectionQuestions[reflectionQuestion.id]) {
        delete selectedReflectionQuestions[reflectionQuestion.id];
        //debugger;
    } else {
        selectedReflectionQuestions[reflectionQuestion.id] = $.extend({}, reflectionQuestion);
        selectedReflectionQuestions[reflectionQuestion.id].index = index;
    }
    toggleButtonHighlight(`list-${index}`);
}

function addCustomReflectionQuestion() {
    let customQuestionText = $('#customReflectionQuestion').val();
    let customReflectionQuestion = {
        id: customQuestionText + modalTemplateData.selectedLearningGoal.text,
        learningGoal: modalTemplateData.selectedLearningGoal.text,
        question: customQuestionText
    };
    modalTemplateData.reflectionQuestions.push(customReflectionQuestion);
    let index = modalTemplateData.reflectionQuestions.length - 1;
    selectedReflectionQuestions[customReflectionQuestion.id] = customReflectionQuestion;
    selectedReflectionQuestions[customReflectionQuestion.id].index = index;
    renderModalTemplate();
    Object.values(selectedReflectionQuestions).forEach(selectedQuestion => toggleButtonHighlight(`list-${selectedQuestion.index}`));
}

function saveButtonClicked() {
    if (Object.values(selectedReflectionQuestions).length === 0) {
        alert('Bitte wählen Sie mindestens eine Reflexionsfrage aus.');
    } else {
        Object.values(selectedReflectionQuestions).forEach(question => {
            delete question.index;
        });
        let learningGoalRequest = {
            learningGoal: modalTemplateData.selectedLearningGoal,
            reflectionQuestions: Object.values(selectedReflectionQuestions),
            projectName: projectName,
        };
        selectLearningGoalAndReflectionQuestions(learningGoalRequest, renderSiteTemplate);
        $('#myModal').modal('hide');
    }

}

//--------------Website template--------------//

function renderSiteTemplate() {
    getSelectedLearningGoalsAndReflectionQuestions(projectName, function (response) {
        let tmpl = $.templates('#selectedLearningGoalTemplate');
        let data = {};
        data.selectedEntries = response;
        let html = tmpl.render(data);
        $('#selectedLearningGoalResult').html(html);
    });
}

function endLearningGoalSelection() {
    if (confirm('Wollen Sie die Auswahl wirklich abschließen? Sie können danach keine weiteren Änderungen vornehmen.')) {
        endLearningGoalAndReflectionQuestionChoice(projectName, changeLocation);
    }
}





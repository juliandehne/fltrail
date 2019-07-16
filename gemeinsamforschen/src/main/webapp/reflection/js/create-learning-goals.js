let learningGoals = [];
let reflectionQuestions = [];
let currentLearningGoal;
let chosenReflectionQuestions = [];
let templateData = {};
$(document).ready(function () {
    projectName = $('#projectName').html().trim();
    setupLearningGoalButton();
});


function setupLearningGoalButton() {
    getLearningGoalsFromStore(function (learningGoalsResponse) {
        for (let goal of learningGoalsResponse) {
            learningGoals[goal.text] = goal;
            learningGoals[goal.text].custom = false;
        }
        templateData.learningGoals = Object.values(learningGoals).sort(sortAlphabetically);
        templateData.learningGoalButtonText = "Bitte wähle ein Lernziel aus.";
        renderTemplate();
    });
}

function renderTemplate() {
    let tmpl = $.templates("#creationTemplate");
    let html = tmpl.render(templateData);
    $("#creationTemplateResult").html(html);
}

function learningGoalChosen(learningGoalText, custom) {
    if (currentLearningGoal) {
        delete learningGoals[currentLearningGoal.text].active;
    }
    currentLearningGoal = $.extend({}, learningGoals[learningGoalText]);
    learningGoals[learningGoalText].active = "active";
    learningGoals[learningGoalText].custom = custom;
    reflectionQuestions = [];
    chosenReflectionQuestions = [];
    templateData.learningGoals = Object.values(learningGoals).sort(sortAlphabetically);
    if (custom) {
        getAllStoreReflectionQuestions(function (allReflectionQuestions) {
            saveReflectionQuestionsAndRender(allReflectionQuestions);
        });
    } else {
        getSpecificStoreReflectionQuestions(learningGoalText, function (specificReflectionQuestions) {
            saveReflectionQuestionsAndRender(specificReflectionQuestions);
        });
    }
}

function saveReflectionQuestionsAndRender(reflectionQuestionsResponse) {
    for (let reflectionQuestion of reflectionQuestionsResponse) {
        reflectionQuestions[reflectionQuestion.id] = reflectionQuestion;
    }
    templateData.reflectionQuestions = Object.values(reflectionQuestions);
    renderTemplate();
}

function reflectionQuestionChosen(reflectionQuestionId) {
    templateData.choseReflectionQuestion = true;
    if (reflectionQuestions[reflectionQuestionId].active) {
        delete reflectionQuestions[reflectionQuestionId].active;
        delete chosenReflectionQuestions[reflectionQuestionId];
    } else {
        chosenReflectionQuestions[reflectionQuestionId] = $.extend({}, reflectionQuestions[reflectionQuestionId]);
        reflectionQuestions[reflectionQuestionId].active = "active";
    }
    templateData.reflectionQuestions = Object.values(reflectionQuestions);
    if (templateData.reflectionQuestions.length === 0) {
        templateData.reflectionQuestionButtonText = "Alle Reflextionsfragen hinzugefügt.";
    }
    renderTemplate();
}

function saveButtonPressed() {
    save(changeLocation);
}

function addAdditionalLearningGoalPressed() {
    save(function () {
        delete learningGoals[currentLearningGoal.text];
        currentLearningGoal = undefined;
        reflectionQuestions = [];
        chosenReflectionQuestions = [];
        templateData.learningGoals = Object.values(learningGoals).sort(sortAlphabetically);
        templateData.choseReflectionQuestion = false;
        delete templateData.reflectionQuestions;
        renderTemplate();
    });
}

function save(callback) {
    delete currentLearningGoal.custom;
    let learningGoalRequest = {
        learningGoal: currentLearningGoal,
        reflectionQuestions: Object.values(chosenReflectionQuestions),
        projectName: projectName
    };
    saveLearningGoalAndReflectionQuestions(learningGoalRequest, callback);
}

function addCustomLearningGoal() {
    let learningGoalText = $('#customLearningGoalField').val();
    let customLearningGoal = {text: learningGoalText};
    learningGoals[customLearningGoal.text] = customLearningGoal;
    learningGoalChosen(learningGoalText, true);
}

function addCustomReflectionQuestion() {
    let customQuestionText = $('#customReflectionQuestion').val();
    let customReflectionQuestion = {
        id: customQuestionText + currentLearningGoal.text,
        learningGoal: currentLearningGoal.text,
        question: customQuestionText
    };
    reflectionQuestions[customReflectionQuestion.id] = customReflectionQuestion;
    reflectionQuestionChosen(customReflectionQuestion.id);
}

function sortAlphabetically(firstElement, secondElement) {
    return firstElement.text.localeCompare(secondElement.text, 'de-DE');
}


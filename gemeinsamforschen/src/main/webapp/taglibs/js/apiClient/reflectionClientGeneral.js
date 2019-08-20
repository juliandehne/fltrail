function selectLearningGoalAndReflectionQuestions(learningGoalRequest, responseHandler) {
    let url = "../rest/reflection";
    let json = JSON.stringify(learningGoalRequest);
    $.ajax({
        url: url,
        type: "POST",
        data: json,
        contentType: "application/json",
        dataType: "json",
        success: function (response) {
            responseHandler(response);
        },
        error: function () {
            console.error("Error while saving learning goal and reflection questions");
        }
    });
}

function getSelectedLearningGoalsAndReflectionQuestions(projectName, responseHandler) {
    let url = `../rest/reflection/projects/${projectName}`;
    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        contentType: "application/json",
        success: function (response) {
            responseHandler(response);
        },
        error: function () {
            console.error("Error while getting selected learning goals");
        }
    });
}

function selectGroupPortfolioEntries(portfolioEntries, responseHandler) {
    let projectName = $('#projectName').html().trim();
    let url = `../rest/reflection//projects/${projectName}/portfolioentries`;
    let data = JSON.stringify(portfolioEntries);
    $.ajax({
        url: url,
        type: "POST",
        data: data,
        contentType: "application/json",
        success: function (response) {
            responseHandler(response);
        },
        error: function (response) {
            console.error("Error while choosing assessment");
        }
    });
}

function getGroupPortfolioEntries(responseHandler) {
    let projectName = $('#projectName').html().trim();
    let url = `../rest/reflection/projects/${projectName}/portfolioentries`;
    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        contentType: "application/json",
        success: function (response) {
            responseHandler(response);
        },
        error: function () {
            console.error("Error while getting assessment material");
        }
    });
}

function getChosenGroupEntries(projectName, responseHandler) {
    let url = `../rest/reflection//projects/${projectName}/portfolioentries/selected`;
    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        contentType: "application/json",
        success: function (response) {
            responseHandler(response);
        },
        error: function () {
            console.error("Error while getting chosenGroupEntries material");
        }
    });
}

async function saveGroupSelection(projectName, groupId, html) {
    let url = `../rest/reflection//projects/${projectName}/save/group/${groupId}`;
    try {
        await $.ajax({
            url: url,
            type: "POST",
            data: html,
            contentType: "application/json",
        });
    } catch (e) {
        console.error(`Error while getting reflection questions! ${e.toString()}`);
    }
}

function deleteLearningGoalAndReflectionQuestion(learningGoalId, responseHandler) {
    let url = `../rest/reflection/learninggoals/${learningGoalId}`;
    $.ajax({
        url: url,
        type: "DELETE",
        contentType: "application/json",
        success: function (response) {
            responseHandler(response);
        },
        error: function (response) {
            console.error("Error while deleting learning goal");
        }
    });
}

function endLearningGoalAndReflectionQuestionChoice(projectName, responseHandler) {
    let url = `../rest/reflection/projects/${projectName}/finish`;
    $.ajax({
        url: url,
        type: "POST",
        contentType: "application/json",
        success: function (response) {
            responseHandler(response);
        },
        error: function () {
            console.error("Error while ending reflection question and learning goal choice");
        }
    });
}
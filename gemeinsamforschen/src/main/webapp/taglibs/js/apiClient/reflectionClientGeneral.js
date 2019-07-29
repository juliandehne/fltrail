function saveLearningGoalAndReflectionQuestions(learningGoalRequest, responseHandler) {
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

function chooseAssessmentMaterial(assessmentHtml, responseHandler) {
    let projectName = $('#projectName').html().trim();
    let url = `../rest/reflection/material/chosen/projects/${projectName}`;
    let data = JSON.stringify(assessmentHtml);
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

function getMaterialForAssessment(responseHandler) {
    let projectName = $('#projectName').html().trim();
    let url = `../rest/reflection/material/choose/projects/${projectName}`;
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
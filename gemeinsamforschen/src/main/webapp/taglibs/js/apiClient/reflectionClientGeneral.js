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
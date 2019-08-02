function getLearningGoalsFromStore(responseHandler) {
    let url = "../rest/learninggoal/store";
    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        success: function (response) {
            // handle the response
            responseHandler(response);
        },
        error: function () {
            console.error("Error while getting learning goals from store");
        }
    })
}

function getExisistingLearningGoals(projectName, responseHandler, errorHandler) {
    let url = `../rest/learninggoal/projects/${projectName}`;
    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        success: function (response) {
            // handle the response
            responseHandler(response);
        },
        error: function () {
            errorHandler();
        }
    })
}

function saveLearningGoalResult(request, responseHandler) {
    let url = "../rest/learninggoal/result";
    let json = JSON.stringify(request);
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
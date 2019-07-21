function getNextReflectionQuestion(projectName, learningGoalId, responseHandler) {
    let url = `../rest/reflectionquestion/projects/${projectName}/learninggoal/${learningGoalId}/next`;
    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        success: function (response) {
            // handle the response
            responseHandler(response);
        },
        error: function () {
            console.error(`Error while getting reflection question.`)
        }
    });
}

async function getUnansweredReflectionQuestions(projectName, learningGoalId) {
    let url = `../rest/reflectionquestion/projects/${projectName}/learninggoal/${learningGoalId}/bulk`;
    let reflectionQuestions;
    try {
        reflectionQuestions = await $.ajax({
            url: url,
            type: "GET",
            dataType: "json"
        });
    } catch (e) {
        console.error(`Error while getting reflection questions! ${e.toString()}`);
    }
    return reflectionQuestions;
}

function getSpecificStoreReflectionQuestions(learningGoal, responseHandler) {
    let url = `../rest/reflectionquestion/store/learninggoal/${learningGoal}`;
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

function getAllStoreReflectionQuestions(responseHandler) {
    let url = `../rest/reflectionquestion/store/`;
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
function getNextReflectionQuestion(projectName, responseHandler) {
    let url = `../rest/reflectionquestion/projects/${projectName}/next`;
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
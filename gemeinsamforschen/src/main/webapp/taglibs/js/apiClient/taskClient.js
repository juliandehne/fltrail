function getTaskInfo(projectName, taskName, responseHandler, errorHandler) {
    let url = `../rest/tasks/progress/projects/${projectName}/task/${taskName.toUpperCase()}`;
    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        success: function (response) {
            // handle the response
            responseHandler(response);
        },
        error: function () {
            response = null;
            responseHandler(response);
        }
    });
}
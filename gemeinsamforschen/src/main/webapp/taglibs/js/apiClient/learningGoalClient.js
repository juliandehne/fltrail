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
$(document).ready(function () {

    let requestObj = new RequestObj(1, "/project","/all",[],[])
    serverSide(requestObj, "GET", function (response) {

        let menuItem = "<a class=\"dropdown-item\" role=\"presentation\" href=\"#\">First Item</a>";
    });
});

function updateView(project) {
    // getProgressFrom DB

    // update ui: disable buttons
}

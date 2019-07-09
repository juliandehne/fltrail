let projectList = [];
let selectedProject = "";

$(document).ready(function () {

    let requestObj = new RequestObj(1, "/wizard","/projects",[],[])
    serverSide(requestObj, "GET", function (response) {
        projectList = response;
        for (let i = 0 ; i < response.length; i++)
        {
            let menuItem = "<a class=\"dropdown-item\" role=\"presentation\" id=\"project_"+i+"\">"+response[i].name+"</a>";
            $("#dropdownContainer").append(menuItem);
            $("#project_"+i).click(function () {
                updateView(projectList[i]);
            });
        }
    });
});

function updateView(project) {
    selectedProject = project;
    // getProgressFrom DB
    $("#projectButtonText").text(project.name);
    // update ui: disable buttons
    $("#createStudents").unbind();
    $("#createStudents").click(function () {
        doSpell(selectedProject.name, "WAIT_FOR_PARTICPANTS");
    });
    $("#skipGroupPhase").click(function () {
        doPhaseSpell(selectedProject.name, "GroupFormation");
    });
    $("button").removeAttr("disabled");
}

function doSpell(project, taskName) {
    let requestObj = new RequestObj(1, "/wizard", "/projects/?/task/?", [project, taskName], [])
    serverSide(requestObj, "POST", function (response) {
        //console.log()
        alert("spell has been cast");
    });
}

function doPhaseSpell(project, phase) {
    let requestObj = new RequestObj(1, "/wizard", "/projects/?/phase/?", [project, phase], [])
    serverSide(requestObj, "POST", function (response) {
        //console.log()
        alert("phase spell has been cast");
    });
}

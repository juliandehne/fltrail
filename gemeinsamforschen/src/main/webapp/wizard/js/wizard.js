let projectList = [];
let selectedProject = "";
const phases = new Enum('GroupFormation', 'DossierFeedback', 'Execution', 'Assessment', 'GRADING', 'Projectfinished');
const taskNames = new Enum( "WAIT_FOR_PARTICPANTS", "UPLOAD_DOSSIER", "ANNOTATE_DOSSIER","GIVE_FEEDBACK",
                            "FINALIZE_DOSSIER", "UPLOAD_PRESENTATION", "UPLOAD_FINAL_REPORT",
                            "GIVE_EXTERNAL_ASSESSMENT","GIVE_INTERNAL_ASSESSMENT",
                            "GIVE_EXTERNAL_ASSESSMENT_TEACHER");

$(document).ready(function () {

    let requestObj = new RequestObj(1, "/wizard", "/projects", [], []);
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
    $("#skipGroupPhase").unbind();
    $("#skipGroupPhase").click(function () {
        doPhaseSpell(selectedProject.name, "GroupFormation");
    });

    $("#uploadDossierButton").unbind();
    $("#uploadDossierButton").click(function () {
        doSpell(selectedProject.name, "UPLOAD_DOSSIER");
    });

    $("#annotateDossierButton").unbind();
    $("#annotateDossierButton").click(function () {
        doSpell(selectedProject.name, "ANNOTATE_DOSSIER");
    });
    $("#giveFeedbackButton").unbind();
    $("#giveFeedbackButton").click(function () {
        doSpell(selectedProject.name, "GIVE_FEEDBACK");
    });
    $("#finalizeDossierButton").unbind();
    $("#finalizeDossierButton").click(function () {
        doSpell(selectedProject.name, "REEDIT_DOSSIER");
    });

    $("#skipDossierPhase").unbind();
    $("#skipDossierPhase").click(function () {
        doPhaseSpell(selectedProject.name, "DossierFeedback");
    });


    $("#uploadPresentationButton").unbind();
    $("#uploadPresentationButton").click(function () {
        doSpell(selectedProject.name, "UPLOAD_PRESENTATION");
    });

    $("#uploadFinalReportButton").unbind();
    $("#uploadFinalReportButton").click(function () {
        doSpell(selectedProject.name, "UPLOAD_FINAL_REPORT");
    });

    $("#externalPAButton").unbind();
    $("#externalPAButton").click(function () {
        doSpell(selectedProject.name, "GIVE_EXTERNAL_ASSESSMENT");
    });

    $("#internalPAButton").unbind();
    $("#internalPAButton").click(function () {
        doSpell(selectedProject.name, "GIVE_INTERNAL_ASSESSMENT");
    });

    $("#docentPAButton").unbind();
    $("#docentPAButton").click(function () {
        doSpell(selectedProject.name, "GIVE_EXTERNAL_ASSESSMENT_TEACHER");
    });

    $("#finishAssessmentAndGradingButton").unbind();
    $("#finishAssessmentAndGradingButton").click(function () {
        doSpell(selectedProject.name, "GIVE_EXTERNAL_ASSESSMENT_TEACHER");
    });

    updateState();
}

function doSpell(project, taskName) {
    loaderStart();
    let requestObj = new RequestObj(1, "/wizard", "/projects/?/task/?", [project, taskName], []);
    serverSide(requestObj, "POST", function () {
        //console.log()
        updateState();
    });
}

function doPhaseSpell(project, phase) {
    loaderStart();
    let requestObj = new RequestObj(1, "/wizard", "/projects/?/phase/?", [project, phase], []);
    serverSide(requestObj, "POST", function (response) {
        //console.log()
        updateState();
    });
}


function updateState() {
    $("button").removeAttr("disabled");
    // update phase states
    updatePhaseState();
}

function updatePhaseState() {
    let requObj = new RequestObj(1, "/phases", "/projects/?/closed", [selectedProject.name], []);
    serverSide(requObj, "GET", function (response) {
        let groupfinding = phases.getName(phases.GroupFormation);
        if (response.includes(groupfinding)) {
            $('button.groupfindingButton').attr("disabled", true);
            $('#testStudentDisplay').empty();
            let getTestStudObj = new RequestObj(1, "/wizard", "/projects/?/teststudent", [selectedProject.name], []);
            serverSide(getTestStudObj, "GET", function (user) {
                $('#testStudentDisplay').append("Email für Teststudent lautet: " + user.email +
                    " und Passwort lautet \"egal\" ");
            })
        }
        if (response.includes(phases.getName(phases.DossierFeedback))) {
            $('button.dossierButton').attr("disabled", true);
        }
        if (response.includes(phases.getName(phases.Execution))) {
            $('button.reflexionButton').attr("disabled", true);
        }
        if (response.includes(phases.getName(phases.Assessment))) {
            $('button.assessmentButton').attr("disabled", true);
        }
        // @TODO add this with promise behind the function call for readability
        updateTaskStates();
    })
}

function updateTaskStates() {
// update task states
    let requObj2 = new RequestObj(1, "/wizard", "/projects/?/tasksFinished", [selectedProject.name], []);
    serverSide(requObj2, "GET", function (tasksfinished) {

        if (tasksfinished.includes(taskNames.getName(taskNames.WAIT_FOR_PARTICPANTS))) {
            $("#createStudents").attr("disabled", true);
        }

        if (tasksfinished.includes(taskNames.getName(taskNames.UPLOAD_DOSSIER))) {
            $("#uploadDossierButton").attr("disabled", true);
        }
        if (tasksfinished.includes(taskNames.getName(taskNames.ANNOTATE_DOSSIER))) {
            $("#annotateDossierButton").attr("disabled", true);
        }
        if (tasksfinished.includes(taskNames.getName(taskNames.GIVE_FEEDBACK))) {
            $("#giveFeedbackButton").attr("disabled", true);

        }
        if (tasksfinished.includes(taskNames.getName(taskNames.FINALIZE_DOSSIER))) {
            $("#finalizeDossierButton").attr("disabled", true);

        }
        if (tasksfinished.includes(taskNames.getName(taskNames.UPLOAD_PRESENTATION))) {
            $("#uploadPresentationButton").attr("disabled", true);

        }
        if (tasksfinished.includes(taskNames.getName(taskNames.UPLOAD_FINAL_REPORT))) {
            $("#uploadFinalReportButton").attr("disabled", true);

        }
        if (tasksfinished.includes(taskNames.getName(taskNames.GIVE_EXTERNAL_ASSESSMENT))) {
            $("#externalPAButton").attr("disabled", true);

        }
        if (tasksfinished.includes(taskNames.getName(taskNames.GIVE_INTERNAL_ASSESSMENT))) {
            $("#internalPAButton").attr("disabled", true);

        }
        if (tasksfinished.includes(taskNames.getName(taskNames.GIVE_EXTERNAL_ASSESSMENT_TEACHER))) {
            $("#docentPAButton").attr("disabled", true);
        }
        loaderStop();
        alert("Spell has been cast. Simulation has run my friend.")
    })
}


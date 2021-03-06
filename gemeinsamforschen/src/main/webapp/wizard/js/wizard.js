let projectList = [];
let selectedProject = "";
const phases = new Enum('GroupFormation', 'DossierFeedback', 'Execution', 'Assessment', 'GRADING', 'Projectfinished');
const taskNames = new Enum(
    "WAIT_FOR_PARTICPANTS",
    "UPLOAD_DOSSIER",
    "ANNOTATE_DOSSIER",
    "GIVE_FEEDBACK",
    "REEDIT_DOSSIER",
    "CREATE_LEARNING_GOALS_AND_CHOOSE_REFLECTION_QUESTIONS",
    "ANSWER_REFLECTION_QUESTIONS",
    "WIZARD_CREATE_PORTFOLIO",
    "DOCENT_GIVE_PORTOLIO_FEEDBACK",
    "CHOOSE_PORTFOLIO_ENTRIES",
    "INTRODUCE_E_PORTFOLIO_STUDENT",
    "UPLOAD_PRESENTATION",
    "UPLOAD_FINAL_REPORT",
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
    let btnCreateStudents = $("#createStudents");
    btnCreateStudents.unbind();
    btnCreateStudents.click(function () {
        doSpell(selectedProject.name, "WAIT_FOR_PARTICPANTS");
    });
    let btnSkipGroupPhase = $("#skipGroupPhase");
    btnSkipGroupPhase.unbind();
    btnSkipGroupPhase.click(function () {
        doPhaseSpell(selectedProject.name, "GroupFormation");
    });
    let btnUploadDossierButton = $("#uploadDossierButton");
    btnUploadDossierButton.unbind();
    btnUploadDossierButton.click(function () {
        doSpell(selectedProject.name, "UPLOAD_DOSSIER");
    });
    let btnAnnotateDossierButton = $("#annotateDossierButton");
    btnAnnotateDossierButton.unbind();
    btnAnnotateDossierButton.click(function () {
        doSpell(selectedProject.name, "ANNOTATE_DOSSIER");
    });
    let btnGiveFeedbackButton = $("#giveFeedbackButton");
    btnGiveFeedbackButton.unbind();
    btnGiveFeedbackButton.click(function () {
        doSpell(selectedProject.name, "GIVE_FEEDBACK");
    });
    let btnFinalizeDossierButton = $("#finalizeDossierButton");
    btnFinalizeDossierButton.unbind();
    btnFinalizeDossierButton.click(function () {
        doSpell(selectedProject.name, "REEDIT_DOSSIER");
    });
    let btnSkipDossierPhase = $("#skipDossierPhase");
    btnSkipDossierPhase.unbind();
    btnSkipDossierPhase.click(function () {
        doPhaseSpell(selectedProject.name, "DossierFeedback");
    });

    // execution phase
    let btnSelectQuestions = $("#selectQuestionsForProject");
    btnSelectQuestions.unbind();
    btnSelectQuestions.click(function () {
        doSpell(selectedProject.name, taskNames.getName(taskNames.CREATE_LEARNING_GOALS_AND_CHOOSE_REFLECTION_QUESTIONS));
    });


    let btnWriteEPortfolioEntries = $("#writeEPortfolioEntries");
    btnWriteEPortfolioEntries.unbind();
    btnWriteEPortfolioEntries.click(function () {
        doSpell(selectedProject.name, taskNames.getName(taskNames.WIZARD_CREATE_PORTFOLIO));
    });

    let btnGiveDocentFeedback = $("#giveFeedbackForReflexion");
    btnGiveDocentFeedback.unbind();
    btnGiveDocentFeedback.click(function () {
        doSpell(selectedProject.name, taskNames.getName(taskNames.DOCENT_GIVE_PORTOLIO_FEEDBACK));
    });

    let btnAnswerQuestions = $("#answerQuestionsForProject");
    btnAnswerQuestions.unbind();
    btnAnswerQuestions.click(function () {
        doSpell(selectedProject.name, taskNames.getName(taskNames.ANSWER_REFLECTION_QUESTIONS));
    });

    let btnSelectEntriesForAssessment = $("#selectEntriesForAssessment");
    btnSelectEntriesForAssessment.unbind();
    btnSelectEntriesForAssessment.click(function () {
        doSpell(selectedProject.name, taskNames.getName(taskNames.CHOOSE_PORTFOLIO_ENTRIES));
    });

    let btnSkipExecutionPhase = $("#skipExecutionPhase");
    btnSkipExecutionPhase.unbind();
    btnSkipExecutionPhase.click(function () {
        doPhaseSpell(selectedProject.name, "Execution");
    });

    // end excution phase

    let btnUploadPresentationButton = $("#uploadPresentationButton");
    btnUploadPresentationButton.unbind();
    btnUploadPresentationButton.click(function () {
        doSpell(selectedProject.name, "UPLOAD_PRESENTATION");
    });
    let btnUploadFinalReportButton = $("#uploadFinalReportButton");
    btnUploadFinalReportButton.unbind();
    btnUploadFinalReportButton.click(function () {
        doSpell(selectedProject.name, "UPLOAD_FINAL_REPORT");
    });
    let btnExternalPAButton = $("#externalPAButton");
    btnExternalPAButton.unbind();
    btnExternalPAButton.click(function () {
        doSpell(selectedProject.name, "GIVE_EXTERNAL_ASSESSMENT");
    });
    let btnInternalPAButton = $("#internalPAButton");
    btnInternalPAButton.unbind();
    btnInternalPAButton.click(function () {
        doSpell(selectedProject.name, "GIVE_INTERNAL_ASSESSMENT");
    });
    let btnDocentPAButton = $("#docentPAButton");
    btnDocentPAButton.unbind();
    btnDocentPAButton.click(function () {
        doSpell(selectedProject.name, "GIVE_EXTERNAL_ASSESSMENT_TEACHER");
    });
    let btnFinishAssessmentAndGradingButton = $("#finishAssessmentAndGradingButton");
    btnFinishAssessmentAndGradingButton.unbind();
    btnFinishAssessmentAndGradingButton.click(function () {
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
    serverSide(requestObj, "POST", function () {
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
        if (response.includes(phases.getName(phases.GRADING))) {
            $('button.GRADING').attr("disabled", true);
        }
        if (response.includes(phases.getName(phases.Projectfinished))) {
            $('button.Projectfinished').attr("disabled", true);
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
        if (tasksfinished.includes(taskNames.getName(taskNames.REEDIT_DOSSIER))) {
            $("#finalizeDossierButton").attr("disabled", true);

        }
        // execution phase

        if (tasksfinished.includes(taskNames.getName(taskNames.CREATE_LEARNING_GOALS_AND_CHOOSE_REFLECTION_QUESTIONS))) {
            $("#selectQuestionsForProject").attr("disabled", true);
        };


        if (tasksfinished.includes(taskNames.getName(taskNames.WIZARD_CREATE_PORTFOLIO))){
            $("#writeEPortfolioEntries").attr("disabled", true);
        };

        if (tasksfinished.includes( taskNames.getName(taskNames.DOCENT_GIVE_PORTOLIO_FEEDBACK))){
            $("#giveFeedbackForReflexion").attr("disabled", true);
        };

        if (tasksfinished.includes( taskNames.getName(taskNames.ANSWER_REFLECTION_QUESTIONS))){
            $("#answerQuestionsForProject").attr("disabled", true);
        };

        if (tasksfinished.includes( taskNames.getName(taskNames.CHOOSE_PORTFOLIO_ENTRIES))){
            $("#selectEntriesForAssessment").attr("disabled", true);
        };


        // end execution phase


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
        //alert("Spell has been cast. Simulation has run my friend.")
    })
}
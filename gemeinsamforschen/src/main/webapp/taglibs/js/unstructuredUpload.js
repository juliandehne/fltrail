/**
 * This function will fire when the DOM is ready
 */
let groupId = 0;
let fileRole;
let hierarchyLevel;
let fullSubmissionId;
let personal;
let currentVisibility;
let possibleVisibilities = [];
let isPortfolioEntry;
let isDossier;
let isReflectionQuestion;
let projectName;
let reflectionQuestionId;
let reflectionQuestions;
let reflectionQuestionTemplateData = {};

$(document).ready(async function () {
    fileRole = $('#fileRole').html().trim();
    let personalString = $("#personal").html().trim();
    personal = personalString.toUpperCase() === 'TRUE';
    isPortfolioEntry = fileRole.toUpperCase() === 'PORTFOLIO_ENTRY';
    isReflectionQuestion = fileRole.toUpperCase() === 'REFLECTION_QUESTION';
    isDossier = fileRole.toUpperCase() === 'DOSSIER';
    hierarchyLevel = $('#hierarchyLevel').html().trim();
    fullSubmissionId = $('#fullSubmissionId').html().trim();
    projectName = $('#projectName').html().trim();

    if (isPortfolioEntry) {
        $('#backToTasks').html(`<i class="fas fa-chevron-circle-left"></i> Zurück zum Portfolio</a>`);
    }
    await setupPageContent();

    $('#btnSave').click(function () {
        getMyGroupId(function (groupId) {
            uploadContribution(groupId, false);
        });
    });
    $('#btnFinalSave').click(function () {
        getMyGroupId(function (groupId) {
            uploadContribution(groupId, true);
        });
    });
    $('#btnBack').click(function () {
        // if there is text inside the textarea
        // show user alert message that the text will be lost
        if (window.confirm("Möchten Sie zur vorherigen Seite zurückkehren? \nIhr bisheriger Text wird nicht gespeichert.")) {
            // clear textarea
            quill.setContents(null);

            // jump to previous page
            //window.history.back();
            location.href = "../../project/projects-student.jsp";
        }

        // nothing to check
        else {
            // jump to previous page
            //window.history.back();
            location.href = "../../project/projects-student.jsp";
        }
    });

});

function handleNextAction() {
    if (isReflectionQuestion && Array.isArray(reflectionQuestions) && reflectionQuestions.length > 0) {
        renderReflectionQuestionTemplate();
    } else if (isPortfolioEntry) {
        location.href = `${hierarchyLevel}portfolio/show-portfolio-student.jsp?projectName=${getProjectName()}`;
    } else {
        location.href = `${hierarchyLevel}project/tasks-student.jsp?projectName=${getProjectName()}`;
    }
}

async function setupPageContent() {
    populateHeaderTemplate();
    await populateTitleEditorTemplate();
    if (isReflectionQuestion) {
        await setupAndRenderReflectionQuestionsTemplate();
    }
    getVisibilities(personal, function (response) {
        Object.entries(response).forEach(([name, buttonText]) => {
            possibleVisibilities[name] = {name: name, buttonText: buttonText};
        });
        if (personal === true) {
            currentVisibility = possibleVisibilities['PERSONAL'];
        } else {
            currentVisibility = possibleVisibilities['GROUP'];
        }
        // TODO: refactor as async await function, so the call for populate textfield don't have to be here twice
        if (fullSubmissionId !== '') {
            getFullSubmission(fullSubmissionId, function (fullSubmission) {
                setQuillContentFromFullSubmission(fullSubmission);
                currentVisibility = possibleVisibilities[fullSubmission.visibility];
                if (getUserEmail() !== fullSubmission.userEmail) {
                    delete possibleVisibilities['PERSONAL'];
                    delete possibleVisibilities['DOCENT'];
                }
                populateVisibilityButton();
                setHeader(fullSubmission.header);
            }, function (error) {

            });
        } else {
            if (fileRole.toUpperCase() === "DOSSIER") {
                getMyGroupId(function (groupId) {
                    getFullSubmissionOfGroupToEditor(groupId, 0);
                });
            } else {
                populateVisibilityButton();
            }
        }
    });
    getAnnotationCategories(function (categories) {
        buildAnnotationList(categories);
    });
    setupSaveButtonLayout();
}

function populateHeaderTemplate() {
    let data = {};
    let headerSubject = fileRole;
    let activityVerb = 'anlegen';
    switch (fileRole.toUpperCase()) {
        case "PORTFOLIO_ENTRY":
            headerSubject = 'Portfolio-Eintrag';
            break;
        case "REFLECTION_QUESTION":
            headerSubject = 'Reflexionsfrage';
            activityVerb = 'beantworten';
    }
    data.header = `${headerSubject} ${activityVerb}`;
    let tmpl = $.templates("#headerTemplate");
    let html = tmpl.render(data);
    $("#headerTemplateResult").html(html);
}

async function populateTitleEditorTemplate() {
    let data = {};
    data.fileRole = fileRole;
    if (isDossier) {
        data.label = "Fragestellung / Projektaufgabe";
        data.placeholder = "Fügen Sie hier Ihre Fragestellung / Projektaufgabe ein";
    } else {
        data.label = "Titel";
        data.placeholder = "Fügen Sie einen Titel für Ihren Eintrag ein";
    }
    let tmpl = $.templates('#editorTitleTemplate');
    let html = tmpl.render(data);
    $('#editorTitleTemplateResult').html(html)
}

async function setupAndRenderReflectionQuestionsTemplate() {
    reflectionQuestions = await getUnansweredReflectionQuestions(projectName);
    reflectionQuestionTemplateData.fileRole = fileRole;
    reflectionQuestionTemplateData.totalQuestions = reflectionQuestions.length;
    renderReflectionQuestionTemplate();
}

function setupSaveButtonLayout() {
    let data = {};
    data.fileRole = fileRole;
    let tmpl = $.templates('#saveTemplate');
    let html = tmpl.render(data);
    $('#saveTemplateResult').html(html);
}

function renderReflectionQuestionTemplate() {
    quill.setContents(null);
    quill.setSelection(0);
    let nextReflectionQuestion = reflectionQuestions.shift();
    reflectionQuestionTemplateData.question = nextReflectionQuestion.question;
    reflectionQuestionTemplateData.currentReflectionQuestionCounter = reflectionQuestionTemplateData.currentReflectionQuestionCounter ? ++reflectionQuestionTemplateData.currentReflectionQuestionCounter : 1;
    reflectionQuestionId = nextReflectionQuestion.id;
    let tmpl = $.templates("#reflectionQuestionTemplate");
    let html = tmpl.render(reflectionQuestionTemplateData);
    $("#reflectionQuestionTemplateResult").html(html);
}

function populateVisibilityButton() {
    let data = {};
    data.fileRole = fileRole;
    data.possibleVisibilities = Object.values(possibleVisibilities);
    data.currentVisibility = currentVisibility;
    let tmpl = $.templates("#visibilityTemplate");
    let html = tmpl.render(data);
    $("#visibilityTemplateResult").html(html);
}

function changeButtonText(clickedItem, callback) {
    let dropBtn = $('.dropbtn');
    let oldText = dropBtn.html();
    let oldVisibility = currentVisibility;
    currentVisibility = possibleVisibilities[clickedItem];
    let newText = oldText.replace(oldVisibility.buttonText, currentVisibility.buttonText);
    dropBtn.html(newText);
    if (callback) {
        callback();
    }
}

function uploadContribution(groupId, finalized) {
    if (quill.getText().length > 1) {
        let content = quill.getContents();
        let html = quill.root.innerHTML;

        // build request
        let visibility = "GROUP";
        if (typeof currentVisibility !== 'undefined') {
            visibility = currentVisibility.name
        }
        let header = "";
        let ownTitle = $('#ownTitle');
        if (ownTitle.is("input")) {
            header = ownTitle.val();
        } else {
            header = ownTitle.html();
        }
        // TODO: separate everything completely, so the frontend template is the same for all pages, but functionality is separated per page
        let fullSubmissionPostRequest = {
            header: header,
            id: fullSubmissionId,
            groupId: groupId,
            text: JSON.stringify(content),
            html: html,
            projectName: projectName,
            saveUsername: personal || fileRole.toUpperCase() !== "DOSSIER",
            fileRole: fileRole.toUpperCase(),
            visibility: visibility,
            reflectionQuestionId: reflectionQuestionId,
            finalized: finalized,
        };
        if (isPortfolioEntry && fullSubmissionId !== '') {
            updatePortfolioSubmission(fullSubmissionPostRequest, handleNextAction);
        } else {
            // save request in database
            createFullSubmission(fullSubmissionPostRequest, handleNextAction)
        }

    } else {
        alert("Ein Text wird benötigt");
    }
}
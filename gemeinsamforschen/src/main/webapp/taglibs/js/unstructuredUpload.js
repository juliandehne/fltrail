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
let isReflectionQuestion;
let projectName;
let reflectionQuestionId;

$(document).ready(function () {
    fileRole = $('#fileRole').html().trim();
    let personalString = $("#personal").html().trim();
    personal = personalString.toUpperCase() === 'TRUE';
    isPortfolioEntry = fileRole.toUpperCase() === 'PORTFOLIO';
    isReflectionQuestion = fileRole.toUpperCase() === 'REFLECTION_QUESTION';
    hierarchyLevel = $('#hierarchyLevel').html().trim();
    fullSubmissionId = $('#fullSubmissionId').html().trim();
    projectName = $('#projectName').html().trim();

    if (isPortfolioEntry) {
        $('#backToTasks').html(`<i class="fas fa-chevron-circle-left"> Zurück zum Portfolio</i></a>`);
    }
    setupPageContent();



    $('#btnSave').click(function () {
        getMyGroupId(function (groupId) {
            if (quill.getText().length > 1) {
                let content = quill.getContents();
                let html = quill.root.innerHTML;

                // build request
                let visibility = "GROUP";
                if (typeof currentVisibility !== 'undefined') {
                    visibility = currentVisibility.name
                }
                // TODO: separate everything completely, so the frontend template is the same for everyone, but everything else isn't
                let fullSubmissionPostRequest = {
                    id: fullSubmissionId,
                    groupId: groupId,
                    text: JSON.stringify(content),
                    html: html,
                    projectName: projectName,
                    personal: personal,
                    fileRole: fileRole.toUpperCase(),
                    visibility: visibility,
                    reflectionQuestionId: reflectionQuestionId
                };
                if (isPortfolioEntry && fullSubmissionId !== '') {
                    updatePortfolioSubmission(fullSubmissionPostRequest, redirectToPreviousPage);
                } else {
                    // save request in database
                    createFullSubmission(fullSubmissionPostRequest, redirectToPreviousPage)
                }

            } else {
                alert("Ein Text wird benötigt");
            }
        });
    });
    $('#btnBack').click(function () {
        // if there is text inside the textarea
        // show user alert message that the text will be lost
        if (window.confirm("Möchten Sie zur vorherigen Seite zurückkehren? \nIhr bisheriger Text wird nicht gespeichert.")) {
            // clear textarea
            quill.setText("");

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

function redirectToPreviousPage() {
    let projectName = $('#projectName').text().trim();
    if (isPortfolioEntry) {
        location.href = `${hierarchyLevel}portfolio/show-portfolio-student.jsp?projectName=${projectName}`;
    } else {
        location.href = `${hierarchyLevel}project/tasks-student.jsp?projectName=${projectName}`;
    }
}

function setupPageContent() {
    populateHeaderTemplate();
    if (isReflectionQuestion) {
        populateReflectionQuestionTemplate();
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
                populateTextFields();
            });
        } else {
            if (!personal) {
                getMyGroupId(function (groupId) {
                    getFullSubmissionOfGroup(groupId, 0);
                    populateTextFields();
                });
            }
        }
    });
    getAnnotationCategories(function (categories) {
        buildAnnotationList(categories);
    });
}

function populateHeaderTemplate() {
    let data = {};
    let headerSubject = fileRole;
    let activityVerb = 'anlegen';
    switch (fileRole) {
        case "Portfolio":
            headerSubject = 'Portfolio-Eintrag';
            break;
        case "Reflection_Question":
            headerSubject = 'Reflexionsfrage';
            activityVerb = 'beantworten';
    }
    data.header = `${headerSubject} ${activityVerb}`;
    let tmpl = $.templates("#headerTemplate");
    let html = tmpl.render(data);
    $("#headerTemplateResult").html(html);
}

function populateReflectionQuestionTemplate() {
    let data = {};
    data.fileRole = fileRole;
    getNextReflectionQuestion(projectName, function (response) {
        reflectionQuestionId = response.id;
        data.reflectionQuestion = response.question;
        let tmpl = $.templates("#reflectionQuestionTemplate");
        let html = tmpl.render(data);
        $("#reflectionQuestionTemplateResult").html(html);
    });
}

function populateTextFields() {
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

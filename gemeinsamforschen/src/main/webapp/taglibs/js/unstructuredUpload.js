/**
 * This function will fire when the DOM is ready
 */
let groupId = 0;
let fileRole;
let hierarchyLevel;
let fullSubmissionId = "";
let personal;
let currentVisibility;
let possibleVisibilities = [];

$(document).ready(function () {
    fileRole = $('#fileRole').html().trim();
    let personalString = $("#personal").html().trim();
    personal = personalString.toUpperCase() === 'TRUE';
    setupPageContent();
    if (!personal) {
        getMyGroupId(function (groupId) {
            getFullSubmissionOfGroup(groupId, 0)
        });
    }
    hierarchyLevel = $('#hierarchyLevel').html().trim();


    $('#btnSave').click(function () {

        getMyGroupId(function (groupId) {
            if (quill.getText().length > 1) {
                let content = quill.getContents();
                let html = quill.root.innerHTML;

                // build request
                let fullSubmissionPostRequest = {
                    groupId: groupId,
                    text: JSON.stringify(content),
                    html: html,
                    projectName: $('#projectName').text().trim(),
                    personal: personal,
                    fileRole: fileRole.toUpperCase(),
                    visibility: currentVisibility.name
                };

                // save request in database
                createFullSubmission(fullSubmissionPostRequest, function () {

                    // back to main page
                    location.href = hierarchyLevel + "project/tasks-student.jsp?projectName=" + $('#projectName').text().trim();
                });
            } else {
                alert("Ein Text wird benötigt");
            }
        });
    });

    $('#backToTasks').click(function () {
        location.href = "../project/tasks-student.jsp?projectName=" + $('#projectName').text().trim();
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

function setupPageContent() {
    populateHeaderTemplate();
    getVisibilities(personal, function (response) {
        Object.entries(response).forEach(([name, buttonText]) => {
            possibleVisibilities[name] = {name: name, buttonText: buttonText};
        });
        if (personal === true) {
            currentVisibility = possibleVisibilities['PERSONAL'];
        } else {
            currentVisibility = possibleVisibilities['GROUP'];
        }
        populateTextFields();
    });
}

function populateHeaderTemplate() {
    let data = {};
    data.header = fileRole === "Portfolio" ? "Portfolio-Eintrag" : fileRole;
    let tmpl = $.templates("#headerTemplate");
    let html = tmpl.render(data);
    $("#headerTemplateResult").html(html);
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

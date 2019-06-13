/**
 * This function will fire when the DOM is ready
 */
let groupId = 0;
let contributionCategory;
let hierarchyLevel;
let fullSubmissionId = "";
let personal;
$(document).ready(function () {
    contributionCategory = $('#contributionCategory').html().trim();
    setPersonal();
    if (!personal) {
        getMyGroupId(function (groupId) {
            getFullSubmissionOfGroup(groupId, 0)
        });
    }
    hierarchyLevel = $('#hierarchyLevel').html().trim();
    populateTextFields();

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
                    contributionCategory: contributionCategory.toUpperCase()
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


function setPersonal() {
    let personalString = $("#personal").html().trim();
    personal = personalString.toUpperCase() === 'TRUE';
}

function populateTextFields() {
    let data = {};
    data.header = contributionCategory === "Portfolio" ? "Portfolio-Eintrag" : contributionCategory;
    let tmpl = $.templates("#headerTemplate");
    //tmpl.link("#result");
    let html = tmpl.render(data);
    $("#result").html(html);

}

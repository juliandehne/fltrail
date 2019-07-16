/**
 * This function will fire when the DOM is ready
 */
let hierarchyLevel;
let projectName;

$(document).ready(async function () {
    hierarchyLevel = $('#hierarchyLevel').html().trim();
    projectName = $('#projectName').html().trim();

    $('#btnSave').click(function () {
        getMyGroupId(function (groupId) {
            if (quill.getText().length > 1) {
                // build request
                let visibility = "GROUP";
                let content = quill.getContents();

                let request = {
                    groupId: groupId,
                    text: JSON.stringify(content),
                    projectName: projectName,
                    visibility: visibility,
                    learningGoalId: $('#learningGoalId').html().trim()
                };

                saveLearningGoalResult(request, function () {
                    location.href = `${hierarchyLevel}project/tasks-student.jsp?projectName=${projectName}`;
                })
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


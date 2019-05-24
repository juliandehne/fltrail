/**
 * This function will fire when the DOM is ready
 */
let groupId = 0;
$(document).ready(function () {
    getMyGroupId(getFullSubmissionOfGroup);


    $('#btnNext').click(function () {

            if (quill.getText().length > 1) {
                let user = getUserEmail();
                let content = quill.getContents();
                let html = quill.root.innerHTML;

                // build request
                let fullSubmissionPostRequest = {
                    groupId: groupId,
                    text: JSON.stringify(content),
                    html: html,
                    projectName: $('#projectName').text().trim()
                };

                // save request in database
                createFullSubmission(fullSubmissionPostRequest, function (response) {

                    // jump to next page
                    location.href = "create-unstructured-annotation.jsp?projectName=" + $('#projectName').text().trim() + "&submissionId=" + response.id;
                });
            } else {
                alert("Ein Text wird benötigt");
            }
            // fetch user and text

    });

    $('#backToTasks').click(function(){
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


function getFullSubmissionOfGroup(groupId) {
    let projectName = $('#projectName').html().trim();
    $.ajax({
        url: '../rest/submissions/full/groupId/' + groupId + '/project/' + projectName,
        type: 'GET',
        headers: {
            "Cache-Control": "no-cache"
        },
        success: function (fullSubmission) {
            //set content in Quill here
            quill.setContents(JSON.parse(fullSubmission.text));
        },
        error: function () {

        }
    })
}

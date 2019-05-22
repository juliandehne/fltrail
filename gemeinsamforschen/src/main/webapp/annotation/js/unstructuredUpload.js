/**
 * This function will fire when the DOM is ready
 */

$(document).ready(function () {

    $('#btnNext').click(function () {

            if (quill.getText().length > 1) {
                let user = getUserEmail();
                let content = quill.getContents();
                let html = quill.root.innerHTML;

                // build request
                let fullSubmissionPostRequest = {
                    user: user,
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
        location.href = "../../project/tasks-student.jsp?projectName="+$('#projectName').text().trim();
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

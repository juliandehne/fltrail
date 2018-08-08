/**
 * This function will fire when the DOM is ready
 */
$(document).ready(function() {

    $('#btnNext').click(function () {
        if ($('#upload-textarea-form').valid()) {

            // fetch user and text
            let user = getUserTokenFromUrl();
            let text = $('#upload-textarea').val();

            // build request
            var fullSubmissionPostRequest = {
                user: user,
                text: text
            };

            // save request in database
            createFullSubmission(fullSubmissionPostRequest, function (response) {
                // clear textarea
                $('#upload-textarea').val("");

                // jump to next page
                location.href="unstructured-annotation.jsp?token=" + getUserTokenFromUrl() + "&submission=" + response.id;
            });
        }
    });

    $('#btnBack').click(function () {
        // if there is text inside the textarea
        if ($('#upload-textarea').val().trim().length > 0) {
            // show user alert message that the text will be lost
            if (window.confirm("Möchten Sie zur vorherigen Seite zurückkehren? \nIhr bisheriger Text wird nicht gespeichert.")) {
                // clear textarea
                $('#upload-textarea').val("");

                // jump to previous page
                location.href="project-student.jsp?token="+getUserTokenFromUrl();
            }
        }
        // nothing to check
        else {
            // jump to previous page
            location.href="project-student.jsp?token="+getUserTokenFromUrl();
        }
    });

    /**
     * validation of upload textarea
     */
    $('#upload-textarea-form').validate({
        rules: {
            uploadtextarea: {
                required: true
            }
        },
        messages: {
            uploadtextarea: {
                required: "Ein Text wird benötigt"
            }
        }
    });

});




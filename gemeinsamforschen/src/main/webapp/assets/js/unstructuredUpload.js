/**
 * This function will fire when the DOM is ready
 */
$(document).ready(function() {

    $('#btnNext').click(function () {
        if ($('#upload-textarea-form').valid()) {
            location.href="project-student.jsp?token="+getUserTokenFromUrl();
        }
    });

    $('#btnBack').click(function () {
        location.href="project-student.jsp?token="+getUserTokenFromUrl();
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




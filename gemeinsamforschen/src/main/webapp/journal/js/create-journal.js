$(document).ready(function () {
    let projectName = $('#projectName').html().trim();
    let userEmail = $('#userEmail').html().trim();
    $('#student').val(projectName);
    $('#project').val(userEmail);

    $('#backLink').on('click', function () {
        location.href = "eportfolio.jsp";
    });

    $.ajax({
        url: "../rest/projectdescription/" + student + "/" + project
    }).then(function (data) {
        $('#editor').append(data.descriptionMD);

        //TODO preselect in select tags
        new InscrybMDE({
            element: document.getElementById("editor"),
            spellChecker: false,
            forceSync: true,
        });

        console.log(data);

    });
});
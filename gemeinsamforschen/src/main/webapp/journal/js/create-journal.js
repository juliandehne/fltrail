var student = getQueryVariable("token");
var project = getQueryVariable("projectName");


$(document).ready(function () {
    $('#student').val(student);
    $('#project').val(project);

    $('#backLink').on('click', function () {
        location.href = "eportfolio.jsp";
    });

    $.ajax({
        url: "../rest/projectdescription/" + student + "/" + project
    }).then(function (data) {
        $('#editor').append(data.descriptionMD);

        //TODO preselet in select tags
        new InscrybMDE({
            element: document.getElementById("editor"),
            spellChecker: false,
            forceSync: true,
        });

        console.log(data);

    });
})
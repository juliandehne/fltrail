var student = getQueryVariable("token");
var project = getQueryVariable("projectId");
var journal = getQueryVariable("journal");


$(document).ready(function () {
    $('#student').val(student);
    $('#project').val(project);

    $('#backLink').on('click', function () {
        location.href = "eportfolio.jsp?token=" + student + "&projectId=" + project;
    });

    $.ajax({
        url: "../rest/journal/" + journal
    }).then(function (data) {
        $('#editor').append(data.entryMD);
        $('#journalid').val(journal);
        $('#visibility').val(data.visibility);
        $('#category').val(data.category);
        new InscrybMDE({
            element: document.getElementById("editor"),
            spellChecker: false,
            forceSync: true,
        });

        console.log(data);

    });
})
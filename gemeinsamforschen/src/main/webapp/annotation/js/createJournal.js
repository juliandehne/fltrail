$(document).ready(function () {
    $('#student').val(student);
    $('#project').val(project);

    $('#backLink').on('click', function () {
        location.href = "eportfolio.jsp";
    });

    let journalID = getQueryVariable("journal");
    console.log(journalID);
    if (journalID) {

        $.ajax({
            url: "../rest/journal/" + journalID
        }).then(function (data) {
            $('#editor').append(data.entryMD);

            new InscrybMDE({
                element: document.getElementById("editor"),
                spellChecker: false,
                forceSync: true,
            });
            $('#journalid').val(journalID);
            console.log(data);

        });
    } else {
        new InscrybMDE({
            element: document.getElementById("editor"),
            spellChecker: false,
            forceSync: true,
        });

        $('#journalid').val("0");
    }


});
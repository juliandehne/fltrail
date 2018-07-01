$(document).ready(function() {
    $.ajax({
        url: "../rest/projectdescription/0"
        }).then(function(data) {
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
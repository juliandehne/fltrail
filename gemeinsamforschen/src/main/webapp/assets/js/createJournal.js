function getQueryVariable(variable)
{
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return pair[1];}
    }
    return(false);
}

$(document).ready(function() {
    $('#backLink').on('click', function(){
        location.href="eportfolio.jsp?token="+getUserTokenFromUrl();
    });

    var journalID = getQueryVariable("journal");
    console.log(journalID);
    if(journalID){
        $.ajax({
            url: "../rest/journal/"+journalID
        }).then(function(data) {
            $('#editor').append(data.entryMD);

            //TODO preselet in select tags
            new InscrybMDE({
                element: document.getElementById("editor"),
                spellChecker: false,
                forceSync: true,
            });

            console.log(data);

        });
    } else {
        new InscrybMDE({
            element: document.getElementById("editor"),
            spellChecker: false,
            forceSync: true,
        });
    }


})
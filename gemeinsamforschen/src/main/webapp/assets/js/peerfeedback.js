/**
 * POST: Save an peerfeedback in the database
 *
 * @param Peer2PeerFeedback The post request
 * @param responseHandler The response handler
 */
/**function createPeerfeedback(Peer2PeerFeedback, responseHandler) {
    var url = "../rest/peerfeedback/save";
    var json = JSON.stringify(Peer2PeerFeedback);
    $.ajax({
        url: url,
        type: "POST",
        data: json,
        contentType: "application/json",
        dataType: "json",
        success: function (response) {
            responseHandler(response);
        }
    });
}*/

var student = getQueryVariable("token");
var project = getQueryVariable("projectId");

function go(){
    var a = document.getElementById("editor").valueOf().toString();
    var b = document.getElementById("as");
    b.innerHTML = a.toString();

    //b = document.getElementById("editor").innerHTML;

        if ($('#form').valid()) {
            // get title and comment from form
            var text = $('#editor').val();

            // save the new annotation in db and display it
            //saveNewAnnotation(title, comment, startCharacter, endCharacter);
        }

}

$(document).ready(function() {
    $('#student').val(student);
    $('#project').val(project);

    $.ajax({
        url: "../rest/peerfeedback/save"
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
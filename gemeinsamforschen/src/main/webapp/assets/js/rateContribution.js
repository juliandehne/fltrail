$(document).ready(function () {
    new InscrybMDE({
        element: document.getElementById("presentationFeedback"),
        spellChecker: false,
        //toolbar: ["bold", "italic", "heading", "|", "quote", "table", "code", "|" , "side-by-side", "fullscreen"],
        minHeight: "80px",
    });
    new InscrybMDE({
        element: document.getElementById("dossierFeedback"),
        spellChecker: false,
        //toolbar: ["bold", "italic", "heading", "|", "quote", "table", "code", "|" , "side-by-side", "fullscreen"],
        minHeight: "80px",
    });


    //editor.style = "min-height: 100px";


    $('#submit').on('click', function () {
        safeContributionRating();
    });
});

function safeContributionRating() {
    let contributions = $('.contributionRating');
    ///////initialize variables///////
    let dataP = {};

    ///////read values from html///////
    for (let contribution = 0; contribution < contributions.length; contribution++) {
        let checkbox = $("#" + contributions[contribution].id + " input:checked");
        dataP[checkbox.attr('name')] = checkbox.val();
    }
    let projectId = $('#projectId').html().trim();
    let fromPeer = $('#user').html().trim();
    let toGroup = $('.peerStudent').attr('id');
    $.ajax({
        url: '../rest/assessments/contributionRating/projectId/' + projectId +
        '/studentId/' + toGroup + '/fromPeer/' + fromPeer,
        type: 'POST',
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        data: JSON.stringify(dataP),
        success: function () {
            location.href = "project-student.jsp?token=" + getUserTokenFromUrl() + "&projectId=" + $('#projectId').html().trim();
        },
        error: function (a, b, c) {

        }
    });
}
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

    whichGroupToRate();

    //editor.style = "min-height: 100px";


    $('#submit').on('click', function () {
        safeContributionRating();
    });
});

function whichGroupToRate() {
    let projectName = $('#projectName').html().trim();
    let userName = $('#user').html().trim();
    $.ajax({
        url: '../rest/assessments/groupRate/project/' + projectName + '/student/' + userName,
        type: 'GET',
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        success: function (groupId) {
            $('#groupId').html(groupId);
        },
        error: function () {

        }
    })
}

function safeContributionRating() {
    let contributions = $('.contributionRating');
    ///////initialize variables///////
    let dataP = {};

    ///////read values from html///////
    for (let contribution = 0; contribution < contributions.length; contribution++) {
        let checkbox = $("#" + contributions[contribution].id + " input:checked");
        dataP[checkbox.attr('name')] = checkbox.val();
    }
    let fromPeer = $('#user').html().trim();
    let groupId = $('#groupId').html().trim();
    $.ajax({
        url: '../rest/assessments/contributionRating/group/' + groupId + '/fromPeer/' + fromPeer,
        type: 'POST',
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        data: JSON.stringify(dataP),
        success: function () {
            location.href = "project-student.jsp?projectName="+projectName;
        },
        error: function (a, b, c) {

        }
    });
}
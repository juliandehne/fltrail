let groupId = 0;
let hierarchyLevel;
let fullSubmissionId = "";
let contributionCategory;
$(document).ready(function () {
    getMyGroupId(getFullSubmissionOfGroup);
    let contribution = getQueryVariable("contribution");
    $('#contributionCategory').html(contribution[0] + contribution.substring(1, contribution.length).toLowerCase());
    $('#finalize').on("click", function () {
        let content = quill.getContents();
        let html = quill.root.innerHTML;

        // build request
        let fullSubmissionPostRequest = {
            groupId: groupId,
            text: JSON.stringify(content),
            html: html,
            projectName: $('#projectName').text().trim(),
            contributionCategory: contribution.toUpperCase()
        };
        updateFullSubmission(fullSubmissionId, fullSubmissionPostRequest, true, function () {
            location.href = "../project/tasks-student.jsp?projectName=" + $('#projectName').html().trim();
        });
    });

    $('#btnSave').click(function () {

        if (quill.getText().length > 1) {
            let content = quill.getContents();
            let html = quill.root.innerHTML;

            // build request
            let fullSubmissionPostRequest = {
                groupId: groupId,
                text: JSON.stringify(content),
                html: html,
                projectName: $('#projectName').text().trim(),
                contributionCategory: contribution.toUpperCase()
            };

            // save request in database
            updateFullSubmission(fullSubmissionId, fullSubmissionPostRequest, false, function () {

                // back to main page
                location.href = hierarchyLevel + "project/tasks-student.jsp?projectName=" + $('#projectName').text().trim();
            });
        } else {
            alert("Ein Text wird benötigt");
        }
        // fetch user and text

    });

});
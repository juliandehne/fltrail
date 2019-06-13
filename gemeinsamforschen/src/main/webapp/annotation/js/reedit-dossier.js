let groupId = 0;
let fullSubmissionId = "";
let contributionCategory;
$(document).ready(function () {
    getMyGroupId(function (groupId) {
        getFullSubmissionOfGroup(groupId, 1)
    });
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
            contributionCategory: contribution.toUpperCase(),
            visibility: 'GROUP'
        };
        updateFullSubmission(fullSubmissionPostRequest, true, function () {
            location.href = "../project/tasks-student.jsp?projectName=" + $('#projectName').html().trim();
        });
    });
    $('#backToTasks').on('click', function () {
        location.href = "../project/tasks-student.jsp?projectName=" + projectName;
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
                contributionCategory: contribution.toUpperCase(),
                visibility: 'GROUP'
            };

            // save request in database
            updateFullSubmission(fullSubmissionPostRequest, false, function () {

                // back to main page
                location.href = "../project/tasks-student.jsp?projectName=" + $('#projectName').text().trim();
            });
        } else {
            alert("Ein Text wird benötigt");
        }
        // fetch user and text

    });

});
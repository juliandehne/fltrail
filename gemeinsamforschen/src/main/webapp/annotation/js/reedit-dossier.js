let groupId = 0;
let fullSubmissionId = "";
let fileRole;
let projectName;
let contribution = getQueryVariable("contribution");

$(document).ready(function () {
    handleLocker("REEDIT_DOSSIER");
    projectName = $('#projectName').text().trim();
    getMyGroupId(function (groupId) {
        getFullSubmissionOfGroupToEditor(groupId, 1)
    });
    $('#fileRole').html(contribution[0] + contribution.substring(1, contribution.length).toLowerCase());
    $('#btnSave').click(saveEditedDossierTrue);
    $('#btnSave2').click(saveEditedDossierFalse);
    quill.focus();
});

function saveEditedDossierTrue() {
    saveEditedDossier(true);
}

function saveEditedDossierFalse() {
    saveEditedDossier(false);
}

function saveEditedDossier(finalized) {

    if (quill.getText().length > 1) {
        let content = quill.getContents();
        let html = quill.root.innerHTML;
        let header = "";
        let ownTitle = $('#ownTitle');
        if (ownTitle.is("input")) {
            header = ownTitle.val();
        } else {
            header = ownTitle.html();
        }
        // build request
        let fullSubmissionPostRequest = {
            header: header,
            id: fullSubmissionId,
            groupId: groupId,
            text: JSON.stringify(content),
            html: html,
            projectName: $('#projectName').text().trim(),
            fileRole: contribution.toUpperCase(),
            visibility: 'GROUP'
        };
        //let finalized = $('#finalizeReedit').prop("checked");
        // save request in database
        updateFullSubmission(fullSubmissionPostRequest, finalized, function () {

            // back to main page
            location.href = "../project/tasks-student.jsp?projectName=" + $('#projectName').text().trim();
        });
    } else {
        alert("Ein Text wird benötigt");
    }
    // fetch user and text

}
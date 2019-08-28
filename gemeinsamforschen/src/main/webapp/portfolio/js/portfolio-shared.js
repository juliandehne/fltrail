quillNewComment = [];

function fillWithExtraTemplateData(fullSubmission, groupId, userEmail, editable) {
    fullSubmission.editable = (fullSubmission.userEmail === userEmail || (fullSubmission.groupId === groupId && fullSubmission.visibility !== 'PERSONAL')) && editable;
    fullSubmission.timestampDateTimeFormat = new Date(fullSubmission.timestamp).toLocaleString();
    if (!fullSubmission.active) {
        fullSubmission.active = false;
    }
    if (fullSubmission.userEmail) {
        fullSubmission.creator = fullSubmission.userEmail;
    } else {
        fullSubmission.creator = "Gruppe " + fullSubmission.groupId;
    }
    if (fullSubmission.groupId === groupId && fullSubmission.userEmail !== userEmail) {
        fullSubmission.creator += " (Ihre Gruppe)";
    }

}

function fillWithTemplateMetadata(templateData) {
    templateData.scriptBegin = "<script>";
    templateData.scriptEnd = "</script>";
}

async function getContributionFeedbackFromSubmission(fullSubmissionId, groupId) {
    let contributionFeedbacks = await getAllContributionFeedback(fullSubmissionId);
    if (contributionFeedbacks) {
        for (let feedback of contributionFeedbacks) {
            fillWithExtraTemplateData(feedback, groupId, false);
        }
    }
    return contributionFeedbacks;
}

async function addContributionFeedback(fullSubmission, groupId) {
    fullSubmission.contributionFeedback = await getContributionFeedbackFromSubmission(fullSubmission.id, groupId);
}

function eventHandling() {
    /*$('.collapsed').on('click', function (e) {
        let distance = $(this).offset().top + $(this).height();
        $('html,body').animate({scrollTop: distance}, 'slow');
    });*/
}
quillNewComment = [];

function fillWithExtraTemplateData(fullSubmission, groupId, userEmail) {
    fullSubmission.editable = fullSubmission.userEmail === userEmail || fullSubmission.userEmail == null && fullSubmission.groupId === groupId;
    fullSubmission.timestampDateTimeFormat = new Date(fullSubmission.timestamp).toLocaleString();
    if (fullSubmission.userEmail) {
        fullSubmission.creator = fullSubmission.userEmail;
    } else {
        fullSubmission.creator = "Gruppe " + fullSubmission.groupId;
    }
    fullSubmission.wantToComment = false;
}

function fillWithTemplateMetadata(templateData) {
    templateData.scriptBegin = "<script>";
    templateData.scriptEnd = "</script>";
}

async function getContributionFeedbackFromSubmission(fullSubmissionId, groupId) {
    let contributionFeedbacks = await getAllContributionFeedback(fullSubmissionId);
    if (contributionFeedbacks) {
        for (let feedback of contributionFeedbacks) {
            fillWithExtraTemplateData(feedback, groupId);
        }
    }
    return contributionFeedbacks;
}

async function addContributionFeedback(fullSubmission, groupId) {
    fullSubmission.contributionFeedback = await getContributionFeedbackFromSubmission(fullSubmission.id, groupId);
}
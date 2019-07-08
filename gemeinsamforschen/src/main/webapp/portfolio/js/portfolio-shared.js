function fillWithExtraTemplateData(data, groupId, userEmail) {
    data.scriptBegin = "<script>";
    data.scriptEnd = "</script>";
    data.editable = data.userEmail === userEmail || data.userEmail == null && data.groupId === groupId;
    data.timestampDateTimeFormat = new Date(data.timestamp).toLocaleString();
    if (data.userEmail) {
        data.creator = data.userEmail;
    } else {
        data.creator = "Gruppe " + data.groupId;
    }
    data.wantToComment = false;
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
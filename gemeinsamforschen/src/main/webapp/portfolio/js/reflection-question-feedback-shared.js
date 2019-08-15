function convertObjectsToFullSubmissions(objectList) {
    let fullSubmissionList = [];
    for (let entry of objectList) {
        let fullSubmission = {};
        fullSubmission.id = entry.answer.id;
        fullSubmission.text = entry.answer.text;
        fullSubmission.header = entry.question.question;
        fullSubmission.userEmail = entry.question.userEmail;
        fullSubmission.timestamp = entry.answer.timestamp;
        fullSubmissionList.push(fullSubmission);
    }
    return fullSubmissionList;
}

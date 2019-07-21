function skip() {
    chooseAssessmentMaterial(function () {
        location.href = "../project/tasks-student.jsp?projectName=" + $('#projectName').html().trim();
    })
}
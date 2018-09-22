var student = getQueryVariable("token");
var project = getQueryVariable("projectId");

$(document).ready(function () {
    $('#student').val(student);
    $('#project').val(project);
    $('#backLink').on('click', function () {
        location.href = "createReportOverview.jsp?token=" + student + "&projectId=" + project;
        return false;
    });

    $('#forwardLink').on('click', function () {
        location.href = "researchReportResearch.jsp?token=" + student + "&projectId=" + project;
        return false;
    });

})
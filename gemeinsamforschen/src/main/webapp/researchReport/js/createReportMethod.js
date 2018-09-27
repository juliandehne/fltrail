var student = getQueryVariable("token");
var project = getQueryVariable("projectName");

$(document).ready(function () {
    $('#student').val(student);
    $('#project').val(project);
    $('#backLink').on('click', function () {
        location.href = "createReportOverview.jsp";
        return false;
    });

    $('#forwardLink').on('click', function () {
        location.href = "researchReportResearch.jsp";
        return false;
    });

})
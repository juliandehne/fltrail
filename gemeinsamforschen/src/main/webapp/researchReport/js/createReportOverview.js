var student = getQueryVariable("token");
var project = getQueryVariable("projectId");

$(document).ready(function () {
    $('#student').val(student);
    $('#project').val(project);
    $('#uploader').on('click', function () {
        //location.href="create-title.jsp?token="+getUserTokenFromUrl();
        //boolean uploaded=true;
    });
    $('#backLink').on('click', function () {
        location.href = "overview-student.jsp?token=" + student + "&projectId=" + project;
        return false;
    });

    $('#forwardLink').on('click', function () {
        location.href = "create-title.jsp?token=" + student + "&projectId=" + project;
        return false;
    });

})

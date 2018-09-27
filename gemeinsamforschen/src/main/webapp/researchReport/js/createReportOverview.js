var student = getQueryVariable("token");
var project = getQueryVariable("projectName");

$(document).ready(function () {
    $('#student').val(student);
    $('#project').val(project);
    $('#uploader').on('click', function () {
        //location.href="create-title.jsp";
        //boolean uploaded=true;
    });
    $('#backLink').on('click', function () {
        location.href = "overview-student.jsp";
        return false;
    });

    $('#forwardLink').on('click', function () {
        location.href = "create-title.jsp";
        return false;
    });

})

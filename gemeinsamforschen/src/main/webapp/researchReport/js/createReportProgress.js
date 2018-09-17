var student = getQueryVariable("token");
var project = getQueryVariable("projectId");

$(document).ready(function () {
    $('#student').val(student);
    $('#project').val(project);

    $('#title').on('click', function () {
        location.href = "create-title.jsp?token=" + student + "&projectId=" + project;
        return false;
    });
    $('#recherche').on('click', function () {
        location.href = "create-research.jsp?token=" + student + "&projectId=" + project;
        return false;
    });
    $('#bibo').on('click', function () {
        location.href = "create-bibliography.jsp?token=" + student + "&projectId=" + project;
        return false;
    });
    $('#question').on('click', function () {
        location.href = "create-question.jsp?token=" + student + "&projectId=" + project;
        return false;
    });
    $('#concept').on('click', function () {
        location.href = "create-concept.jsp?token=" + student + "&projectId=" + project;
        return false;
    });
    $('#method').on('click', function () {
        location.href = "create-method.jsp?token=" + student + "&projectId=" + project;
        return false;
    });
    $('#reportDo').on('click', function () {
        location.href = "create-process-description.jsp?token=" + student + "&projectId=" + project;
        return false;
    });
    $('#evaluation').on('click', function () {
        location.href = "create-evaluation.jsp?token=" + student + "&projectId=" + project;
        return false;
    });

})

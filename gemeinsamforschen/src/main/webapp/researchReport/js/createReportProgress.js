var student = getQueryVariable("token");
var project = getQueryVariable("projectName");

$(document).ready(function () {
    $('#student').val(student);
    $('#project').val(project);

    $('#title').on('click', function () {
        location.href = "create-title.jsp";
        return false;
    });
    $('#recherche').on('click', function () {
        location.href = "create-research.jsp";
        return false;
    });
    $('#bibo').on('click', function () {
        location.href = "create-bibliography.jsp";
        return false;
    });
    $('#question').on('click', function () {
        location.href = "create-question.jsp";
        return false;
    });
    $('#concept').on('click', function () {
        location.href = "create-concept.jsp";
        return false;
    });
    $('#method').on('click', function () {
        location.href = "create-method.jsp";
        return false;
    });
    $('#reportDo').on('click', function () {
        location.href = "create-process-description.jsp";
        return false;
    });
    $('#evaluation').on('click', function () {
        location.href = "create-evaluation.jsp";
        return false;
    });

})

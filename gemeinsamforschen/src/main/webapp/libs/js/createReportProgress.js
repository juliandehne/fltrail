var student = getQueryVariable("token");
var project = getQueryVariable("projectId");

$(document).ready(function() {
    $('#student').val(student);
    $('#project').val(project);

    $('#title').on('click', function(){
        location.href = "researchReportTitle.jsp?token=" + student + "&projectId=" + project;
        return false;
    });
    $('#recherche').on('click', function(){
        location.href = "researchReportRecherche.jsp?token=" + student + "&projectId=" + project;
        return false;
    });
    $('#bibo').on('click', function(){
        location.href = "researchReportBibo.jsp?token=" + student + "&projectId=" + project;
        return false;
    });
    $('#question').on('click', function(){
        location.href = "researchReportQuestion.jsp?token=" + student + "&projectId=" + project;
        return false;
    });
    $('#concept').on('click', function(){
        location.href = "researchReportConcept.jsp?token=" + student + "&projectId=" + project;
        return false;
    });
    $('#method').on('click', function(){
        location.href = "researchReportMethod.jsp?token=" + student + "&projectId=" + project;
        return false;
    });
    $('#reportDo').on('click', function(){
        location.href = "researchReportDo.jsp?token=" + student + "&projectId=" + project;
        return false;
    });
    $('#evaluation').on('click', function(){
        location.href = "researchReportEvaluation.jsp?token=" + student + "&projectId=" + project;
        return false;
    });

})

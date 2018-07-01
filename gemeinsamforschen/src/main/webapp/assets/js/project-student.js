$(document).ready(function(){
    $('#projectId').on('click', function(){
        location.href="project-student.jsp?token="+getUserTokenFromUrl();
    });

    $('.givefeedback').click(function () {
        location.href="annotation-document.jsp?token="+getUserTokenFromUrl();
    })
});
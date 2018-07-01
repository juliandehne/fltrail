$(document).ready(function(){
    $('#projectId').on('click', function(){
        location.href="project-student.jsp?token="+getUserTokenFromUrl();
    });
});
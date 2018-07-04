$(document).ready(function(){
    $('#project1Link').on('click', function(){
        location.href="project-student.jsp?token="+getUserTokenFromUrl();
    });
    $('#enrollProject').on('click', function(){
        location.href="enrollProject.jsp?token="+getUserTokenFromUrl();
    });
});
$(document).ready(function(){
    $('#project1Link').on('click', function(){
        location.href="project-docent.jsp?token="+getUserTokenFromUrl();
    });
    $('#project2Link').on('click', function(){
        location.href="project-docent.jsp?token="+getUserTokenFromUrl();
    });
    $('#createProject').on('click', function(){
        location.href="createProject.jsp?token="+getUserTokenFromUrl();
    });

});
$(document).ready(function(){
    $('#project1Link').on('click', function(){
        location.href="project-docent.jsp?token="+getUserTokenFromUrl();
    });
});

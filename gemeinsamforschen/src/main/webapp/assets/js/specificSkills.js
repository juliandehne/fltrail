$(document).ready(function () {
    $('#submit').on('click',function(){
        document.location="project-student.jsp?token="+getUserTokenFromUrl();
    });
});
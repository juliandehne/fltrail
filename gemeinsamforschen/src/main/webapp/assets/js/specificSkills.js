$(document).ready(function () {
    $('#submit').on('click',function(){
        document.location="project-docent.jsp?token="+getUserTokenFromUrl();
    });
});
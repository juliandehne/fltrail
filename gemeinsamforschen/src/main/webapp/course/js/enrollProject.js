$(document).ready(function () {
    $('#submit').on('click',function(){
        document.location="specificSkills.jsp?token="+getUserEmail();
    });
});
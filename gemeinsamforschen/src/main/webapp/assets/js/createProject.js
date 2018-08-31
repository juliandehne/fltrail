$(document).ready(function(){
    $('#submit').on('click', function(){
        location.href="specificRequirement.jsp?token="+getUserTokenFromUrl();
    });
});

$(document).ready(function(){
    $('#givefeedback').on('click', function(){
        location.href="givefeedback.jsp?token="+getUserTokenFromUrl();
    });
});


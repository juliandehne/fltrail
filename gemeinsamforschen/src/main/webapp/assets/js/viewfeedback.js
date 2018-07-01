$(document.getElementById("viewfeedback")).ready(function(){
    $('#viewfeedback').on('click', function(data){
        location.href="viewfeedback.jsp?token="+getUserTokenFromUrl();
    });
});
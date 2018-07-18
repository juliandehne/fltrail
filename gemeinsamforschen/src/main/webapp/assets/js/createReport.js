$(document).ready(function() {
        $('#backLink').on('click', function(){
                location.href="researchReportTitle.jsp?token="+getUserTokenFromUrl();
            })})
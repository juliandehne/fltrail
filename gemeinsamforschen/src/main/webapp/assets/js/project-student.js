$(document).ready(function(){

    $('.projectId').click(function () {
        location.href="project-student.jsp?token="+getUserTokenFromUrl();
    });

    $('.givefeedback').click(function () {
        location.href="givefeedback.jsp?token="+getUserTokenFromUrl();
    })

    $('.viewfeedback').click(function () {
        location.href="viewfeedback.jsp?token="+getUserTokenFromUrl();
    })

    $('.annotationview').click(function () {
        location.href="annotation-document.jsp?token="+getUserTokenFromUrl();
    })

    $('. overviewstudent').click(function () {
        location.href="project-student.jsp?token="+getUserTokenFromUrl();
    })

});
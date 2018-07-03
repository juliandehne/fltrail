$(document).ready(function () {
    $("#save").on('click', function () {
        document.location = "project-student.jsp?token=" + getUserTokenFromUrl();
    });
});

$(document).ready(function () {
    $("#save").on('click', function () {
        document.location = "Quiz.jsp?token=" + getUserTokenFromUrl();
    });
});

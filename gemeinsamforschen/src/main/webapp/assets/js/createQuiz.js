$(document).ready(function () {
    $("#save").on('click', function () {//todo: remember to cut out whitespace and signs (?.,;)
        document.location = "Quiz.jsp?token=" + getUserTokenFromUrl();
    });
});

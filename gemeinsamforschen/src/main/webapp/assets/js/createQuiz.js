$(document).ready(function () {
    $("#save").on('click', function () {
        var quiz = {
            question: 'who am I',//todo: naja halt Quizskelett finden, erstellen und so URL encoden
            answer: ''
        };
        $.ajax({
            url: '../rest/assessments/quiz',
            type: 'POST',
            success: function(){

        },
        error: function(a){

        }
        });
        document.location = "Quiz.jsp?token=" + getUserTokenFromUrl();
    });
});

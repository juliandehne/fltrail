$(document).ready(function () {
    $('#addCorrectAnswer').on('click', function(){
        var divCorrectAnswer = document.getElementById('correctAnswers');
        var i = divCorrectAnswer.children.length;
        var inputCorrectAnswer = document.createElement('INPUT');
        inputCorrectAnswer.id='correctAnswer'+i;
        divCorrectAnswer.appendChild(inputCorrectAnswer);
    });

    $('#addIncorrectAnswer').on('click', function(){
        var divIncorrectAnswer = document.getElementById('incorrectAnswers');
        var i = divIncorrectAnswer.children.length;
        var inputIncorrectAnswer = document.createElement('INPUT');
        inputIncorrectAnswer.id='incorrectAnswer'+i;
        divIncorrectAnswer.appendChild(inputIncorrectAnswer);
    });

    $('#deleteCorrectAnswer').on('click', function(){
        var divCorrectAnswer = document.getElementById('correctAnswers');
        divCorrectAnswer.removeChild(divCorrectAnswer.lastChild);
    });

    $('#deleteIncorrectAnswer').on('click', function(){
        var divIncorrectAnswer = document.getElementById('incorrectAnswers');
        divIncorrectAnswer.removeChild(divIncorrectAnswer.lastChild);
    });

    $("#save").on('click', function () {
        var correctAnswers= [];
        var incorrectAnswers= [];
        var shuttleList = document.getElementById('correctAnswers');
        for (var i=0; i<shuttleList.children.length; i++)
        {
            correctAnswers.push(shuttleList.children[i].value.trim())
        }
        shuttleList = document.getElementById('incorrectAnswers');
        for (i=0; i<shuttleList.children.length; i++)
        {
            incorrectAnswers.push(shuttleList.children[i].value.trim())
        }
        var quiz = {
            question: $('#question').val().trim(),
            type: 'mc',
            correctAnswers: correctAnswers,
            incorrectAnswers: incorrectAnswers
        };
        var studentIdentifier = {
            studentId: $('#user').html().trim(),
            projectId: $('#projectId').html().trim()
        };
        var data = JSON.stringify({
            studentIdentifier: studentIdentifier,
            quiz: quiz
        });
        $.ajax({
            data: data,
            url: '../rest/assessments/quiz',
            headers: {
                "Content-Type": "application/json",
                "Cache-Control": "no-cache"
            },
            type: 'POST',
            success: function(){

        },
        error: function(a){

        }
        });
       //document.location = "Quiz.jsp?token=" + getUserTokenFromUrl();
    });
});

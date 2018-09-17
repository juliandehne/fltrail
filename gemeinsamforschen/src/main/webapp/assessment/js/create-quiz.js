$(document).ready(function () {
    $('#addCorrectAnswer').on('click', function () {
        let divCorrectAnswer = document.getElementById('correctAnswers');
        let i = divCorrectAnswer.children.length;
        let inputCorrectAnswer = document.createElement('INPUT');
        inputCorrectAnswer.id = 'correctAnswer' + i;
        divCorrectAnswer.appendChild(inputCorrectAnswer);
    });

    $('#addIncorrectAnswer').on('click', function () {
        let divIncorrectAnswer = document.getElementById('incorrectAnswers');
        let i = divIncorrectAnswer.children.length;
        let inputIncorrectAnswer = document.createElement('INPUT');
        inputIncorrectAnswer.id = 'incorrectAnswer' + i;
        divIncorrectAnswer.appendChild(inputIncorrectAnswer);
    });

    $('#deleteCorrectAnswer').on('click', function () {
        let divCorrectAnswer = document.getElementById('correctAnswers');
        divCorrectAnswer.removeChild(divCorrectAnswer.lastChild);
    });

    $('#deleteIncorrectAnswer').on('click', function () {
        let divIncorrectAnswer = document.getElementById('incorrectAnswers');
        divIncorrectAnswer.removeChild(divIncorrectAnswer.lastChild);
    });

    $("#save").on('click', function () {
        let correctAnswers = [];
        let incorrectAnswers = [];
        let shuttleList = document.getElementById('correctAnswers');
        for (var i = 0; i < shuttleList.children.length; i++) {
            correctAnswers.push(shuttleList.children[i].value.trim())
        }
        shuttleList = document.getElementById('incorrectAnswers');
        for (i = 0; i < shuttleList.children.length; i++) {
            incorrectAnswers.push(shuttleList.children[i].value.trim())
        }
        let quiz = {
            question: $('#question').val().trim(),
            type: 'mc',
            correctAnswers: correctAnswers,
            incorrectAnswers: incorrectAnswers
        };
        let studentIdentifier = {
            studentId: $('#user').html().trim(),
            projectId: $('#projectId').html().trim()
        };
        let data = JSON.stringify({
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
            success: function () {
                location.href = "quiz.jsp?token=" + getUserTokenFromUrl() + "&projectId=" + $('#projectId').html().trim();
            },
            error: function (a) {

            }
        });
    });
});

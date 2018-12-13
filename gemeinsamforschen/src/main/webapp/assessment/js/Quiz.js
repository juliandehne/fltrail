$(document).ready(function () {
    let projectName = document.getElementById('projectName').innerText.trim();
    let userName = document.getElementById('userEmail').innerText.trim();
    $.ajax({
        url: '../rest/assessments/project/' + projectName + '/quiz/author/' + userName,
        projectName: projectName,
        type: 'GET',
        success: function (data) {
            let table = document.getElementById('myQuizzes');
            for (let quiz = 0; quiz < data.length; quiz++) {
                let answers = data[quiz].correctAnswers.concat(data[quiz].incorrectAnswers);
                let colspan = answers.length;
                let trQuestion = document.createElement('TR');
                trQuestion.className = "pageChanger";
                trQuestion.innerHTML = '<td colspan="' + colspan + '"><h3>' +
                    '<a href="view-quiz.jsp' +
                    '?projectName=' + projectName +
                    '&quizId=' + encodeURIComponent(data[quiz].question) + '"</a>' +
                    data[quiz].question + '</h3></td>';
                table.appendChild(trQuestion);
            }
        },
        error: function (a, b) {
            alert('Fehler ' + b);
        }
    });

    $('#newQuiz').on('click', function () {
        location.href = "create-quiz.jsp?projectName=" + projectName;
    });
});

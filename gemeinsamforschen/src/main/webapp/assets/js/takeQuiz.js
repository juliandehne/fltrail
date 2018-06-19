$(document).ready(function () {
    $.ajax({
        url: 'http://localhost:8080/gemeinsamforschen/rest/assessments/project/1/quiz/',
        type: 'GET',
        success: function (data) {
            var table = document.getElementById('tableQuiz');
            for (var quiz = 0; quiz < data.length; quiz++){
                var answers = data[quiz].correctAnswers.concat(data[quiz].incorrectAnswers);
                var colspan = answers.length;
                var trQuestion = document.createElement('TR');
                var question = '<td colspan="' + colspan + '" class="questionTd">' + data[quiz].question + '</td>';
                trQuestion.innerHTML = question;
                var trAnswers = document.createElement('TR');
                answers = shuffle(answers);
                var answersTd='<td style="display: block;">';
                for (var i = 0; i < answers.length; i++) {
                    answersTd = answersTd + '<div><label><input type="checkbox">' + answers[i] + '</label></div>';
                }
                trAnswers.innerHTML = answersTd+'</td>';
                table.appendChild(trQuestion);
                table.appendChild(trAnswers);
            }
        },
        error: function (a, b, c) {
            alert('Fehler ' + a);
        }
    });
    $("#submitQuiz").on("click", function () {

    });
});

function shuffle(a) {
    var j, x, i;
    for (i = a.length - 1; i > 0; i--) {
        j = Math.floor(Math.random() * (i + 1));
        x = a[i];
        a[i] = a[j];
        a[j] = x;
    }
    return a;
}
$(document).ready(function () {
    var loading = $('#loadbar').hide();
    $(document)
        .ajaxStart(function () {
            loading.show();
        }).ajaxStop(function () {
        loading.hide();
    });

    $("label.btn").on('click', function () {
        var choice = $(this).find('input:radio').val();
        $('#loadbar').show();
        $('#quiz').fadeOut();
        setTimeout(function () {
            $("#answer").html($(this).checking(choice));
            $('#quiz').show();
            $('#loadbar').fadeOut();
            /* something else */
        }, 1500);
    });

    $ans = 3;

    $.fn.checking = function (ck) {
        if (ck != $ans)
            return 'INCORRECT';
        else
            return 'CORRECT';
    };
    var parts = window.location.search.substr(1).split("&");
    var $_GET = {};
    for (var i = 0; i < parts.length; i++) {
        var temp = parts[i].split("=");
        $_GET[decodeURIComponent(temp[0])] = decodeURIComponent(temp[1]);
    }
    var quizId = $_GET['quizId'];
    var projectId = document.getElementById('projectId').innerText.trim();
    $.ajax({
        url: '../rest/assessments/project/'+projectId+'/quiz/'+quizId,
        type: 'GET',
        success: function (data) {
            var table = document.getElementById('tableQuiz');
            var answers = data.correctAnswers.concat(data.incorrectAnswers);
            var colspan = answers.length;
            var trQuestion = document.createElement('TR');
            var question = '<td colspan="' + colspan + '">' + data.question + '</td>';
            trQuestion.innerHTML = question;
            var trAnswers = document.createElement('TR');
            var answersTd = '<div class="quiz" id="quiz" data-toggle="buttons"><td style="display: block;">';
            for (var i = 0; i < data.correctAnswers.length; i++) {
                answersTd = answersTd + '<div><label class="element-animation1 btn btn-lg btn-success btn-block"><span class="btn-label"><i class="glyphicon glyphicon-chevron-right"></i></span><input type="checkbox">' + data.correctAnswers[i] + '</label></div>';
            }
            for (i = 0; i < data.incorrectAnswers.length; i++) {
                answersTd = answersTd + '<div><label class="element-animation1 btn btn-lg btn-danger btn-block"><span class="btn-label"><i class="glyphicon glyphicon-chevron-right"></i></span><input type="checkbox">' + data.incorrectAnswers[i] + '</label></div>';
            }
            trAnswers.innerHTML = answersTd + '</div></td>';
            table.appendChild(trQuestion);
            table.appendChild(trAnswers);
        },
        error: function (a, b, c) {
            alert('Fehler ' + a);
        }
    });
    $("#submitQuiz").on("click", function () {

    });
});
$(document).ready(function () {
    let loading = $('#loadbar').hide();
    $(document)
        .ajaxStart(function () {
            loading.show();
        }).ajaxStop(function () {
        loading.hide();
    });

    $("label.btn").on('click', function () {
        let choice = $(this).find('input:radio').val();
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
    let parts = window.location.search.substr(1).split("&");
    let $_GET = {};
    for (let i = 0; i < parts.length; i++) {
        let temp = parts[i].split("=");
        $_GET[decodeURIComponent(temp[0])] = decodeURIComponent(temp[1]);
    }
    let quizId = encodeURIComponent($_GET['quizId']);
    let author = $('#userEmail').html().trim();
    let projectName = $('#projectName').html().trim();
    $.ajax({
        url: '../rest/assessments/project/' + projectName + '/quiz/' + quizId + '/author/' + author,
        type: 'GET',
        success: function (data) {
            let table = document.getElementById('tableQuiz');
            let answers = data.correctAnswers.concat(data.incorrectAnswers);
            let colspan = answers.length;
            let trQuestion = document.createElement('TR');
            let question = '<td colspan="' + colspan + '">' + data.question + '</td>';
            trQuestion.innerHTML = question;
            let trAnswers = document.createElement('TR');
            let answersTd = '<div class="quiz" id="quiz" data-toggle="buttons"><td style="display: block;">';
            for (let i = 0; i < data.correctAnswers.length; i++) {
                answersTd = answersTd + '<div><label class="element-animation1 btn btn-lg btn-success btn-block"><span class="btn-label"><i class="glyphicon glyphicon-chevron-right"></i></span><input type="checkbox">' + data.correctAnswers[i] + '</label></div>';
            }
            for (let i = 0; i < data.incorrectAnswers.length; i++) {
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
    $("#deleteQuiz").on("click", function () {
        $.ajax({
            url: '../rest/assessments/quiz/' + encodeURIComponent(quizId),
            type: 'POST',
            success: function () {
                document.location.href = "Quiz.jsp?projectName=" + projectName;
            },
            error: function (a) {
                alert(a)
            }
        });
    });
});
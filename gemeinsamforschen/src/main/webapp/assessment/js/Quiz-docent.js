$(document).ready(function () {
    $('#newQuiz').on('click', function () {
        location.href = "create-quiz.jsp?projectName="+projectName;
    });

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

    let projectName = document.getElementById('projectName').innerText.trim();
    $.ajax({
        url: '../rest/assessments/project/' + projectName + '/quiz/',
        type: 'GET',
        success: function (data) {
            let table = document.getElementById('tableQuiz');
            for (let quiz = 0; quiz < data.length; quiz++) {
                let question = data[quiz].question.replace(/ /g,"").replace(/\?/g,"").replace(/,/g,"");
                question = question.replace(/\"/g, "").replace(/\'/g,"");
                let answers = data[quiz].correctAnswers.concat(data[quiz].incorrectAnswers);
                let colspan = answers.length;
                let trQuestion = document.createElement('TR');
                let tdQuestion = '<td colspan="' + colspan + '"' +
                    ' data-toggle="collapse" href="#' + question + '" aria-expanded="false" aria-controls="' + question + '">' +
                    '' + data[quiz].question + '</td>';
                trQuestion.innerHTML = tdQuestion;
                let trAnswers = document.createElement('TR');
                let answersTd = '<td style="display: block;">' +
                    '<div ' +
                    'class="quiz collapse" ' +
                    'id="' + question + '" ' +
                    'data-toggle="buttons">' +
                    '<p hidden>' + data[quiz].question + '</p>';
                for (let i = 0; i < answers.length; i++) {
                    answersTd = answersTd + '<div>' +
                        '<label class="element-animation1 btn btn-lg btn-primary btn-block">' +
                        '<span class="btn-label">' +
                        '<i class="glyphicon glyphicon-chevron-right">' +
                        '</i>' +
                        '</span>' +
                        '<input type="checkbox" value="' + answers[i] + '">' + answers[i] + '' +
                        '</label>' +
                        '</div>';
                }
                tdQuestion = "";
                answers = [];
                let deletebutton = '<button class="btn btn-danger" id="delete' + question + '">löschen</button>';
                trAnswers.innerHTML = answersTd + deletebutton + '</div></td>';
                table.appendChild(trQuestion);
                table.appendChild(trAnswers);
                $("#delete" + question).click({quizId: data[quiz].question}, deleteQuiz);
            }
        },
        error: function (a) {
            alert('Fehler ' + a);
        }
    });

    function deleteQuiz(event) {
        $.ajax({
            url: '../rest/assessments/quiz/' + encodeURIComponent(event.data.quizId),
            type: 'POST',
            success: function () {
                document.location.href = "quiz-docent.jsp?projectName="+projectName;
            },
            error: function (a) {
                alert(a)
            }
        });
    }
});

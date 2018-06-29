$(document).ready(function () {
    var loading = $('#loadbar').hide();
    $(document)
        .ajaxStart(function () {
            loading.show();
        }).ajaxStop(function () {
        loading.hide();
    });

    $("label.btn").on('click',function () {
        var choice = $(this).find('input:radio').val();
        $('#loadbar').show();
        $('#quiz').fadeOut();
        setTimeout(function(){
            $( "#answer" ).html(  $(this).checking(choice) );
            $('#quiz').show();
            $('#loadbar').fadeOut();
            /* something else */
        }, 1500);
    });

    $ans = 3;

    $.fn.checking = function(ck) {
        if (ck != $ans)
            return 'INCORRECT';
        else
            return 'CORRECT';
    };

    $.ajax({
        url: '../rest/assessments/project/1/quiz/',
        type: 'GET',
        success: function (data) {
            var table = document.getElementById('tableQuiz');
            for (var quiz = 0; quiz < data.length; quiz++){
                var question = data[quiz].question.replace(/ /g,"").replace("?","").replace(",","");
                var answers = data[quiz].correctAnswers.concat(data[quiz].incorrectAnswers);
                var colspan = answers.length;
                var trQuestion = document.createElement('TR');
                var tdQuestion = '<td colspan="' + colspan + '"' +
                    'data-toggle="collapse" href="#'+question+'" aria-expanded="false" aria-controls="'+question+'">' +
                    '' + data[quiz].question + '</td>';
                trQuestion.innerHTML = tdQuestion;
                var trAnswers = document.createElement('TR');
                answers = shuffle(answers);
                var answersTd='<td style="display: block;"><div class="quiz collapse" id="'+question+'" data-toggle="buttons">';
                for (var i = 0; i < answers.length; i++) {
                    answersTd = answersTd + '<div>' +
                        '<label class="element-animation1 btn btn-lg btn-primary btn-block">' +
                        '<span class="btn-label">' +
                        '<i class="glyphicon glyphicon-chevron-right">' +
                        '</i>' +
                        '</span>' +
                        '<input type="checkbox">' + answers[i] + '' +
                        '</label>' +
                        '</div>';
                }
                tdQuestion ="";
                answers=[];
                trAnswers.innerHTML = answersTd+'</div></td>';
                table.appendChild(trQuestion);
                table.appendChild(trAnswers);
            }
        },
        error: function (a, b, c) {
            alert('Fehler ' + a);
        }
    });
    $("#submitQuiz").on("click", function () {
        location.href="rateContribution.jsp?token="+getUserTokenFromUrl();
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
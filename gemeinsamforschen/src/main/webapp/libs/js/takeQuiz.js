$(document).ready(function () {
    let loading = $('#loadbar').hide();
    $(document)
        .ajaxStart(function () {
            loading.show();
        }).ajaxStop(function () {
        loading.hide();
    });

    $("label.btn").on('click',function () {
        let choice = $(this).find('input:radio').val();
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

    let projectId = document.getElementById('projectId').innerText.trim();
    $.ajax({
        url: '../rest/assessments/project/'+projectId+'/quiz/',
        type: 'GET',
        success: function (data) {
            let table = document.getElementById('tableQuiz');
            for (let quiz = 0; quiz < data.length; quiz++){
                let question = data[quiz].question.replace(/ /g,"").replace("?","").replace(",","");
                let answers = data[quiz].correctAnswers.concat(data[quiz].incorrectAnswers);
                let colspan = answers.length;
                let trQuestion = document.createElement('TR');
                let tdQuestion = '<td colspan="' + colspan + '"' +
                    ' data-toggle="collapse" href="#'+question+'" aria-expanded="false" aria-controls="'+question+'">' +
                    '' + data[quiz].question + '</td>';
                trQuestion.innerHTML = tdQuestion;
                let trAnswers = document.createElement('TR');
                answers = shuffle(answers);
                let answersTd='<td style="display: block;">' +
                    '<div ' +
                    'class="quiz collapse" ' +
                    'id="'+question+'" ' +
                    'data-toggle="buttons">' +
                    '<p hidden>'+data[quiz].question+'</p>';
                for (let i = 0; i < answers.length; i++) {
                    answersTd = answersTd + '<div>' +
                        '<label class="element-animation1 btn btn-lg btn-primary btn-block">' +
                        '<span class="btn-label">' +
                        '<i class="glyphicon glyphicon-chevron-right">' +
                        '</i>' +
                        '</span>' +
                        '<input type="checkbox" value="'+answers[i]+'">' + answers[i] + '' +
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
        error: function (a) {
            alert('Fehler ' + a);
        }
    });
    $("#submitQuiz").on("click", function () {
        safeQuizAnswers();
    });
});

function shuffle(a) {
    let j, x, i;
    for (i = a.length - 1; i > 0; i--) {
        j = Math.floor(Math.random() * (i + 1));
        x = a[i];
        a[i] = a[j];
        a[j] = x;
    }
    return a;
}

function safeQuizAnswers(){
    let quizzes = $('.quiz');
    ///////initialize variables///////
    let dataP = {};

    ///////read values from html///////
    for (let quiz=0; quiz<quizzes.length; quiz++){
        let answerList = [];
        if (quizzes[quiz].id !== ""){
            let checkedBoxes = $("#"+quizzes[quiz].id+" input:checked");
            checkedBoxes.each(function(){
                answerList.push($(this).val());
            });
            let question = $("#"+quizzes[quiz].id+" p").html().trim();
            dataP[question]= answerList;
        }
    }
    let projectId=$('#projectId').html().trim();
    let studentId=$('#user').html().trim();
    $.ajax({
        url:'../rest/assessments/quizAnswer/projectId/'+projectId+'/studentId/'+studentId,
        type: 'POST',
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        data: JSON.stringify(dataP),
        success: function(){
            location.href="rate-contribution.jsp?token="+getUserTokenFromUrl()+"&projectId="+$('#projectId').html().trim();
        },
        error: function(a,b,c){

        }
    });
}
$(document).ready(function(){
    let projectId = document.getElementById('projectId').innerText.trim();
    $.ajax({
        url: '../rest/assessments/project/'+projectId+'/quiz/',
        projectId: projectId,
        type: 'GET',
        success: function (data) {
            let table = document.getElementById('myQuizzes');
            for (let quiz = 0; quiz < data.length; quiz++){
                let answers = data[quiz].correctAnswers.concat(data[quiz].incorrectAnswers);
                let colspan = answers.length;
                let trQuestion = document.createElement('TR');
                trQuestion.className="pageChanger";
                trQuestion.innerHTML = '<td colspan="' + colspan + '"><h3>' +
                    '<a href="viewQuiz.jsp' +
                    '?token='+getUserTokenFromUrl()+
                    '&projectId='+projectId+
                    '&quizId='+ encodeURIComponent(data[quiz].question)+'"</a>' +
                    data[quiz].question+'</h3></td>';
                table.appendChild(trQuestion);
            }
        },
        error: function (a) {
            alert('Fehler ' + a);
        }
    });

    $('#newQuiz').on('click', function(){
        location.href="createQuiz.jsp?token="+getUserTokenFromUrl()+"&projectId="+$('#projectId').html().trim();
    });
});

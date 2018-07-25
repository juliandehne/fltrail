$(document).ready(function(){
    var projectId = document.getElementById('projectId').innerText.trim();
    $.ajax({
        url: '../rest/assessments/project/'+projectId+'/quiz/',
        projectId: projectId,
        type: 'GET',
        success: function (data) {
            var table = document.getElementById('myQuizzes');
            for (var quiz = 0; quiz < data.length; quiz++){
                var answers = data[quiz].correctAnswers.concat(data[quiz].incorrectAnswers);
                var colspan = answers.length;
                var trQuestion = document.createElement('TR');
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
        error: function (a, b, c) {
            alert('Fehler ' + a);
        }
    });

    $('#newQuiz').on('click', function(){
        location.href="createQuiz.jsp?token="+getUserTokenFromUrl()+"&projectId="+$('#projectId').html().trim();
    });
});

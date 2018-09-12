$(document).ready(function(){
    $('#headLineProject').html($('#projectId').html());
    $('#logout').click(function(){
        //todo: delete cookies / reset session
        document.location="../index.jsp";
    });
    $('#assessment').click(function(){
       checkAssessementPhase();
    });
    $('#footerBack').click(function(){
       goBack();
    });
});

function goBack() {
    window.history.back();
}

function checkAssessementPhase(){
    let studentId = $('#user').html().trim();
    let projectId = $('#projectId').html().trim();
    $.ajax({
        url: '../rest/assessments/whatToRate/project/'+projectId+'/student/'+studentId,
        type: 'GET',
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        success: function (phase) {
            switch (phase){
                case "workRating":{
                    document.location="finalAssessment.jsp?token=" + getUserTokenFromUrl() + "&projectId=" + $('#projectId').html().trim();
                    break;
                }
                case "quiz":{
                    location.href = "takeQuiz.jsp?token=" + getUserTokenFromUrl() + "&projectId=" + $('#projectId').html().trim();
                    break;
                }
                case "contributionRating":{
                    location.href = "rateContribution.jsp?token=" + getUserTokenFromUrl() + "&projectId=" + $('#projectId').html().trim();
                    break;
                }
                case "done":{
                    location.href = "project-student.jsp?token=" + getUserTokenFromUrl() + "&projectId=" + $('#projectId').html().trim();
                    break;
                }
            }
        },
        error: function(a){
        }
    });
}

function getUserTokenFromUrl() {
    let parts = window.location.search.substr(1).split("&");
    let $_GET = {};
    for (let i = 0; i < parts.length; i++) {
        let temp = parts[i].split("=");
        $_GET[decodeURIComponent(temp[0])] = decodeURIComponent(temp[1]);
    }
    return $_GET['token'];

}

function getQueryVariable(variable) {
    let query = window.location.search.substring(1);
    let vars = query.split("&");
    for (let i = 0; i < vars.length; i++) {
        let pair = vars[i].split("=");
        if (pair[0] === variable) {
            return pair[1];
        }
    }
    return (false);
}


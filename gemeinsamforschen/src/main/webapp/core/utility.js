$(document).ready(function(){
    $('#headLineProject').html($('#projectId').html());
    $('#logout').click(function(){
        //todo: delete cookies / reset session
        let target = "index.jsp";
        let link = changeLocationTo(target);
        document.location=link;
    });
    $('#assessment').click(function(){
       checkAssessementPhase();
    });
    $('#footerBack').click(function(){
       goBack();
    });
});

function changeLocationTo(target) {
    let level = $('#hierarchyLevel').html().trim();
    let link = calculateHierachy(level) + target;
    return link;
}


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
                    changeLocationTo("finalAssessment.jsp?token=" + getUserTokenFromUrl() + "&projectId=" + $('#projectId').html().trim());
                    break;
                }
                case "quiz":{
                    changeLocationTo("take-quiz.jsp?token=" + getUserTokenFromUrl() + "&projectId=" + $('#projectId').html().trim());
                    break;
                }
                case "contributionRating":{
                    changeLocationTo("rate-contribution.jsp?token=" + getUserTokenFromUrl() + "&projectId=" + $('#projectId').html().trim());
                    break;
                }
                case "done":{
                    changeLocationTo("project-student.jsp?token=" + getUserTokenFromUrl() + "&projectId=" + $('#projectId').html().trim());
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



function calculateHierachy(level) {

    if (level == 0) {

        return "";

    } else {

        return calculateHierachy(level-1)+"../";

    }

}
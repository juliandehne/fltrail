$(document).ready(function () {
    $('#headLineProject').html($('#projectName').html());
    $('#logout').click(function () {
        //todo: delete cookies / reset session
        let target = "index.jsp";
        let link = changeLocationTo(target);
        document.location = link;
    });
    $('#assessment').click(function () {
        checkAssessementPhase();
    });
    $('#footerBack').click(function () {
        goBack();
    });
});

function changeLocationTo(target) {
    let level = $('#hierarchyLevel').html().trim();
    return calculateHierachy(level) + target;
}


function goBack() {
    window.history.back();
}

function checkAssessementPhase() {
    let userName = $('#user').html().trim();
    let projectName = $('#projectName').html().trim();
    $.ajax({
        url: 'rest/assessments/whatToRate/project/' + projectName + '/student/' + userName,
        type: 'GET',
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        success: function (phase) {
            switch (phase) {
                case "workRating": {
                    changeLocationTo("finalAssessment.jsp");
                    break;
                }
                case "quiz": {
                    changeLocationTo("take-quiz.jsp");
                    break;
                }
                case "contributionRating": {
                    changeLocationTo("rate-contribution.jsp");
                    break;
                }
                case "done": {
                    changeLocationTo("project-student.jsp");
                    break;
                }
            }
        },
        error: function (a) {
        }
    });
}

function getUserEmail() {
    return $('#userEmail').html().trim();
}


function getProjectName() {
    return getQueryVariable("projectName");
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

        return calculateHierachy(level - 1) + "../";

    }
}
$(document).ready(function () {
    let projectId = $('#projectId').html().trim();
    $.ajax({
        url: '../rest/phases/projects/'+projectId,
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (response) {
            let phaseDiv = $('#'+response);
            if (phaseDiv !== null){
                phaseDiv.toggleClass('alert-info');
            } else {
                $('#end').toggleClass('alert-info');
            }
        },
        error: function (a) {

        }
    });
    $('#btnAssessment').on('click', function(){
        changePhase('Assessment');
    });
    $('#btnExecution').on('click', function(){
        changePhase('Execution');
    });
    $('#btnGroupformation').on('click', function(){
        changePhase('GroupFormation');
    });
    $('#btnCourseCreation').on('click', function(){
        changePhase('CourseCreation');
    });
    $('#btnDossierFeedback').on('click', function(){
        changePhase('DossierFeedback');
    });
    $('#btnProjectfinished').on('click', function(){
        changePhase('Projectfinished');
    });
});

function changePhase(toPhase){
    let projectId = $('#projectId').html().trim();
    $.ajax({
        url: '../rest/phases/'+toPhase+'/projects/'+projectId,
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'POST',
        success: function () {
            location.href="#"
        },
        error: function (a) {

        }
    });
}
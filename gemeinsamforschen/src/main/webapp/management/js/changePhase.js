$(document).ready(function () {
    let projectId = $('#projectId').html().trim();
    $.ajax({
        url: '../rest/phases/projects/' + projectId,
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (response) {
            let arrow = $('#changePhase');
            arrow.toggleClass('arrow'+response);
            let phaseDiv = $('#' + response);
            if (phaseDiv !== null) {
                phaseDiv.toggleClass('alert-info');
            } else {
                $('#end').toggleClass('alert-info');
            }
        },
        error: function (a) {

        }
    });
    $('#changePhase').on('click', function () {
        let projectId = $('#projectId').html().trim();
        $.ajax({
            url: '../rest/phases/projects/'+projectId,
            headers: {
                "Content-Type": "application/json",
                "Cache-Control": "no-cache"
            },
            type: 'GET',
            success: function (response) {
        changePhase(response);
            },
            error: function (a) {

            }
        });
    });
});

function changePhase(currentPhase) {
    let projectId = $('#projectId').html().trim();
    $.ajax({
        url: '../rest/phases/' + currentPhase + '/projects/' + projectId,
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'POST',
        success: function () {
            location.reload(1);
        },
        error: function (a) {

        }
    });
}
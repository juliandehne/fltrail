$(document).ready(function () {
    //$("#save").on('click', function () {
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
                phaseDiv.toggleClass('alert','alert-info');
            } else {
                $('#end').addClass('alert-info');
            }

        },
        error: function (a) {

        }
    });
    //});
});
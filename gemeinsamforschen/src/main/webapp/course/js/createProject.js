$(document).ready(function () {
    $('#submit').on('click', function () {
        location.href = "specificRequirement.jsp" + getUserEmail();
    });
    let projectName = $('#projectName').html().trim();
    $.ajax({
        url: '../rest/phases/projects/' + projectName,
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (response) {
            let phaseDiv = $('#' + response);
            if (phaseDiv !== null) {
                phaseDiv.toggleClass('alert', 'alert-info');
            } else {
                $('#end').addClass('alert-info');
            }

        },
        error: function (a) {

        }
    });
});
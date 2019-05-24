$(document).ready(function () {
    $('#resizeGroup').on('click', function () {
        $.ajax({
            url: '../rest/project/update/project/' + $('#projectName').html().trim() + '/groupSize/' + $('#groupSize').html().trim(),
            headers: {
                "Cache-Control": "no-cache"
            },
            type: 'POST',
            success: function (response) {
            }
        });
    });
});

function waitForParticipantsResizeGroups() {
    return "" +
        "<p>Sollten Sie sich für eine andere Gruppengröße entschieden haben," +
        "können sie dies hier ändern. </p>" +
        "<label>Präferierte Gruppengröße <input value='3' id='userCount' style='width:20px;'></label>" +
        "<a data-toggle='collapse' href='#howToBuildGroups' role='button' aria-expanded='false' aria-controls='howToBuildGroups'>" +
        "<i class='fas fa-question'></i></a>" +
        "Mit dieser Gruppengröße benötigt das Projekt wenigstens <span id='groupSize'>6</span> Teilnehmer" +
        "um Gruppen bilden zu können." +
        "<div class='collapse' id='howToBuildGroups'>" +
        "<div class='card card-body'>" +
        "Es werden so viele Gruppen mit Ihrer präferierten Gruppengröße gebildet wie möglich." +
        "Die verbleibenden Studenten werden dann zufällig auf die bestehenden Gruppen verteilt." +
        "</div></div>" +
        "<button id='resizeGroup'>speichern</button>";
}


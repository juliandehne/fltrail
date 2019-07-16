$(document).ready(function () {
    $.ajax({
        url: '../rest/fileStorage/isOccupied/project/' + $('#project').html().trim(),
        type: 'GET',
        headers: {
            "Cache-Control": "no-cache"
        },
        success: function () {

        },
        error: function (a, b, c) {
            if (c === "Unauthorized") {
                $("main").addClass("block");
                $('#unauthorized').attr("hidden", false);
            }
        }
    });
});
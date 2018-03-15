/**
 * Created by fides-WHK on 09.01.2018.
 */
$(document).ready(function () {
    $("#projectWrongPassword").hide();
    $("#projectIsMissing").hide();

    $("#projectName").keypress(function (e) {
        if (e.which == 13) {
            getTags();
            document.getElementById("projectPassword").focus();
        }
    });
    $("#projectPassword").keypress(function (e) {
        if (e.which == 13) {
            seeProject($('#projectName').val().trim());
        }
    });
    $("#seeProject").on('click', function () {
        seeProject($('#projectName').val());
    });
});

function seeProject(projectName) {
    var token = getUserTokenFromUrl();
    var url = "../database/getProjects.php?project=" + projectName + "&password=" + document.getElementById('projectPassword').value+"&token="+token;
    if (projectName === "") {
        return false;
    } else {
        $.ajax({
            url: url,
            projectName: projectName,
            Accept: "text/plain; charset=utf-8",
            contentType: "text/plain",
            success: function (response) {
                if (response === "project missing") {
                    $("#projectIsMissing").show();
                } else {
                    if (response === "wrong password") {
                        $("#projectIsMissing").hide();
                        $('#projectWrongPassword').show();
                    } else {
                        window.location.href= "./preferences.php?token="+token+"&projectToken="+response;
                    }
                }
            },
            error: function (a, b, c) {
                console.log(a);
            }
        });
    }
}
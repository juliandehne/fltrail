/**
 * Created by fides-WHK on 09.01.2018.
 */
$(document).ready(function () {
    $("#projectWrongPassword").hide();
    $("#projectIsMissing").hide();

    $("#projectName").keypress(function (e) {
        if (e.which == 13) {
            document.getElementById("projectPassword").focus();
        }
    });
    $("#projectPassword").keypress(function (e) {
        if (e.which == 13) {
            seeProject($('#projectName').val());
        }
    });
    $("#loginProject").on('click', function () {
        seeProject($('#projectName').val());
    });
});

function seeProject(projectName) {
    var url = "../database/getProjects.php?project=" + projectName + "&password=" + document.getElementById('projectPassword').value;
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
                    if (response !== "wrong password") {            //if response !== project missing and not wrong password, its the projectToken
                        location.href="preferences.php?token="+getUserTokenFromUrl()+"&projectToken="+response;
                    } else {
                        $("#projectIsMissing").hide();
                        $('#projectWrongPassword').show();
                    }
                }
            },
            error: function (a, b, c) {
                console.log(a);
            }
        });
    }
}
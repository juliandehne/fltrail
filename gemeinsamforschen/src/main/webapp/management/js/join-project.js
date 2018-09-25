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
            loginProject($('#projectName').val());
        }
    });
    $("#loginProject").on('click', function () {
        loginProject($('#projectName').val());
    });
});

function loginProject(projectName) {
    var password = $('#projectPassword').val();
    var url = "../../gemeinsamforschen/rest/project/login/"+projectName+"?password="+password;
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
                        var projectToken = response;
                        document.location.href = "../groupfinding/enter-preferences.jsp?token="+getUserEmail()+"&projectToken="+projectToken;
                    } else {
                        $("#projectIsMissing").hide();
                        $('#projectWrongPassword').show();
                    }
                }
            },
            error: function (a, b, c) {
                console.log(a);
                $("#projectIsMissing").show();
            }
        });
    }
}
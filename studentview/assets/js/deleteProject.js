/**
 * Created by dehne on 28.03.2018.
 */
$(document).ready(function () {
    $("#projectWrongPassword").hide();
    $("#projectIsMissing").hide();

    $("#deleteProject").on('click', function () {
        deleteProject($('#projectName').val().trim());
    });
});

function deleteProject(projectName) {
    var url = "../database/deleteProject.php?project=" + projectName + "&password=" + document.getElementById('projectPassword').value.trim() + "&token=" + getUserTokenFromUrl();
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
                        $.ajax({
                            url: "https://esb.uni-potsdam.de:8243/services/competenceBase/api1/courses/"+projectName,
                            Accept: "text/plain; charset=utf-8",
                            type: 'DELETE',
                            contentType: "text/plain",
                            success: function (response) {

                            },
                            error: function (a, b, c) {
                                console.log(a);
                            }
                        });
                        window.location.href = " ../pages/projects.php?token="+getUserTokenFromUrl();
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

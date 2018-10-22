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

    let localurl = "../rest/project/delete/project/";
    if (projectName === "") {
        return false;
    } else {
        $.ajax({
            url: localurl,
            projectName: projectName,
            Accept: "text/plain; charset=utf-8",
            contentType: "text/plain",
            success: function (response) {
                if (response === "project missing") {
                    $("#projectIsMissing").show();
                } else {
                    if (response !== "wrong password") {            //if response !== project missing and not wrong password, its the projectName
                        $.ajax({
                            url: compbaseUrl + "/api1/courses/" + projectName,
                            Accept: "text/plain; charset=utf-8",
                            type: 'DELETE',
                            contentType: "text/plain",
                            async: false,
                            success: function (response) {

                            },
                            error: function (a, b, c) {
                                console.log(a);
                            }
                        });
                        window.location.href = " ../pages/projects.php" + getUserEmail();
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

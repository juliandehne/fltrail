/**
 * Created by dehne on 28.03.2018.
 */
$(document).ready(function () {
    $("#projectWrongPassword").hide();
    $("#projectIsMissing").hide();

    $("#deleteProject").on('click', function () {
        let projectName = $('#projectName').text().trim();
        deleteProject(projectName);
    });
});

function deleteProject(projectName) {
    let localurl = "../rest/project/delete/project/" + projectName;
    if (projectName === "") {
        return false;
    } else {
        $.ajax({
            url: localurl,
            data: "",
            projectName: projectName,
            contentType: "text/plain",
            type: 'POST',
            success: function (response) {
                if (response === "project missing") {
                    $("#projectIsMissing").show();
                } else {
                    $.ajax({
                        url: compbaseUrl + "/api1/courses/" + projectName,
                        Accept: "text/plain; charset=utf-8",
                        type: 'DELETE',
                        contentType: "text/plain",
                        async: false,
                        success: function (response) {

                        },
                        error: function (a) {
                            console.log(a);
                        }
                    });
                    window.location.href = " ../projects/overview-docent.jsp";
                }
            },
            error: function (a) {
                console.log(a);
            }
        });
    }
}

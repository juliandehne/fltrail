$(document).ready(function () {

    getProjects(getUserEmail());

    $('#createProject').on('click', function () {
        location.href = "create-project.jsp";
    });


});

function getProjects(userName) {
    $.ajax({
        url: '../rest/project/all/author/' + userName,
        headers: {
            "Content-Type": "text/plain",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (response) {
            let tmplObject = [];
            for (let project in response) {
                if (response.hasOwnProperty(project))
                    tmplObject.push({
                        projectName: response[project].name,
                        projectId: project,
                        projectDescription: response[project].description
                    });
            }
            $('#projectTRTemplate').tmpl(tmplObject).appendTo('#projects');
            for (let projectName in response) {
                if (response.hasOwnProperty(projectName)) {
                    $('#project_' + projectName).on('click', function () {
                        location.href = "tasks-docent.jsp?projectName=" + response[projectName].name;
                    });
                    //updateStatus(response[projectName]);

                }
            }
        },
        error: function (a) {

        }
    });
}

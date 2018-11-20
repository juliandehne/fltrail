$(document).ready(function(){

    getProjects(getUserEmail());

    $('#createProject').on('click', function(){
        location.href="create-project.jsp";
    });



});

function getProjectOverview(user) {
    var url = compbaseUrl + "/api2/user/" + user + "/projects";
    $.ajax({
        url: url,
        user: user,
        type: 'GET',
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            var projects = data.data;
            //printProjects(projects, 0);
            //getProjectsOfAuthor(user, projects, printProjects);
        },
        error: function (a, b, c) {
            console.log(a);
        }
    });
}


function getGroups(projectName) {

    var url = compbaseUrl + "/api2/groups/" + projectName;     //this API is used, since fleckenroller has security
    // issues with
    // CORS and stuff
    $.ajax({
        url: url,
        type: 'GET',
        contentType: "application/json",
        dataType: "json",
        success: function (data) {

        },
        error: function (a, b, c) {

        }
    });
}

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
                        projectDescription: response[project].description
                    });
            }
            $('#projectTRTemplate').tmpl(tmplObject).appendTo('#projects');
            for (let projectName in response) {
                if (response.hasOwnProperty(projectName)) {
                    $('#project_' + response[projectName].name).on('click', function () {
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

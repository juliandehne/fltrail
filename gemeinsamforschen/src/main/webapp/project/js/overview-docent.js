$(document).ready(function(){
    $('#project1Link').on('click', function(){
        location.href = "projects-docent.jsp" + getUserEmail() + '&projectName=' + 'gemeinsamForschen';
    });
    $('#project2Link').on('click', function(){
        location.href = "projects-docent.jsp" + getUserEmail() + '&projectName=' + 'Kaleo';
    });
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

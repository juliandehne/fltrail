$(document).ready(function () {
    let userName = $('#userEmail').html().trim();
    getProjects(userName);
    $('#enrollProject').on('click', function () {
        location.href = "join-project.jsp";
    });
});

function updateStatus(projectName) {
    $.ajax({
        url: '../rest/phases/projects/' + projectName,
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (response) {
            let statusField = $('#status' + projectName);
            switch (response) {
                case "CourseCreation":
                    statusField.html("Der Kurs wurde gerade angelegt. Sie können sich nun anmelden.");
                    break;
                case "GroupFormation":
                    statusField.html("Ihr Dozent ordnet Sie nun einer Gruppe zu.");
                    break;
                case "DossierFeedback":
                    statusField.html("Geben sie wenigstens einem Gruppenmitglied Feedback und erstellen sie ein Dossier in Ihrer Gruppe.");
                    break;
                case "Execution":
                    statusField.html("Forschen Sie zu Ihrer Forschungsfrage und reflektieren Sie ihr Vorgehen mit dem Journal");
                    break;
                case "Assessment":
                    statusField.html("Nehmen Sie die Bewertungen vor.");
                    break;
                case "Projectfinished":
                    getGrade(projectName);
                    break;
                default:
                    break;
            }

        },
        error: function (a) {

        }
    });
}

function getGrade(projectName) {
    let userName = $('#userEmail').html().trim();
    $.ajax({
        url: '../rest/assessments/get/project/' + projectName + '/student/' + userName,
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (response) {
            $('#status_' + projectName).html("Sie erreichten " + response + "%");
        },
        error: function (a) {
        }
    });
}

function getProjects() {
    $.ajax({
        url: '../rest/project/all',
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (response) {
            let tmplObject = [];
            for (let project in response) {
                if (response.hasOwnProperty(project))
                    if (response[project].active) {
                        tmplObject.push({
                            projectName: response[project].name,
                            projectAuthor: response[project].authorEmail,
                            projectDescription: response[project].description
                        });
                    }
            }

            $('#projectTemplate').tmpl(tmplObject).appendTo('#projects');
            for (let project in response) {
                if (response.hasOwnProperty(project)) {
                    $('#project_' + response[project].name).on('click', function () {
                        location.href = "../groupfinding/enter-preferences.jsp?projectName=" + response[project].name;
                    });
                    updateStatus(response[project].name);
                }
            }
        },
        error: function (a) {

        }
    });
}
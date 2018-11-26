// projects from db
var projects = [];

let projectCollectorF = getMyProjects;

let userName = "";

let response = {};


// in the search project view the get variable should be set to "all=true"
$(document).ready(function () {

    if (getQueryVariable("all")) {
        projectCollectorF = getAllProjects;
    }

    userName = $('#userEmail').html().trim();

    projectCollectorF(userName);

    $('#enrollProject').on('click', function () {
        location.href = "join-project.jsp";
    });

    // fill search fields
    $('#searchField').keyup(function () {
        let data = {projects: projects};
        if ($('#searchField').val().trim() == "") {
            repaintDropDown(data);
        } else {
            data.projects = data.projects.filter(filterF);
            repaintDropDown(data)
        }
    });

});

function repaintDropDown(data) {
    $('#projectDropdownDynamic').remove();
    $('#searchingTemplate').tmpl(data).appendTo('#projectDropdown');
}

function filterF(string, index, strings) {
    let searchString = $('#searchField').val().trim();
    return filterString(string, searchString);
}

function filterString(name, filterString) {
    return name.indexOf(filterString) !== -1;
}

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

function printProjectCard(response, project, tmplObject) {
    if (response[project].active) {
        let projectDescription = "Der Kurs wurde beschrieben mit \"" +
            response[project].tags[0] + "\", \"" +
            response[project].tags[1] + "\", \" " +
            response[project].tags[2] + "\", \" " +
            response[project].tags[3] + "\" und \" " +
            response[project].tags[4] + "\"";
        tmplObject.push({
            projectName: response[project].name,
            projectAuthor: response[project].authorEmail,
            projectDescription: projectDescription
        });
    }
}

function repaintProjectList(response, linkUrl, filterList) {
    let tmplObject = [];
    for (let project in response) {
        if (response.hasOwnProperty(project))
            if (filterList != "undefined") {
                if (!filterList.contains(response[project].name)) {
                    printProjectCard(response, project, tmplObject);
                }
            } else {
                printProjectCard(response, project, tmplObject);
            }
    }

    $('#projectTemplate').tmpl(tmplObject).appendTo('#projects');
    for (let project in response) {
        if (response.hasOwnProperty(project)) {
            let projectName = response[project].name;
            if (filterList != "undefined") {
                if (!filterList.contains(projectName)) {
                    $('#project_' + projectName).on('click', function () {
                        location.href = linkUrl + response[project].name;
                    });
                    updateStatus(response[project].name);
                }
            } else {
                projects.push(projectName);
            }
        }
    }
    repaintDropDown({projects: projects});
}

function getMyProjects(userName) {
    $.ajax({
        url: '../rest/project/all/student/' + userName,
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (response) {
            var linkUrl = "tasks-student.jsp?projectName=";
            repaintProjectList(response, linkUrl);
        },
        error: function (a) {
            console.log(a);
        }
    });
}

function getAllProjects() {
    $.ajax({
        url: '../rest/project/not/student/' + userName,
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (response) {
            let linkUrl = "../groupfinding/enter-preferences.jsp?projectName=";
            repaintProjectList(response, linkUrl);
        },
        error: function (a) {
            console.log(a);
        }
    });
}
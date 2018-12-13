// projects from db
let projects = [];
let projectResponse;

let projectCollectorF = getMyProjects;

let userName = "";

let response = {};


// in the search project view the get variable should be set to "all=true"
$(document).ready(function () {
    if (getQueryVariable("all")) {
        $('#headLine').html("Kurssuche");
        projectCollectorF = getAllProjects;
    } else {
        $('#headLine').html("Meine Kurse");
    }

    userName = $('#userEmail').html().trim();

    projectCollectorF(userName);

    $('#enrollProject').on('click', function () {
        location.href = "join-project.jsp";
    });

    // fill search fields
    $('#searchField').keyup(function () {
        let data = {projects: projects};
        if ($('#searchField').val().trim() === "") {
            repaintProjectList(function () {
                buttonHandler()
            });
        } else {
            data.projects = data.projects.filter(filterF);
            repaintProjectList(function () {
                buttonHandler()
            }, data.projects)
        }
    });

});

function repaintDropDown(data) {
    $('#searchingTemplate').tmpl(data).appendTo('#projectDropdown');
}

function filterF(string) {
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
        let projectAction = "Einsehen";
        if (isSearching()) {
            projectAction = "Beitreten";
        }
        tmplObject.push({
            projectName: response[project].name,
            projectAuthor: response[project].authorEmail,
            projectTags: response[project].tags,
            projectDescription: response[project].description,
            projectAction: projectAction
        });
    }
}

function repaintProjectList(callback, filterList) {
    $('.projectDynamic').remove();

    let tmplObject = [];
    // filter project cards
    for (let project in projectResponse) {
        if (projectResponse.hasOwnProperty(project))
            if (filterList !== undefined) {
                if (filterList.includes(projectResponse[project].name)) {
                    printProjectCard([projectResponse[project]], 0, tmplObject);
                }
            } else {
                printProjectCard(projectResponse, project, tmplObject);
            }
    }
    // print projectcards
    $('#projectTemplate').tmpl(tmplObject).appendTo('#projects');
    repaintDropDown({projects: projects});
    callback(filterList);
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
            // initiate project and response arrays
            if (response.length !== 0) {
                projectResponse = response;
                for (let project in projectResponse) {
                    if (projectResponse.hasOwnProperty(project)) {
                        let projectName = projectResponse[project].name;
                        projects.push(projectName);
                    }
                }
                repaintProjectList(function () {
                    buttonHandler()
                });
            } else {
                $('#headLine').html("Sie sind in keinem Kurs eingetragen.");
                $('.select_arrow').each(function () {
                    $(this).hide();
                });
                $('.search').each(function () {
                    $(this).hide();
                });
                $('#introduction').html("Um sich in einen Kurs einzutragen wählen sie oben links \"suche Kurs\".")
            }
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
            // iniate project and response arrays
            projectResponse = response;
            for (let project in projectResponse) {
                if (projectResponse.hasOwnProperty(project)) {
                    let projectName = projectResponse[project].name;
                    projects.push(projectName);
                }
            }
            repaintProjectList(function () {
                buttonHandler()
            });
        },
        error: function (a) {
            console.log(a);
        }
    });
}

function isSearching() {
    return !!getQueryVariable("all");
}

function buttonHandler() {
    // append buttons for project cards
    let linkUrl = "";
    if (isSearching()) {
        linkUrl = "../groupfinding/enter-preferences.jsp?projectName=";
    } else {
        linkUrl = "tasks-student.jsp?projectName=";
    }
    $('.project_Button').each(function () {
        $(this).on('click', function () {
            let projectName = $(this).attr('name');
            location.href = linkUrl + projectName;
            updateStatus(projectName);
        });
    });
}
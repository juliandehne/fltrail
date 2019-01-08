let projects = [];
let projectResponse;
let userName = "";
let response = {};

$(document).ready(function () {

    getProjects(getUserEmail());

    $('#createProject').on('click', function () {
        location.href = "create-project.jsp";
    });
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

function printProjectCard(response, project, tmplObject) {
    if (response[project].active) {
        tmplObject.push({
            projectName: response[project].name,
            projectAuthor: response[project].authorEmail,
            projectTags: response[project].tags,
            projectDescription: response[project].description
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
    $('#projectTRTemplate').tmpl(tmplObject).appendTo('#projects');
    repaintDropDown({projects: projects});
    callback(filterList);
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
                $('#headLine').html("Sie haben noch keine Kurse erstellt.");
                $('.select_arrow').each(function () {
                    $(this).hide();
                });
                $('.search').each(function () {
                    $(this).hide();
                });
                $('#introduction').html("Um ein Projekt zu öffnen, drücken sie oben links auf \"Projekt erstellen\".")
            }
        },
        error: function (a) {

        }
    });
}
function buttonHandler() {
    // append buttons for project cards
    let linkUrl = "tasks-docent.jsp?projectName=";
    $('.project_Button').each(function () {
        $(this).on('click', function () {
            let projectName = $(this).attr('name');
            location.href = linkUrl + projectName;
        });
    });
}

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
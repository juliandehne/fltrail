let projects = [];
let projectResponse;
let userName = "";
let response = {};
let projectTitels = [];
let projectTags = [];
let projectDescription = [];


$(document).ready(function () {

    getProjects(getUserEmail());

    $('#createProject').on('click', function () {
        location.href = "create-project.jsp";
    });
    $('#searchField').keyup(function () {
        let data = {
            projectTitels: projectTitels,
            projectDescription: projectDescription,
            projectTags: projectTags
        };
        if ($('#searchField').val().trim() === "") {
            repaintProjectList(function () {
                buttonHandler()
            });
        } else {
            data.projectTitels = data.projectTitels.filter(filterF);
            data.projectDescription = data.projectDescription.filter(filterF);
            data.projectTags = data.projectTags.filter(filterF);
            repaintProjectList(function () {
                buttonHandler()
            }, data.projectDescription.concat(data.projectTitels).concat(data.projectTags));
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
                if (filterList.includes(projectResponse[project].name) ||
                    filterList.includes(projectResponse[project].description) ||
                    filterList.includes(projectResponse[project].tags)) {
                    printProjectCard([projectResponse[project]], 0, tmplObject);
                }
            } else {
                printProjectCard(projectResponse, project, tmplObject);
            }
    }
    if (tmplObject.length === 0) {
        $('#projectDropdown').hide();
    } else {
        $('#projectDropdown').show();
    }
    // print projectcards
    $('#projectTRTemplate').tmpl(tmplObject).appendTo('#projects');
    repaintDropDown({projects: projectTitels});
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
                        projectTitels.push(projectResponse[project].name);
                        projectTags.push(projectResponse[project].tags);
                        projectDescription.push(projectResponse[project].description);

                    }
                }
                repaintProjectList(function () {
                    buttonHandler()
                });
            } else {
                $('#headLine').html("Sie haben noch keine Kurse erstellt.");
                $('#selfMade').hide();
                $('.select_arrow').each(function () {
                    $(this).hide();
                });
                $('.search').each(function () {
                    $(this).hide();
                });
                $('#introduction').html("Um ein Projekt zu öffnen, drücken Sie oben links auf \"Projekt erstellen\".")
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

function filterF(searchObj) {
    let searchString = $('#searchField').val().trim().toLowerCase();
    return filterString(searchObj, searchString);
}

function filterString(searchObj, filterString) {
    if (Array.isArray(searchObj)) {
        for (let i = 0; i < searchObj.length; i++) {
            if (searchObj[i].toLowerCase().indexOf(filterString) !== -1) {
                return true;
            }
        }
        return false;
    } else {
        return searchObj.toLowerCase().indexOf(filterString) !== -1;
    }
}
// projects from db
let projectTitels = [];
let projectTags=[];
let projectDescription=[];
let projectResponse;
let projectCollectorF = getMyProjects;
let userName = "";
let response = {};

// in the search project view the get variable should be set to "all=true"
$(document).ready(function () {
    $('introduction').hide();
    if (getQueryVariable("all")) {
        $('#headLine').html("Projekt finden");
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

function repaintDropDown(data) {
    $('#searchingTemplate').tmpl(data).appendTo('#projectDropdown');
}

function filterF(searchObj) {
    let searchString = $('#searchField').val().trim().toLowerCase();
    return filterString(searchObj, searchString);
}

function filterString(searchObj, filterString) {
    if (Array.isArray(searchObj)){
        for (let i =0; i<searchObj.length; i++){
            if (searchObj[i].toLowerCase().indexOf(filterString)!==-1){
                return true;
            }
        }
        return false;
    }else{
        return searchObj.toLowerCase().indexOf(filterString) !== -1;
    }
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
                    statusField.html("Geben Sie wenigstens einem Gruppenmitglied Feedback und erstellen sie ein Dossier in Ihrer Gruppe.");
                    break;
                case "Execution":
                    statusField.html("Forschen Sie zu Ihrer Forschungsfrage und reflektieren Sie Ihr Vorgehen mit dem Journal");
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
        url: '../rest/assessment/get/project/' + projectName + '/student/' + userName,
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
            projectAction: projectAction,
            isSearching: isSearching(),
            passwordRequired: response[project].password !=="",
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
                    filterList.includes(projectResponse[project].description)||
                    filterList.includes(projectResponse[project].tags)) {
                    printProjectCard([projectResponse[project]], 0, tmplObject);
                }
            } else {
                printProjectCard(projectResponse, project, tmplObject);
            }
    }
    if (tmplObject.length===0){
        $('#projectDropdown').hide();
    }else{
        $('#projectDropdown').show();
    }
    // print projectcards
    $('#projectTRTemplate').tmpl(tmplObject).appendTo('#projects');
    repaintDropDown({projects: projectTitels});
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
                        projectTitels.push(projectResponse[project].name);
                        projectTags.push(projectResponse[project].tags);
                        projectDescription.push(projectResponse[project].description);
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

                $('#introduction').show().html("Um sich in einen Kurs einzutragen wählen Sie oben links" +
                    "<a href=\"courses-student.jsp?all=true\"> \"suche Kurs\"</a>.")
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
                    projectTitels.push(projectResponse[project].name);
                    projectTags.push(projectResponse[project].tags);
                    projectDescription.push(projectResponse[project].description);
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
    let linkUrl = "tasks-student.jsp?projectName=";
    $('.project_Button').each(function () {
        $(this).on('click', function () {
            let projectName = $(this).attr('name');
            if (!checkPassword(projectName, $('#projectPassword').val(), function (response) {
                if (response !== undefined) {
                    linkToRegister(projectName, linkUrl);
                    updateStatus(projectName);
                }

            })) {
                document.getElementById('projectWrongPassword').style.display = "block";
            }
        });
    });
}

function linkToRegister(projectName, linkUrl){
    $.ajax({
        url: '../rest/group/get/gfm/projects/' + projectName,
        projectName: projectName,
        linkUrl: linkUrl,
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (response) {
            if (isSearching()){
                switch (response.gfm){
                    case "Manual":
                    case "SingleUser":
                        loginProject(projectName);
                        break;
                    case "LearningGoalStrategy":
                        linkUrl= "../groupfinding/enter-preferences.jsp?projectName=";
                        location.href = linkUrl + projectName;
                        break;
                    case "UserProfilStrategy":
                        linkUrl= "../groupfinding/groupALSurvey.jsp?context=fl&projectName=";
                        location.href = linkUrl + projectName;
                        break;
                }
            }else{
                location.href = linkUrl + projectName;
            }
        }
    });
}

function loginProject(projectName) {
    loaderStart();
    let url = "../rest/project/login/" + projectName;
    if (projectName === "") {
        return false;
    } else {
        $.ajax({
            url: url,
            projectName: projectName,
            Accept: "text/plain; charset=utf-8",
            contentType: "text/plain",
            success: function () {
                loaderStop();
                location.href = "tasks-student.jsp?projectName=" + projectName;
            },
            error: function (a) {
                loaderStop();
                console.log(a);
            }
        });
    }
}

function checkPassword(projectName, password, callback) {
    if (password !== undefined) {
        $.ajax({
            url: "../rest/project/login/" + projectName + "/password/" + password,
            Accept: "text/plain; charset=utf-8",
            contentType: "text/plain",
            success: function (response) {
                callback(response);
            },
            error: function () {
                return false;
            }
        });
    }
    callback();
}
$(document).ready(function(){
    let userName = $('#userEmail').html().trim();
    getProjects(userName);
    $('#enrollProject').on('click', function(){
        location.href="join-project.jsp";
    });
});

function updateStatus(projectName){
    $.ajax({
        url: '../rest/phases/projects/'+projectName,
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (response) {
            let statusField = $('#status'+projectName);
            switch (response){
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

function getGrade(projectName){
    let userName = $('#userEmail').html().trim();
    $.ajax({
        url: '../rest/assessments/get/project/'+projectName+'/student/'+userName,
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (response) {
            $('#status_'+projectName).html("Sie erreichten "+response+"%");
        },
        error: function(a){
        }
    });
}

function getProjects(userName){
    $.ajax({
        url: '../rest/project/all/student/' + userName,
        headers: {
            "Content-Type": "text/plain",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (response) {
            let tmplObject = [];
            for (let project in response){
                if (response.hasOwnProperty(project))
                    tmplObject.push({projectName: response[project]});
            }
            $('#projectTRTemplate').tmpl(tmplObject).appendTo('#projects');
            for (let projectName in response){
                if (response.hasOwnProperty(projectName)) {
                    $('#project_' + response[projectName]).on('click', function () {
                        location.href="tasks-student.jsp?projectName="+response[projectName];
                    });
                    updateStatus(response[projectName]);

                }
            }
        },
        error: function(a){

        }
    });
}
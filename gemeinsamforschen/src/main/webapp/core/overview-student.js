$(document).ready(function(){
    let studentId = $('#user').html().trim();
    getProjects(studentId);
    $('#enrollProject').on('click', function(){
        location.href="management/join-project.jsp?token="+getUserEmail();
    });
});

function updateStatus(projectId){
    $.ajax({
        url: 'rest/phases/projects/'+projectId,
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (response) {
            let statusField = $('#status'+projectId);
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
                    getGrade(projectId);
                    break;
                default:
                    break;
            }

        },
        error: function (a) {

        }
    });
}

function getGrade(projectId){
    let studentId = $('#user').html().trim();
    $.ajax({
        url: 'rest/assessments/get/project/'+projectId+'/student/'+studentId,
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (response) {
            $('#status'+projectId).html("Sie erreichten "+response+"%");
        },
        error: function(a){
        }
    });
}

function getProjects(studentId){
    $.ajax({
        url: 'rest/project/all/student/' + studentId,
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
                    $('#project' + response[projectName]).on('click', function () {
                        viewProject(response[projectName]);
                    });
                    updateStatus(response[projectName]);

                }
            }
        },
        error: function(a){

        }
    });
}

function viewProject(projectName){
    $.ajax({
        url: 'rest/project/view/project/' + projectName,
        headers: {
            "Content-Type": "text/plain",
            "Cache-Control": "no-cache"
        },
        type: 'POST',
        success: function (response) {

        },
        error: function(a){
        }
    });
}
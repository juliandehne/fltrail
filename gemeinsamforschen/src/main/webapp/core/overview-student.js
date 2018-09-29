$(document).ready(function(){
    let studentId = $('#user').html().trim();
    console.log(studentId);
    console.log(getQueryVariable("projectId"));
    getProjects(studentId);
    $('#enrollProject').on('click', function(){
        location.href="management/join-project.jsp?token="+getUserTokenFromUrl();
    });
});
let id = "gemeinsamForschen"
console.log(projectId);
function updateStatus(id){
    $.ajax({
        url: 'rest/phases/projects/'+id,
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (response) {
            let statusField = $('#status'+id);
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
                    getGrade(id);
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
            let proj = "gemeinsamForschen"
            console.log(proj);
            for (let project in response){
                if (response.hasOwnProperty(project))
                    tmplObject.push({projectName: response[project]});
            }
            $('#projectTRTemplate').tmpl(tmplObject).appendTo('#projects');

            for (let i=0; i<response.length; i++){
                let projectName = response[i];
                updateStatus(projectName);
                $('#project'+projectName).on('click', function(){
                    location.href = "project-student.jsp?token=" + getUserTokenFromUrl() + '&projectId=' + projectName;
                });
            }
        },
        error: function(a){

        }
    });

    var token= getUserTokenFromUrl();
    var proid = document.getElementById("project");
    proid.href = "project-student.jsp?token="+token;
}
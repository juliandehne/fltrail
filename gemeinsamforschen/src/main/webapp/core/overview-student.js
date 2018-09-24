$(document).ready(function(){
    let studentToken = getUserTokenFromUrl();
    getProjects(studentToken);

    let projectId="gemeinsamForschen";
    updateStatus(projectId);
    $('#project1Link').on('click', function(){
        location.href = "project-student.jsp?token=" + getUserTokenFromUrl() + '&projectId=' + 'gemeinsamForschen';
    });
    $('#project2Link').on('click', function () {
        location.href = "project-student.jsp?token=" + getUserTokenFromUrl() + '&projectId=' + 'Kaleo';
    });
    $('#enrollProject').on('click', function(){
        location.href="management/join-project.jsp?token="+getUserTokenFromUrl();
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

function getProjects(studentToken){
    $.ajax({
        url: 'rest/project/all/author/' + studentToken,
        headers: {
            "Content-Type": "text/plain",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (response) {
            for (let i=0; i<response.size(); i++){
                let projectName = response[i];
            }
        },
        error: function(a){

        }
    });
}
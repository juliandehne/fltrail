$(document).ready(function(){
    let studentId = $('#user').html().trim();
    getProjects(studentId);
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

function getProjects(studentId){
    $.ajax({
        url: 'rest/project/all/student/' + studentId,
        headers: {
            "Content-Type": "text/plain",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (response) {
            let projectTable = document.getElementById('projects');
            for (let i=0; i<response.length; i++){
                let projectName = response[i];
                let projectDiv = document.createElement('DIV');
                let projectTRString = nameToTableString(projectName);
                projectDiv.innerHTML=projectTRString;
                projectTable.appendChild(projectDiv);
                updateStatus(projectName);
                $('#project'+projectName).on('click', function(){
                    location.href = "project-student.jsp?token=" + getUserTokenFromUrl() + '&projectId=' + projectName;
                });
            }
        },
        error: function(a){

        }
    });
}

function nameToTableString(projectName){
    return'<tr class="pageChanger">'+
        '   <td>'+
        '       <a id="project'+projectName+'">'+
        '       <h1>'+projectName+'</h1>'+
        '       </a>'+
        '   </td>'+
        '</tr>'+
        '<tr>'+
        '   <td>'+
        '       <div class="panel panel-default">'+
        '           <div class="panel-heading">'+
        '               <h3 class="panel-title">Newsfeed </h3>'+
        '               Status: <p id="status'+projectName+'"></p>'+
        '           </div>'+
        '           <div class="panel-body">'+
        '               <ul class="list-group">'+
        newsFeedMessages(projectName)+
        '              </ul>'+
        '          </div>'+
        '      </div>'+
        '   </td>'+
        '</tr>'+
        '<tr>'+
        '   <td></td>'+
        '</tr>';
}

function newsFeedMessages(projectName){
    return'                       <li class="list-group-item">'+
    '                           <span>dummy</span>'+
    '                       </li>'+
    '                       <li class="list-group-item">' +
    '                           <span>dummy</span>'+
    '                       </li>'+
    '                       <li class="list-group-item">'+
    '                           <span>dummy</span></li>';
}
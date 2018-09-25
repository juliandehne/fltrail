$(document).ready(function(){
    $('#project1Link').on('click', function(){
        location.href = "project-docent.jsp?token=" + getUserEmail() + '&projectId=' + 'gemeinsamForschen';
    });
    $('#project2Link').on('click', function(){
        location.href = "project-docent.jsp?token=" + getUserEmail() + '&projectId=' + 'Kaleo';
    });
    $('#createProject').on('click', function(){
        location.href="./management/create-project.jsp?token="+getUserEmail();
    });

});
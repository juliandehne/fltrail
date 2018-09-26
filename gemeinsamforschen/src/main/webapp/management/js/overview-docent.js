$(document).ready(function () {
    $('#project1Link').on('click', function () {
        window.location.href = "project-docent_CG.jsp?token=" + getUserTokenFromUrl();
        location.href = "project-docent_CG.jsp?token=" + getUserTokenFromUrl() + '&projectId=' + 'gemeinsamForschen';
    });
    $('#project2Link').on('click', function () {
        window.location.href = "project-docent_CG.jsp?token=" + getUserTokenFromUrl();
        location.href = "project-docent_CG.jsp?token=" + getUserTokenFromUrl() + '&projectId=' + 'Kaleo';
    });
    $('#createProject').on('click', function () {
        location.href = "createProject.jsp?token=" + getUserTokenFromUrl();
    });

});
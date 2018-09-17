$(document).ready(function () {
    $('#project1Link').on('click', function () {
        location.href = "project-student.jsp?token=" + getUserTokenFromUrl() + '&projectId=' + 'gemeinsamForschen';
    });
    $('#project2Link').on('click', function () {
        location.href = "project-student.jsp?token=" + getUserTokenFromUrl() + '&projectId=' + 'Kaleo';
    });
    $('#enrollProject').on('click', function () {
        location.href = "enrollProject.jsp?token=" + getUserTokenFromUrl();
    });
});
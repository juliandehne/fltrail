$(document).ready(function(){
    $('#project1Link').on('click', function(){
        location.href = "project-docent.jsp" + getUserEmail() + '&projectName=' + 'gemeinsamForschen';
    });
    $('#project2Link').on('click', function(){
        location.href = "project-docent.jsp" + getUserEmail() + '&projectName=' + 'Kaleo';
    });
    $('#createProject').on('click', function(){
        location.href="./management/create-project.jsp";
    });

});
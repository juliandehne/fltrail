$(document).ready(function(){
    let userEmail = $('#userEmail').html().trim();
    let projectName = $('#projectName').html().trim();
    fillTasks(projectName, userEmail);
});

function fillTasks(projectName, userEmail){
    $.ajax({
        url: '../rest/tasks/user/'+userEmail+'/project/' + projectName,
        headers: {
            "Content-Type": "text/plain",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (response) {
            let tmplObject = fillObjectWithTasks(response);
            $('#taskTemplate').tmpl(tmplObject).appendTo('#projects');
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
function fillObjectwithTasks(){
    let tempObject=[];
    for (let task in response){
        if (response.hasOwnProperty(project))
            tmplObject.push({projectName: response[project]});
    }
    return tempObject;
}

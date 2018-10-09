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
            for (let task in tmplObject){
                $('#taskTemplate').tmpl(tmplObject[task]).appendTo('#projects');
            }

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
function fillObjectWithTasks(response){
    let tempObject=[];
    for (let task in response){
        if (response.hasOwnProperty(task))
            tmplObject.push({
                taskType: response[task].taskType,
                taskData: response[task].taskData,
                taskName: response[task].taskName,
                hasRenderModel: response[task].hasRenderModel,
                eventCreated: response[task].eventCreated,
                deadline: response[task].deadline,
                groupTask: response[task].groupTask,
                importance: response[task].importance,
                phase: response[task].phase,
                link: response[task].link,
                userEmail: response[task].userEmail,
                projectName: response[task].projectName,
                progress: response[task].progress
            });
    }
    return tempObject;
}
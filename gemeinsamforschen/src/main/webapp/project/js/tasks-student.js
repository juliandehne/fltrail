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
            let object = fillObjectWithTasks(response);
            for (let task in object){
                let tmplObject = fitObjectInTmpl(object[task]);
                $('#taskTemplate').tmpl(tmplObject).appendTo('#projects');
            }
        },
        error: function(a){

        }
    });
}

function fitObjectInTmpl(object){
    let result = {
        taskType: "",
        infoText: "",
        phase: "",
        solveTaskWith: "",
        helpLink: "",
        timeFrame: ""
    };
    if (object.taskType!=="INFO"){
        if (object.groupTask===true){
            result.taskType="grouptask"
        }else{
            result.taskType="usertask"
        }
    }else{
        result.taskType="infotask"
    }
    switch (object.phase){
        case "CourseCreation":
            result.phase="card-draft";
            break;
        case "GroupFormation":
            result.phase="card-grouping";
            break;
        case "DossierFeedback":
            result.phase="card-feedback";
            break;
        case "Execution":
            result.phase="card-execution";
            break;
        case "Assessment":
            result.phase="card-assessment";
            break;
        case "Projectfinished":
            result.phase="card-grades";
            break;
        default:
            result.phase="";
    }
    if (object.link !=="")
        result.helpLink = "<a href=\"#\">"+object.link+"</a>";
    if (object.deadline != null){
        let daysLeft = (object.deadline - Date.now())/1000/60/60/24;
        result.timeFrame="Noch "+daysLeft+" Tage Zeit";
    }else {result.timeFrame="";}
    switch (object.taskName){
        case "WAIT_FOR_PARTICPANTS":
            result.infoText="<h4>Warten Sie auf die Anmeldungen der Studenten.</h4>";
            break;
        case "BUILD_GROUPS":
            result.infoText="<h4>Erstellen Sie die Gruppen.</h4>";
            break;
        case "CLOSE_GROUP_FINDING_PHASE":
            result.infoText="<h4>Gehen Sie zur nächsten Phase über.</h4>";
            break;
        default:
            result.infoText="";
    }
    if (object.taskType!=="INFO"){
        //todo: implement rest
        result.solveTaskWith="<button class='primary'>Lege ein Dossier an</button>"
    }
    return result;
}

function fillObjectWithTasks(response){
    let tempObject=[];
    for (let task in response){
        if (response.hasOwnProperty(task))
            tmplObject.push({
                taskType: response[task].taskType,  //
                taskData: response[task].taskData,
                taskName: response[task].taskName,  //
                hasRenderModel: response[task].hasRenderModel,
                eventCreated: response[task].eventCreated,
                deadline: response[task].deadline, //
                groupTask: response[task].groupTask,//
                importance: response[task].importance,
                phase: response[task].phase,  //
                link: response[task].link,  //
                userEmail: response[task].userEmail,
                projectName: response[task].projectName,
                progress: response[task].progress
            });
    }
    return tempObject;
}
/**
 * TODO @Axel use rendertemplate for participantcount and link manual groups creation as option
 */
$(document).ready(function(){
    let userEmail = $('#userEmail').html().trim();
    let projectName = $('#projectName').html().trim();
    fillTasks(projectName, userEmail);
});

function fillTasks(projectName, userEmail){
    $.ajax({
        url: '../rest/tasks/user/'+encodeURI(userEmail)+'/project/' + projectName,
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (response) {
            let object = fillObjectWithTasks(response);
            for (let task in object){
                let tmplObject = fitObjectInTmpl(object[task]);
                $('#taskTemplate').tmpl(tmplObject).appendTo('#listOfTasks');
            }
        },
        error: function(a){}


    });
}

function fitObjectInTmpl(object){
    let result = {
        taskType: "",
        infoText: "",
        phase: "",
        solveTaskWith: "",
        helpLink: "",
        timeFrame: "",
        taskData: object.taskData,
        taskProgress: ""

    };

    if (object.progress==="FINISHED"){
        if(object.taskName.includes("CLOSE")){
            result.taskProgress="FINISHED";
            result.infoText=object.phase;
            let created = new Date(object.eventCreated);
            let deadline=new Date(object.deadline);
            result.timeFrame="<p>"+created.getDate()+"."+(created.getMonth()+1)+"."+created.getFullYear() +
                " bis "+deadline.getDate()+"."+(deadline.getMonth()+1)+"."+deadline.getFullYear()+"</p>";
            return result;
        }
    }


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
    if (object.deadline != null){
        let daysLeft = Math.round((object.deadline - Date.now())/1000/60/60/24);
        if (daysLeft>=1)
            result.timeFrame="<div class='status icon'><p>Noch "+daysLeft+" Tage Zeit</p></div>";
        else
            result.timeFrame="<div class='status alert icon'><p>Du bist zu spät.</p></div>";
    }else {result.timeFrame="";}
    switch (object.taskName){
        case "WAIT_FOR_PARTICPANTS":
            result.infoText = "Warten Sie auf die Anmeldungen der Studenten.";
            break;
        case "BUILD_GROUPS":
            result.infoText="Erstellen Sie die Gruppen.";
            break;
        case "CLOSE_GROUP_FINDING_PHASE":
            result.infoText="Gehen Sie zur nächsten Phase über.";
            break;
        case "WAITING_FOR_GROUP":
            result.infoText="[STUDENT] Die Arbeitsgruppen werden gebildet. Sie werden informiert, wenn es so weit" +
                " ist.";
            break;

        case "GIVE_FEEDBACK":
            result.infoText="[STUDENT] Geben Sie ein Feedback .....";
            break;
        case "EDIT_FORMED_GROUPS":
            result.infoText = "[TEACHER] Die Gruppen wurden vom Algorithmus gebildet. Sie können noch manuell" +
                " editiert werden."; // hier müsste noch ein Link eingefügt werden, zur manuellen Gruppenbildung
            break;
        default:
            result.infoText="";
    }
    if (object.taskType.includes("LINKED")){
        switch (object.taskName) {
            case "CLOSE_GROUP_FINDING_PHASE":
                result.solveTaskWith="Entwurfsphase starten";
                result.solveTaskWithLink="closePhase(\'"+object.phase+"\', \'"+object.projectName+"\');";
                break;
            case "UPLOAD_DOSSIER":
                result.solveTaskWith="Lege ein Dossier an";
                result.solveTaskWithLink="redirect(\'../annotation/upload-unstructured-dossier.jsp?projectName="+object.projectName+"\')";
                break;
         /*   case "GIVE_FEEDBACK":
                result.solveTaskWith="Erteile Feedback";
                result.solveTaskWithLink="redirect(\'../feedback/give-feedback.jsp?projectName="+object.projectName+"\')";
                break;*/
            case "CREATE_QUIZ":
                result.solveTaskWith="Erstelle ein Quiz";
                result.solveTaskWithLink="redirect(\'../assessment/create-quiz.jsp?projectName="+object.projectName+"\')";
                break;
            case "WRITE_EJOURNAL":
                result.solveTaskWith="Lege ein EJournal an";
                result.solveTaskWithLink="redirect(\'../journal/create-journal.jsp?projectName="+object.projectName+"\')";
                break;
            case "ANNOTATE_DOSSIER":
                result.solveTaskWith="Annotiere das Dossier";
                result.solveTaskWithLink="redirect(\'../annotation/create-unstructured-annotation.jsp?projectName="+object.projectName+"&submissionId=" + object.taskData.fullSubmissionId+"\')";
                break;
            case "FINALIZE_DOSSIER":
                result.solveTaskWith="Finalisiere das Dossier";
                result.solveTaskWithLink="redirect(\'../annotation/create-unstructured-annotation.jsp?projectName="+object.projectName+"&submissionId=" + object.taskData.fullSubmissionId+"\')";
                break;
            case "FINALIZE_EJOURNAL":
                result.solveTaskWith="Finalisiere dein EJournal";
                result.solveTaskWithLink="redirect(\'../journal/edit-description.jsp?projectName="+object.projectName+"\')";
                break;
            case "ASSESSMENT":
                result.solveTaskWith="Starte Bewertung";
                result.solveTaskWithLink="redirect(\'../assessment/assess-work.jsp?projectName="+object.projectName+"\')";
                break;
            case "GIVE_FEEDBACK":
                result.solveTaskWith="Geben Sie ein Feedback";
                result.solveTaskWithLink="redirect(\'../annotation/annotation-document.jsp?fullSubmissionId="+object.taskData.fullSubmission.id+"&category="+object.taskData.category+"\')";
                break;
            default:
                result.solveTaskWith=null;

        }
    }
    return result;
}

function fillObjectWithTasks(response){
    let tempObject=[];
    for (let task in response){
        if (response.hasOwnProperty(task))
            tempObject.push({
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

function redirect(url){
    location.href=url;
}

function closePhase(phase, projectName){
    let innerurl =  '../rest/phases/'+phase+'/projects/'+projectName+'/end';
    $.ajax({
        url: innerurl,
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function () {
            location.reload();
        },
        error: function(a){
        }


    })
}
$(document).ready(function(){
    let userEmail = $('#userEmail').html().trim();
    let projectName = $('#projectName').html().trim();
    fillTasks(projectName, userEmail);
    /*let object = [{
        taskType: "ONSITE",  //
        taskData: "",
        taskName: "WAIT_FOR_PARTICPANTS",  //
        hasRenderModel: false,
        eventCreated: Date.now(),
        deadline: Date.now()+172800000, //
        groupTask: false,//
        importance: "",
        phase: "CourseCreation",  //
        link: "www.google.de",  //
        userEmail: userEmail,
        projectName: projectName,
        progress: "JUSTSTARTED"
    }, {
        taskType: "LINKED",  //
        taskData: "",
        taskName: "ASSESSMENT",  //
        hasRenderModel: false,
        eventCreated: Date.now()-410,
        deadline: Date.now() + 17223613, //
        groupTask: false,//
        importance: "",
        phase: "Assessment",  //
        link: "www.youtube.de",  //
        userEmail: userEmail,
        projectName: projectName,
        progress: "FINISHED"
    }
    ];
    for (let task in object){
        let tmplObject = fitObjectInTmpl(object[task]);
        $('#taskTemplate').tmpl(tmplObject).appendTo('#listOfTasks');
    }*/
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
        result.helpLink = object.link;
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
        default:
            result.infoText="";
    }
    if (object.taskType.includes("LINKED")){
        //todo: implement rest
        switch (object.taskName) {
            case "UPLOAD_DOSSIER":
                result.solveTaskWith="Lege ein Dossier an";
                result.solveTaskWithLink="../annotation/annotation-document.jsp?projectName="+object.projectName;
                break;
            case "GIVE_FEEDBACK":
                result.solveTaskWith="Erteile Feedback";
                result.solveTaskWithLink="../feedback/give-feedback.jsp?projectName="+object.projectName;
                break;
            case "CREATE_QUIZ":
                result.solveTaskWith="Erstelle ein Quiz";
                result.solveTaskWithLink="../assessment/create-quiz.jsp?projectName="+object.projectName;
                break;
            case "WRITE_EJOURNAL":
                result.solveTaskWith="Lege ein EJournal an";
                result.solveTaskWithLink="../journal/create-journal.jsp?projectName="+object.projectName;
                break;
            case "FINALIZE_DOSSIER":
                result.solveTaskWith="Finalisiere das Dossier";
                result.solveTaskWithLink="../annotation/annotation-document.jsp?projectName="+object.projectName;
                break;
            case "FINALIZE_EJOURNAL":
                result.solveTaskWith="Finalisiere dein EJournal";
                result.solveTaskWithLink="../journal/edit-description.jsp?projectName="+object.projectName;
                break;
            case "ASSESSMENT":
                result.solveTaskWith="Starte Bewertung";
                result.solveTaskWithLink="../assessment/assess-work.jsp?projectName="+object.projectName;
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
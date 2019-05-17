let object;
let groupViewLink;
$(document).ready(function () {
    let userEmail = $('#userEmail').html().trim();
    let projectName = $('#projectName').html().trim();
    groupViewLink =$('#groupView');
    groupViewLink.hide();
    fillTasks(projectName, userEmail);
    groupViewLink.on('click', function(){
        location.href="../groupfinding/view-groups.jsp?projectName="+projectName;
    });
});

function fillTasks(projectName, userEmail) {
    $.ajax({
        url: '../rest/tasks/user/' + encodeURI(userEmail) + '/project/' + projectName,
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (response) {
            object = fillObjectWithTasks(response);
            for (let task in object) {
                let tmplObject = fitObjectInTmpl(object[task]);
                if (tmplObject.taskProgress === "FINISHED") {
                    $('#finishedTaskTemplate').tmpl(tmplObject).appendTo('#listOfTasks');
                } else {
                    $('#taskTemplate').tmpl(tmplObject).appendTo('#listOfTasks');
                }
            }
        },
        error: function (a) {
        }
    });
}

function fitObjectInTmpl(object) {
    let result = {
        taskType: "",
        infoText: "",
        phase: "",
        headLine: "",
        solveTaskWith: "",
        helpLink: "",
        timeFrame: "",
        taskData: object.taskData,
        taskProgress: ""
    };

    if (object.taskType !== "INFO") {
        if (object.groupTask === true) {
            result.taskType = "grouptask"
        } else {
            result.taskType = "usertask"
        }
    } else {
        result.taskType = "infotask"
    }
    switch (object.phase) {
        case "CourseCreation":
            result.phase = "card-draft";
            result.headLine = "Projektstart";
            break;
        case "GroupFormation":
            result.phase = "card-grouping";
            result.headLine = "Gruppenbildung";
            break;
        case "DossierFeedback":
            groupViewLink.show();
            result.phase = "card-feedback";
            result.headLine = "Entwurfsphase";
            break;
        case "Execution":
            groupViewLink.show();
            result.phase = "card-execution";
            result.headLine = "Reflexionsphase";
            break;
        case "Assessment":
            groupViewLink.show();
            result.phase = "card-assessment";
            result.headLine = "Bewertungsphase";
            break;
        case "Projectfinished":
            groupViewLink.show();
            result.phase = "card-grades";
            result.headLine = "Projektabschluss";
            break;
        default:
            result.phase = "";
    }
    if (object.deadline != null) {
        let daysLeft = Math.round((object.deadline - Date.now()) / 1000 / 60 / 60 / 24);
        if (daysLeft >= 1)
            result.timeFrame = "<div class='status icon'><p>Noch " + daysLeft + " Tage Zeit</p></div>";
        else
            result.timeFrame = "<div class='status alert icon'><p>Du bist zu spät.</p></div>";
    } else {
        result.timeFrame = "";
    }
    switch (object.taskName) {
        case "WAIT_FOR_PARTICPANTS":
            let countMissing = object.taskData.participantCount.participantsNeeded - object.taskData.participantCount.participants;
            result.infoText = "Warten Sie auf die Anmeldungen der Studenten.\n"+
                "Es sind bereits " + object.taskData.participantCount.participants + " Studenten eingetragen.";
            if (object.taskData.participantCount.participants===0){
                result.infoText = " Es gibt noch keine Teilnehmer.";
            }
            if (countMissing>0){
                result.infoText += " Es fehlen noch " + countMissing+".";
            }

            break;
        case "BUILD_GROUPS":
            result.infoText = "Erstellen Sie die Gruppen.";
            break;
        case "CLOSE_GROUP_FINDING_PHASE":
            result.infoText = "Gehen Sie zur nächsten Phase über.";
            break;
        case "WAITING_FOR_GROUP":
            result.infoText = "[STUDENT] Die Arbeitsgruppen werden gebildet. Sie werden informiert, wenn es so weit" +
                " ist.";
            break;
        case "UPLOAD_DOSSIER":
            result.infoText = "Legen Sie ein Dossier an.";
            break;
        case "ANNOTATE_DOSSIER":
            result.infoText = "Annotieren Sie ihr Dossier.";
            break;
        case "GIVE_FEEDBACK":
            result.infoText = "[STUDENT] Geben Sie ein Feedback .....";
            if (object.taskData === null) {
                result.infoText += "nachdem ein weiterer Teilnehmer ein Dossier abgegeben hat."
            }
            break;
        case "SEE_FEEDBACK":
            result.infoText = "Sie erhielten Feedback zu Ihrem Dossier.";
            break;
        case "WAITING_FOR_STUDENT_DOSSIERS":
            result.infoText = "[TEACHER] Warten Sie darauf, dass jeder Student ein Dossier" +
                "hochlädt und ein Feedback für jemanden gab.";
            break;
        case "CLOSE_DOSSIER_FEEDBACK_PHASE":
            let count = object.taskData.length;
            if (count <= 3) {
                result.infoText = "Warten sie noch auf die Studenten ";
                for (let i = 0; i < object.taskData.length; i++) {
                    result.infoText += object.taskData[i] + " ";
                }
            } else {
                result.infoText = "Noch haben nicht alle Studenten ihren Peers ein Feedback gegeben.";
            }
            break;
        case "WAIT_FOR_REFLECTION":
            result.infoText = "[TEACHER] Nun haben die Studenten Zeit über sich und die Welt nachzudenken.";
            break;
        case "EDIT_FORMED_GROUPS":
            result.infoText = "[TEACHER] Die Gruppen wurden vom Algorithmus gebildet. Sie können noch manuell" +
                " editiert werden."; // hier müsste noch ein Link eingefügt werden, zur manuellen Gruppenbildung
            break;
        case "CONTACT_GROUP_MEMBERS":
            result.infoText = "Sagen sie hallo zu ihren Gruppenmitgliedern über den Chat.";
            break;
        default:
            result.infoText = "";
    }
    if (object.taskType.includes("LINKED")) {
        switch (object.taskName) {
            case "WAIT_FOR_PARTICPANTS":
                let countMissing = object.taskData.participantCount.participantsNeeded - object.taskData.participantCount.participants;
                if(object.taskData.participantCount.participants >= object.taskData.participantCount.participantsNeeded){
                    result.infoText = "Sehen Sie sich den Gruppenvorschlag des Algorithmus an oder " +
                        "warten Sie auf weitere Teilnehmer. Die Gruppen sind noch nicht final gespeichert.\n"+
                        "Es sind bereits " + object.taskData.participantCount.participants + " Studenten eingetragen.";
                    result.solveTaskWith = "Gruppen einsehen";
                    switch (object.taskData.gfm){
                        default:
                            result.solveTaskWithLink = "initializeGroups('"+object.projectName+"');";
                            break;
                    }
                }else{
                    result.infoText = "Warten sie auf Anmeldung der StudentInnen. \n"+
                        "Es sind bereits " + object.taskData.participantCount.participants + " Studenten eingetragen.";
                    if (object.taskData.participantCount.participants===0){
                        result.infoText = " Es gibt noch keine Teilnehmer.";
                    }
                    if (countMissing>0){
                        result.infoText += " Es fehlen noch " + countMissing+".";
                    }
                }
                break;
            case "CLOSE_GROUP_FINDING_PHASE":
                result.solveTaskWith = "Entwurfsphase starten";
                result.solveTaskWithLink = "closePhase(\'" + object.phase + "\', \'" + object.projectName + "\');";
                break;
            case "UPLOAD_DOSSIER":
                result.solveTaskWith = "Lege ein Dossier an";
                result.solveTaskWithLink = "redirect(\'../annotation/upload-unstructured-dossier.jsp?projectName=" + object.projectName + "\')";
                break;
            case "CREATE_QUIZ":
                result.solveTaskWith = "Erstelle ein Quiz";
                result.solveTaskWithLink = "redirect(\'../assessment/create-quiz.jsp?projectName=" + object.projectName + "\')";
                break;
            case "WRITE_EJOURNAL":
                result.solveTaskWith = "Lege ein EJournal an";
                result.solveTaskWithLink = "redirect(\'../journal/create-journal.jsp?projectName=" + object.projectName + "\')";
                break;
            case "ANNOTATE_DOSSIER":
                result.solveTaskWith = "Annotiere das Dossier";
                result.solveTaskWithLink = "redirect(\'../annotation/create-unstructured-annotation.jsp?projectName=" + object.projectName + "&submissionId=" + object.taskData.fullSubmissionId + "\')";
                break;
            case "FINALIZE_DOSSIER":
                result.solveTaskWith = "Finalisiere das Dossier";
                result.solveTaskWithLink = "redirect(\'../annotation/create-unstructured-annotation.jsp?projectName=" + object.projectName + "&submissionId=" + object.taskData.fullSubmissionId + "\')";
                break;
            case "FINALIZE_EJOURNAL":
                result.solveTaskWith = "Finalisiere dein EJournal";
                result.solveTaskWithLink = "redirect(\'../journal/edit-description.jsp?projectName=" + object.projectName + "\')";
                break;
            case "CLOSE_DOSSIER_FEEDBACK_PHASE":
                result.taskData = object.taskData;
                if (result.taskData.length === 0) {
                    result.solveTaskWith = "Reflexionsphase starten";
                    result.solveTaskWithLink = "closePhase(\'" + object.phase + "\', \'" + object.projectName + "\');";
                } else {
                    if (result.taskData.length <= 3) {  //todo: probably its better to have a percentage of all participants as constraint

                    }
                }

                break;
            case "ASSESSMENT":
                result.solveTaskWith = "Starte Bewertung";
                result.solveTaskWithLink = "redirect(\'../assessment/assess-work.jsp?projectName=" + object.projectName + "\')";
                break;
            case "GIVE_FEEDBACK":
                if (object.taskData !== null) {
                    result.solveTaskWith = "Geben Sie ein Feedback";
                    result.solveTaskWithLink = "redirect(\'../annotation/annotation-document.jsp?" +
                        "projectName=" + object.projectName +
                        "&fullSubmissionId=" + object.taskData.fullSubmission.id + "&category=" + object.taskData.category + "\')";
                }

                break;
            case "SEE_FEEDBACK":
                if (object.taskData !== null) {
                    result.solveTaskWith = "zum Feedback";
                    result.solveTaskWithLink = "redirect(\'../annotation/annotation-document.jsp?" +
                        "projectName=" + object.projectName +
                        "&fullSubmissionId=" + object.taskData.fullSubmission.id +
                        "&category=" + object.taskData.category +
                        "&seeFeedback=true\')";
                }
                break;
            default:
                result.solveTaskWith = null;

        }
    }

    if (object.progress === "FINISHED") {
        if (object.taskName === "WAIT_FOR_PARTICPANTS"){
            result.infoText = "Gruppen sind final gespeichert. \n"+
                "Es sind bereits " + object.taskData.participantCount.participants + " Studenten eingetragen.";
        }
        if (object.taskName.includes("CLOSE")) {
            result.taskProgress = "FINISHED";
            result.infoText = object.phase;
            let created = new Date(object.eventCreated);
            let deadline = new Date(object.deadline);
            result.timeFrame = "<p>" + created.getDate() + "." + (created.getMonth() + 1) + "." + created.getFullYear() +
                " bis " + deadline.getDate() + "." + (deadline.getMonth() + 1) + "." + deadline.getFullYear() + "</p>";
            return result;
        } else {
            result.timeFrame = "";
        }
        result.taskProgress = "FINISHED";
        return result;
    }

    return result;
}

function fillObjectWithTasks(response) {
    let tempObject = [];
    let first = true;
    for (let task in response) {
        let headLine = "";
        switch (response[task].phase){
            case ("card-grouping"):
                break;
        }
        if (response.hasOwnProperty(task)) {
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
                headLine: "",
                link: response[task].link,  //
                userEmail: response[task].userEmail,
                projectName: response[task].projectName,
                progress: response[task].progress,
                current: first
            });
            if(first){
                first=false;
            }
        }
    }

    return tempObject;
}

function redirect(url) {
    location.href = url;
}

function closePhase(phase, projectName) {
    let innerurl = '../rest/phases/' + phase + '/projects/' + projectName + '/end';
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
        error: function (a) {
        }


    })
}

function initializeGroups(projectName){
    let projq = new RequestObj(1, "/group", "/all/projects/?", [projectName], []);
    serverSide(projq, "GET", function (response) {
        redirect("../groupfinding/create-groups-manual.jsp?projectName=" + projectName);
    });
}
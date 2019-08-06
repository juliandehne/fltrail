let object;
let groupViewLink;
let projectName;
let userEmail;
$(document).ready(function () {
    userEmail = $('#userEmail').html().trim();
    projectName = $('#projectName').html().trim();
    groupViewLink = $('#liGroupWindow');
    groupViewLink.toggleClass("disabled");

    fillTasks(projectName, userEmail, function () {
        $('.groupView').on('click', function () {
            location.href = "../groupfinding/view-groups.jsp?projectName=" + projectName;
        });
    });
});

function fillTasks(projectName, userEmail, callback) {
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
                switch (tmplObject.taskProgress) {
                    case "FINISHED":
                        $('#finishedTaskTemplate').tmpl(tmplObject).appendTo('#listOfTasks');
                        break;
                    case "INPROGRESS":
                        $('#inProgressTaskTemplate').tmpl(tmplObject).appendTo('#listOfTasks');
                        break;
                    case "JUSTSTARTED":
                        $('#taskTemplate').tmpl(tmplObject).appendTo('#listOfTasks');
                        break;
                    default:
                        break;
                }
            }
            callback();
        },
        error: function (a) {
        }
    });
}

function handlePhases(object, result) {
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
            result.phase = "card-feedback";
            result.headLine = "Entwurf";
            break;
        case "Execution":
            result.phase = "card-execution";
            result.headLine = "Durchführung";
            break;
        case "Assessment":
            result.phase = "card-assessment";
            result.headLine = "Bewertung";
            break;
        case "GRADING":
            result.phase = "card-grades";
            result.headLine = "Projektabschluss";
            break;
        default:
            result.phase = "";
    }
}

function handleDeadlines(object, result) {
    if (object.deadline != null) {
        let daysLeft = Math.round((object.deadline - Date.now()) / 1000 / 60 / 60 / 24);
        if (daysLeft >= 1)
            result.timeFrame = "<div class='status icon'><p>Noch " + daysLeft + " Tage Zeit</p></div>";
        else
            result.timeFrame = "<div class='status alert icon'><p>Du bist zu spät.</p></div>";
    } else {
        result.timeFrame = "";
    }
}

function handleTaskType(object, result) {
    if (object.taskType !== "INFO") {
        if (object.groupTask !== 0) {
            result.taskType = "grouptask"
        } else {
            result.taskType = "usertask"
        }
    } else {
        result.taskType = "infotask"
    }
}

function handleInfoTasks(object, result) {

    /* if (object.hasRenderModel) {
         result.inCardSolver = object.taskName;
     }*/
    switch (object.taskName) {
        case "WAIT_FOR_PARTICPANTS":
            result.infoText = waitForParticipantsInfoText(object);
            switch (object.taskData.gfm) {
                case "UserProfilStrategy":
                    result.inCardSolver = "RESIZE_GROUP";
                    result.groupSize = object.taskData.groupSize;
                    result.memberCount = object.taskData.groupSize * (object.taskData.groupSize - 1);
                    break;
            }
            break;
        case "UPLOAD_FINAL_REPORT":
            result.infoText = "Bitte laden Sie den Abschlussbericht (stellvertretend für ihre Gruppe) hoch!";
            break;
        case "BUILD_GROUPS":
            result.infoText = "Erstellen Sie die Gruppen.";
            break;
        case "CLOSE_GROUP_FINDING_PHASE":
            result.infoText = "Gehen Sie zur nächsten Phase über. Dies fixiert die Gruppen.";
            break;
        case "WAITING_FOR_GROUP":
            result.infoText = "Die Arbeitsgruppen werden gebildet. Sie werden informiert, wenn es so weit" +
                " ist.";
            break;
        case "UPLOAD_DOSSIER":
            result.infoText = "Legen Sie ein Dossier an.";
            break;
        case "ANNOTATE_DOSSIER":
            result.infoText = "Markieren Sie in ihrem Dossier Teile für das Feedback. \n" +
                "Dies finalisiert die Einreichung Ihres Dossiers.";
            break;
        case "GIVE_FEEDBACK":
            result.infoText = "Geben Sie ein Feedback ";
            if (object.taskData === null) {
                result.infoText += "nachdem ein weiterer Teilnehmer ein Dossier abgegeben hat."
            }
            break;
        case "SEE_FEEDBACK":
            result.infoText = "Sie erhielten Feedback zu Ihrem Dossier.";
            break;
        case "WAITING_FOR_STUDENT_DOSSIERS":
            result.infoText = "Studierende legen nun ein Dossier an und" +
                " geben sich gegenseitig Feedback.";
            break;
        case "REEDIT_DOSSIER":
            result.infoText = "Sobald Sie ein Feedback bekommen haben, können Sie hier Ihr Dossier überarbeiten.";
            break;
        case "CLOSE_DOSSIER_FEEDBACK_PHASE":
            let count = object.taskData.length;
            if (count <= 3) {
                result.infoText = "Es fehlen noch die Feedbacks der Gruppe/n ";
                for (let i = 0; i < object.taskData.length; i++) {
                    for (let j = 0; j < object.taskData[i].members.length; j++) {
                        result.infoText += object.taskData[i].members[j].name;
                        if (j < object.taskData[i].members.length - 1) {
                            result.infoText += ", "
                        }
                    }
                    if (i < object.taskData.length - 1) {
                        result.infoText += " und "
                    }
                }
            } else {
                result.infoText = "Noch haben nicht alle Studenten ihren Peers ein Feedback gegeben.";
            }
            break;
        case "WAIT_FOR_REFLECTION_QUESTION_CHOICE":
            result.infoText = "Warten Sie darauf, dass der Lehrende Reflexionsfragen ausgewählt hat.";
            break;
        case "ANSWER_REFLECTION_QUESTIONS":
            result.infoText = "Bitte beantworten Sie die Reflexionsfragen.";
            break;
        case "CHOOSE_ASSESSMENT_MATERIAL":
            result.infoText = "Wählen Sie die Einträge aus, die Sie zur Bewertung einreichen möchten.";
            break;
        case "WAIT_FOR_EXECUTION_PHASE_END":
            result.infoText = "Warten Sie darauf, dass der Lehrende die Durchführungsphase beendet";
            break;
        case "CREATE_LEARNING_GOALS_AND_CHOOSE_REFLEXION_QUESTIONS":
            result.infoText = "Um die Durchführungsphase zu beginnen, müssen Sie zuerst Lernziele und Reflexionsfragen erstellen bzw. auswählen.";
            break;
        case "START_LEARNING_GOAL_PERIOD":
            result.infoText = "Starten Sie die Arbeit am Lernziel";
            break;
        case "CLOSE_EXECUTION_PHASE":
            result.infoText = "Beenden Sie nun die Durchführungsphase.";
            result.taskData.numberOfMissingReflectionQuestions = result.taskData.userUnansweredReflectionQuestions.length;
            result.taskData.numberOfMissingForAssessmentChosen = result.taskData.userUnchosenAssessmentMaterial.length;
            break;
        case "CONTACT_GROUP_MEMBERS":
            groupViewLink.toggleClass("disabled");
            result.infoText = "";
            break;
        case "INTRODUCE_E_PORTFOLIO_STUDENT":
            result.infoText = "Sie können hier Ihr E-Portfolio beginnen. \n " +
                "Am Ende des Projekts muss jede Gruppe ein gemeinsames Portfolio abgeben.";
            break;
        case "INTRODUCE_E_PORTFOLIO_DOCENT":
            result.infoText = "Sie können hier die E-Portfolios der Studenten und Gruppen einsehen, wenn es für Sie freigegeben ist.";
            break;
        case "UPLOAD_PRESENTATION":
            result.infoText = "Bitte laden Sie die Präsentation (stellvertretend für ihre Gruppe) hoch!";
            break;
        case "GIVE_INTERNAL_ASSESSMENT":
            result.infoText = "Bitte bewerten Sie die Gruppenarbeit ihrer Gruppenmitglieder!";
            let numOfMissing = object.taskData.numberOfMissing;
            if (numOfMissing && numOfMissing > 0) {
                if (numOfMissing === 1) {
                    result.infoText += " Es fehlt noch eine Bewertung."
                } else {
                    result.infoText += " Es fehlen noch " + object.taskData.numberOfMissing + " Bewertungen."
                }
            }
            break;
        case "GIVE_EXTERNAL_ASSESSMENT":
            result.infoText = "Bewerten Sie eine andere Gruppe!";
            break;
        case "WAIT_FOR_GRADING":
            result.infoText = "Ihr Dozent und andere Peers bewerten Sie nun!";
            break;
        case "GIVE_EXTERNAL_ASSESSMENT_TEACHER":
            result.infoText = "Bewerten Sie die einzelnen Gruppen!";
            break;
        case "CLOSE_PEER_ASSESSMENTS_PHASE":

            break;
        case "GIVE_FINAL_GRADES":
            result.infoText = "Vergeben Sie finale Noten!";
            break;
        case "END_DOCENT": {
            result.infoText = "Das Projekt ist beendet!";
            break;
        }
        case "END_STUDENT": {
            result.infoText = "Das Projekt ist beendet! Sie haben eine " + object.taskData + " erreicht.";
            break;
        }
        case "EVALUATION_TECHNISCH" : {
            result.infoText = "Bitte bewerten Sie die verwendete Software Fl-Trail!";
            break;
        }


        default:
            result.infoText = "";
    }
}

function handleLinkedTasks(object, result) {
    if (object.taskType.includes("LINKED")) {
        switch (object.taskName) {
            case "WAIT_FOR_PARTICPANTS":
                if (object.taskData.participantCount.participants >= object.taskData.participantCount.participantsNeeded) {
                    result.infoText = "Sehen Sie sich den Gruppenvorschlag des Algorithmus an oder " +
                        "warten Sie auf weitere Teilnehmer. Die Gruppen sind noch nicht final gespeichert.\n" +
                        "Es sind bereits " + object.taskData.participantCount.participants + " Studenten eingetragen.";
                    result.solveTaskWith = "Gruppen einsehen";
                    switch (object.taskData.gfm) {
                        default:
                            result.solveTaskWithLink = "initializeGroups('" + object.projectName + "');";
                            break;
                    }
                } else {
                    result.infoText = waitForParticipantsInfoText(object);
                }
                break;
            case "CLOSE_GROUP_FINDING_PHASE":
                result.solveTaskWith = "Entwurfsphase starten";
                result.solveTaskWithLink = "closePhase(\'" + object.phase + "\', \'" + object.projectName + "\');";
                break;
            case "UPLOAD_DOSSIER":
                result.solveTaskWith = "Erstelle Dossier";
                result.solveTaskWithLink = "redirect(\'../annotation/upload-unstructured-dossier.jsp?projectName=" + object.projectName + "&fileRole=Dossier" + "\')";
                break;
            case "REEDIT_DOSSIER":
                result.infoText = "Basierend auf dem erhaltenen Feedback, können Sie nun Ihr Dossier überarbeiten.";
                result.solveTaskWith = "Überarbeite Dossier";
                result.solveTaskWithLink = "redirect(\'../annotation/reedit-dossier.jsp?fullsubmissionid=" + object.taskData.fullSubmissionId + "&projectName=" + object.projectName + "&contribution=DOSSIER\')";
                break;
            case "ANSWER_REFLECTION_QUESTIONS":
                result.solveTaskWith = "Reflexionsfragen beantworten";
                result.solveTaskWithLink = "redirect(\'../annotation/upload-unstructured-dossier.jsp?" + $.param({
                    projectName: object.projectName,
                    fileRole: "Reflection_Question",
                    personal: true,
                }) + "\')";

                break;
            case "CHOOSE_ASSESSMENT_MATERIAL":
                result.solveTaskWith = "Einträge zur Bewertung auswählen";
                result.solveTaskWithLink = "redirect(\'../reflection/choose-for-assessment.jsp?projectName=" + object.projectName + "\')";
                break;
            case "CREATE_LEARNING_GOALS_AND_CHOOSE_REFLEXION_QUESTIONS":
                result.solveTaskWith = "Auswahl treffen";
                result.solveTaskWithLink = "redirect(\'../reflection/create-learning-goals.jsp?projectName=" + object.projectName + "\')";
                break;
            case "ANNOTATE_DOSSIER":
                result.solveTaskWith = "Annotiere Dossier";
                result.solveTaskWithLink = "redirect(\'../annotation/create-unstructured-annotation.jsp?" + $.param({
                    projectName: object.projectName,
                    submissionId: object.taskData.fullSubmissionId,
                    fileRole: "Dossier"
                }) + "\')";
                break;
            case "CLOSE_DOSSIER_FEEDBACK_PHASE":
                let count = object.taskData.length;
                result.taskData = object.taskData;
                if (count === 0) {
                    result.infoText = "Alle Gruppen haben Feedback gegeben";
                    result.solveTaskWith = "Durchführungsphase starten";
                    result.solveTaskWithLink = "closePhase(\'" + object.phase + "\', \'" + object.projectName + "\');";
                } else {
                    if (count <= 3) {
                        result.infoText = "Es fehlen noch die Feedbacks der Gruppe/n ";
                        for (let i = 0; i < object.taskData.length; i++) {
                            for (let j = 0; j < object.taskData[i].members.length; j++) {
                                result.infoText += object.taskData[i].members[j].name;
                                if (j < object.taskData[i].members.length - 1) {
                                    result.infoText += ", "
                                }
                            }
                            if (i < object.taskData.length - 1) {
                                result.infoText += " und "
                            }
                        }
                    } else {
                        result.infoText = "Noch haben nicht alle Studenten ihren Peers ein Feedback gegeben.";
                    }
                }

                break;
            case "ASSESSMENT":
                result.solveTaskWith = "Starte Bewertung";
                result.solveTaskWithLink = "redirect(\'../assessment/assess-work.jsp?projectName=" + object.projectName + "\')";
                break;
            case "GIVE_FEEDBACK":
                if (object.taskData !== null) {
                    result.solveTaskWith = "Gib Feedback";
                    result.solveTaskWithLink = "redirect(\'../annotation/give-feedback.jsp?" +
                        "projectName=" + object.projectName +
                        "&fullSubmissionId=" + object.taskData.fullSubmission.id + "&category=" + object.taskData.category + "\')";
                }

                break;
            case "SEE_FEEDBACK":
                if (object.taskData !== null) {
                    result.solveTaskWith = "Feedback sehen";
                    result.solveTaskWithLink = "redirect(\'../annotation/see-feedback.jsp?" +
                        "projectName=" + object.projectName +
                        "&fullSubmissionId=" + object.taskData.fullSubmissionId +
                        "&category=" + object.taskData.category +
                        "&contribution=DOSSIER\')";
                }
                break;
            case "UPLOAD_PRESENTATION":
                result.solveTaskWith = "Präsentation hochladen";
                result.solveTaskWithLink = "redirect(\'../assessment/upload-presentation.jsp?" +
                    "projectName=" + object.projectName + "\')";

                break;
            case "UPLOAD_FINAL_REPORT":
                result.solveTaskWith = "Abschlussbericht hochladen";
                result.solveTaskWithLink = "redirect(\'../assessment/upload-final-report.jsp?" +
                    "projectName=" + object.projectName + "\')";

                break;
            case "INTRODUCE_E_PORTFOLIO_STUDENT":
                result.solveTaskWith = "E-Portfolio annsehen";
                result.solveTaskWithLink = "redirect(\'../portfolio/show-portfolio-student.jsp?" + $.param({
                    projectName: object.projectName,
                }) + "\')";
                break;
            case "INTRODUCE_E_PORTFOLIO_DOCENT":
                result.solveTaskWith = "Bisherigen Einträge";
                result.solveTaskWithLink = "redirect(\'../portfolio/show-portfolio-docent.jsp?" + $.param({
                    projectName: object.projectName,
                }) + "\')";
                break;
            /* case "GIVE_ASSESSMENT":
                 result.solveTaskWith = "Bewerten";
                 result.solveTaskWithLink = "redirect(\'../annotation/upload-unstructured-dossier.jsp?" + $.param({
                     projectName: object.projectName,
                     contributionCategory: "Portfolio",
                     personal: "true"
                 }) + "\')";
                 break;*/
            case "GIVE_EXTERNAL_ASSESSMENT":
                if (object.progress !== "FINISHED") {
                    result.solveTaskWith = "Kommilitonen bewerten";
                    result.solveTaskWithLink = "redirect(\'../assessment/rate-contribution.jsp?" +
                        "projectName=" + object.projectName + "&groupId=" + result.taskData.objectGroup.id + "\')";
                }
                break;
            case "GIVE_INTERNAL_ASSESSMENT":
                result.solveTaskWith = "Gruppenarbeit bewerten";
                result.solveTaskWithLink = "redirect(\'../assessment/rate-group-work.jsp?projectName=" + projectName + "\')";
                break;
            case "GIVE_EXTERNAL_ASSESSMENT_TEACHER":
                if (object.progress !== "FINISHED") {
                    result.solveTaskWith = "Bewerte Gruppe";
                    result.solveTaskWithLink = "redirect(\'../assessment/rate-contribution.jsp?" +
                        "projectName=" + object.projectName + "&groupId=" + result.taskData.taskMapping.objectGroup.id + "\')";
                }
                break;
            case "CLOSE_PEER_ASSESSMENTS_PHASE":
                result.solveTaskWith = "";
                result.solveTaskWithLink = "";
                break;
            case "GIVE_FINAL_GRADES":
                result.solveTaskWith = "Noten vergeben";
                result.solveTaskWithLink = "redirect(\'../assessment/final-grades.jsp?" +
                    "projectName=" + object.projectName + "\')";
                break;
            case "END_DOCENT":
                result.solveTaskWith = "Zur Notenübersicht";
                result.solveTaskWithLink = "redirect(\'../assessment/final-grades.jsp?" +
                    "final=true&" +
                    "projectName=" + object.projectName + "\')";
                break;
            case "EVALUATION_TECHNISCH":
                result.solveTaskWith = "Zur Evaluation";
                result.solveTaskWithLink = "redirect(\'../evaluation/evaluation_technical.jsp?" + $.param({
                    projectName: object.projectName,
                }) + "\')";
                break;
            default:
                result.solveTaskWith = null;

        }
    }
}

function handleFinishedTasks(object, result) {
    if (object.progress === "FINISHED") {
        switch (object.taskName) {
            case "WAIT_FOR_PARTICPANTS":
                result.infoText = "Gruppen sind final gespeichert. \n" +
                    "Es sind " + object.taskData.participantCount.participants + " Studenten in diesem Projekt.";
                break;
            case "WAITING_FOR_GROUP":
                result.infoText = "";
                break;
            case "GIVE_FEEDBACK":
                /*if (object.taskData !== null) {
                    result.infoText = "Sie können weiterhin Ihr Feedback editieren";
                    result.solveTaskWith = "Geben Sie ein Feedback";
                    result.solveTaskWithLink = "redirect(\'../annotation/give-feedback.jsp?" +
                        "projectName=" + object.projectName +
                        "&fullSubmissionId=" + object.taskData.fullSubmission.id + "&category=" + object.taskData.category + "\')";
                } else {*/
                result.infoText = "Ihr Feedback wurde an die betreffende Gruppe übermittelt.";
                //}
                break;
            case "REEDIT_DOSSIER":
                result.infoText = "Ihre Gruppe hat eine finale Abgabe des Dossiers gespeichert. \n" +
                    "Warten Sie nun auf die nächste Phase.";
                break;
            case "ANNOTATE_DOSSIER":
            case "UPLOAD_DOSSIER":
                result.solveTaskWith = "";
                result.solveTaskWithLink = "";
                break;
            case "WAITING_FOR_STUDENT_DOSSIERS":
                result.infoText = "Studierende haben Dossiers angelegt und sich gegenseitig Feedback gegeben";
                break;
            case "WAIT_FOR_UPLOAD":
                result.infoText = "Jede Gruppe hat einen abschließenden Report und eine Präsentation hochgeladen.";
                break;
            case "GIVE_EXTERNAL_ASSESSMENT_TEACHER" :
                result.infoText = "Sie haben die Arbeit der Studierenden bewertet.";
                break;
            case "GIVE_INTERNAL_ASSESSMENT":
                result.infoText = "Sie haben die Arbeit ihrer Gruppenmitglieder bewertet.";
                break;
            case "UPLOAD_FINAL_REPORT":
                result.infoText = "Ihre Gruppe hat einen Abschlussbericht hochgeladen.";
                break;
            case "UPLOAD_PRESENTATION":
                result.infoText = "Ihre Gruppe hat eine Präsentation hochgeladen.";
                break;
            case "GIVE_FINAL_GRADES":
                result.infoText = "Sie haben finale Noten vergeben.";
                break;
            case "CLOSE_PEER_ASSESSMENTS_PHASE":
                result.infoText = "Hier können Sie die Bewertung zwischen den Studierenden beenden und Ihre eigene starten.";
                break;
        }
        if (object.taskName.includes("CLOSE")) {
            result.infoText = object.phase;
            let created = new Date(object.eventCreated);
            let deadline = new Date(object.deadline);
            result.timeFrame = "<p>" + created.getDate() + "." + (created.getMonth() + 1) + "." + created.getFullYear() +
                " bis " + deadline.getDate() + "." + (deadline.getMonth() + 1) + "." + deadline.getFullYear() + "</p>";
        } else {
            result.timeFrame = "";
        }
        result.taskProgress = "FINISHED";
        result.taskType = "closed";
    }
}

function handleInProgressTasks(object, result) {
    if (object.progress === "INPROGRESS") {
        switch (object.taskName) {
            case "UPLOAD_DOSSIER":
                result.infoText = "Für Ihre Gruppe wurde ein Dossier angelegt. Sie können es noch überarbeiten.";
                result.solveTaskWith = "Bearbeite Dossier";
                result.solveTaskWithLink = "redirect(\'../annotation/upload-unstructured-dossier.jsp?projectName=" + object.projectName + "&fileRole=Dossier" + "\')";
                break;
        }
    }

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
        taskProgress: object.progress,
        current: object.current,
        inCardSolver: object.taskName,
    };
    handleTaskType(object, result);
    handlePhases(object, result);
    handleDeadlines(object, result);
    handleInfoTasks(object, result);
    handleLinkedTasks(object, result);
    handleFinishedTasks(object, result);
    handleInProgressTasks(object, result);
    return result;
}

function fillObjectWithTasks(response) {
    let tempObject = [];
    let thisPhase = "";
    for (let task in response) {
        let first;
        if (response.hasOwnProperty(task) && thisPhase === response[task].phase) {
            first = false;
        } else {
            thisPhase = response[task].phase;
            first = true;
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
        }
    }

    return tempObject;
}

function countMissingStudents(object) {
    return object.taskData.participantCount.participantsNeeded - object.taskData.participantCount.participants;
}

function waitForParticipantsInfoText(object) {
    let result = "Warten Sie auf die Anmeldungen der Studenten.\n" +
        "Es sind bereits " + object.taskData.participantCount.participants + " Studenten eingetragen.";
    if (object.taskData.participantCount.participants === 0) {
        result = " Es gibt noch keine Teilnehmer.";
    }
    if (countMissingStudents(object) > 0) {
        if (countMissingStudents(object) === 1) {
            result += " Um Gruppen bilden zu können, fehlt noch ein Student.";
        } else {
            result += " Um Gruppen bilden zu können, fehlen noch " + countMissingStudents(object) + " Studenten.";
        }
    }
    return result
}

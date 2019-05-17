package unipotsdam.gf.process.phases;

import com.fasterxml.jackson.core.JsonProcessingException;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.exceptions.WrongNumberOfParticipantsException;
import unipotsdam.gf.modules.communication.service.EmailService;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.interfaces.IJournal;
import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.interfaces.IPhases;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.communication.Messages;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.process.DossierCreationProcess;
import unipotsdam.gf.process.GroupFormationProcess;
import unipotsdam.gf.process.constraints.ConstraintsMessages;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.xml.bind.JAXBException;
import java.util.Map;

/**
 * Created by dehne on 31.05.2018.
 * This class should be used to manage changing the phases in a central location
 * it has dependencies to most modules, as they are required to check the constraints
 * when changing between phases
 */
@ManagedBean
@Singleton
public class PhasesImpl implements IPhases {

    @Inject
    MysqlConnect connect;

    @Inject
    private IPeerAssessment iPeerAssessment;

    @Inject
    private ICommunication iCommunication;

    @Inject
    private IJournal iJournal;

    @Inject
    private DossierCreationProcess dossierCreationProcess;

    @Inject
    private GroupFormationProcess groupFormationProcess;


    @Inject
    private EmailService emailService;



    public PhasesImpl() {
    }

    /*Optionen für die Constraints:
    Gesucht ist ein Objekt, welches man an den Dozenten übergibt, in dem die fehlenden Abgaben codiert sind

    Als Map<StudentIdentifier, class Constraints>
        + Immer wenn etwas nicht erfüllt wurde, speichert man es hier ab
        - Jedes Interface bräuchte eine Funktion, die diese Datenstruktur bedient
        - einige nutzlose Daten müssten mitgeschliffen werden
        - Die variable wird immer wieder neu erzeugt und so sollte alles in der DB gespeichert sein!?
    Constraints als Enum
        + Die Funktionen der Interfaces checken ob dieser Constraint überall gilt
        - Jedes Interface muss eine Funktion schreiben, die jeden Studenten untersucht.
            Sinnvoller wäre nur die Studenten zurück zu geben, die die Constraint nicht erfüllen
        - Enums können glaube keine Werte als Default tragen
    Map<StudentIdentifier, String>
        + Wenn Map keine Elemente trägt, ist alles erfüllt.
        + zurück zu geben vom Interface wäre die Kennung (StudentIdentifier) und was fehlt (Constraint)
        - Keine Default Werte
    */

    @Override
    public void endPhase(Phase currentPhase, Project project) throws RocketChatDownException, UserDoesNotExistInRocketChatException, WrongNumberOfParticipantsException, JAXBException, JsonProcessingException {
        Phase changeToPhase = getNextPhase(currentPhase);
        Map<StudentIdentifier, ConstraintsMessages> tasks;
        switch (currentPhase) {
            case GroupFormation:
                // inform users about the formed groups, optionally giving them a hint on what happens next
                emailService.sendMessageToUsers(project, Messages.GroupFormation(project));
                saveState(project, changeToPhase);
                groupFormationProcess.finalize(project);
                dossierCreationProcess.start(project);
                break;
            case DossierFeedback:
                // check if everybody has uploaded a dossier
                dossierCreationProcess.finishPhase(project);
                break;
            case Execution:
                // check if the portfolios have been prepared for evaluation (relevant entries selected)
                tasks = iJournal.getPortfoliosForEvaluationPrepared(project);
                if (tasks.size() < 1) {
                    // inform users about the end of the phase
                    emailService.sendMessageToUsers(project, Messages.AssessmentPhaseStarted(project));
                    saveState(project, changeToPhase);
                } else {
                    emailService.informAboutMissingTasks(tasks, project);
                }
                break;
            case Assessment:
                tasks = iPeerAssessment.allAssessmentsDone(project.getName());
                if (tasks.size() < 1) {
                    emailService.sendMessageToUsers(project, Messages.CourseEnds(project));
                    iPeerAssessment.finalizeAssessment(project);
                    saveState(project, changeToPhase);
                } else {
                    iPeerAssessment.assignMissingAssessmentTasks(project);
                }
                break;
            case Projectfinished:
                closeProject();
                break;
            default: {
            }
        }
    }

    private void closeProject() {
        // TODO implement
    }

    private Phase getNextPhase(Phase phase) {
        switch (phase) {
            case GroupFormation:
                return Phase.DossierFeedback;
            case DossierFeedback:
                return Phase.Execution;
            case Execution:
                return Phase.Assessment;
            case Assessment:
                return Phase.Projectfinished;
            default:
                return Phase.Projectfinished;
        }
    }

    @Override
    public void saveState(Project project, Phase phase) {
        assert project.getName() != null;
        connect.connect();
        String mysqlRequest = "UPDATE `projects` SET `phase`=? WHERE name=? LIMIT 1";
        connect.issueUpdateStatement(mysqlRequest, phase.name(), project.getName());
        connect.close();
    }



}

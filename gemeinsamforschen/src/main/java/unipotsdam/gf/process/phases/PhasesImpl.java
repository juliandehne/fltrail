package unipotsdam.gf.process.phases;

import com.fasterxml.jackson.core.JsonProcessingException;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.exceptions.WrongNumberOfParticipantsException;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.interfaces.IPhases;
import unipotsdam.gf.modules.communication.Messages;
import unipotsdam.gf.modules.communication.service.EmailService;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.process.DossierCreationProcess;
import unipotsdam.gf.process.GroupFormationProcess;
import unipotsdam.gf.process.IExecutionProcess;
import unipotsdam.gf.process.PeerAssessmentProcess;
import unipotsdam.gf.process.tasks.Task;
import unipotsdam.gf.process.tasks.TaskName;
import unipotsdam.gf.util.CollectionUtil;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.xml.bind.JAXBException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static unipotsdam.gf.util.CollectionUtil.*;

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
    private IExecutionProcess iExecutionProcess;

    @Inject
    private DossierCreationProcess dossierCreationProcess;

    @Inject
    private GroupFormationProcess groupFormationProcess;

    @Inject
    private PeerAssessmentProcess peerAssessmentProcess;

    @Inject
    private EmailService emailService;

    private static Map<Phase, ArrayList<TaskName>> phaseMap;


    public static synchronized Map<Phase, ArrayList<TaskName>> getPhaseMap() {
        if (phaseMap == null){
            try {
                phaseMap = getPhaseTaskMap();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return phaseMap;
    }


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
    public void endPhase(Phase currentPhase, Project project)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException, WrongNumberOfParticipantsException, JAXBException, JsonProcessingException {
        Phase changeToPhase = getNextPhase(currentPhase);
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
                saveState(project, changeToPhase);
                iExecutionProcess.start(project);
                break;
            case Execution:
                // check if the portfolios have been prepared for evaluation (relevant entries selected)
                iExecutionProcess.finishPhase(project);
                if (!iExecutionProcess.isPhaseCompleted(project)) {
                    return;
                }
                saveState(project, changeToPhase);
                peerAssessmentProcess.startPeerAssessmentPhase(project);
                break;
            case Assessment:
                saveState(project, changeToPhase);
                peerAssessmentProcess.startDocentGrading(project);
                break;
            case GRADING:
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
                return Phase.GRADING;
            case GRADING:
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

    private static Map<Phase, ArrayList<TaskName>> getPhaseTaskMap()
            throws InstantiationException, IllegalAccessException {
        HashMap<Phase, ArrayList<TaskName>> phaseMap = new HashMap<Phase, ArrayList<TaskName>>();
        TaskName[] values = TaskName.values();
        for (TaskName value : values) {
            switch (value) {
                case WAIT_FOR_GRADING:
                    updateValueInMap(phaseMap, Phase.Assessment, value);
                    break;
                case GIVE_FEEDBACK:
                    updateValueInMap(phaseMap, Phase.DossierFeedback, value);
                    break;
                case WAIT_FOR_PARTICPANTS:
                    updateValueInMap(phaseMap, Phase.GroupFormation, value);
                    break;
                case CHOOSE_REFLEXION_QUESTIONS:
                    updateValueInMap(phaseMap, Phase.Assessment, value);
                    break;
                case GIVE_EXTERNAL_ASSESSMENT:
                    updateValueInMap(phaseMap, Phase.Assessment, value);
                    break;
                case UPLOAD_DOSSIER:
                    updateValueInMap(phaseMap, Phase.DossierFeedback, value);
                    break;
                case GIVE_INTERNAL_ASSESSMENT:
                    updateValueInMap(phaseMap, Phase.Assessment, value);
                    break;
                case CLOSE_GROUP_FINDING_PHASE:
                    updateValueInMap(phaseMap, Phase.GroupFormation, value);
                    break;
                case CHOOSE_FITTING_COMPETENCES:
                    updateValueInMap(phaseMap, Phase.Execution, value);
                    break;
                case GIVE_EXTERNAL_ASSESSMENT_TEACHER:
                    updateValueInMap(phaseMap, Phase.GRADING, value);
                    break;
                case SEE_FEEDBACK:
                    updateValueInMap(phaseMap, Phase.DossierFeedback, value);
                    break;
                case REEDIT_DOSSIER:
                    updateValueInMap(phaseMap, Phase.DossierFeedback, value);
                    break;
                case WAIT_FOR_UPLOAD:
                    updateValueInMap(phaseMap, Phase.Assessment, value);
                    break;
                case ANNOTATE_DOSSIER:
                    updateValueInMap(phaseMap, Phase.DossierFeedback, value);
                    break;
                case WAITING_FOR_GROUP:
                    updateValueInMap(phaseMap, Phase.GroupFormation, value);
                    break;
                case UPLOAD_PRESENTATION:
                    updateValueInMap(phaseMap, Phase.Assessment, value);
                    break;
                case ANSWER_REFLEXION_QUESTIONS:
                    updateValueInMap(phaseMap, Phase.Execution, value);
                    break;
                case CLOSE_DOSSIER_FEEDBACK_PHASE:
                    updateValueInMap(phaseMap, Phase.DossierFeedback, value);
                    break;
                case CLOSE_PEER_ASSESSMENTS_PHASE:
                    updateValueInMap(phaseMap, Phase.Assessment, value);
                    break;
                case WAITING_FOR_STUDENT_DOSSIERS:
                    updateValueInMap(phaseMap, Phase.DossierFeedback, value);
                    break;
                case COLLECT_RESULTS_FOR_ASSESSMENT:
                    updateValueInMap(phaseMap, Phase.Execution, value);
                    break;
                case END_STUDENT:
                    updateValueInMap(phaseMap, Phase.GRADING, value);
                    break;
                case GIVE_FINAL_GRADES:
                    updateValueInMap(phaseMap, Phase.GRADING, value);
                    break;
                case END_EXECUTION_PHASE:
                    updateValueInMap(phaseMap, Phase.Execution, value);
                    break;
                case UPLOAD_FINAL_REPORT:
                    updateValueInMap(phaseMap, Phase.Assessment, value);
                    break;
                case CLOSE_EXECUTION_PHASE:
                    updateValueInMap(phaseMap, Phase.Execution, value);
                    break;
                case INTRODUCE_E_PORTFOLIO_DOCENT:
                    updateValueInMap(phaseMap, Phase.Execution, value);
                    break;
                case INTRODUCE_E_PORTFOLIO_STUDENT:
                    updateValueInMap(phaseMap, Phase.Execution, value);
                    break;
                case END_DOCENT:
                    updateValueInMap(phaseMap, Phase.GRADING, value);
                    break;
                case WAIT_FOR_REFLECTION:
                    updateValueInMap(phaseMap, Phase.Execution, value);
                    break;
                case CONTACT_GROUP_MEMBERS:
                    updateValueInMap(phaseMap, Phase.GroupFormation, value);
                    break;
                case CREATE_LEARNING_GOAL_DIARY:
                    updateValueInMap(phaseMap, Phase.Execution, value);
                    break;
                case EDIT_FORMED_GROUPS:
                    updateValueInMap(phaseMap, Phase.GroupFormation, value);
                    break;
                case FINALIZE_ASSESSMENT:
                    updateValueInMap(phaseMap, Phase.Execution, value);
                    break;
                case CLOSE_ASSESSMENT_PHASE:
                    updateValueInMap(phaseMap, Phase.Assessment, value);
                    break;
                case REFLECT_DOSSIER_CREATION:
                    updateValueInMap(phaseMap, Phase.Execution, value);
                    break;
                case WAIT_FOR_EXECUTION_PHASE_END:
                    updateValueInMap(phaseMap, Phase.Execution, value);
                    break;
                case WAIT_FOR_ASSESSMENT_MATERIAL_COMPILATION:
                    updateValueInMap(phaseMap, Phase.Execution, value);
                    break;
                case CREATE_QUIZ:
                    updateValueInMap(phaseMap, Phase.Execution, value);
                    break;
                case EDIT_FEEDBACK:
                    updateValueInMap(phaseMap, Phase.DossierFeedback, value);
                    break;
                case WRITE_EJOURNAL:
                    updateValueInMap(phaseMap, Phase.Execution, value);
                    break;
                case FINALIZE_DOSSIER:
                    updateValueInMap(phaseMap, Phase.DossierFeedback, value);
                    break;
                case FINALIZE_EJOURNAL:
                    updateValueInMap(phaseMap, Phase.Execution, value);
                    break;
                case FORM_GROUPS_MANUALLY:
                    updateValueInMap(phaseMap, Phase.GroupFormation, value);
                    break;
            }
        }

        return phaseMap;
    }

    @Override
    public java.util.List<TaskName> getTaskNames(Phase phase) {
        return phaseMap.get(phase);
    }

    @Override
    public Phase getCorrespondingPhase(TaskName taskName) {
        for (Phase phase : phaseMap.keySet()) {
            if (phaseMap.get(phase).contains(taskName)) {
                return phase;
            }
        }
        return null;
    }

    @Override
    public TaskName getLastTask(Phase phase) {
        switch (phase) {
            case GRADING:
                return TaskName.END_DOCENT;
            case DossierFeedback:
                return TaskName.CLOSE_DOSSIER_FEEDBACK_PHASE;
            case Execution:
                return TaskName.CLOSE_EXECUTION_PHASE;
            case Assessment:
                return TaskName.CLOSE_ASSESSMENT_PHASE;
            case GroupFormation:
                return TaskName.CLOSE_GROUP_FINDING_PHASE;
            case Projectfinished:
                return TaskName.END_DOCENT;
        }
        return null;
    }

    @Override
    public java.util.List<Phase> getPreviousPhases(Phase phase) {
        ArrayList<Phase> phases = new ArrayList<>();
        phases.add(Phase.GroupFormation);
        phases.add(Phase.DossierFeedback);
        phases.add(Phase.Execution);
        phases.add(Phase.Assessment);
        phases.add(Phase.GRADING);
        if (phase.equals(Phase.GroupFormation)) {
            return null;
        }
        return phases.subList(0, phases.indexOf(phase) -1);
    }


}

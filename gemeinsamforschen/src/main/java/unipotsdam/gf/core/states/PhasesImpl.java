package unipotsdam.gf.core.states;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.states.model.Constraints;
import unipotsdam.gf.core.states.model.ProjectPhase;
import unipotsdam.gf.interfaces.Feedback;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.interfaces.IJournal;
import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.interfaces.IPhases;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.assessment.controller.service.PeerAssessmentDummy;
import unipotsdam.gf.modules.communication.service.CommunicationDummyService;
import unipotsdam.gf.modules.peer2peerfeedback.DummyFeedback;
import unipotsdam.gf.view.Messages;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dehne on 31.05.2018.
 * This class should be used to manage changing the phases in a central location
 * it has dependencies to most modules, as they are required to check the constraints
 * when changing between phases
 */
@ManagedBean
public class PhasesImpl implements IPhases {

    private IPeerAssessment iPeerAssessment = new PeerAssessmentDummy();

    private Feedback feedback = new DummyFeedback();

    private ICommunication iCommunication = new CommunicationDummyService();

    private IJournal iJournal;

    public PhasesImpl() {
    }

    /**
     * use this if you don't know how dependency injection works
     * @param iPeerAssessment
     * @param feedback
     * @param iCommunication
     * @param iJournal
     */
    @Inject
    public PhasesImpl(IPeerAssessment iPeerAssessment, Feedback feedback, ICommunication iCommunication, IJournal iJournal) {
        this.iPeerAssessment = iPeerAssessment;
        this.feedback = feedback;
        this.iCommunication = iCommunication;
        this.iJournal = iJournal;
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
    public void endPhase(ProjectPhase currentPhase, Project project) {
        ProjectPhase changeToPhase = getNextPhase(currentPhase);
        Map<StudentIdentifier, Constraints> tasks = new HashMap<>();
        switch (changeToPhase) {
            case CourseCreation:
                // saving the state
                saveState(project, changeToPhase);
                break;
            case GroupFormation:
                // inform users about the formed groups, optionally giving them a hint on what happens next
                iCommunication.sendMessageToUsers(project, Messages.GroupFormation(project));
                saveState(project, changeToPhase);
                break;
            case DossierFeedback:
                // check if everybody has uploaded a dossier

                Boolean feedbacksGiven = feedback.checkFeedbackConstraints(project);
                if (!feedbacksGiven) {
                    feedback.assigningMissingFeedbackTasks(project);
                } else {
                    // send a message to the users informing them about the start of the new phase
                    iCommunication.sendMessageToUsers(project, Messages.NewFeedbackTask(project));
                    saveState(project, changeToPhase);
                }
                break;
            case Execution:
                // check if the portfolios have been prepared for evaluation (relevant entries selected)
                // todo: Boolean portfoliosReady = iJournal.getPortfoliosForEvaluationPrepared(project);
                Boolean portfoliosReady = true;
                if (portfoliosReady) {
                    // inform users about the end of the phase
                    iCommunication.sendMessageToUsers(project, Messages.AssessmentPhaseStarted(project));
                    saveState(project, changeToPhase);
                } else {
                    iJournal.assignMissingPortfolioTasks(project);
                }
                break;
            case Assessment:
                Boolean allAssessmentsDone = iPeerAssessment.allAssessmentsDone(project.getId());
                if (allAssessmentsDone) {
                    iCommunication.sendMessageToUsers(project, Messages.CourseEnds(project));
                    saveState(project, changeToPhase);
                } else {
                    iPeerAssessment.assignMissingAssessmentTasks(project);
                }
                break;
            case Projectfinished:
                closeProject();
                break;
        }
    }

    private void closeProject() {
        // TODO implement
    }

    ProjectPhase getNextPhase(ProjectPhase projectPhase) {
        switch (projectPhase) {
            case CourseCreation:
                return ProjectPhase.GroupFormation;
            case GroupFormation:
                return ProjectPhase.DossierFeedback;
            case DossierFeedback:
                return ProjectPhase.Execution;
            case Execution:
                return ProjectPhase.Assessment;
            case Assessment:
                return ProjectPhase.Projectfinished;
        }
        return null;
    }

    private void saveState(Project project, ProjectPhase currentPhase) {
        assert project.getId() != null;
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "UPDATE `projects` SET `phase`=? WHERE id=? LIMIT 1";
        connect.issueUpdateStatement(mysqlRequest, currentPhase.name(), project.getId());
        connect.close();
    }


    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }
}

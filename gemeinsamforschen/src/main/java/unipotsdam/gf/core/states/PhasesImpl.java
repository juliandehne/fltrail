package unipotsdam.gf.core.states;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.UserDAO;
import unipotsdam.gf.core.states.model.ConstraintsMessages;
import unipotsdam.gf.core.states.model.ProjectPhase;
import unipotsdam.gf.interfaces.Feedback;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.interfaces.IJournal;
import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.interfaces.IPhases;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.assessment.controller.service.PeerAssessment;
import unipotsdam.gf.modules.communication.service.CommunicationService;
import unipotsdam.gf.modules.communication.service.UnirestService;
import unipotsdam.gf.modules.groupfinding.service.GroupDAO;
import unipotsdam.gf.modules.journal.service.IJournalImpl;
import unipotsdam.gf.modules.peer2peerfeedback.DummyFeedback;
import unipotsdam.gf.view.Messages;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import java.util.Map;

/**
 * Created by dehne on 31.05.2018.
 * This class should be used to manage changing the phases in a central location
 * it has dependencies to most modules, as they are required to check the constraints
 * when changing between phases
 */
@ManagedBean
public class PhasesImpl implements IPhases {

    private IPeerAssessment iPeerAssessment = new PeerAssessment();

    private Feedback feedback = new DummyFeedback();

    private UserDAO userDAO = new UserDAO(new MysqlConnect());

    private GroupDAO groupDAO = new GroupDAO(new MysqlConnect());

    private ICommunication iCommunication = new CommunicationService(new UnirestService(), userDAO, groupDAO);

    private IJournal iJournal = new IJournalImpl();

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
        Map<StudentIdentifier, ConstraintsMessages> tasks;
        if (changeToPhase != null)
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

                tasks = feedback.checkFeedbackConstraints(project);
                if (tasks.size()>0) {
                    iCommunication.informAboutMissingTasks(tasks, project);
                } else {
                    // send a message to the users informing them about the start of the new phase
                    iCommunication.sendMessageToUsers(project, Messages.NewFeedbackTask(project));
                    saveState(project, changeToPhase);
                }
                break;
            case Execution:
                // check if the portfolios have been prepared for evaluation (relevant entries selected)
                tasks = iJournal.getPortfoliosForEvaluationPrepared(project);
                if (tasks.size()<1) {
                    // inform users about the end of the phase
                    iCommunication.sendMessageToUsers(project, Messages.AssessmentPhaseStarted(project));
                    saveState(project, changeToPhase);
                } else {
                    iCommunication.informAboutMissingTasks(tasks, project);
                }
                break;
            case Assessment:
                tasks = iPeerAssessment.allAssessmentsDone(project.getId());
                if(tasks.size()<1){
                    iCommunication.sendMessageToUsers(project, Messages.CourseEnds(project));
                    saveState(project, changeToPhase);
                } else {
                    iPeerAssessment.assignMissingAssessmentTasks(project);
                }
                break;
            case Projectfinished:
                closeProject();
                break;
            default:{}
        }
    }

    private void closeProject() {
        // TODO implement
    }

    private ProjectPhase getNextPhase(ProjectPhase projectPhase) {
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

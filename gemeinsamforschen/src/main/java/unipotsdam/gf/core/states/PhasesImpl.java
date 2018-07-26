package unipotsdam.gf.core.states;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.interfaces.*;
import unipotsdam.gf.view.Messages;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by dehne on 31.05.2018.
 * This class should be used to manage changing the phases in a central location
 * it has dependencies to most modules, as they are required to check the constraints
 * when changing between phases
 */
@ManagedBean
public class PhasesImpl implements IPhases {

    private IPeerAssessment iPeerAssessment;

    private Feedback feedback;

    private ICommunication iCommunication;

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

    @Override
    public void endPhase(ProjectPhase currentPhase, Project project) {
        switch (currentPhase) {
            case CourseCreation:
                // saving the state
                saveState(project,getNextPhase(currentPhase));
                break;
            case GroupFormation:
                // inform users about the formed groups, optionally giving them a hint on what happens next
                iCommunication.sendMessageToUsers(project, Messages.GroupFormation(project));
                saveState(project,getNextPhase(currentPhase));
                break;
            case DossierFeedback:
                // check if everybody has uploaded a dossier
                Boolean feedbacksGiven = feedback.checkFeedbackConstraints(project);
                if (!feedbacksGiven) {
                    feedback.assigningMissingFeedbackTasks(project);
                } else {
                    // send a message to the users informing them about the start of the new phase
                    iCommunication.sendMessageToUsers(project, Messages.NewFeedbackTask(project));
                    saveState(project,getNextPhase(currentPhase));
                }
                break;
            case Execution:
                // check if the portfolios have been prepared for evaluation (relevant entries selected)
                Boolean portfoliosReady = iJournal.getPortfoliosForEvaluationPrepared(project);
                if (portfoliosReady) {
                    // inform users about the end of the phase
                    iCommunication.sendMessageToUsers(project, Messages.AssessmentPhaseStarted(project));
                    saveState(project,getNextPhase(currentPhase));
                } else {
                    iJournal.assignMissingPortfolioTasks(project);
                }
                break;
            case Assessment:
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

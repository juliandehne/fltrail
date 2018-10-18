package unipotsdam.gf.process;

import unipotsdam.gf.interfaces.Feedback;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.submission.controller.SubmissionController;
import unipotsdam.gf.modules.submission.model.FullSubmission;
import unipotsdam.gf.modules.submission.model.FullSubmissionPostRequest;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.constraints.Constraints;
import unipotsdam.gf.process.constraints.ConstraintsImpl;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class DossierCreationProcess {


    @Inject
    private SubmissionController submissionController;

    @Inject
    private Management management;

    @Inject
    private UserDAO userDAO;

    @Inject
    private TaskDAO taskDAO;

    @Inject
    private Feedback feedback;

    @Inject
    private ConstraintsImpl constraints;

    /**
     * start the Dossier Phase
     *
     * @param project
     */
    public void startDossierPhase(Project project) {
        Task task = new Task(TaskName.CLOSE_GROUP_FINDING_PHASE, project.getAuthorEmail(), project.getName(),
                Progress.FINISHED);
        taskDAO.updateForUser(task);

        // create a task, telling the docent to wait for students upload of dossiers
        taskDAO.persist(project, new User(project.getAuthorEmail()), TaskName.WAITING_FOR_STUDENT_DOSSIERS, Phase
                .DossierFeedback, TaskType.INFO);

        // TODO create waiting for feedback to complete task

        taskDAO.persistMemberTask(project, TaskName.UPLOAD_DOSSIER, Phase.DossierFeedback);
    }

    /**
     * @param fullSubmissionPostRequest
     * @param user
     * @param project
     * @return
     */
    public FullSubmission addSubmission(
            FullSubmissionPostRequest fullSubmissionPostRequest, User user, Project project) {
        FullSubmission fullSubmission = submissionController.addFullSubmission(fullSubmissionPostRequest);

        // this completes the upload task
        Task task = new Task(TaskName.UPLOAD_DOSSIER, user.getEmail(), project.getName(), Progress.FINISHED);
        taskDAO.updateForUser(task);

        // this triggers the annotate task
        taskDAO.persist(project, user, TaskName.ANNOTATE_DOSSIER, Phase.DossierFeedback, TaskType.LINKED);

        return fullSubmission;
    }

    /**
     * @param fullSubmission
     * @param user
     */
    public void finalizeDossier(FullSubmission fullSubmission, User user, Project project) {
        // mark as final in db
        submissionController.markAsFinal(fullSubmission);

        // mark annotate task as finished in db
        Task task = new Task(TaskName.ANNOTATE_DOSSIER, user.getEmail(), fullSubmission.getProjectName(),
                Progress.FINISHED);
        taskDAO.updateForUser(task);

        if (constraints.checkIfFeedbackCanBeDistributed(project)) {
            // distributefeedbacks
            feedback.assignFeedbackTasks(project);

            // persist tasks for feedback
            taskDAO.persistMemberTask(
                    new Project(fullSubmission.getProjectName()), TaskName.GIVE_FEEDBACK, Phase.DossierFeedback);
        }
    }


    public void finishPhase(Project project) {
        /*
        TODO implement
         */
        /** TODO: Move this to the dossierCreationProcess
         /*   if (tasks.size() > 0) {
         iCommunication.informAboutMissingTasks(tasks, project);
         } else {
         // send a message to the users informing them about the start of the new phase
         iCommunication.sendMessageToUsers(project, Messages.NewFeedbackTask(project));
         saveState(project, changeToPhase);
         }*/
    }
}

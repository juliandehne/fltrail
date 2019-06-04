package unipotsdam.gf.process;

import com.itextpdf.text.DocumentException;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition.FormDataContentDispositionBuilder;
import unipotsdam.gf.interfaces.Feedback;
import unipotsdam.gf.modules.assessment.controller.model.ContributionCategory;
import unipotsdam.gf.modules.fileManagement.FileManagementService;
import unipotsdam.gf.modules.fileManagement.FileRole;
import unipotsdam.gf.modules.fileManagement.FileType;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.submission.controller.SubmissionController;
import unipotsdam.gf.modules.submission.model.FullSubmission;
import unipotsdam.gf.modules.submission.model.FullSubmissionPostRequest;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.constraints.ConstraintsImpl;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class DossierCreationProcess {


    @Inject
    private SubmissionController submissionController;

    @Inject
    private TaskDAO taskDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private ConstraintsImpl constraints;

    @Inject
    private Feedback feedback;

    @Inject
    private FileManagementService fileManagementService;

    /**
     * start the Dossier Phase
     *
     * @param project
     */
    public void start(Project project) {
        Task task = new Task(TaskName.CLOSE_GROUP_FINDING_PHASE, project.getAuthorEmail(), project.getName(),
                Progress.FINISHED);
        taskDAO.updateForUser(task);

        // create a task, telling the docent to wait for students upload of dossiers
        taskDAO.persist(project, new User(project.getAuthorEmail()), TaskName.WAITING_FOR_STUDENT_DOSSIERS, Phase
                .DossierFeedback, TaskType.INFO);
        taskDAO.persistTaskForAllGroups(project, TaskName.UPLOAD_DOSSIER, Phase.DossierFeedback);
    }

    /**
     * @param fullSubmissionPostRequest
     * @param user
     * @param project
     * @return
     */
    public FullSubmission addSubmission(
            FullSubmissionPostRequest fullSubmissionPostRequest, User user, Project project) throws DocumentException, IOException {

        FormDataContentDispositionBuilder builder = FormDataContentDisposition.name("dossierUpload").fileName(fullSubmissionPostRequest.getContributionCategory() + "_" + user.getEmail() + ".pdf");
        fileManagementService.saveStringAsPDF(user, project, fullSubmissionPostRequest.getHtml(), builder.build(),
                FileRole.DOSSIER, FileType.HTML);

        FullSubmission fullSubmission = submissionController.addFullSubmission(fullSubmissionPostRequest);

        // this completes the upload task
        if (fullSubmission.getContributionCategory() == ContributionCategory.DOSSIER) {
            Task task = new Task(TaskName.UPLOAD_DOSSIER, user.getEmail(), project.getName(), Progress.INPROGRESS);
            taskDAO.updateForGroup(task);

            // this triggers the annotate task
            taskDAO.persistTaskGroup(project, user, TaskName.ANNOTATE_DOSSIER, Phase.DossierFeedback);
        }
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
        //todo: for iterative work, these two tasks need to be seperated
        Task task = new Task(TaskName.UPLOAD_DOSSIER, user.getEmail(), project.getName(),
                Progress.FINISHED);
        taskDAO.updateForGroup(task);
        Task task1 = new Task(TaskName.ANNOTATE_DOSSIER, user.getEmail(), project.getName(),
                Progress.FINISHED);
        taskDAO.updateForGroup(task1);
        taskDAO.persistTaskGroup(project, user, TaskName.GIVE_FEEDBACK, Phase.DossierFeedback);


        if (constraints.checkIfFeedbackCanBeDistributed(project)) {
            // create Task to give Feedback
            List<User> projectParticipants = userDAO.getUsersByProjectName(project.getName());
            List<Task> allFeedbackTasks = new ArrayList<>();
            for (User participant : projectParticipants) {
                Task giveFeedbackTask1 = taskDAO.getTasksWithTaskName(project, participant, TaskName.GIVE_FEEDBACK).get(0);
                if (!allFeedbackTasks.contains(giveFeedbackTask1))
                    allFeedbackTasks.add(giveFeedbackTask1);
            }
            //specifies user, who needs to give a feedback in DB
            feedback.specifyFeedbackTasks(allFeedbackTasks);
        }
    }

    public void createCloseFeedBackPhaseTask(Project project) {
        taskDAO.persistTeacherTask(project, TaskName.CLOSE_DOSSIER_FEEDBACK_PHASE, Phase.DossierFeedback);
    }

    public void finishPhase(Project project) {

        User user = userDAO.getUserByEmail(project.getAuthorEmail());
        Task task = new Task();
        task.setUserEmail(user.getEmail());
        task.setProjectName(project.getName());
        task.setProgress(Progress.FINISHED);
        task.setTaskName(TaskName.CLOSE_DOSSIER_FEEDBACK_PHASE);
        taskDAO.updateForUser(task);
        taskDAO.persist(taskDAO.createUserDefault(project, user, TaskName.WAIT_FOR_REFLECTION, Phase.Execution));
        //todo: implement communication stuff
        /*   if (tasks.size() > 0) {
         iCommunication.informAboutMissingTasks(tasks, project);
         } else {
         // send a message to the users informing them about the start of the new phase
         iCommunication.sendMessageToUsers(project, Messages.NewFeedbackTask(project));
         saveState(project, changeToPhase);
         }*/
    }

    public void createSeeFeedBackTask(Project project, Integer groupId) {
        Integer feedbackedgroup = submissionController.getFeedbackedgroup(project, groupId);
        taskDAO.persistTaskGroup(project, feedbackedgroup, TaskName.SEE_FEEDBACK, Phase.DossierFeedback);
    }

    public int getFeedBackTarget(Project project, User user) {
        return feedback.getFeedBackTarget(project, user);
    }
}

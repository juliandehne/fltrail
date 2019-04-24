package unipotsdam.gf.process;

import com.itextpdf.text.DocumentException;
import com.itextpdf.tool.xml.exceptions.CssResolverException;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition.FormDataContentDispositionBuilder;
import unipotsdam.gf.interfaces.Feedback;
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
import unipotsdam.gf.process.tasks.Progress;
import unipotsdam.gf.process.tasks.Task;
import unipotsdam.gf.process.tasks.TaskDAO;
import unipotsdam.gf.process.tasks.TaskName;
import unipotsdam.gf.process.tasks.TaskType;

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
        taskDAO.persistMemberTask(project, TaskName.UPLOAD_DOSSIER, Phase.DossierFeedback);
    }

    /**
     * @param fullSubmissionPostRequest
     * @param user
     * @param project
     * @return
     */
    public FullSubmission addSubmission(
            FullSubmissionPostRequest fullSubmissionPostRequest, User user, Project project) throws CssResolverException, DocumentException, IOException {

        FormDataContentDispositionBuilder builder = FormDataContentDisposition.name("dossierUpload").fileName("dossier.pdf");
        fileManagementService.saveFileAsPDF(user, project, fullSubmissionPostRequest.getHtml(), builder.build(),
                FileRole.DOSSIER, FileType.HTML);

        FullSubmission fullSubmission = submissionController.addFullSubmission(fullSubmissionPostRequest);
        // TODO: add pdf convert and save here -> maybe somewhere else, to get it automated each time you save an dossier
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
        Task task = new Task(TaskName.ANNOTATE_DOSSIER, user.getEmail(), project.getName(),
                Progress.FINISHED);
        taskDAO.updateForUser(task);

        if (constraints.checkIfFeedbackCanBeDistributed(project)) {
            // create Task to give Feedback
            List<User> projectParticipants = userDAO.getUsersByProjectName(project.getName());
            List<Task> allFeedbackTasks = new ArrayList<>();
            for (User participant : projectParticipants) {
                Task giveFeedbackTask = taskDAO.createDefault(
                        project, participant, TaskName.GIVE_FEEDBACK, Phase.DossierFeedback);
                taskDAO.persist(giveFeedbackTask);
                allFeedbackTasks.add(giveFeedbackTask);
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
        taskDAO.persist(taskDAO.createDefault(project, user, TaskName.WAIT_FOR_REFLECTION, Phase.Execution));
        //todo: implement communication stuff
        /*   if (tasks.size() > 0) {
         iCommunication.informAboutMissingTasks(tasks, project);
         } else {
         // send a message to the users informing them about the start of the new phase
         iCommunication.sendMessageToUsers(project, Messages.NewFeedbackTask(project));
         saveState(project, changeToPhase);
         }*/
    }

    public void createSeeFeedBackTask(Project project, User distributeur) {
        User user = submissionController.getFeedbackedUser(project, distributeur);
        taskDAO.persist(project, user, TaskName.SEE_FEEDBACK, Phase.DossierFeedback);
    }

    public String getFeedBackTarget(Project project, User user) {
        return feedback.getFeedBackTarget(project, user);
    }
}

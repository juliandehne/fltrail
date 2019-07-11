package unipotsdam.gf.process;

import com.itextpdf.text.DocumentException;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import unipotsdam.gf.interfaces.Feedback;
import unipotsdam.gf.interfaces.IContributionFeedback;
import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.interfaces.IReflectionQuestion;
import unipotsdam.gf.modules.contributionFeedback.model.ContributionFeedback;
import unipotsdam.gf.modules.fileManagement.FileManagementService;
import unipotsdam.gf.modules.fileManagement.FileRole;
import unipotsdam.gf.modules.fileManagement.FileType;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.reflection.model.ReflectionQuestion;
import unipotsdam.gf.modules.submission.controller.SubmissionController;
import unipotsdam.gf.modules.submission.model.FullSubmission;
import unipotsdam.gf.modules.submission.model.FullSubmissionPostRequest;
import unipotsdam.gf.modules.submission.model.Visibility;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.modules.wizard.WizardRelevant;
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
    private FileManagementService fileManagementService;

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
    private GroupDAO groupDAO;

    @Inject
    private IPeerAssessment peerAssessment;

    @Inject
    private ReflexionProcess reflexionProcess;

    @Inject
    private IContributionFeedback contributionFeedbackService;

    @Inject
    private IReflectionQuestion reflectionQuestionService;


    /**
     * start the Dossier Phase
     *
     * @param project the project that is about to get updated to next phase (to dossierFeedback-phase)
     */
    public void start(Project project) {
        Task task = new Task(TaskName.CLOSE_GROUP_FINDING_PHASE, new User(project.getAuthorEmail()), project,
                Progress.FINISHED);
        taskDAO.updateForUser(task);

        // create a task, telling the docent to wait for students upload of dossiers
        taskDAO.persist(project, new User(project.getAuthorEmail()), TaskName.WAITING_FOR_STUDENT_DOSSIERS, Phase
                .DossierFeedback, TaskType.INFO);
        taskDAO.persistTaskForAllGroups(project, TaskName.UPLOAD_DOSSIER, Phase.DossierFeedback);
    }


    /**
     *
     * add the initial dossier
     * @param fullSubmissionPostRequest
     * @param userEmail
     * @param user
     * @param project
     * @return
     */
    @WizardRelevant
    public FullSubmission addDossier(
            FullSubmissionPostRequest fullSubmissionPostRequest, String userEmail, User user, Project project) {
        if (fullSubmissionPostRequest.isPersonal()) {
            fullSubmissionPostRequest.setUserEMail(userEmail);
        }

        final FullSubmission fullSubmission = submissionController.addFullSubmission(fullSubmissionPostRequest);
        fileManagementService.deleteFiles(project, user, fullSubmission.getFileRole());
        switch (fullSubmission.getFileRole()) {
            case DOSSIER:
                try {
                    fileManagementService.saveStringAsPDF(user, project, fullSubmissionPostRequest);
                } catch (DocumentException | IOException e) {
                    e.printStackTrace();
                }
                notifyAboutSubmission(user, project);
                break;
            case PORTFOLIO:
                break;
            case REFLECTION_QUESTION:
                ReflectionQuestion reflectionQuestion = new ReflectionQuestion(fullSubmissionPostRequest.getReflectionQuestionId());
                reflectionQuestionService.saveAnswerReference(fullSubmission, reflectionQuestion);
        }
        return fullSubmission;
    }


    /**
     * update dossier by group
     * @param fullSubmissionPostRequest
     * @param user
     * @param project
     * @param finalize
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public FullSubmission updateSubmission(FullSubmissionPostRequest fullSubmissionPostRequest,
                                           User user, Project project, Boolean finalize) throws IOException, DocumentException {
        // delete old files
        fileManagementService.deleteFiles(new Project(fullSubmissionPostRequest.getProjectName()), user, fullSubmissionPostRequest.getFileRole());
        // write new ones
        FormDataContentDisposition.FormDataContentDispositionBuilder builder = FormDataContentDisposition.name("dossierUpload").fileName(fullSubmissionPostRequest.getFileRole() + "_" + user.getEmail() + ".pdf");
        fileManagementService.saveStringAsPDF(user, project, fullSubmissionPostRequest.getHtml(), builder.build(),
                FileRole.DOSSIER, FileType.HTML);

        FullSubmission fullSubmission = submissionController.addFullSubmission(fullSubmissionPostRequest, 1);
        submissionController.markAsFinal(fullSubmission, finalize);

        if (finalize) {
            createCloseFeedBackPhaseTask(new Project(fullSubmission.getProjectName()), user);
        }

        return fullSubmission;
    }

    /**
     * finalize dossier
     * @param submissionId
     * @param projectId
     * @param userEmail
     */
    public void finalizeDossier(
            String submissionId, String projectId,
            String userEmail) {
        FullSubmission fullSubmission = submissionController.getFullSubmission(submissionId);
        switch (fullSubmission.getFileRole()) {
            case DOSSIER:
                finalizeDossier(fullSubmission, new User(userEmail), new Project(projectId));
                break;
            case PORTFOLIO:
                break;
        }
    }

    /**
     * save feedback
     * @param contributionFeedback
     * @return
     */
    @WizardRelevant
    public ContributionFeedback saveFeedback(ContributionFeedback contributionFeedback) {
        return contributionFeedbackService.saveContributionFeedback(contributionFeedback);
    }

    /**
     * Feedback is persisted and tasks are created accordingly
     * @param groupId
     * @param project
     */
    @WizardRelevant
    public void saveFinalFeedback(int groupId, Project project) {
        contributionFeedbackService.endFeedback(project.getName(), groupId);
        createSeeFeedBackTask(project, groupId);
        createReeditDossierTask(project, groupId);
    }

    public int getFeedBackTarget(Project project, User user) {
        return feedback.getFeedBackTarget(project, user);
    }

    public void finishPhase(Project project) {

        User user = userDAO.getUserByEmail(project.getAuthorEmail());
        Task task = new Task(TaskName.CLOSE_DOSSIER_FEEDBACK_PHASE, user, project, Progress.FINISHED );
        taskDAO.updateForUser(task);

        //todo: implement communication stuff
        /*   if (tasks.size() > 0) {
         iCommunication.informAboutMissingTasks(tasks, project);
         } else {
         // send a message to the users informing them about the start of the new phase
         iCommunication.sendMessageToUsers(project, Messages.NewFeedbackTask(project));
         saveState(project, changeToPhase);
         }*/

        // add peer assessment tasks
        // note that this should be moved to another process later on
        //peerAssessmentProcess.startPeerAssessmentPhase(project);
    }

    public void createSeeFeedBackTask(Project project, Integer groupId) {
        Integer feedbackedgroup = submissionController.getFeedbackedgroup(project, groupId);
        int feedbackGroup = peerAssessment.whichGroupToRate(project, groupId);
        ArrayList<Task> reeditDossierTasks = taskDAO.getTasksWithTaskName(feedbackGroup, project, TaskName.REEDIT_DOSSIER);
        if (reeditDossierTasks.size() != 0) {
            Task task = reeditDossierTasks.get(0);
            taskDAO.addTaskType(task, TaskType.LINKED);
        }
        taskDAO.persistTaskGroup(project, feedbackedgroup, TaskName.SEE_FEEDBACK, Phase.DossierFeedback, TaskType.LINKED);
    }

    public void createReeditDossierTask(Project project, Integer groupId) {
        String submissionId = submissionController.getFullSubmissionId(groupId, project, FileRole.DOSSIER);
        FullSubmission fullSubmission = submissionController.getFullSubmission(submissionId);
        FullSubmissionPostRequest fspr = new FullSubmissionPostRequest();
        fspr.setFileRole(fullSubmission.getFileRole());
        fspr.setText(fullSubmission.getText());
        fspr.setProjectName(project.getName());
        fspr.setHeader(fullSubmission.getHeader());
        fspr.setGroupId(groupId);
        fspr.setVisibility(Visibility.GROUP);
        submissionController.addFullSubmission(fspr, 1);
        taskDAO.persistTaskGroup(project, groupId, TaskName.REEDIT_DOSSIER, Phase.DossierFeedback, TaskType.INFO);
    }

    /**
     * @param user                      User who uploaded the Submission for his / her group
     * @param project                   the project the submission was written for
     * @return the fullSubmission with correct ID
     */
    private void notifyAboutSubmission(User user, Project project) {
        // this completes the upload task
        Task task = new Task(TaskName.UPLOAD_DOSSIER, user, project, Progress.INPROGRESS);
        taskDAO.updateForGroup(task);

        // this triggers the annotate task
        taskDAO.persistTaskGroup(project, user, TaskName.ANNOTATE_DOSSIER, Phase.DossierFeedback);

        Group group = groupDAO.getMyGroup(user, project);
        reflexionProcess.startEPortfolioIntroduceTasks(project, group);
    }

    /**
     * @param fullSubmission created in a groupTask, identified by projectName and groupId. Holds Text
     * @param user           User who finalized the Dossier for whole Group.
     */
    private void finalizeDossier(FullSubmission fullSubmission, User user, Project project) {
        // mark as final in db
        submissionController.markAsFinal(fullSubmission, true);

        // mark annotate task as finished in db
        Task taskUpload = new Task(TaskName.UPLOAD_DOSSIER, user, project,
                Progress.FINISHED);
        taskDAO.updateForGroup(taskUpload);
        Task taskAnnotate = new Task(TaskName.ANNOTATE_DOSSIER, user, project,
                Progress.FINISHED);
        taskDAO.updateForGroup(taskAnnotate);
        taskDAO.persistTaskGroup(project, user, TaskName.GIVE_FEEDBACK, Phase.DossierFeedback);
        createReeditDossierTask(project, groupDAO.getMyGroupId(user, project));
        if (constraints.checkIfFeedbackCanBeDistributed(project)) {
            // create Task to give Feedback
            List<Group> groupsInProject = groupDAO.getGroupsByProjectName(project.getName());
            List<Task> allFeedbackTasks = new ArrayList<>();
            for (Group group : groupsInProject) {
                Task giveFeedbackTask1 = taskDAO.getTasksWithTaskName(group.getId(), project, TaskName.GIVE_FEEDBACK).get(0);
                if (!allFeedbackTasks.contains(giveFeedbackTask1))
                    allFeedbackTasks.add(giveFeedbackTask1);
            }
            //specifies user, who needs to give a feedback in DB
            feedback.specifyFeedbackTasks(allFeedbackTasks);
        }
    }

    private void createCloseFeedBackPhaseTask(Project project, User user) {
        Task task = new Task(TaskName.REEDIT_DOSSIER, user, project, Progress.FINISHED);
        taskDAO.updateForGroup(task);
        taskDAO.persistTeacherTask(project, TaskName.CLOSE_DOSSIER_FEEDBACK_PHASE, Phase.DossierFeedback);
    }
}

package unipotsdam.gf.process;

import com.itextpdf.text.DocumentException;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import unipotsdam.gf.interfaces.Feedback;
import unipotsdam.gf.interfaces.IContributionFeedback;
import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.contributionFeedback.model.ContributionFeedback;
import unipotsdam.gf.modules.fileManagement.FileManagementService;
import unipotsdam.gf.modules.fileManagement.FileRole;
import unipotsdam.gf.modules.fileManagement.FileType;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.reflection.model.SelectedReflectionQuestion;
import unipotsdam.gf.modules.submission.controller.SubmissionController;
import unipotsdam.gf.modules.submission.model.FullSubmission;
import unipotsdam.gf.modules.submission.model.FullSubmissionPostRequest;
import unipotsdam.gf.modules.submission.model.Visibility;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.modules.wizard.WizardRelevant;
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
    private FileManagementService fileManagementService;

    @Inject
    private SubmissionController submissionController;

    @Inject
    private TaskDAO taskDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private ProjectDAO projectDao;

    @Inject
    private ConstraintsImpl constraints;

    @Inject
    private Feedback feedback;

    @Inject
    private GroupDAO groupDAO;

    @Inject
    private IPeerAssessment peerAssessment;

    @Inject
    private IContributionFeedback contributionFeedbackService;

    @Inject
    private IExecutionProcess executionProcess;

    /**
     * start the Dossier Phase
     *
     * @param project the project that is about to get updated to next phase (to dossierFeedback-phase)
     */
    public void start(Project project) {
        taskDAO.persistMemberTask(project, TaskName.CONTACT_GROUP_MEMBERS, Phase.DossierFeedback);
        // create a task, telling the docent to wait for students upload of dossiers
        taskDAO.persist(project, new User(project.getAuthorEmail()), TaskName.WAITING_FOR_STUDENT_DOSSIERS, Phase
                .DossierFeedback, TaskType.INFO);
        taskDAO.persistTaskForAllGroups(project, TaskName.UPLOAD_DOSSIER, Phase.DossierFeedback);
    }


    /**
     * add the initial dossier
     *
     * @param fullSubmissionPostRequest*
     * @param user who wrote the dossier
     * @param project for which the dossier is uploaded
     * @return a object which holds all information about the dossier
     */
    @WizardRelevant
    public FullSubmission addDossier(
            FullSubmissionPostRequest fullSubmissionPostRequest, User user, Project project) {
        if (fullSubmissionPostRequest.isSaveUsername()) {
            fullSubmissionPostRequest.setUserEMail(user.getEmail());
        }

        final FullSubmission fullSubmission = submissionController.addFullSubmission(fullSubmissionPostRequest, 0);
        fileManagementService.deleteFiles(project, user, fullSubmission.getFileRole());
        //TODO: this needs to be switched out of dossier creation process again
        switch (fullSubmission.getFileRole()) {
            case DOSSIER:
                try {
                    fileManagementService.saveStringAsPDF(user, project, fullSubmissionPostRequest);
                } catch (DocumentException | IOException e) {
                    e.printStackTrace();
                }
                if (fullSubmissionPostRequest.isFinalized()) {
                    // this completes the upload task
                    Task task = new Task(TaskName.UPLOAD_DOSSIER, user, project, Progress.FINISHED);
                    taskDAO.updateForGroup(task);

                    // this triggers the annotate task
                    taskDAO.persistTaskGroup(project, user, TaskName.ANNOTATE_DOSSIER, Phase.DossierFeedback);
                } else {
                    // this completes the upload task
                    Task task = new Task(TaskName.UPLOAD_DOSSIER, user, project, Progress.INPROGRESS);
                    taskDAO.updateForGroup(task);
                }
                break;
            case PORTFOLIO_ENTRY:
                break;
            case REFLECTION_QUESTION:
                try {
                    SelectedReflectionQuestion reflectionQuestion = new SelectedReflectionQuestion(fullSubmissionPostRequest.getReflectionQuestionId());
                    executionProcess.answerReflectionQuestion(fullSubmission, reflectionQuestion);
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
        return fullSubmission;
    }


    /**
     * update dossier by group
     */
    public FullSubmission updateSubmission(FullSubmissionPostRequest fullSubmissionPostRequest,
                                           User user, Project project, Boolean finalize) throws Exception {
        // delete old files
        fileManagementService.deleteFiles(new Project(fullSubmissionPostRequest.getProjectName()), user, fullSubmissionPostRequest.getFileRole());
        // write new ones
        FormDataContentDisposition.FormDataContentDispositionBuilder builder = FormDataContentDisposition.name("dossierUpload").fileName(fullSubmissionPostRequest.getHeader() + ".pdf");
        fileManagementService.saveStringAsPDF(user, project, fullSubmissionPostRequest.getHtml(), builder.build(),
                FileRole.DOSSIER, FileType.HTML);

        FullSubmission fullSubmission = submissionController.addFullSubmission(fullSubmissionPostRequest, 1);
        submissionController.markAsFinal(fullSubmission, finalize);

        if (finalize) {
            Project project1 = projectDao.getProjectByName(fullSubmission.getProjectName());
            createCloseFeedBackPhaseTask(project1, user);
        }

        return fullSubmission;
    }

    public void finalizeDossier(
            FullSubmission fullSubmission, Project project,
            User user) {
        switch (fullSubmission.getFileRole()) {
            case DOSSIER:
                finalizeDossier(fullSubmission, user, project);
                break;
            case PORTFOLIO_ENTRY:
                break;
        }
    }

    /**
     * save feedback
     */
    @WizardRelevant
    public ContributionFeedback saveFeedback(ContributionFeedback contributionFeedback) throws Exception {
        return contributionFeedbackService.saveContributionFeedback(contributionFeedback);
    }

    /**
     * Feedback is persisted and tasks are created accordingly
     */
    @WizardRelevant
    public void saveFinalFeedback(int groupId, Project project) {
        contributionFeedbackService.endFeedback(project.getName(), groupId);
        linkReeditDossierTask(project, groupId);
        createReeditDossierTask(project, groupId);
    }

    public int getFeedBackTarget(Project project, User user) {
        return feedback.getFeedBackTarget(project, user);
    }

    public void finishPhase(Project project) throws Exception {

        // finish contact group members tasks
        taskDAO.updateForAll(new Task(TaskName.CONTACT_GROUP_MEMBERS, null, project, Progress.FINISHED));

        User user = userDAO.getUserByEmail(project.getAuthorEmail());
        Task task = new Task(TaskName.CLOSE_DOSSIER_FEEDBACK_PHASE, user, project, Progress.FINISHED);
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

    private void linkReeditDossierTask(Project project, Integer groupId) {
        int feedbackGroup = peerAssessment.whichGroupToRate(project, groupId);
        Task reeditDossierTasks = taskDAO.getTasksWithTaskName(feedbackGroup, project, TaskName.REEDIT_DOSSIER);
        if (reeditDossierTasks != null) {
            taskDAO.addTaskType(reeditDossierTasks, TaskType.LINKED);
        }
    }

    private void createReeditDossierTask(Project project, Integer groupId) {
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
     * @param user    User who uploaded the Submission for his / her group
     * @param project the project the submission was written for
     */
    private void notifyAboutSubmission(User user, Project project) {
        // this completes the upload task
        Task task = new Task(TaskName.UPLOAD_DOSSIER, user, project, Progress.INPROGRESS);
        taskDAO.updateForGroup(task);

        // this triggers the annotate task
        taskDAO.persistTaskGroup(project, user, TaskName.ANNOTATE_DOSSIER, Phase.DossierFeedback);
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
        List<Group> groupsInProject = groupDAO.getGroupsByProjectName(project.getName());
        if (groupsInProject.size() > 1) {
            taskDAO.persistTaskGroup(project, user, TaskName.GIVE_FEEDBACK, Phase.DossierFeedback);
            createReeditDossierTask(project, groupDAO.getMyGroupId(user, project));
            if (constraints.checkIfFeedbackCanBeDistributed(project)) {
                // create Task to give Feedback
                List<Task> allFeedbackTasks = new ArrayList<>();
                for (Group group : groupsInProject) {
                    Task giveFeedbackTask1 = taskDAO.getTasksWithTaskName(group.getId(), project, TaskName.GIVE_FEEDBACK);
                    if (!allFeedbackTasks.contains(giveFeedbackTask1))
                        allFeedbackTasks.add(giveFeedbackTask1);
                }
                //specifies user, who needs to give a feedback in DB
                feedback.specifyFeedbackTasks(allFeedbackTasks);
            }
        } else {
            //There is just one group in the project

            taskDAO.createGroupTask(project, groupDAO.getMyGroupId(user, project), TaskName.REEDIT_DOSSIER, Phase.DossierFeedback, Progress.FINISHED);
            taskDAO.persistTeacherTask(project, TaskName.CLOSE_DOSSIER_FEEDBACK_PHASE, Phase.DossierFeedback);
        }
    }

    private void createCloseFeedBackPhaseTask(Project project, User user) {
        Task task = new Task(TaskName.REEDIT_DOSSIER, user, project, Progress.FINISHED);
        taskDAO.updateForGroup(task);
        taskDAO.persistTeacherTask(project, TaskName.CLOSE_DOSSIER_FEEDBACK_PHASE, Phase.DossierFeedback);
    }
}

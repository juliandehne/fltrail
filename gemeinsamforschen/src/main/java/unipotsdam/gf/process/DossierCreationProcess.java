package unipotsdam.gf.process;

import unipotsdam.gf.interfaces.Feedback;
import unipotsdam.gf.interfaces.IReflectionService;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.submission.controller.SubmissionController;
import unipotsdam.gf.modules.submission.model.FullSubmission;
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
    private GroupDAO groupDAO;

    @Inject
    private IReflectionService reflectionService;


    /**
     * start the Dossier Phase
     *
     * @param project the project that is about to get updated to next phase (to dossierFeedback-phase)
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
     * @param user                      User who uploaded the Submission for his / her group
     * @param project                   the project the submission was written for
     * @return the fullSubmission with correct ID
     */
    public void notifyAboutSubmission(User user, Project project) {
        // this completes the upload task
        Task task = new Task(TaskName.UPLOAD_DOSSIER, user.getEmail(), project.getName(), Progress.INPROGRESS);
        taskDAO.updateForGroup(task);

        // this triggers the annotate task
        taskDAO.persistTaskGroup(project, user, TaskName.ANNOTATE_DOSSIER, Phase.DossierFeedback);

        Group group = groupDAO.getMyGroup(user, project);
        reflectionService.startOptionalPortfolioTask(project, group, Phase.DossierFeedback);

    }

    public FullSubmission updateSubmission(FullSubmissionPostRequest fullSubmissionPostRequest,
                                           User user, Project project, Boolean finalize) throws IOException, DocumentException {
        FormDataContentDispositionBuilder builder = FormDataContentDisposition.name("dossierUpload").fileName(fullSubmissionPostRequest.getContributionCategory() + "_" + user.getEmail() + ".pdf");
        fileManagementService.saveStringAsPDF(user, project, fullSubmissionPostRequest.getHtml(), builder.build(),
                FileRole.DOSSIER, FileType.HTML);

        FullSubmission fullSubmission = submissionController.addFullSubmission(fullSubmissionPostRequest, 1);
        submissionController.markAsFinal(fullSubmission, finalize);

        return fullSubmission;
    }

    /**
     * @param fullSubmission created in a groupTask, identified by projectName and groupId. Holds Text
     * @param user           User who finalized the Dossier for whole Group.
     */
    public void finalizeDossier(FullSubmission fullSubmission, User user, Project project) {
        // mark as final in db
        submissionController.markAsFinal(fullSubmission, true);

        // mark annotate task as finished in db
        Task taskUpload = new Task(TaskName.UPLOAD_DOSSIER, user.getEmail(), project.getName(),
                Progress.FINISHED);
        taskDAO.updateForGroup(taskUpload);
        Task taskAnnotate = new Task(TaskName.ANNOTATE_DOSSIER, user.getEmail(), project.getName(),
                Progress.FINISHED);
        taskDAO.updateForGroup(taskAnnotate);
        taskDAO.persistTaskGroup(project, user, TaskName.GIVE_FEEDBACK, Phase.DossierFeedback);


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

        // add peer assessment tasks
        // note that this should be moved to another process later on



         //peerAssessmentProcess.startPeerAssessmentPhaseForTest(project);
    }

    public void createSeeFeedBackTask(Project project, Integer groupId) {
        Integer feedbackedgroup = submissionController.getFeedbackedgroup(project, groupId);
        taskDAO.persistTaskGroup(project, feedbackedgroup, TaskName.SEE_FEEDBACK, Phase.DossierFeedback);
    }

    public void createReeditDossierTask(Project project, Integer groupId) {
        String submissionId = submissionController.getFullSubmissionId(groupId, project);
        FullSubmission fullSubmission = submissionController.getFullSubmission(submissionId);
        FullSubmissionPostRequest fspr = new FullSubmissionPostRequest();
        fspr.setContributionCategory(fullSubmission.getContributionCategory());
        fspr.setHtml(fullSubmission.getText());
        fspr.setProjectName(project.getName());
        fspr.setGroupdId(groupId);
        submissionController.addFullSubmission(fspr, 1);
        taskDAO.persistTaskGroup(project, groupId, TaskName.REEDIT_DOSSIER, Phase.DossierFeedback);
    }

    public int getFeedBackTarget(Project project, User user) {
        return feedback.getFeedBackTarget(project, user);
    }
}

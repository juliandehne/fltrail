package unipotsdam.gf.process;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import unipotsdam.gf.interfaces.IReflection;
import unipotsdam.gf.modules.fileManagement.FileManagementService;
import unipotsdam.gf.modules.fileManagement.FileRole;
import unipotsdam.gf.modules.fileManagement.FileType;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.reflection.model.LearningGoalRequest;
import unipotsdam.gf.modules.reflection.model.LearningGoalRequestResult;
import unipotsdam.gf.modules.reflection.model.ReflectionQuestion;
import unipotsdam.gf.modules.reflection.service.ReflectionQuestionDAO;
import unipotsdam.gf.modules.submission.controller.SubmissionController;
import unipotsdam.gf.modules.submission.model.FullSubmission;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.GroupTask;
import unipotsdam.gf.process.tasks.Progress;
import unipotsdam.gf.process.tasks.Task;
import unipotsdam.gf.process.tasks.TaskDAO;
import unipotsdam.gf.process.tasks.TaskName;
import unipotsdam.gf.process.tasks.TaskType;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import java.util.List;

import static unipotsdam.gf.process.tasks.TaskName.ANSWER_REFLECTION_QUESTIONS;
import static unipotsdam.gf.process.tasks.TaskName.CHOOSE_PORTFOLIO_ENTRIES;
import static unipotsdam.gf.process.tasks.TaskName.CLOSE_EXECUTION_PHASE;
import static unipotsdam.gf.process.tasks.TaskName.CREATE_LEARNING_GOALS_AND_CHOOSE_REFLEXION_QUESTIONS;
import static unipotsdam.gf.process.tasks.TaskName.INTRODUCE_E_PORTFOLIO_STUDENT;
import static unipotsdam.gf.process.tasks.TaskName.WAIT_FOR_EXECUTION_PHASE_END;
import static unipotsdam.gf.process.tasks.TaskName.WAIT_FOR_REFLECTION_QUESTION_CHOICE;

@ManagedBean
public class ExecutionProcess implements IExecutionProcess {

    private static Phase PHASE = Phase.Execution;

    @Inject
    private TaskDAO taskDAO;

    @Inject
    private IReflection reflectionService;

    @Inject
    private GroupDAO groupDAO;

    @Inject
    private ProjectDAO projectDAO;

    @Inject
    private ReflectionQuestionDAO reflectionQuestionDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private FileManagementService fileManagementService;

    @Inject
    private SubmissionController submissionController;

    public void start(Project project) {
        taskDAO.persistTeacherTask(project, CREATE_LEARNING_GOALS_AND_CHOOSE_REFLEXION_QUESTIONS, PHASE);
        taskDAO.persistTaskForAllGroups(project, WAIT_FOR_REFLECTION_QUESTION_CHOICE, PHASE);
    }

    @Override
    public LearningGoalRequestResult saveLearningGoalsAndReflectionQuestions(LearningGoalRequest learningGoalRequest) throws Exception {
        LearningGoalRequestResult requestResult = reflectionService.createLearningGoalWithQuestions(learningGoalRequest);
        if (requestResult == null) {
            return null;
        }

        if (learningGoalRequest.isEndTask()) {
            Project project = new Project(learningGoalRequest.getProjectName());
            endSavingLearningGoalsAndReflectionQuestions(project);
        }
        return requestResult;
    }

    @Override
    public void endSavingLearningGoalsAndReflectionQuestions(Project project) throws Exception {
        Project fullProject = projectDAO.getProjectByName(project.getName());
        User docent = new User(fullProject.getAuthorEmail());
        finishTask(fullProject, docent, CREATE_LEARNING_GOALS_AND_CHOOSE_REFLEXION_QUESTIONS);
        startNewTask(fullProject, docent, CLOSE_EXECUTION_PHASE, false);
        taskDAO.persistMemberTask(fullProject, ANSWER_REFLECTION_QUESTIONS, PHASE);

        List<Group> groups = groupDAO.getGroupsByProjectName(project.getName());
        groups.forEach(group -> {
            GroupTask task = taskDAO.createGroupTask(project, group.getId(), WAIT_FOR_REFLECTION_QUESTION_CHOICE, PHASE, Progress.FINISHED);
            taskDAO.updateGroupTask(task);
        });
    }

    @Override
    public void answerReflectionQuestion(FullSubmission fullSubmission, ReflectionQuestion reflectionQuestion) throws Exception {
        Project project = projectDAO.getProjectByName(fullSubmission.getProjectName());
        reflectionQuestionDAO.saveAnswerReference(fullSubmission, reflectionQuestion);
        ReflectionQuestion fullReflectionQuestion = reflectionQuestionDAO.findBy(reflectionQuestion.getId());
        User user = new User(fullReflectionQuestion.getUserEmail());
        setTaskInProgress(project, user, ANSWER_REFLECTION_QUESTIONS);

        List<ReflectionQuestion> reflectionQuestions = reflectionQuestionDAO.getUnansweredQuestions(project, user, true);

        if (reflectionQuestions.isEmpty()) {
            finishTask(project, user, ANSWER_REFLECTION_QUESTIONS);
            startNewTask(project, user, CHOOSE_PORTFOLIO_ENTRIES, true);
        }
    }

    @Override
    public void selectPortfolioEntries(Project project, User user, List<FullSubmission> selectedPortfolioEntries) throws Exception {
        Project fullProject = projectDAO.getProjectByName(project.getName());
        selectedPortfolioEntries.forEach(selectedPortfolioEntry -> submissionController.markAsFinal(selectedPortfolioEntry, true));
        finishTask(fullProject, user, CHOOSE_PORTFOLIO_ENTRIES);
        startNewTask(fullProject, user, WAIT_FOR_EXECUTION_PHASE_END, false, TaskType.INFO);
    }

    @Override
    public void saveGroupSubmission(Project project, int groupId, String html) throws Exception {
        FormDataContentDisposition.FormDataContentDispositionBuilder builder = FormDataContentDisposition.name("groupSubmissions").fileName(FileRole.GROUP_PORTFOLIO + "_group" + groupId + ".pdf");
        fileManagementService.saveStringAsPDF(groupId, project, html, builder.build(), FileRole.GROUP_PORTFOLIO, FileType.HTML);
    }

    @Override
    public void finishPhase(Project project) throws Exception {
        Project fullProject = projectDAO.getProjectByName(project.getName());
        User docent = new User(fullProject.getAuthorEmail());
        finishTask(fullProject, docent, TaskName.INTRODUCE_E_PORTFOLIO_DOCENT);
        Task closeTask = taskDAO.getUserTask(project, docent, CLOSE_EXECUTION_PHASE, PHASE);
        if (closeTask == null) {
            return;
        }
        finishTask(fullProject, docent, CLOSE_EXECUTION_PHASE);
        finishTaskForAllMember(project, WAIT_FOR_EXECUTION_PHASE_END);
        finishTaskForAllMember(project, INTRODUCE_E_PORTFOLIO_STUDENT);
    }

    @Override
    public boolean isPhaseCompleted(Project project) {
        Task task = taskDAO.getUserTask(project, new User(project.getAuthorEmail()), TaskName.CLOSE_EXECUTION_PHASE, PHASE);

        return task != null && task.getProgress() == Progress.FINISHED;
    }

    private void startNewTask(Project project, User user, TaskName taskName, boolean deadline, TaskType... taskTypes) throws Exception {
        Task newTask = taskDAO.createUserDefault(project, user, taskName, PHASE, Progress.JUSTSTARTED);
        if (taskTypes.length > 0) {
            newTask.setTaskType(taskTypes);
        }
        if (!deadline) {
            newTask.setDeadline(0L);
        }
        Task existingTask = taskDAO.getUserTask(project, user, taskName, PHASE);
        if (existingTask == null) {
            taskDAO.persist(newTask);
        } else {
            taskDAO.updateForUser(existingTask);
        }
    }

    private void setTaskInProgress(Project project, User user, TaskName taskName) throws Exception {
        Task inProgressTask = taskDAO.createUserDefault(project, user, taskName, PHASE, Progress.INPROGRESS);
        taskDAO.updateForUser(inProgressTask);
    }

    private void finishTask(Project project, User user, TaskName taskName) throws Exception {
        Task taskToFinish = taskDAO.createUserDefault(project, user, taskName, PHASE, Progress.FINISHED);
        taskDAO.updateForUser(taskToFinish);
    }

    private void finishTaskForAllMember(Project project, TaskName taskName) {
        List<User> members = userDAO.getUsersByProjectName(project.getName());
        members.forEach(member -> {
            try {
                finishTask(project, member, taskName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

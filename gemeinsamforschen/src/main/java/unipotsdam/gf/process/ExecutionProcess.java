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
import unipotsdam.gf.modules.reflection.model.LearningGoal;
import unipotsdam.gf.modules.reflection.model.LearningGoalRequest;
import unipotsdam.gf.modules.reflection.model.LearningGoalRequestResult;
import unipotsdam.gf.modules.reflection.model.LearningGoalStudentResult;
import unipotsdam.gf.modules.reflection.model.ReflectionQuestion;
import unipotsdam.gf.modules.reflection.service.LearningGoalsDAO;
import unipotsdam.gf.modules.reflection.service.ReflectionQuestionDAO;
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
import static unipotsdam.gf.process.tasks.TaskName.CHOOSE_ASSESSMENT_MATERIAL;
import static unipotsdam.gf.process.tasks.TaskName.CLOSE_EXECUTION_PHASE;
import static unipotsdam.gf.process.tasks.TaskName.CREATE_LEARNING_GOALS_AND_CHOOSE_REFLEXION_QUESTIONS;
import static unipotsdam.gf.process.tasks.TaskName.END_LEARNING_GOAL_PERIOD;
import static unipotsdam.gf.process.tasks.TaskName.PREVIOUS_LEARNING_GOAL_DONE;
import static unipotsdam.gf.process.tasks.TaskName.START_LEARNING_GOAL_PERIOD;
import static unipotsdam.gf.process.tasks.TaskName.UPLOAD_LEARNING_GOAL_RESULT;
import static unipotsdam.gf.process.tasks.TaskName.WAIT_FOR_ASSESSMENT_MATERIAL_COMPILATION;
import static unipotsdam.gf.process.tasks.TaskName.WAIT_FOR_EXECUTION_PHASE_END;
import static unipotsdam.gf.process.tasks.TaskName.WAIT_FOR_LEARNING_GOAL_TO_START;
import static unipotsdam.gf.process.tasks.TaskName.WAIT_FOR_OTHER_STUDENTS_FINISH_REFLECTION;
import static unipotsdam.gf.process.tasks.TaskName.WAIT_FOR_REFLECTION;
import static unipotsdam.gf.process.tasks.TaskName.WORK_ON_LEARNING_GOAL;

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
    private LearningGoalsDAO learningGoalsDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private FileManagementService fileManagementService;

    public void start(Project project) {
        taskDAO.persistTeacherTask(project, CREATE_LEARNING_GOALS_AND_CHOOSE_REFLEXION_QUESTIONS, PHASE);
        taskDAO.persistTaskForAllGroups(project, TaskName.WAIT_FOR_LEARNING_GOAL_TO_START, PHASE);
    }

    @Override
    public LearningGoalRequestResult saveLearningGoalsAndReflectionQuestions(LearningGoalRequest learningGoalRequest) throws Exception {
        LearningGoalRequestResult requestResult = reflectionService.createLearningGoalWithQuestions(learningGoalRequest);

        if (requestResult == null) {
            return null;
        }

        if (learningGoalRequest.isEndTask()) {
            Project project = projectDAO.getProjectByName(learningGoalRequest.getProjectName());
            User docent = new User(project.getAuthorEmail());
            finishTask(project, docent, CREATE_LEARNING_GOALS_AND_CHOOSE_REFLEXION_QUESTIONS);
            startNewTask(project, docent, START_LEARNING_GOAL_PERIOD);
        }
        return requestResult;
    }

    @Override
    public void startLearningGoalPeriod(Project project) throws Exception {
        Project fullProject = projectDAO.getProjectByName(project.getName());
        User docent = new User(fullProject.getAuthorEmail());
        finishTask(project, docent, START_LEARNING_GOAL_PERIOD);
        startNewTask(project, docent, END_LEARNING_GOAL_PERIOD);
        List<Group> groups = groupDAO.getGroupsByProjectName(project.getName());
        groups.forEach(group -> {
            GroupTask task = taskDAO.createGroupTask(project, group.getId(), WAIT_FOR_LEARNING_GOAL_TO_START, PHASE, Progress.FINISHED);
            taskDAO.updateGroupTask(task);
            taskDAO.persistTaskGroup(project, group.getId(), WORK_ON_LEARNING_GOAL, PHASE, TaskType.INFO);
        });
    }

    @Override
    public void finishLearningGoalPeriod(Project project) throws Exception {
        Project fullProject = projectDAO.getProjectByName(project.getName());
        User docent = new User(fullProject.getAuthorEmail());
        finishTask(fullProject, docent, END_LEARNING_GOAL_PERIOD);
        List<Group> groups = groupDAO.getGroupsByProjectName(fullProject.getName());
        groups.forEach(group -> {
            GroupTask groupTask = new GroupTask(TaskName.WORK_ON_LEARNING_GOAL, group.getId(), Progress.FINISHED, fullProject);
            taskDAO.updateGroupTask(groupTask);
        });
        taskDAO.persistMemberTask(fullProject, TaskName.UPLOAD_LEARNING_GOAL_RESULT, PHASE);
        startNewTask(fullProject, docent, WAIT_FOR_REFLECTION);
    }

    @Override
    public LearningGoalStudentResult uploadLearningGoalResult(LearningGoalStudentResult studentResult, User user) throws Exception {
        LearningGoalStudentResult newStudentResult = reflectionService.saveStudentResult(studentResult, user);
        if (newStudentResult == null) {
            return null;
        }
        Project project = projectDAO.getProjectByName(studentResult.getProjectName());
        finishTask(project, user, UPLOAD_LEARNING_GOAL_RESULT);
        startNewTask(project, user, ANSWER_REFLECTION_QUESTIONS);
        return newStudentResult;
    }

    @Override
    public void answerReflectionQuestion(FullSubmission fullSubmission, ReflectionQuestion reflectionQuestion) throws Exception {
        Project project = projectDAO.getProjectByName(fullSubmission.getProjectName());
        reflectionQuestionDAO.saveAnswerReference(fullSubmission, reflectionQuestion);
        ReflectionQuestion fullReflectionQuestion = reflectionQuestionDAO.findBy(reflectionQuestion.getId());
        User user = new User(fullReflectionQuestion.getUserEmail());
        setTaskInProgress(project, user, ANSWER_REFLECTION_QUESTIONS);

        List<ReflectionQuestion> reflectionQuestions = reflectionQuestionDAO.getUnansweredQuestions(project, user,
                fullReflectionQuestion.getLearningGoalId(), true);

        if (reflectionQuestions.isEmpty()) {
            finishTask(project, user, ANSWER_REFLECTION_QUESTIONS);
            startNewTask(project, user, WAIT_FOR_OTHER_STUDENTS_FINISH_REFLECTION, TaskType.INFO);
        }
        List<Task> reflectionTasks = taskDAO.getTaskForProjectWithoutProgress(project, ANSWER_REFLECTION_QUESTIONS, Progress.FINISHED);
        List<Task> learningGoalTasks = taskDAO.getTaskForProjectWithoutProgress(project, UPLOAD_LEARNING_GOAL_RESULT, Progress.FINISHED);
        //TODO: maybe think about synchronizing here? Could be a problem if this is called from the last two students and both don't finish the phase
        if (reflectionTasks.isEmpty() && learningGoalTasks.isEmpty()) {
            User docent = new User(project.getAuthorEmail());
            finishTask(project, docent, WAIT_FOR_REFLECTION);
            learningGoalsDAO.finishLearningGoal(new LearningGoal(fullReflectionQuestion.getLearningGoalId()));
            finishTaskForAllMember(project, WAIT_FOR_OTHER_STUDENTS_FINISH_REFLECTION);
            LearningGoal learningGoal = learningGoalsDAO.getNextUnfinishedLearningGoal(project);
            if (learningGoal == null) {
                taskDAO.persistTeacherTask(project, TaskName.WAIT_FOR_ASSESSMENT_MATERIAL_COMPILATION, PHASE);
                taskDAO.persistMemberTask(project, TaskName.CHOOSE_ASSESSMENT_MATERIAL, PHASE);
            } else {
                taskDAO.deleteIntermediateExecutionProcess(project, PHASE);
                startNewTask(project, docent, TaskName.PREVIOUS_LEARNING_GOAL_DONE, TaskType.INFO);
                finishTask(project, docent, PREVIOUS_LEARNING_GOAL_DONE);
                taskDAO.persistMemberTask(project, PREVIOUS_LEARNING_GOAL_DONE, PHASE);
                finishTaskForAllMember(project, PREVIOUS_LEARNING_GOAL_DONE);
                taskDAO.persistTaskForAllGroups(project, TaskName.WAIT_FOR_LEARNING_GOAL_TO_START, PHASE);
                startNewTask(project, docent, START_LEARNING_GOAL_PERIOD);
            }
        }
    }

    @Override
    public void chooseAssessmentMaterial(Project project, User user, String html) throws Exception {
        Project fullProject = projectDAO.getProjectByName(project.getName());
        FormDataContentDisposition.FormDataContentDispositionBuilder builder = FormDataContentDisposition.name("assessmentMaterial").fileName(FileRole.ASSESSMENT_MATERIAL + "_" + user.getEmail() + ".pdf");
        fileManagementService.saveStringAsPDF(user, project, html, builder.build(), FileRole.ASSESSMENT_MATERIAL, FileType.HTML);
        finishTask(fullProject, user, CHOOSE_ASSESSMENT_MATERIAL);
        startNewTask(fullProject, user, WAIT_FOR_EXECUTION_PHASE_END, TaskType.INFO);

        List<Task> tasks = taskDAO.getTaskForProjectWithoutProgress(fullProject, TaskName.CHOOSE_ASSESSMENT_MATERIAL, Progress.FINISHED);
        if (tasks.isEmpty()) {
            User docent = new User(fullProject.getAuthorEmail());
            finishTask(fullProject, docent, WAIT_FOR_ASSESSMENT_MATERIAL_COMPILATION);
            startNewTask(fullProject, docent, CLOSE_EXECUTION_PHASE);
        }
    }

    @Override
    public void finishPhase(Project project) throws Exception {
        Project fullProject = projectDAO.getProjectByName(project.getName());
        User docent = new User(fullProject.getAuthorEmail());
        finishTask(fullProject, docent, CLOSE_EXECUTION_PHASE);
        finishTaskForAllMember(project, WAIT_FOR_EXECUTION_PHASE_END);
    }

    @Override
    public boolean isPhaseCompleted(Project project) {
        Task task = taskDAO.getUserTask(project, new User(project.getAuthorEmail()), TaskName.CLOSE_EXECUTION_PHASE, PHASE);

        return task.getProgress() == Progress.FINISHED;
    }

    private void startNewTask(Project project, User user, TaskName taskName, TaskType... taskTypes) throws Exception {
        Task newTask = taskDAO.createUserDefault(project, user, taskName, PHASE, Progress.JUSTSTARTED);
        if (taskTypes.length > 0) {
            newTask.setTaskType(taskTypes);
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

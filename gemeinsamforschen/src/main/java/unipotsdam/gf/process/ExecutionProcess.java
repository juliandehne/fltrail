package unipotsdam.gf.process;

import unipotsdam.gf.interfaces.IReflection;
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

import static unipotsdam.gf.process.tasks.TaskName.START_LEARNING_GOAL_PERIOD;
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

    public void start(Project project) {
        taskDAO.persistTeacherTask(project, TaskName.CREATE_LEARNING_GOALS_AND_CHOOSE_REFLEXION_QUESTIONS, PHASE);
        taskDAO.persistTaskForAllGroups(project, TaskName.WAIT_FOR_LEARNING_GOALS, PHASE);
    }

    // TODO: überlegen, ob man nicht das project aus der DB holt für dozenten, da sicherer. Ja doch sollte man
    @Override
    public LearningGoalRequestResult saveLearningGoalsAndReflectionQuestions(LearningGoalRequest learningGoalRequest) throws Exception {
        LearningGoalRequestResult requestResult = reflectionService.createLearningGoalWithQuestions(learningGoalRequest);

        if (requestResult == null) {
            return null;
        }

        if (learningGoalRequest.isEndTask()) {
            Project project = projectDAO.getProjectByName(learningGoalRequest.getProjectName());
            User docent = new User(project.getAuthorEmail());
            Task task = taskDAO.createUserDefault(project, docent, TaskName.CREATE_LEARNING_GOALS_AND_CHOOSE_REFLEXION_QUESTIONS, PHASE, Progress.FINISHED);
            taskDAO.updateForUser(task);
            Task newDocentTask = taskDAO.createUserDefault(project, docent, TaskName.START_LEARNING_GOAL_PERIOD, PHASE, Progress.JUSTSTARTED);
            taskDAO.persist(newDocentTask);
        }
        return requestResult;
    }

    @Override
    public void startLearningGoalPeriod(Project project) throws Exception {
        Project fullProject = projectDAO.getProjectByName(project.getName());
        User docent = new User(fullProject.getAuthorEmail());
        Task docentFinishingTask = taskDAO.createUserDefault(project, docent, START_LEARNING_GOAL_PERIOD, PHASE, Progress.FINISHED);
        taskDAO.updateForUser(docentFinishingTask);
        Task newDocentTask = taskDAO.createUserDefault(project, docent, TaskName.END_LEARNING_GOAL_PERIOD, PHASE, Progress.JUSTSTARTED);
        taskDAO.persist(newDocentTask);
        List<Group> groups = groupDAO.getGroupsByProjectName(project.getName());
        groups.forEach(group -> taskDAO.persistTaskGroup(project, group.getId(), WORK_ON_LEARNING_GOAL, PHASE, TaskType.INFO));
    }

    @Override
    public void finishLearningGoalPeriod(Project project) throws Exception {
        Project fullProject = projectDAO.getProjectByName(project.getName());
        User docent = new User(project.getAuthorEmail());
        Task task = taskDAO.createUserDefault(fullProject, docent, TaskName.END_LEARNING_GOAL_PERIOD, PHASE, Progress.FINISHED);
        taskDAO.updateForUser(task);
        List<Group> groups = groupDAO.getGroupsByProjectName(fullProject.getName());
        groups.forEach(group -> {
            GroupTask groupTask = new GroupTask(TaskName.WORK_ON_LEARNING_GOAL, group.getId(), Progress.FINISHED, fullProject);
            taskDAO.updateGroupTask(groupTask);
        });
        taskDAO.persistMemberTask(fullProject, TaskName.UPLOAD_LEARNING_GOAL_RESULT, PHASE);
        Task learningGoalTask = taskDAO.createUserDefault(fullProject, docent, TaskName.WAIT_FOR_LEARNING_GOAL_RESULTS, PHASE, Progress.JUSTSTARTED);
        learningGoalTask.setTaskType(TaskType.INFO);
        taskDAO.persist(learningGoalTask);
        Task reflectionQuestionsTask = taskDAO.createUserDefault(fullProject, docent, TaskName.WAIT_FOR_REFLECTION_QUESTIONS_ANSWERS, PHASE, Progress.JUSTSTARTED);
        reflectionQuestionsTask.setTaskType(TaskType.INFO);
        taskDAO.persist(reflectionQuestionsTask);
    }

    @Override
    public LearningGoalStudentResult uploadLearningGoalResult(LearningGoalStudentResult studentResult, User user) throws Exception {
        LearningGoalStudentResult newStudentResult = reflectionService.saveStudentResult(studentResult, user);
        if (newStudentResult == null) {
            return null;
        }
        Project project = projectDAO.getProjectByName(studentResult.getProjectName());
        Task task = taskDAO.createUserDefault(project, user, TaskName.UPLOAD_LEARNING_GOAL_RESULT, PHASE, Progress.FINISHED);
        taskDAO.updateForUser(task);
        Task reflectionQuestionAnswerTask = taskDAO.createUserDefault(project, user, TaskName.ANSWER_REFLECTION_QUESTIONS, PHASE, Progress.JUSTSTARTED);
        taskDAO.persist(reflectionQuestionAnswerTask);
        //TODO: maybe think about synchronizing here? Could be a problem if this is called from the last two students and both don't finish the phase
        List<Task> tasks = taskDAO.getTaskForProjectWithoutProgress(project, TaskName.UPLOAD_LEARNING_GOAL_RESULT, Progress.FINISHED);
        if (tasks.isEmpty()) {
            User docent = new User(project.getAuthorEmail());
            Task reflectionQuestionsTask = taskDAO.createUserDefault(project, docent, TaskName.WAIT_FOR_LEARNING_GOAL_RESULTS, PHASE, Progress.FINISHED);
            reflectionQuestionsTask.setTaskType(TaskType.INFO);
            taskDAO.updateForUser(reflectionQuestionsTask);
        }
        return newStudentResult;
    }

    @Override
    public void answerReflectionQuestion(FullSubmission fullSubmission, ReflectionQuestion reflectionQuestion) throws Exception {
        Project project = projectDAO.getProjectByName(fullSubmission.getProjectName());
        reflectionQuestionDAO.saveAnswerReference(fullSubmission, reflectionQuestion);
        ReflectionQuestion fullReflectionQuestion = reflectionQuestionDAO.findBy(reflectionQuestion.getId());
        User user = new User(reflectionQuestion.getUserEmail());

        List<ReflectionQuestion> reflectionQuestions = reflectionQuestionDAO.getUnansweredQuestions(project, user, fullReflectionQuestion.getLearningGoalId(), true);
        Task reflectionQuestionTask = taskDAO.createUserDefault(project, user, TaskName.ANSWER_REFLECTION_QUESTIONS, PHASE, Progress.INPROGRESS);
        taskDAO.persist(reflectionQuestionTask);
        if (reflectionQuestions.isEmpty()) {
            Task task = taskDAO.createUserDefault(project, user, TaskName.ANSWER_REFLECTION_QUESTIONS, PHASE, Progress.FINISHED);
            taskDAO.persist(task);
        }
        List<Task> tasks = taskDAO.getTaskForProjectWithoutProgress(project, TaskName.ANSWER_REFLECTION_QUESTIONS, Progress.FINISHED);
        if (tasks.isEmpty()) {
            User docent = new User(project.getAuthorEmail());
            Task docentTask = taskDAO.createUserDefault(project, docent, TaskName.WAIT_FOR_REFLECTION_QUESTIONS_ANSWERS, PHASE, Progress.FINISHED);
            taskDAO.updateForUser(docentTask);
            learningGoalsDAO.finishLearningGoal(new LearningGoal(fullReflectionQuestion.getLearningGoalId()));
            LearningGoal learningGoal = learningGoalsDAO.getNextUnfinishedLearningGoal(project);
            if (learningGoal == null) {
                taskDAO.persistTeacherTask(project, TaskName.WAIT_FOR_ASSESSMENT_MATERIAL_COMPILATION, PHASE);
                taskDAO.persistMemberTask(project, TaskName.CHOOSE_ASSESSMENT_MATERIAL, PHASE);
            } else {
                Task docentNewTask = taskDAO.createUserDefault(project, docent, TaskName.START_LEARNING_GOAL_PERIOD, PHASE, Progress.JUSTSTARTED);
                taskDAO.updateForUser(docentNewTask);
            }
        }
    }

    @Override
    public void chooseAssessmentMaterial(Project project, User user) throws Exception {
        Project fullProject = projectDAO.getProjectByName(project.getName());
        Task task = taskDAO.createUserDefault(project, user, TaskName.CHOOSE_ASSESSMENT_MATERIAL, PHASE, Progress.FINISHED);
        taskDAO.updateForUser(task);
        Task newStudentTask = taskDAO.createUserDefault(project, user, TaskName.WAIT_FOR_EXECUTION_PHASE_END, PHASE, Progress.JUSTSTARTED);
        newStudentTask.setTaskType(TaskType.INFO);
        taskDAO.persist(newStudentTask);

        List<Task> tasks = taskDAO.getTaskForProjectWithoutProgress(project, TaskName.CHOOSE_ASSESSMENT_MATERIAL, Progress.FINISHED);
        if (tasks.isEmpty()) {
            User docent = new User(fullProject.getAuthorEmail());
            Task docentTask = taskDAO.createUserDefault(fullProject, docent, TaskName.WAIT_FOR_ASSESSMENT_MATERIAL_COMPILATION, PHASE, Progress.FINISHED);
            taskDAO.updateForUser(docentTask);
            Task newDocentTask = taskDAO.createUserDefault(fullProject, docent, TaskName.CLOSE_EXECUTION_PHASE, PHASE, Progress.JUSTSTARTED);
            taskDAO.persist(newDocentTask);
        }
    }


    @Override
    public void finishPhase(Project project) throws Exception {
        Task task = taskDAO.createUserDefault(project, new User(project.getAuthorEmail()), TaskName.CLOSE_EXECUTION_PHASE, PHASE, Progress.FINISHED);
        taskDAO.updateForUser(task);
    }

    @Override
    public boolean isPhaseCompleted(Project project) {
        Task task = taskDAO.getTaskByProjectNameAndUserEmailAndTaskNameAndPhase(project, new User(project.getAuthorEmail()), TaskName.CLOSE_EXECUTION_PHASE, PHASE);

        return task.getProgress() == Progress.FINISHED;
    }
}

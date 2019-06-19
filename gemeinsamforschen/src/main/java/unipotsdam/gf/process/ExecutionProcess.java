package unipotsdam.gf.process;

import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.GroupTask;
import unipotsdam.gf.process.tasks.Progress;
import unipotsdam.gf.process.tasks.Task;
import unipotsdam.gf.process.tasks.TaskDAO;
import unipotsdam.gf.process.tasks.TaskName;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import java.util.List;

@ManagedBean
public class ExecutionProcess implements IExecutionProcess {

    private static Phase PHASE = Phase.Execution;

    @Inject
    private TaskDAO taskDAO;

    public void start(Project project) {
        taskDAO.persistTeacherTask(project, TaskName.CHOOSE_FITTING_COMPETENCES, PHASE);
        taskDAO.persistTaskForAllGroups(project, TaskName.CREATE_LEARNING_GOAL_DIARY, PHASE);
    }

    public void finishCreateLearningGoalDiaryAndStartNext(Project project, Group group) {
        finishTaskAndStartNext(project, group, TaskName.CREATE_LEARNING_GOAL_DIARY, TaskName.CHOOSE_FITTING_COMPETENCES);
    }

    public void finishChooseFittingCompetences(Project project, Group group) {
        finishTaskAndStartNext(project, group, TaskName.CHOOSE_FITTING_COMPETENCES, TaskName.CHOOSE_REFLEXION_QUESTIONS);
    }

    public void finishChooseFittingCompetenceDocent(Project project, User user) {
        finishTaskAndStartNext(project, user, TaskName.CHOOSE_FITTING_COMPETENCES, TaskName.CHOOSE_REFLEXION_QUESTIONS);
    }

    public void finishChooseReflexionQuestions(Project project, Group group) {
        TaskName finishedTaskName = TaskName.CHOOSE_REFLEXION_QUESTIONS;
        Task docentTask = taskDAO.getTaskByProjectNameAndUserEmailAndTaskNameAndPhase(project, new User(project.getAuthorEmail()),
                TaskName.CHOOSE_REFLEXION_QUESTIONS, PHASE);

        if (docentTask == null || docentTask.getProgress() != Progress.FINISHED) {
            GroupTask task = taskDAO.createGroupTask(project, group.getId(), finishedTaskName, PHASE, Progress.FINISHED);
            taskDAO.updateGroupTask(task);
            return;
        }

        finishTaskAndStartNext(project, group, finishedTaskName, TaskName.ANSWER_REFLEXION_QUESTIONS);
    }

    public void finishChooseReflexionQuestionsDocent(Project project, User user) {
        finishTaskAndStartNext(project, user, TaskName.CHOOSE_REFLEXION_QUESTIONS, TaskName.WAIT_FOR_ASSESSMENT_MATERIAL_COMPILATION);
        List<Task> groupsTask = taskDAO.getTaskOfGroupsByProjectNameAndTaskNameAndPhase(project, TaskName.CHOOSE_REFLEXION_QUESTIONS, PHASE);
        if (groupsTask == null) {
            return;
        }
        boolean groupWithNotFinishedTask = groupsTask.stream().anyMatch(task -> task.getProgress() != Progress.FINISHED);

        if (groupWithNotFinishedTask) {
            return;
        }
        groupsTask.forEach(task -> {
            int groupId = task.getGroupTask();
            GroupTask answerTask = taskDAO.createGroupTask(project, groupId, TaskName.ANSWER_REFLEXION_QUESTIONS, PHASE, Progress.JUSTSTARTED);
            taskDAO.updateGroupTask(answerTask);
        });


    }

    public void finishAnswerReflexionQuestion(Project project, Group group) {
        finishTaskAndStartNext(project, group, TaskName.ANSWER_REFLEXION_QUESTIONS, TaskName.COLLECT_RESULTS_FOR_ASSESSMENT);
    }

    public void finishCollectResultsForAssessment(Project project, Group group) {
        finishTaskAndStartNext(project, group, TaskName.COLLECT_RESULTS_FOR_ASSESSMENT, TaskName.WAIT_FOR_EXECUTION_PHASE_END);
        List<Task> groupsTask = taskDAO.getTaskOfGroupsByProjectNameAndTaskNameAndPhase(project, TaskName.COLLECT_RESULTS_FOR_ASSESSMENT, PHASE);
        boolean oneGroupNotFinished = groupsTask.stream().anyMatch(task -> task.getProgress() != Progress.FINISHED);

        if (oneGroupNotFinished) {
            return;
        }
        Task task = taskDAO.createUserDefault(project, new User(project.getAuthorEmail()), TaskName.END_EXECUTION_PHASE, PHASE, Progress.JUSTSTARTED);
        taskDAO.persist(task);
    }

    public void finishPhase(Project project) {
        Task task = taskDAO.createUserDefault(project, new User(project.getAuthorEmail()), TaskName.END_EXECUTION_PHASE, PHASE, Progress.FINISHED);
        taskDAO.updateForUser(task);
    }

    @Override
    public boolean isPhaseCompleted(Project project) {
        Task task = taskDAO.getTaskByProjectNameAndUserEmailAndTaskNameAndPhase(project, new User(project.getAuthorEmail()), TaskName.END_EXECUTION_PHASE, PHASE);

        return task.getProgress() == Progress.FINISHED;
    }

    private void finishTaskAndStartNext(Project project, Object groupOrUser, TaskName finishedTaskName, TaskName newTaskName) {
        Task newTask = null;
        if (groupOrUser instanceof Group) {
            Group group = (Group) groupOrUser;
            Task finishedTask = taskDAO.createGroupTask(project, group.getId(), finishedTaskName, PHASE, Progress.FINISHED);
            taskDAO.updateGroupTask(finishedTask, group.getId());
            newTask = taskDAO.createGroupTask(project, group.getId(), newTaskName, PHASE, Progress.JUSTSTARTED);

        }

        if (groupOrUser instanceof User) {
            User user = (User) groupOrUser;
            Task finishedTask = taskDAO.createUserDefault(project, user, TaskName.CHOOSE_FITTING_COMPETENCES, PHASE, Progress.FINISHED);
            taskDAO.updateForUser(finishedTask);
            newTask = taskDAO.createUserDefault(project, user, TaskName.CHOOSE_REFLEXION_QUESTIONS, PHASE, Progress.JUSTSTARTED);
        }

        taskDAO.persist(newTask);
    }
}

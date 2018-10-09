package unipotsdam.gf.modules.tasks;

import com.mysql.jdbc.NotImplemented;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.states.ProjectPhase;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import java.util.ArrayList;

import static unipotsdam.gf.modules.tasks.TaskName.*;

@ManagedBean
public class TaskDAO {


    @Inject
    TaskDAO taskDAO;

    @Inject
    ProjectDAO projectDAO;

    @Inject
    MysqlConnect connect;

    /**
     * deprecated methods are used in order to not break existing code
     * remove this method if not needed anymore
     * @param userEmail
     * @param projectName
     * @return
     */
    @Deprecated
    public Task[] getTasks(String userEmail, String projectName) {
        connect.connect();
        String query = "Select * from tasks where userEmail = ? & projectName = ?";
        ArrayList<Task> result = new ArrayList<>();
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query, userEmail, projectName);
        while (vereinfachtesResultSet.next()) {
            String taskName = vereinfachtesResultSet.getString("taskName");

            TaskName taskName1 = TaskName.valueOf(taskName);
            switch (taskName1) {
                case WAIT_FOR_PARTICPANTS: {
                    result.add(getTaskWaitForParticipants(vereinfachtesResultSet));
                }
                default: {
                    result.add(getGeneralTask(vereinfachtesResultSet));
                }
            }
        }

        connect.close();

        return result.toArray(new Task[0]);
    }
    public Task[] getTasks(User user, Project project) {
        return getTasks(user.getEmail(), project.getName());
    }

    // fill the task with the general data
    private Task getGeneralTask(VereinfachtesResultSet vereinfachtesResultSet) {
        Task task = new Task();

        task.setImportance(Importance.valueOf(vereinfachtesResultSet.getString("importance")));
        task.setUserEmail(vereinfachtesResultSet.getString("userEmail"));
        task.setLink(vereinfachtesResultSet.getString("taskUrl"));
        task.setProjectName(vereinfachtesResultSet.getString("projectName"));
        task.setGroupTask(vereinfachtesResultSet.getBoolean("groupTask"));
        task.setProgress(vereinfachtesResultSet.getString("progress"));
        task.setEventCreated(vereinfachtesResultSet.getLong("created"));
        task.setDeadline(vereinfachtesResultSet.getLong("due"));
        task.setPhase(ProjectPhase.valueOf(vereinfachtesResultSet.getString("phase")));
        getTasks(vereinfachtesResultSet);

        return task;
    }

    // bundle the taskModes
    private void getTasks(VereinfachtesResultSet vereinfachtesResultSet) {
        Task task = new Task();
        ArrayList<TaskType> taskTypes = new ArrayList<>();
        String taskMode = vereinfachtesResultSet.getString("taskMode");
        String taskMode2 = vereinfachtesResultSet.getString("taskMode2");
        String taskMode3 = vereinfachtesResultSet.getString("taskMode3");
        if (!taskMode.equals("")) {
            taskTypes.add(TaskType.valueOf(vereinfachtesResultSet.getString("taskMode")));
        }
        if (!taskMode.equals("")) {
            taskTypes.add(TaskType.valueOf(vereinfachtesResultSet.getString("taskMode2")));
        }

        if (!taskMode.equals("")) {
            taskTypes.add(TaskType.valueOf(vereinfachtesResultSet.getString("taskMode3")));

        }
        task.setTaskType(taskTypes.toArray(new TaskType[0]));
    }


    private Task getTaskWaitForParticipants(VereinfachtesResultSet vereinfachtesResultSet) {
        Task task = getGeneralTask(vereinfachtesResultSet);
        Project project = new Project();
        project.setName(vereinfachtesResultSet.getString("projectName"));
        ParticipantsCount participantsCount = projectDAO.getParticipantCount(project);
        task.setTaskData(participantsCount);
        return new Task();
    }


    public void createTaskWaitForParticipants(Project project, User target) {
        Task task = new Task();
        task.setEventCreated(System.currentTimeMillis());
        task.setGroupTask(false);
        task.setLink("../groupfinding/create-groups-manual.jsp");
        task.setPhase(ProjectPhase.GroupFormation);
        task.setRenderModel(WAIT_FOR_PARTICPANTS);
        task.setTaskType(TaskType.LINKED, TaskType.INFO);
        task.setProjectName(project.getName());
        task.setUserEmail(project.getAuthorEmail());
        task.setImportance(Importance.MEDIUM);
        taskDAO.persist(task);

    }

    public void persist(Task task) {

        String taskMode2 = "";
        if (task.getTaskType() != null && task.getTaskType().length > 1) {
            taskMode2 = task.getTaskType()[1].toString();
        }

        String taskMode3 = "";
        if (task.getTaskType() != null && task.getTaskType().length > 2) {
            taskMode3 = task.getTaskType()[2].toString();
        }

        connect.connect();
        String query =
                "INSERT INTO fltrail.tasks (userEmail, projectName, taskUrl, taskName, " + "linkedMode, groupTask, importance, progress, phase, created, due, taskMode, taskMode2, taskMode3) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        connect.issueInsertOrDeleteStatement(query, task.getUserEmail(), task.getProjectName(), task.getLink(),
                task.getTaskName(), task.getGroupTask(), task.getImportance(), task.getProgress(), task.getPhase(),
                null, task.getDeadline(), task.getTaskType()[0].toString(), taskMode2, taskMode3);
        connect.close();
    }

    public void update(Task task, Progress progress) throws NotImplemented {
        connect.connect();
        String query =
                "UPDATE tasks set progress = ? where task.userEmail = ? & task.projectName = ? & task.taskName" + " = ?";
        connect.issueUpdateStatement(
                query, progress.name(), task.getUserEmail(), task.getProjectName(), task.getTaskName());
        connect.close();
    }
}

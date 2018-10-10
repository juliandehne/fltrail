package unipotsdam.gf.modules.tasks;

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
    ProjectDAO projectDAO;

    @Inject
    MysqlConnect connect;

    // get all the tasks a user has in a specific project
    public ArrayList<Task> getTaskType(String userEmail, String projectName)  {
        connect.connect();
        String query = "Select * from tasks where userEmail = ? AND projectName = ?";
        ArrayList<Task> result = new ArrayList<>();
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query, userEmail, projectName);
        while (vereinfachtesResultSet.next()) {
            String taskName = vereinfachtesResultSet.getString("taskName");

            TaskName taskName1 = TaskName.valueOf(taskName);
            Task task = getGeneralTask(vereinfachtesResultSet);
            task.setTaskName(taskName1);
            switch (taskName1) {
                case WAIT_FOR_PARTICPANTS: {
                    result.add(getTaskWaitForParticipants(task, vereinfachtesResultSet));
                    break;
                }
                default: {
                    result.add(task);
                }
            }
        }

        connect.close();

        return result;
    }

    // fill the task with the general data
    private Task getGeneralTask(VereinfachtesResultSet vereinfachtesResultSet) {
        Task task = new Task();

        task.setImportance(Importance.valueOf(vereinfachtesResultSet.getString("importance")));
        task.setUserEmail(vereinfachtesResultSet.getString("userEmail"));
        task.setLink(vereinfachtesResultSet.getString("taskUrl"));
        task.setProjectName(vereinfachtesResultSet.getString("projectName"));
        task.setGroupTask(vereinfachtesResultSet.getBoolean("groupTask"));
        task.setProgress(Progress.valueOf(vereinfachtesResultSet.getString("progress")));
        task.setEventCreated(vereinfachtesResultSet.getLong("created"));
        try {
            task.setDeadline(vereinfachtesResultSet.getTimestamp("due").getTime());
        }catch(Exception e){}
        task.setPhase(ProjectPhase.valueOf(vereinfachtesResultSet.getString("phase")));
        getTaskType(task, vereinfachtesResultSet);

        return task;
    }

    // bundle the taskModes
    private void getTaskType(Task task, VereinfachtesResultSet vereinfachtesResultSet) {
        ArrayList<TaskType> taskTypes = new ArrayList<>();
        String taskMode = vereinfachtesResultSet.getString("taskMode");
        String taskMode2 = vereinfachtesResultSet.getString("taskMode2");
        String taskMode3 = vereinfachtesResultSet.getString("taskMode3");
        if (taskMode != null && !taskMode.equals("") ) {
            taskTypes.add(TaskType.valueOf(vereinfachtesResultSet.getString("taskMode")));
        }
        if (taskMode2 != null && !taskMode2.equals("")) {
            taskTypes.add(TaskType.valueOf(vereinfachtesResultSet.getString("taskMode2")));
        }

        if (taskMode3 != null && !taskMode3.equals("")) {
            taskTypes.add(TaskType.valueOf(vereinfachtesResultSet.getString("taskMode3")));

        }
        task.setTaskType(taskTypes.toArray(new TaskType[0]));
    }


    private Task getTaskWaitForParticipants(Task task, VereinfachtesResultSet vereinfachtesResultSet) {
        task.setTaskName(TaskName.WAIT_FOR_PARTICPANTS);
        Project project = new Project();
        project.setName(vereinfachtesResultSet.getString("projectName"));
        ParticipantsCount participantsCount = projectDAO.getParticipantCount(project);
        task.setTaskData(participantsCount);
        return task;
    }

    private Task createGeneralTask(Project project, User target) {
        Task task = new Task();
        task.setEventCreated(System.currentTimeMillis());
        task.setProjectName(project.getName());
        task.setUserEmail(project.getAuthorEmail());
        task.setImportance(Importance.MEDIUM);
        task.setProgress(Progress.JUSTSTARTED);

        return task;
    }


    public void createTaskWaitForParticipants(Project project, User target)  {
        Task task = createGeneralTask(project, target);
        task.setGroupTask(false);
        task.setLink("../groupfinding/create-groups-manual.jsp");
        task.setPhase(ProjectPhase.GroupFormation);
        task.setRenderModel(WAIT_FOR_PARTICPANTS);
        task.setTaskType(TaskType.LINKED, TaskType.INFO);
        task.setImportance(Importance.MEDIUM);
        task.setProgress(Progress.JUSTSTARTED);
        persist(task);
    }

    public void createWaitingForGroupFormationTask(Project project, User target) {
        Task task = createGeneralTask(project, target);
        task.setGroupTask(false);
        task.setPhase(ProjectPhase.GroupFormation);
        task.setRenderModel(TaskName.WAITING_FOR_GROUP);
        task.setTaskType(TaskType.INFO);
        task.setImportance(Importance.MEDIUM);
        task.setProgress(Progress.JUSTSTARTED);
        persist(task);
    }

    private void persist(Task task) {

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
                "INSERT INTO fltrail.tasks (userEmail, projectName, taskUrl, taskName, " +
                        "groupTask, importance, progress, phase, created, due, taskMode, taskMode2, taskMode3) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        connect.issueInsertOrDeleteStatement(query, task.getUserEmail(), task.getProjectName(), task.getLink(),
                task.getTaskName(), task.getGroupTask(), task.getImportance(), task.getProgress(), task
                        .getPhase(),
                null, task.getDeadline(), task.getTaskType()[0].toString(), taskMode2, taskMode3);
        connect.close();
    }

    public void update(Task task, Progress progress)  {
        connect.connect();
        String query =
                "UPDATE tasks set progress = ? where task.userEmail = ? & task.projectName = ? & task.taskName" + " = ?";
        connect.issueUpdateStatement(
                query, progress.name(), task.getUserEmail(), task.getProjectName(), task.getTaskName());
        connect.close();
    }

    public Task[] getTaskType(User teacher, Project project) {
        return getTaskType(teacher.getEmail(), project.getName()).toArray(new Task[0]);
    }
}

package unipotsdam.gf.process.tasks;

import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import java.util.ArrayList;

import static unipotsdam.gf.process.tasks.TaskName.*;

@ManagedBean
public class TaskDAO {


    @Inject
    ProjectDAO projectDAO;

    @Inject
    UserDAO userDAO;

    @Inject
    MysqlConnect connect;

    @Inject
    Management management;

    // get all the tasks a user has in a specific project
    public ArrayList<Task> getTasks(String userEmail, String projectName)  {
        connect.connect();
        String query = "Select * from tasks where userEmail = ? AND projectName = ?";
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

        return result;
    }

    // fill the task with the general data
    private Task getGeneralTask(VereinfachtesResultSet vereinfachtesResultSet) {
        Task task = new Task();

        task.setImportance(Importance.valueOf(vereinfachtesResultSet.getString("importance")));
        task.setUserEmail(vereinfachtesResultSet.getString("userEmail"));
        task.setProjectName(vereinfachtesResultSet.getString("projectName"));
        task.setGroupTask(vereinfachtesResultSet.getBoolean("groupTask"));
        task.setProgress(Progress.valueOf(vereinfachtesResultSet.getString("progress")));
        task.setEventCreated(vereinfachtesResultSet.getLong("created"));
        task.setDeadline(vereinfachtesResultSet.getLong("due"));
        task.setPhase(Phase.valueOf(vereinfachtesResultSet.getString("phase")));
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


    private Task getTaskWaitForParticipants(VereinfachtesResultSet vereinfachtesResultSet) {
        Task task = getGeneralTask(vereinfachtesResultSet);
        Project project = new Project();
        project.setName(vereinfachtesResultSet.getString("projectName"));
        ParticipantsCount participantsCount = projectDAO.getParticipantCount(project);
        task.setTaskData(participantsCount);
        return task;
    }

    private Task createDefault(Project project, User target, TaskName taskName, Phase phase) {
        Task task = new Task();
        task.setTaskName(taskName);
        task.setEventCreated(System.currentTimeMillis());
        task.setProjectName(project.getName());
        task.setUserEmail(project.getAuthorEmail());
        task.setImportance(Importance.MEDIUM);
        task.setProgress(Progress.JUSTSTARTED);
        task.setGroupTask(false);
        task.setTaskName(taskName);
        task.setPhase(phase);
        task.setTaskType(TaskType.INFO);

        return task;
    }

    public void persist(Project project, User target, TaskName taskName, Phase phase) {
        Task aDefault = createDefault(project, target, taskName, phase);
        persist(aDefault);
    }

    public void persistTeacherTask(Project project, TaskName taskName, Phase phase) {
        User user = new User(projectDAO.getProjectByName(project.getName()).getAuthorEmail());
        Task aDefault = createDefault(project, user, taskName, phase);
        persist(aDefault);
    }


    public void createTaskWaitForParticipants(Project project, User target)  {
        Task task = createDefault(project, target, TaskName.WAIT_FOR_PARTICPANTS, Phase.GroupFormation);
        task.setRenderModel(WAIT_FOR_PARTICPANTS);
        task.setTaskType(TaskType.LINKED, TaskType.INFO);
        persist(task);
    }

    public void createWaitingForGroupFormationTask(Project project, User target) {
        Task task = createDefault(project, target, WAITING_FOR_GROUP, Phase.GroupFormation);
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
                "INSERT INTO fltrail.tasks (userEmail, projectName, taskName, " +
                        "groupTask, importance, progress, phase, created, due, taskMode, taskMode2, taskMode3) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,  ?, ?, ?)";

        if (task.getTaskType() == null || task.getTaskType().length < 0) {
            try {
                throw new Exception("set a task type");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        connect.issueInsertOrDeleteStatement(query, task.getUserEmail(), task.getProjectName(),
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

    public Task[] getTasks(User teacher, Project project) {
        return getTasks(teacher.getEmail(), project.getName()).toArray(new Task[0]);
    }

    public void persistMemberTask(Project project, TaskName taskName, Phase phase) {
        java.util.List<User> members = userDAO.getUsersByProjectName(project.getName());
        for (User member : members) {
            persist(project, member, taskName, phase );
        }
    }
}

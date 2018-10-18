package unipotsdam.gf.process.tasks;

import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.submission.controller.SubmissionController;
import unipotsdam.gf.modules.submission.view.SubmissionRenderData;
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
    private ProjectDAO projectDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private MysqlConnect connect;

    @Inject
    private IGroupFinding groupFinding;

    @Inject
    private SubmissionController submissionController;

    // fill the task with the general data
    private Task getGeneralTask(VereinfachtesResultSet vereinfachtesResultSet) {
        Task task = new Task();

        task.setImportance(Importance.valueOf(vereinfachtesResultSet.getString("importance")));
        task.setUserEmail(vereinfachtesResultSet.getString("userEmail"));
        task.setProjectName(vereinfachtesResultSet.getString("projectName"));
        task.setGroupTask(vereinfachtesResultSet.getBoolean("groupTask"));
        task.setProgress(Progress.valueOf(vereinfachtesResultSet.getString("progress")));
        try{
            task.setEventCreated(vereinfachtesResultSet.getTimestamp("created").getTime());
        }catch(Exception e){ }
        try{
            task.setDeadline(vereinfachtesResultSet.getTimestamp("due").getTime());
        }catch(Exception e){ }
        task.setPhase(Phase.valueOf(vereinfachtesResultSet.getString("phase")));
        task.setTaskName(TaskName.valueOf(vereinfachtesResultSet.getString("taskName")));
        task.setHasRenderModel(false);
        TaskType[] taskTypes = getTaskModes(vereinfachtesResultSet).toArray(new TaskType[0]);
        task.setTaskType(taskTypes);
        return task;
    }

    // bundle the taskModes
    private ArrayList<TaskType> getTaskModes(VereinfachtesResultSet vereinfachtesResultSet) {
        ArrayList<TaskType> taskTypes = new ArrayList<>();
        String taskMode = vereinfachtesResultSet.getString("taskMode");
        String taskMode2 = vereinfachtesResultSet.getString("taskMode2");
        String taskMode3 = vereinfachtesResultSet.getString("taskMode3");
        if (taskMode != null && !taskMode.equals("")) {
            taskTypes.add(TaskType.valueOf(vereinfachtesResultSet.getString("taskMode")));
        }
        if (taskMode2 != null && !taskMode2.equals("")) {
            taskTypes.add(TaskType.valueOf(vereinfachtesResultSet.getString("taskMode2")));
        }

        if (taskMode3 != null && !taskMode3.equals("")) {
            taskTypes.add(TaskType.valueOf(vereinfachtesResultSet.getString("taskMode3")));

        }

        return taskTypes;
    }

    private Task getTaskWaitForParticipants(VereinfachtesResultSet vereinfachtesResultSet) {
        Task task = getGeneralTask(vereinfachtesResultSet);
        Project project = new Project();
        project.setName(vereinfachtesResultSet.getString("projectName"));
        ParticipantsCount participantsCount = projectDAO.getParticipantCount(project);
        participantsCount.setParticipantsNeeded(groupFinding.getMinNumberOfStudentsNeeded(project));
        task.setTaskData(participantsCount);
        task.setHasRenderModel(true);
        return task;
    }

    private Task createDefault(Project project, User target, TaskName taskName, Phase phase) {
        Task task = new Task();
        task.setTaskName(taskName);
        task.setEventCreated(System.currentTimeMillis());
        task.setProjectName(project.getName());
        task.setUserEmail(target.getEmail());
        task.setImportance(Importance.MEDIUM);
        task.setProgress(Progress.JUSTSTARTED);
        task.setGroupTask(false);
        task.setTaskName(taskName);
        task.setPhase(phase);
        task.setTaskType(TaskType.INFO, TaskType.LINKED);

        return task;
    }

    private void persist(Task task)  {

        if (task.getTaskName() == null) {
            throw new Error("no taskName given");
        }


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
                "INSERT IGNORE INTO fltrail.tasks (userEmail, projectName, taskName, " +
                        "groupTask, importance, progress, phase, created, due, " +
                        "taskMode, taskMode2, taskMode3)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,  ?, ?, ?)";

        if (task.getTaskType() == null || task.getTaskType().length == 0) {
            try {
                throw new Exception("set a task type");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        connect.issueInsertOrDeleteStatement(query, task.getUserEmail(), task.getProjectName(), task.getTaskName(),
                task.getGroupTask(), task.getImportance(), task.getProgress(), task.getPhase(), null,
                task.getDeadline(), task.getTaskType()[0].toString(), taskMode2, taskMode3);
        connect.close();
    }

    // get all the tasks a user has in a specific project
    public ArrayList<Task> getTasks(User user, Project project) {
        connect.connect();
        String query = "Select * from tasks where userEmail = ? AND projectName = ?";
        ArrayList<Task> result = new ArrayList<>();
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query, user.getEmail(),project.getName());
        while (vereinfachtesResultSet.next()) {
            String taskName = vereinfachtesResultSet.getString("taskName");

            TaskName taskName1 = TaskName.valueOf(taskName);
            switch (taskName1) {
                case WAIT_FOR_PARTICPANTS: {
                    result.add(getTaskWaitForParticipants(vereinfachtesResultSet));
                    break;
                }
                case ANNOTATE_DOSSIER: {
                    Task finalizeDossierTask = getFinalizeDossierTask(vereinfachtesResultSet);
                    //finalizeDossierTask.setTaskType(TaskType.LINKED);
                    result.add(finalizeDossierTask);
                    break;
                }
                case UPLOAD_DOSSIER: {
                    Task generalTask = getGeneralTask(vereinfachtesResultSet);
                    //generalTask.setTaskType(TaskType.LINKED);
                    result.add(generalTask);
                    break;
                }

                case GIVE_FEEDBACK: {
                    Task feedbackTask = getGeneralTask(vereinfachtesResultSet);
                    feedbackTask.setTaskData(submissionController.getFeedbackTaskData(user, project));
                    feedbackTask.setHasRenderModel(true);
                    result.add(feedbackTask);
                    break;
                }
                case WAITING_FOR_STUDENT_DOSSIERS: {
                    Task task = getGeneralTask(vereinfachtesResultSet);
                    task.setHasRenderModel(true);
                    task.setTaskData(submissionController.getProgressData(project));
                    break;
                }
                default: {
                    result.add(getGeneralTask(vereinfachtesResultSet));
                }
            }
        }

        connect.close();

        return result;
    }

    private Task getFinalizeDossierTask(VereinfachtesResultSet vereinfachtesResultSet) {
        Task task = getGeneralTask(vereinfachtesResultSet);
        task.setTaskData(submissionController.getSubmissionData(new User(task.getUserEmail()), new Project(task.getProjectName())));
        return task;
    }

    public void persist(Project project, User target, TaskName taskName, Phase phase) {
        Task aDefault = createDefault(project, target, taskName, phase);
        persist(aDefault);
    }

    public void persistTeacherTask(Project project, TaskName taskName, Phase phase) {
        User user = new User(projectDAO.getProjectByName(project.getName()).getAuthorEmail());
        Task aDefault = createDefault(project, user, taskName, phase);
        ////////don't know yet if it should happen here//////////
        aDefault.setTaskType(TaskType.LINKED);
        ////////could also be a default                //////////
        persist(aDefault);
    }

    public void createTaskWaitForParticipants(Project project, User author) {
        Task task = createDefault(project, author, TaskName.WAIT_FOR_PARTICPANTS, Phase.GroupFormation);
        task.setTaskName(WAIT_FOR_PARTICPANTS);
        task.setTaskType(TaskType.LINKED, TaskType.INFO);
        persist(task);
    }

    public Task createWaitingForGroupFormationTask(Project project, User target) {
        Task task = createDefault(project, target, WAITING_FOR_GROUP, Phase.GroupFormation);
        task.setTaskType(TaskType.INFO);
        task.setImportance(Importance.MEDIUM);
        task.setProgress(Progress.JUSTSTARTED);

        persist(task);
        return task;
    }

    public void updateForUser(Task task) {
        connect.connect();
        String query =
                "UPDATE tasks set progress = ? where userEmail = ? AND projectName = ? AND taskName = ?";
        connect.issueUpdateStatement(
                query, task.getProgress().name(), task.getUserEmail(), task.getProjectName(), task.getTaskName());
        connect.close();
    }

    public void updateForAll(Task task) {
        connect.connect();
        String query =
                "UPDATE tasks set progress = ? where projectName = ? AND taskName = ?";
        connect.issueUpdateStatement(
                query, task.getProgress().name(), task.getProjectName(), task.getTaskName());
        connect.close();
    }

    public void persistMemberTask(Project project, TaskName taskName, Phase phase) {
        java.util.List<User> members = userDAO.getUsersByProjectName(project.getName());
        for (User member : members) {
            persist(project, member, taskName, phase);
        }
    }

    public void persist(Project project, User user, TaskName finalizeDossier, Phase dossierFeedback, TaskType linked) {
        Task task = createDefault(project, user, finalizeDossier, dossierFeedback);
        task.setTaskType(linked);
        persist(task);
    }

    public void persistFeedbackTask(Project project, FeedbackTaskData feedbackTaskData) {
        // create task
        persist(project, feedbackTaskData.getTarget(), TaskName.GIVE_FEEDBACK, Phase.DossierFeedback, TaskType.LINKED);
    }

}

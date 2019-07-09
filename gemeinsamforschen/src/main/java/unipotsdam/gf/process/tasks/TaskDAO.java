package unipotsdam.gf.process.tasks;

import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.modules.assessment.AssessmentDAO;
import unipotsdam.gf.modules.fileManagement.FileRole;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.quiz.StudentIdentifier;
import unipotsdam.gf.modules.submission.controller.SubmissionController;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;
import unipotsdam.gf.process.constraints.ConstraintsImpl;
import unipotsdam.gf.process.phases.Phase;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static unipotsdam.gf.process.tasks.TaskName.WAITING_FOR_GROUP;
import static unipotsdam.gf.process.tasks.TaskName.WAIT_FOR_PARTICPANTS;

@ManagedBean
public class TaskDAO {


    @Inject
    private ProjectDAO projectDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private GroupDAO groupDAO;

    @Inject
    private MysqlConnect connect;

    @Inject
    private IGroupFinding groupFinding;

    @Inject
    private SubmissionController submissionController;

    @Inject
    private AssessmentDAO assessmentDAO;

    @Inject
    private ConstraintsImpl constraints;

    // fill the task with the general data
    private Task getGeneralTask(VereinfachtesResultSet vereinfachtesResultSet) {


        User user = new User(vereinfachtesResultSet.getString("userEmail"));
        Project project = new Project(vereinfachtesResultSet.getString("projectName"));
        Progress progress = Progress.valueOf(vereinfachtesResultSet.getString("progress"));
        TaskName taskName = TaskName.valueOf(vereinfachtesResultSet.getString("taskName"));
        Task task = new Task(taskName, user, project, progress);

        task.setGroupTask(vereinfachtesResultSet.getInt("groupTask"));
        task.setImportance(Importance.valueOf(vereinfachtesResultSet.getString("importance")));
        try {
            task.setEventCreated(vereinfachtesResultSet.getLong("created"));
        } catch (Exception ignored) {
        }
        try {
            task.setDeadline(vereinfachtesResultSet.getTimestampIgnoreError("due").getTime());
        } catch (Throwable ignored) {
        }
        task.setPhase(Phase.valueOf(vereinfachtesResultSet.getString("phase")));

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
        ProjectStatus projectStatus = projectDAO.getParticipantCount(project);
        projectStatus.setParticipantsNeeded(groupFinding.getMinNumberOfStudentsNeeded(project));
        Map<String, Object> taskData = new HashMap<>();
        taskData.put("participantCount", projectStatus);
        GroupFormationMechanism gfm = groupFinding.getGFM(project);
        taskData.put("gfm", gfm);
        task.setTaskData(taskData);
        if (gfm.equals(GroupFormationMechanism.Manual)) {
            task.setTaskType(TaskType.LINKED);
        }
        task.setHasRenderModel(true);
        return task;
    }

    public Task getTaskByProjectNameAndUserEmailAndTaskNameAndPhase(Project project, User user, TaskName taskName, Phase phase) {
        connect.connect();

        String query = "Select * from tasks where userEmail = ? and projectName = ? and phase = ? and taskName = ?";
        VereinfachtesResultSet resultSet = connect.issueSelectStatement(query, user.getEmail(), project.getName(), phase.name(), taskName.name());
        Task task = null;
        if (resultSet.next()) {
            task = getGeneralTask(resultSet);
        }
        connect.close();
        return task;
    }

    public List<Task> getTaskOfGroupsByProjectNameAndTaskNameAndPhase(Project project, TaskName taskName, Phase phase) {
        connect.connect();
        String query = "Select * from tasks where groupTask != 0 and projectName = ? and phase = ? and taskName = ?";
        VereinfachtesResultSet resultSet = connect.issueSelectStatement(query, project.getName(), phase.name(), taskName.name());
        ArrayList<Task> groupsTask = null;

        while (resultSet.next()) {
            if (groupsTask == null) {
                groupsTask = new ArrayList<>();
            }
            groupsTask.add(getGeneralTask(resultSet));
        }
        connect.close();
        return groupsTask;
    }

    /**
     * TODO  refactor reduce overloading by introducing
     * @param project
     * @param target
     * @param taskName
     * @param phase
     * @return
     */
    private Task createUserDefault(Project project, User target, TaskName taskName, Phase phase) {
        return createUserDefault(project, target, taskName, phase, Importance.MEDIUM);
    }

    public Task createUserDefault(Project project, User target, TaskName taskName, Phase phase, Progress progress) {
        return createUserDefault(project, target, taskName, phase, Importance.MEDIUM, progress);
    }

    private Task createUserDefault(Project project, User target, TaskName taskName, Phase phase, Importance importance) {
        return createUserDefault(project, target, taskName, phase, importance, Progress.JUSTSTARTED);
    }

    private Task createUserDefault(
            Project project, User target, TaskName taskName, Phase phase, Importance importance, Progress progress) {
        Task task = new Task(taskName, target, project, progress);
        task.setEventCreated(System.currentTimeMillis());
        task.setDeadline(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7));
        task.setImportance(importance);
        task.setGroupTask(0);
        task.setPhase(phase);
        task.setTaskType(TaskType.INFO, TaskType.LINKED);
        return task;
    }

    public Task createUserDefaultWithoutDeadline(Project project, User target, TaskName taskName, Phase phase, Importance importance, Progress progress) {
        Task task = createUserDefault(project, target, taskName, phase, importance, progress);
        task.setDeadline(0L);
        return task;
    }

    private GroupTask createGroupDefault(Project project, Integer groupId, TaskName taskName, Phase phase) {
        return createGroupTask(project, groupId, taskName, phase, Progress.JUSTSTARTED);
    }

    public GroupTask createGroupTask(Project project, Integer groupId, TaskName taskName, Phase phase, Progress progress) {
        GroupTask groupTask = new GroupTask(taskName, groupId, progress, project);
        groupTask.setEventCreated(System.currentTimeMillis());
        groupTask.setDeadline(System.currentTimeMillis() + 7000 * 60 * 60 * 24);
        groupTask.setUserEmail("");
        groupTask.setImportance(Importance.MEDIUM);
        groupTask.setPhase(phase);
        groupTask.setTaskType(TaskType.INFO, TaskType.LINKED);
        return groupTask;
    }

    public void persist(Task task) {

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
        //Timestamp creationTime = new Timestamp(task.getEventCreated());
        Timestamp deadline = new Timestamp(task.getDeadline());
        connect.issueInsertOrDeleteStatement(query, task.getUserEmail(), task.getProjectName(), task.getTaskName(),
                task.getGroupTask(), task.getImportance(), task.getProgress(), task.getPhase(), task.getEventCreated(),
                deadline, task.getTaskType()[0].toString(), taskMode2, taskMode3);
        connect.close();
    }

    // get all the tasks a user has in a specific project
    public ArrayList<Task> getTasks(User user, Project project) {
        connect.connect();
        String query = "Select * from tasks t where t.userEmail = ? AND t.projectName = ? OR t.groupTask IN " +
                "(SELECT gu.groupId FROM groupuser gu JOIN groups g on gu.groupId = g.id and g.projectName=? " +
                "AND gu.userEmail=?) ORDER BY created DESC";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(query, user.getEmail(), project.getName(), project.getName(), user.getEmail());
        ArrayList<Task> result = new ArrayList<>();
        while (vereinfachtesResultSet.next()) {
            result.add(resultSetToTask(user, project, vereinfachtesResultSet));
        }
        connect.close();
        TaskOrder taskOrder = new TaskOrder();
        result.sort(taskOrder.byName);
        return result;
    }

    private Task resultSetToTask(User user, Project project, VereinfachtesResultSet vereinfachtesResultSet) {
        int groupId = vereinfachtesResultSet.getInt("groupTask");
        if (groupId == 0) {
            return resultSetToUserTask(user, project, vereinfachtesResultSet);
        } else {
            return resultSetToGroupTask(groupId, project, vereinfachtesResultSet);
        }
    }

    private Task resultSetToTask(Integer groupId, Project project, VereinfachtesResultSet vereinfachtesResultSet) {
        return resultSetToGroupTask(groupId, project, vereinfachtesResultSet);
    }

    private Task resultSetToGroupTask(Integer groupId, Project project, VereinfachtesResultSet vereinfachtesResultSet) {
        String taskName = vereinfachtesResultSet.getString("taskName");
        Task result;
        TaskName taskName1 = TaskName.valueOf(taskName);
        switch (taskName1) {
            case ANNOTATE_DOSSIER: {
                //finalizeDossierTask.setTaskType(TaskType.LINKED);
                result = getFinalizeDossierTask(vereinfachtesResultSet);
                break;
            }
            case UPLOAD_DOSSIER: {
                //generalTask.setTaskType(TaskType.LINKED);
                result = getGeneralTask(vereinfachtesResultSet);
                break;
            }
            case GIVE_FEEDBACK: {
                Task feedbackTask = getGeneralTask(vereinfachtesResultSet);
                //feedback.assigningMissingFeedbackTasks();
                feedbackTask.setTaskData(submissionController.getFeedbackTaskData(groupId, project));
                feedbackTask.setHasRenderModel(true);
                result = feedbackTask;
                break;
            }
            case SEE_FEEDBACK: {
                Task task = getGeneralTask(vereinfachtesResultSet);
                task.setTaskType(TaskType.LINKED);
                Map<String, String> taskData = new HashMap<>();
                taskData.put("fullSubmissionId", submissionController.getFullSubmissionId(groupId, project, FileRole.DOSSIER));
                List<String> startCategory = submissionController.getAnnotationCategories(project);
                taskData.put("category", startCategory.get(0));
                task.setTaskData(taskData);
                result = task;
                break;
            }
            case REEDIT_DOSSIER: {
                result = getGeneralTask(vereinfachtesResultSet);
                GroupFeedbackTaskData groupFeedbackTaskData = submissionController.getMyFeedback(groupId, project);
                if (groupFeedbackTaskData == null) {
                    break;
                }
                Map<String, String> taskData = new HashMap<>();
                taskData.put("fullSubmissionId", submissionController.getFullSubmissionId(groupId, project, FileRole.DOSSIER, 1));
                result.setTaskData(taskData);
                break;
            }
            default: {
                result = getGeneralTask(vereinfachtesResultSet);
            }
        }
        return result;
    }

    private Task resultSetToUserTask(User user, Project project, VereinfachtesResultSet vereinfachtesResultSet) {
        String taskName = vereinfachtesResultSet.getString("taskName");
        Task result;
        TaskName taskName1 = TaskName.valueOf(taskName);
        switch (taskName1) {
            case WAIT_FOR_PARTICPANTS: {
                result = getTaskWaitForParticipants(vereinfachtesResultSet);
                break;
            }
            case SEE_FEEDBACK: {
                Task feedbackTask = getGeneralTask(vereinfachtesResultSet);
                feedbackTask.setTaskData(submissionController.getMyFeedback(user, project));
                feedbackTask.setHasRenderModel(true);
                result = feedbackTask;
                break;
            }
            case WAITING_FOR_STUDENT_DOSSIERS: {
                Task task = getGeneralTask(vereinfachtesResultSet);
                task.setHasRenderModel(true);
                task.setTaskData(submissionController.getProgressData(project));
                result = task;
                break;
            }
            case CLOSE_DOSSIER_FEEDBACK_PHASE: {
                Task task = getGeneralTask(vereinfachtesResultSet);
                task.setHasRenderModel(true);
                List<Group> missingFeedbacks = constraints.checkWhichDossiersAreNotFinalized(project);
                task.setTaskData(missingFeedbacks);  //frontendCheck if missingFeedbacks.size ==0
                result = task;
                Task waitingForDossiers = new Task(
                        TaskName.WAITING_FOR_STUDENT_DOSSIERS, user, project, Progress.FINISHED);
                updateForUser(waitingForDossiers);
                break;
            }
            case CLOSE_PEER_ASSESSMENTS_PHASE:
            case WAIT_FOR_UPLOAD: {
                Task task = getGeneralTask(vereinfachtesResultSet);
                task.setHasRenderModel(true);
                // get Progress from peer assessment
                task.setTaskData(assessmentDAO.getProgress(new Project(task.getProjectName())));
                result = task;
                break;
            }
            case GIVE_INTERNAL_ASSESSMENT: {
                Task task = getGeneralTask(vereinfachtesResultSet);
                task.setTaskData(assessmentDAO.getInternalPeerAssessmentProgress(project, user));
                result = task;
                break;
            }
            case GIVE_EXTERNAL_ASSESSMENT: {
                Task task = getGeneralTask(vereinfachtesResultSet);
                task.setTaskData(assessmentDAO.getTargetGroupForAssessment(new User(task.getUserEmail())));
                result = task;
                break;
            }
            case WAIT_FOR_GRADING:
                result = getGeneralTask(vereinfachtesResultSet);
                break;
            case GIVE_EXTERNAL_ASSESSMENT_TEACHER: {
                Task task = getGeneralTask(vereinfachtesResultSet);
                task.setTaskData(assessmentDAO.getNextGroupToFeedbackForTeacher(project));
                result = task;
                break;
            }case END_STUDENT:
                Task task = getGeneralTask(vereinfachtesResultSet);
                task.setTaskData(assessmentDAO.getGradesFromDB(project, user));
                result = task;
                break;
            default: {
                result = getGeneralTask(vereinfachtesResultSet);
            }
        }
        return result;
    }

    public ArrayList<Task> getTasksWithTaskName(Integer groupId, Project project, TaskName taskname) {
        connect.connect();
        String query = "Select * from tasks t where t.groupTask = ? AND t.projectName = ? AND t.taskName= ? ORDER BY created DESC";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(query, groupId, project.getName(), taskname.toString());
        ArrayList<Task> result = new ArrayList<>();
        while (vereinfachtesResultSet.next()) { //an empty userEmail includes groupTasks and excludes userTasks
            result.add(resultSetToTask(groupId, project, vereinfachtesResultSet));
        }
        connect.close();
        return result;
    }

    public ArrayList<Task> getTasksWithTaskName(Project project, User user, TaskName taskname) {
        connect.connect();
        String query = "Select * from tasks t where (t.userEmail = ? AND t.projectName = ? OR t.groupTask IN " +
                "(SELECT gu.groupId FROM groupuser gu JOIN groups g on gu.groupId = g.id and g.projectName=? " +
                "AND gu.userEmail=?)) AND t.taskName= ? ORDER BY created DESC";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(query, user.getEmail(), project.getName(), project.getName(), user.getEmail(), taskname.toString());
        ArrayList<Task> result = new ArrayList<>();
        while (vereinfachtesResultSet.next()) {
            result.add(resultSetToTask(user, project, vereinfachtesResultSet));
        }
        connect.close();
        return result;
    }

    private Task getFinalizeDossierTask(VereinfachtesResultSet vereinfachtesResultSet) {
        Task task = getGeneralTask(vereinfachtesResultSet);
        task.setTaskData(submissionController
                .getSubmissionData(task.getGroupTask(), new Project(task.getProjectName())));
        return task;
    }

    public void persist(Project project, User target, TaskName taskName, Phase phase) {
        Task aDefault = createUserDefault(project, target, taskName, phase);
        persist(aDefault);
    }

    public void persist(Project project, User target, TaskName taskName, Phase phase, Progress progress) {
        Task aDefault = createUserDefault(project, target, taskName, phase);
        aDefault.setProgress(progress);
        persist(aDefault);
    }

    public void persistTeacherTask(Project project, TaskName taskName, Phase phase) {
        User user = new User(projectDAO.getProjectByName(project.getName()).getAuthorEmail());
        Task aDefault = createUserDefault(project, user, taskName, phase);
        ////////don't know yet if it should happen here//////////
        aDefault.setTaskType(TaskType.LINKED);
        ////////could also be a default                //////////
        persist(aDefault);
    }

    public void createTaskWaitForParticipants(Project project, User author) {
        Task task = createUserDefault(project, author, TaskName.WAIT_FOR_PARTICPANTS, Phase.GroupFormation);
        task.setTaskName(WAIT_FOR_PARTICPANTS);
        task.setTaskType(TaskType.LINKED, TaskType.INFO);
        persist(task);
    }

    public void createWaitingForGroupFormationTask(Project project, User target) {
        Task task = createUserDefault(project, target, WAITING_FOR_GROUP, Phase.GroupFormation);
        task.setTaskType(TaskType.INFO);
        task.setImportance(Importance.MEDIUM);
        task.setProgress(Progress.JUSTSTARTED);

        persist(task);
    }

    public void updateForUser(Task task) {
        connect.connect();
        String query = "UPDATE tasks set progress = ? where userEmail = ? AND projectName = ? AND taskName = ?";
        connect.issueUpdateStatement(
                query, task.getProgress().name(), task.getUserEmail(), task.getProjectName(), task.getTaskName());
        connect.close();
    }

    public void updateTeacherTask(Project project, TaskName taskName, Progress progress) {
        User user = new User(projectDAO.getProjectByName(project.getName()).getAuthorEmail());
        updateForUser(new Task(taskName, user, project, progress));
    }

    /**
     * task needs to contain a user or a groupTask. Besides that it needs a projectName, TaskName and Progress
     *
     * @param task The task, that is meant to become updated
     */
    public void updateForGroup(Task task) {
        Integer groupId;
        if (task.getUserEmail() != null) {
            groupId = groupDAO.getGroupByStudent(new Project(task.getProjectName()), new User(task.getUserEmail()));
        } else {
            groupId = task.getGroupTask();
        }
        updateGroupTask(task, groupId);
    }

    public void updateGroupTask(Task task, int groupId) {
        connect.connect();
        String query = "UPDATE tasks SET `progress` = ? where groupTask = ? AND projectName = ? AND taskName = ?";
        connect.issueUpdateStatement(
                query, task.getProgress().name(), groupId, task.getProjectName(), task.getTaskName());
        connect.close();
    }

    public void updateGroupTask(GroupTask task) {
        updateGroupTask(task, task.getGroupTask());
    }

    public void updateForAll(Task task) {
        connect.connect();
        String query = "UPDATE tasks set progress = ? where projectName = ? AND taskName = ?";
        connect.issueUpdateStatement(query, task.getProgress().name(), task.getProjectName(), task.getTaskName());
        connect.close();
    }

    public void persistMemberTask(Project project, TaskName taskName, Phase phase) {
        List<User> members = userDAO.getUsersByProjectName(project.getName());
        for (User member : members) {
            persist(project, member, taskName, phase);
        }
    }

    public void persistTaskForAllGroups(Project project, TaskName taskName, Phase phase) {
        List<Group> groups = groupFinding.getGroups(project);
        for (Group group : groups) {
            persist(project, group.getId(), taskName, phase, TaskType.LINKED);
        }
    }

    public void persistTaskGroup(Project project, User user, TaskName taskName, Phase phase) {
        Integer groupId = groupFinding.getMyGroupId(user, project);
        persist(project, groupId, taskName, phase, TaskType.LINKED);
    }

    public void persistTaskGroup(Project project, Integer groupId, TaskName taskName, Phase phase) {
        persist(project, groupId, taskName, phase, TaskType.LINKED);
    }

    public void persist(Project project, Integer groupId, TaskName taskName, Phase phase, TaskType linked) {
        Task task = createGroupDefault(project, groupId, taskName, phase);
        task.setTaskType(linked);
        persist(task);
    }

    public void persist(Project project, User user, TaskName finalizeDossier, Phase dossierFeedback, TaskType linked) {
        Task task = createUserDefault(project, user, finalizeDossier, dossierFeedback);
        task.setTaskType(linked);
        persist(task);
    }

    public void persistFeedbackTask(Project project, GroupFeedbackTaskData groupFeedbackTaskData) {
        // create task
        persist(project, groupFeedbackTaskData.getTarget(), TaskName.GIVE_FEEDBACK, Phase.DossierFeedback, TaskType.LINKED);
    }

    /*
     * if this takes long rewrite it as batch updateRocketChatUserName
     */
    public void finishMemberTask(Project project, TaskName taskName) {
        java.util.List<User> members = userDAO.getUsersByProjectName(project.getName());
        for (User member : members) {
            Task task = new Task(taskName, member, project, Progress.FINISHED);
            updateForUser(task);
        }
    }

}

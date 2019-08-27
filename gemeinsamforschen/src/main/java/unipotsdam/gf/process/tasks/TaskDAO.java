package unipotsdam.gf.process.tasks;

import org.slf4j.LoggerFactory;
import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.modules.assessment.AssessmentDAO;
import unipotsdam.gf.modules.assessment.InternalPeerAssessmentProgress;
import unipotsdam.gf.modules.assessment.ProgessAndTaskMapping;
import unipotsdam.gf.modules.fileManagement.FileRole;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.reflection.model.ReflectionPhaseProgress;
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
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static unipotsdam.gf.process.tasks.TaskName.*;

@ManagedBean
public class TaskDAO {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SubmissionController.class);


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

    /**
     * adds general details to task
     */
    private Task getGeneralTask(Task task) {
        Task result = task;
        connect.connect();
        String query = "Select * from tasks where (userEmail = ? OR groupTask = ?) and projectName = ? and taskName = ?";
        if (task.getGroupTask() == 0) {
            query = "Select * from tasks where userEmail = ? AND groupTask = ? and projectName = ? and taskName = ?";
        }

        VereinfachtesResultSet resultSet = connect.issueSelectStatement(
                query,
                task.getUserEmail(),
                task.getGroupTask(),
                task.getProjectName(),
                task.getTaskName());
        if (resultSet.next()) {
            result = getGeneralTask(resultSet);
        }
        connect.close();
        return result;
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

    private Task getTaskWaitForParticipants(Task task) throws Exception {
        Task result = getGeneralTask(task);
        Project project = projectDAO.getProjectByName(task.getProjectName());

        ProjectStatus projectStatus = projectDAO.getParticipantCount(project);
        projectStatus.setParticipantsNeeded(groupFinding.getMinNumberOfStudentsNeeded(project));
        Map<String, Object> taskData = new HashMap<>();
        taskData.put("participantCount", projectStatus);
        GroupFormationMechanism gfm = groupFinding.getGFM(project);
        taskData.put("gfm", gfm);
        taskData.put("groupSize", project.getGroupSize());
        result.setTaskData(taskData);
        if (gfm.equals(GroupFormationMechanism.Manual)) {
            result.setTaskType(TaskType.LINKED);
        }
        result.setHasRenderModel(true);
        return result;
    }

    public Task getUserTask(Project project, User user, TaskName taskName, Phase phase) {
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

    public Task getUserTask(Project project, User user, TaskName taskName) {
        connect.connect();

        String query = "Select * from tasks where userEmail = ? and projectName = ? and taskName = ?";
        VereinfachtesResultSet resultSet = connect.issueSelectStatement(query, user.getEmail(), project.getName(), taskName.name());
        Task task = null;
        if (resultSet.next()) {
            task = getGeneralTask(resultSet);
        }
        connect.close();
        return task;
    }

    private List<Task> getTaskForProjectWithoutProgress(Project project, TaskName taskName, Progress progress) {
        connect.connect();
        String query = "Select * from tasks t where t.projectName = ? AND t.taskName = ? and t.progress <> ?";
        return getTasks(project, taskName, progress, query);
    }

    private List<Task> getTaskForProjectWithProgress(Project project, TaskName taskName, Progress progress) {
        connect.connect();
        String query = "Select * from tasks t where t.projectName = ? AND t.taskName = ? and t.progress = ?";
        return getTasks(project, taskName, progress, query);
    }

    private List<Task> getTasks(Project project, TaskName taskName, Progress progress, String query) {
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(query, project.getName(), taskName.toString(), progress.name());
        ArrayList<Task> result = new ArrayList<>();
        while (vereinfachtesResultSet != null && vereinfachtesResultSet.next()) {
            result.add(getGeneralTask(vereinfachtesResultSet));
        }
        connect.close();
        return result;
    }

/*    public List<Task> getTaskForProject(Project project, TaskName taskName) {
        connect.connect();
        String query = "Select * from tasks t where t.projectName = ? AND t.taskName = ? and t.progress = ?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(query, project.getName(), taskName.toString());
        ArrayList<Task> result = new ArrayList<>();
        while (vereinfachtesResultSet != null && vereinfachtesResultSet.next()) {
            result.add(getGeneralTask(vereinfachtesResultSet));
        }
        connect.close();
        return result;
    }*/

    public List<Task> getTaskForProjectByTaskName(Project project, TaskName taskName) {
        connect.connect();
        String query = "Select * from tasks t where t.projectName = ? AND t.taskName = ? ORDER BY created DESC";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query, project.getName(), taskName.name());
        ArrayList<Task> result = new ArrayList<>();
        while (vereinfachtesResultSet.next()) {
            result.add(getGeneralTask(vereinfachtesResultSet));
        }
        connect.close();
        return result;
    }

    /*
     * TODO  refactor reduce overloading by introducing builder?
     */
    private Task createUserDefault(Project project, User target, TaskName taskName, Phase phase) {
        return createUserDefault(project, target, taskName, phase, Importance.MEDIUM, Progress.JUSTSTARTED);
    }

    public Task createUserDefault(Project project, User target, TaskName taskName, Phase phase, Progress progress) {
        return createUserDefault(project, target, taskName, phase, Importance.MEDIUM, progress);
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
                "INSERT IGNORE INTO tasks (userEmail, projectName, taskName, " +
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
    ArrayList<Task> getTasks(User user, Project project) throws Exception {
        connect.connect();
        String query = "Select * from tasks t where t.userEmail = ? AND t.projectName = ? OR t.groupTask IN " +
                "(SELECT gu.groupId FROM groupuser gu JOIN groups g on gu.groupId = g.id and g.projectName=? " +
                "AND gu.userEmail=?) ORDER BY created DESC";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(query, user.getEmail(), project.getName(), project.getName(), user.getEmail());
        ArrayList<Task> shuttle = new ArrayList<>();
        while (vereinfachtesResultSet.next()) {
            Task task = new Task(
                    TaskName.valueOf(vereinfachtesResultSet.getString("taskName")),
                    user,
                    project,
                    Progress.valueOf(vereinfachtesResultSet.getString("progress"))
            );
            task.setGroupTask(vereinfachtesResultSet.getInt("groupTask"));
            shuttle.add(task);
        }
        connect.close();
        ArrayList<Task> result = new ArrayList<>();
        for (Task task : shuttle) {
            result.add(getTaskSpecifications(user, project, task));
        }
        result = result.stream().filter(Objects::nonNull).collect(Collectors.toCollection(ArrayList::new));
        TaskOrder taskOrder = new TaskOrder();
        result.sort(taskOrder.byName);
        return result;
    }

    private Task getTaskSpecifications(User user, Project project, Task task)
            throws Exception {
        int groupId = task.getGroupTask();
        if (groupId == 0) {
            return resultSetToUserTask(user, project, task);
        } else {
            return resultSetToGroupTask(groupId, project, task);
        }

    }

    private Task getTaskSpecifications(Integer groupId, Project project, Task task) {
        return resultSetToGroupTask(groupId, project, task);
    }

    private Task resultSetToGroupTask(Integer groupId, Project project, Task task) {
        Task result;
        switch (task.getTaskName()) {
            case ANNOTATE_DOSSIER: {
                //finalizeDossierTask.setTaskType(TaskType.LINKED);
                result = getFinalizeDossierTask(task);
                break;
            }
            case UPLOAD_DOSSIER: {
                //generalTask.setTaskType(TaskType.LINKED);
                result = getGeneralTask(task);
                break;
            }
            case GIVE_FEEDBACK: {
                result = getGeneralTask(task);
                //feedback.assigningMissingFeedbackTasks();
                result.setTaskData(submissionController.getFeedbackTaskData(groupId, project));
                result.setHasRenderModel(true);
                break;
            }
            case REEDIT_DOSSIER: {
                result = getGeneralTask(task);
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
                result = getGeneralTask(task);
            }
        }
        return result;
    }

    private Task resultSetToUserTask(User user, Project project, Task task)
            throws Exception {
        Task result;
        switch (task.getTaskName()) {
            case WAIT_FOR_PARTICPANTS: {
                result = getTaskWaitForParticipants(task);
                break;
            }
            case WAITING_FOR_STUDENT_DOSSIERS: {
                result = getGeneralTask(task);
                result.setHasRenderModel(true);
                result.setTaskData(submissionController.getProgressData(project));
                break;
            }
            case CLOSE_DOSSIER_FEEDBACK_PHASE: {
                result = getGeneralTask(task);
                result.setHasRenderModel(true);
                List<Group> missingFeedbacks;
                if (groupDAO.getGroupsByProjectName(project.getName()).size() > 1) {
                    missingFeedbacks = constraints.checkWhichDossiersAreNotFinalized(project);
                } else {
                    missingFeedbacks = new ArrayList<>();
                }
                result.setTaskData(missingFeedbacks);  //frontendCheck if missingFeedbacks.size ==0
                Task waitingForDossiers = new Task(
                        TaskName.WAITING_FOR_STUDENT_DOSSIERS, user, project, Progress.FINISHED);
                updateForUser(waitingForDossiers);
                break;
            }
            case CLOSE_PEER_ASSESSMENTS_PHASE:
            case WAIT_FOR_UPLOAD: {
                result = getGeneralTask(task);
                task.setHasRenderModel(true);
                // get Progress from peer assessment
                result.setTaskData(assessmentDAO.getProgress(new Project(task.getProjectName())));
                break;
            }
            case GIVE_INTERNAL_ASSESSMENT: {
                result = getGeneralTask(task);
                InternalPeerAssessmentProgress ipap = assessmentDAO.getInternalPeerAssessmentProgress(project, user);
                if (ipap.getNumberOfMissing() > 0) {
                    result.setTaskData(ipap);
                } else {
                    result = null;
                }
                break;
            }
            case GIVE_EXTERNAL_ASSESSMENT: {
                result = getGeneralTask(task);
                TaskMapping taskMapping = assessmentDAO.getTargetGroupForAssessment(new User(task.getUserEmail()), project);
                if (taskMapping == null) {
                    result = null;
                    break;
                } else {
                    result.setTaskData(taskMapping);
                }
                break;
            }
            case WAIT_FOR_GRADING:
                result = getGeneralTask(task);
                break;
            case GIVE_EXTERNAL_ASSESSMENT_TEACHER: {
                result = getGeneralTask(task);
                ProgessAndTaskMapping taskMappingAndProgress = assessmentDAO.getTaskMappingAndProgress(project);
                result.setTaskData(taskMappingAndProgress);
                //task.setTaskData(assessmentDAO.getNextGroupToFeedbackForTeacher(project));
                break;
            }
            case END_STUDENT: {
                result = getGeneralTask(task);
                result.setTaskData(assessmentDAO.getGradesFromDB(project, user));
                break;
            }
            case CONTACT_GROUP_MEMBERS: {
                result = getGeneralTask(task);
                Group myGroup = groupDAO.getMyGroup(user, project);
                if (myGroup.getMembers().size() < 2) {
                    result = null;
                    break;
                }
                break;
            }
            case ANSWER_REFLECTION_QUESTIONS: {
                result = getGeneralTask(task);
                break;
            }

            case CLOSE_EXECUTION_PHASE:
                result = getGeneralTask(task);
                List<Task> notAllReflectionQuestionAnswered = getTaskForProjectWithoutProgress(project, TaskName.ANSWER_REFLECTION_QUESTIONS, Progress.FINISHED);
                List<User> userWithUnansweredReflectionQuestions = new ArrayList<>();
                notAllReflectionQuestionAnswered.forEach(unansweredReflectionQuestion -> {
                    User student = userDAO.getUserByEmail(unansweredReflectionQuestion.getUserEmail());
                    userWithUnansweredReflectionQuestions.add(student);
                });

                List<User> userWithNoMaterialChosen = getUsersWithUnfinishedTask(project, CHOOSE_PORTFOLIO_ENTRIES);
                ReflectionPhaseProgress progress = new ReflectionPhaseProgress(userWithUnansweredReflectionQuestions, userWithNoMaterialChosen);
                result.setTaskData(progress);
                break;
            default: {
                result = getGeneralTask(task);
            }
        }
        return result;
    }

    private List<User> getUsersWithUnfinishedTask(Project project, TaskName taskName) {
        List<Task> tasksMaterialChosen = getTaskForProjectWithProgress(project, taskName, Progress.FINISHED);
        List<User> users = userDAO.getUsersByProjectName(project.getName());
        return users.stream()
                .filter(student -> tasksMaterialChosen.stream().map(Task::getUserEmail).noneMatch(email -> student.getEmail().equals(email)))
                .collect(Collectors.toList());
    }

    public Task getTasksWithTaskName(Integer groupId, Project project, TaskName taskname) {

        connect.connect();
        String query = "Select * from tasks t where t.groupTask = ? AND t.projectName = ? AND t.taskName= ? ORDER BY created DESC";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(query, groupId, project.getName(), taskname.toString());
        Task shuttle = null;
        if (vereinfachtesResultSet.next()) { //an empty userEmail includes groupTasks and excludes userTasks
            shuttle = new Task(taskname,
                    null,
                    project,
                    Progress.valueOf(vereinfachtesResultSet.getString("progress")));
            shuttle.setGroupTask(vereinfachtesResultSet.getInt("groupTask"));
        }
        connect.close();
        if (shuttle != null) {
            return getTaskSpecifications(groupId, project, shuttle);
        } else {
            return null;
        }

    }

    private Task getFinalizeDossierTask(Task task) {
        Task result = getGeneralTask(task);
        result.setTaskData(submissionController.getSubmissionData(result.getGroupTask(), new Project(result.getProjectName())));
        return result;
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
        User user = null;
        try {
            user = new User(projectDAO.getProjectByName(project.getName()).getAuthorEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void updateForUser(Task task) throws Exception {
        if (task.getUserEmail() == null) {
            throw new Exception("needs user not null to work properly" + task.getTaskName());
        }
        connect.connect();
        String query = "UPDATE tasks set progress = ? where userEmail = ? AND projectName = ? AND taskName = ?";
        connect.issueUpdateStatement(
                query, task.getProgress().name(), task.getUserEmail(), task.getProjectName(), task.getTaskName());
        connect.close();
    }

    public void updateTeacherTask(Project project, TaskName taskName, Progress progress) throws Exception {
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

    private void updateGroupTask(Task task, int groupId) {
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
        members.forEach(member -> persist(project, member, taskName, phase));
    }

    public void persistTaskForAllGroups(Project project, TaskName taskName, Phase phase) {
        List<Group> groups = groupFinding.getGroups(project);
        groups.forEach(group -> persist(project, group.getId(), taskName, phase, TaskType.LINKED));
    }

    public void persistTaskGroup(Project project, User user, TaskName taskName, Phase phase) {
        Integer groupId = groupFinding.getMyGroupId(user, project);
        persist(project, groupId, taskName, phase, TaskType.LINKED);
    }

    public void persistTaskGroup(Project project, Integer groupId, TaskName taskName, Phase phase, TaskType taskType) {
        persist(project, groupId, taskName, phase, taskType);
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

    /*
     * if this takes long rewrite it as batch updateRocketChatUserName
     */
    public void finishMemberTask(Project project, TaskName taskName) throws Exception {
        List<User> members = userDAO.getUsersByProjectName(project.getName());
        for (User member : members) {
            Task task = new Task(taskName, member, project, Progress.FINISHED);
            updateForUser(task);
        }
    }

    public void addTaskType(Task task, TaskType taskType) {
        boolean contains = false;
        int position = 1;
        for (TaskType containedTaskType : task.getTaskType()) {
            if (containedTaskType == taskType) {
                contains = true;
            }
            position++;
        }
        if (contains) {
            log.info("taskType was already contained in Task.");
            return;
        }
        connect.connect();
        String query = "UPDATE tasks set taskMode" + position + " = ? where projectName = ? AND taskName = ? AND groupTask = ? and userEmail = ?";
        connect.issueUpdateStatement(query, taskType, task.getProjectName(), task.getTaskName(), task.getGroupTask(), task.getUserEmail());
        connect.close();
    }

}

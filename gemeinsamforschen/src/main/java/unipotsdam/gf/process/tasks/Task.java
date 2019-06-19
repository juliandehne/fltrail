package unipotsdam.gf.process.tasks;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.phases.Phase;

import javax.inject.Inject;

public class Task {
    // as in "Feedback"

    @Inject
    protected ProjectDAO projectDAO;

    private TaskType[] taskType;

    // optional: only relevant if the task is show on the side
    private Object taskData;
    private TaskName taskName;
    private Boolean hasRenderModel;

    // relevant for time based warnings
    private Long eventCreated;
    private Long deadline;

    private Integer groupTask;
    private Importance importance;
    private Phase phase;


    private String userEmail;
    private String projectName;
    private Progress progress;


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Task(TaskName taskName, User user, Project project, Progress progress) {
        this.taskName = taskName;
        if (user != null) {
            this.userEmail = user.getEmail();
        }
        this.projectName = project.getName();
        this.progress = progress;
    }

    public TaskName getTaskName() {
        return taskName;
    }

    public void setTaskName(TaskName taskName) {
        this.taskName = taskName;
    }

    public TaskType[] getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType ... taskType) {
        this.taskType = taskType;
    }

    public Object getTaskData() {
        return taskData;
    }

    public void setTaskData(Object taskData) {
        this.taskData = taskData;
    }


    public Long getEventCreated() {
        return eventCreated;
    }

    public void setEventCreated(Long eventCreated) {
        this.eventCreated = eventCreated;
    }

    public Long getDeadline() {
        return deadline;
    }

    public void setDeadline(Long deadline) {
        this.deadline = deadline;
    }

    public Integer getGroupTask() {
        return groupTask;
    }

    public void setGroupTask(Integer groupTask) {
        this.groupTask = groupTask;
    }

    public Importance getImportance() {
        return importance;
    }

    public void setImportance(Importance importance) {
        this.importance = importance;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }


    public Progress getProgress() {
        return progress;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }


    public Boolean getHasRenderModel() {
        return hasRenderModel;
    }

    public void setHasRenderModel(Boolean hasRenderModel) {
        this.hasRenderModel = hasRenderModel;
    }
}

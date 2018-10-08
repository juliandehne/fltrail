package unipotsdam.gf.modules.tasks;

import unipotsdam.gf.modules.states.ProjectPhase;

import java.sql.Timestamp;

public class Task {
    // as in "Feedback"

    private TaskType[] taskType;

    // optional: only relevant if the task is show on the side
    private Object taskData;

    public TaskName getTaskName() {
        return taskName;
    }

    public void setTaskName(TaskName taskName) {
        this.taskName = taskName;
    }

    private TaskName taskName;
    private Boolean hasRenderModell;

    // relevant for time based warnings
    private Long eventCreated;
    private Long deadline;

    private Boolean groupTask;
    private Importance importance;
    private ProjectPhase phase;

    private String link;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    private String userEmail;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    private String projectName;

    private String progress;

    public Task() {
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

    public TaskName getRenderModel() {
        return taskName;
    }

    public void setRenderModel(TaskName renderModel) {
        this.taskName = renderModel;
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

    public Boolean getGroupTask() {
        return groupTask;
    }

    public void setGroupTask(Boolean groupTask) {
        this.groupTask = groupTask;
    }

    public Importance getImportance() {
        return importance;
    }

    public void setImportance(Importance importance) {
        this.importance = importance;
    }

    public ProjectPhase getPhase() {
        return phase;
    }

    public void setPhase(ProjectPhase phase) {
        this.phase = phase;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }


    public Boolean getHasRenderModell() {
        return hasRenderModell;
    }

    public void setHasRenderModell(Boolean hasRenderModell) {
        this.hasRenderModell = hasRenderModell;
    }
}

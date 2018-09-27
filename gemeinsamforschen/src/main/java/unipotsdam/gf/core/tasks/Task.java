package unipotsdam.gf.core.tasks;

import unipotsdam.gf.core.states.model.ProjectPhase;

import java.sql.Timestamp;

public class Task {
    // as in "Feedback"
    private String technicalName;
    // as in "Feedback geben"
    private String title;

    private Enum taskType;

    // optional: only relevant if the task is show on the side
    private Object taskData;
    private Enum renderModel;

    // relevant for time based warnings
    private Timestamp eventCreated;
    private Timestamp deadline;

    private Boolean groupTask;
    private Importance importance;
    private ProjectPhase phase;

    private String link;

    public String getUserToken() {
        return userEmail;
    }

    public void setUserToken(String userEmail) {
        this.userEmail = userEmail;
    }

    private String userEmail;

    public String getProjectToken() {
        return projectToken;
    }

    public void setProjectToken(String projectToken) {
        this.projectToken = projectToken;
    }

    private String projectToken;

    public Task() {
    }

    public String getTechnicalName() {
        return technicalName;
    }

    public void setTechnicalName(String technicalName) {
        this.technicalName = technicalName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Enum getTaskType() {
        return taskType;
    }

    public void setTaskType(Enum taskType) {
        this.taskType = taskType;
    }

    public Object getTaskData() {
        return taskData;
    }

    public void setTaskData(Object taskData) {
        this.taskData = taskData;
    }

    public Enum getRenderModel() {
        return renderModel;
    }

    public void setRenderModel(Enum renderModel) {
        this.renderModel = renderModel;
    }

    public Timestamp getEventCreated() {
        return eventCreated;
    }

    public void setEventCreated(Timestamp eventCreated) {
        this.eventCreated = eventCreated;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
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
}

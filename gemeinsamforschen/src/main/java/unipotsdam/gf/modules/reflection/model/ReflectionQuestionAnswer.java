package unipotsdam.gf.modules.reflection.model;

import unipotsdam.gf.modules.submission.model.FullSubmission;

public class ReflectionQuestionAnswer {

    private String id;
    private long timestamp;
    private String text;
    private String projectName;
    private String userEmail;

    public ReflectionQuestionAnswer() {
    }

    public ReflectionQuestionAnswer(FullSubmission fullSubmission) {
        this.id = fullSubmission.getId();
        this.timestamp = fullSubmission.getTimestamp();
        this.text = fullSubmission.getText();
        this.projectName = fullSubmission.getProjectName();
        this.userEmail = fullSubmission.getUserEmail();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}

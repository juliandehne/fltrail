package unipotsdam.gf.modules.reflection.model;

import unipotsdam.gf.modules.submission.model.FullSubmission;

public class ReflectionQuestionAnswer {

    private String id;
    private long timeStamp;
    private String text;
    private String projectName;
    private String userEmail;

    public ReflectionQuestionAnswer(String id, long timeStamp, String text, String projectName, boolean finalized, String userEmail) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.text = text;
        this.projectName = projectName;
        this.userEmail = userEmail;
    }

    public ReflectionQuestionAnswer() {
    }

    public ReflectionQuestionAnswer(FullSubmission fullSubmission) {
        this.id = fullSubmission.getId();
        this.timeStamp = fullSubmission.getTimestamp();
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

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
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

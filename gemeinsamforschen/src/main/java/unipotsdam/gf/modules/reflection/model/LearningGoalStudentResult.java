package unipotsdam.gf.modules.reflection.model;

import unipotsdam.gf.modules.submission.model.Visibility;

//TODO: refactor fullSubmission so it is base for each submission
public class LearningGoalStudentResult {

    private String id;
    private String projectName;
    private long creationDate;
    private String learningGoalId;
    private int groupId;
    private String userEmail;
    private String text;
    private Visibility visibility;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    public String getLearningGoalId() {
        return learningGoalId;
    }

    public void setLearningGoalId(String learningGoalId) {
        this.learningGoalId = learningGoalId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }
}

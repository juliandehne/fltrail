package unipotsdam.gf.modules.reflection.model;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;

public class ReflectionQuestionToAnswer {

    private String id;
    private String learningGoalId;
    private String selectedReflectionQuestionId;
    private String fullSubmissionId;
    private String userEmail;
    private String projectName;

    public ReflectionQuestionToAnswer() {
    }

    public ReflectionQuestionToAnswer(String id) {
        this.id = id;
    }

    public ReflectionQuestionToAnswer(String id, String learningGoalId, String selectedReflectionQuestionId, String fullSubmissionId, String userEmail, String projectName) {
        this.id = id;
        this.learningGoalId = learningGoalId;
        this.selectedReflectionQuestionId = selectedReflectionQuestionId;
        this.fullSubmissionId = fullSubmissionId;
        this.userEmail = userEmail;
        this.projectName = projectName;
    }

    public ReflectionQuestionToAnswer(ReflectionQuestionToAnswer other) {
        this.id = other.id;
        this.learningGoalId = other.learningGoalId;
        this.selectedReflectionQuestionId = other.selectedReflectionQuestionId;
        this.fullSubmissionId = other.fullSubmissionId;
        this.userEmail = other.userEmail;
        this.projectName = other.projectName;
    }

    public ReflectionQuestionToAnswer(SelectedReflectionQuestion selectedReflectionQuestion, User user, Project project) {
        this.learningGoalId = selectedReflectionQuestion.getLearningGoalId();
        this.selectedReflectionQuestionId = selectedReflectionQuestion.getId();
        this.userEmail = user.getEmail();
        this.projectName = project.getName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLearningGoalId() {
        return learningGoalId;
    }

    public void setLearningGoalId(String learningGoalId) {
        this.learningGoalId = learningGoalId;
    }

    public String getSelectedReflectionQuestionId() {
        return selectedReflectionQuestionId;
    }

    public void setSelectedReflectionQuestionId(String selectedReflectionQuestionId) {
        this.selectedReflectionQuestionId = selectedReflectionQuestionId;
    }

    public String getFullSubmissionId() {
        return fullSubmissionId;
    }

    public void setFullSubmissionId(String fullSubmissionId) {
        this.fullSubmissionId = fullSubmissionId;
    }

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
}


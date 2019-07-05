package unipotsdam.gf.modules.reflection.model;

public class ReflectionQuestion {

    private String id;
    private String learningGoalId;
    private String question;
    private String fullSubmissionId;
    private String userEmail;
    private String projectName;

    public ReflectionQuestion() {
    }

    public ReflectionQuestion(String id) {
        this.id = id;
    }

    public ReflectionQuestion(String id, String learningGoalId, String question, String fullSubmissionId, String userEmail, String projectName) {
        this.id = id;
        this.learningGoalId = learningGoalId;
        this.question = question;
        this.fullSubmissionId = fullSubmissionId;
        this.userEmail = userEmail;
        this.projectName = projectName;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
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

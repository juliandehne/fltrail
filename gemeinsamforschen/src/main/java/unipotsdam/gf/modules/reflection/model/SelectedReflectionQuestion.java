package unipotsdam.gf.modules.reflection.model;

public class SelectedReflectionQuestion {

    private String id;
    private String learningGoalId;
    private String question;
    private String projectName;

    public SelectedReflectionQuestion(String learningGoalId, String question, String projectName) {
        this.learningGoalId = learningGoalId;
        this.question = question;
        this.projectName = projectName;
    }

    public SelectedReflectionQuestion(String id, String learningGoalId, String question, String projectName) {
        this(learningGoalId, question, projectName);
        this.id = id;
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}

package unipotsdam.gf.modules.reflection.model;

public class SelectedReflectionQuestion {

    private String id;
    private String learningGoalId;
    private String question;

    public SelectedReflectionQuestion() {
    }

    public SelectedReflectionQuestion(String id) {
        this.id = id;
    }

    public SelectedReflectionQuestion(String learningGoalId, String question) {
        this.learningGoalId = learningGoalId;
        this.question = question;
    }

    public SelectedReflectionQuestion(String id, String learningGoalId, String question) {
        this(learningGoalId, question);
        this.id = id;
    }

    public SelectedReflectionQuestion(SelectedReflectionQuestion other) {
        this(other.id, other.learningGoalId, other.question);
    }

    public SelectedReflectionQuestion(ReflectionQuestion reflectionQuestion) {
        this(reflectionQuestion.getId(), reflectionQuestion.getLearningGoalId(), reflectionQuestion.getQuestion());
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
}

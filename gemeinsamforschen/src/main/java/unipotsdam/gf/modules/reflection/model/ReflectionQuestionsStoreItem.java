package unipotsdam.gf.modules.reflection.model;

public class ReflectionQuestionsStoreItem {

    private String question;
    private String learningGoal;

    public String getId() {
        return question + learningGoal;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getLearningGoal() {
        return learningGoal;
    }

    public void setLearningGoal(String learningGoal) {
        this.learningGoal = learningGoal;
    }
}

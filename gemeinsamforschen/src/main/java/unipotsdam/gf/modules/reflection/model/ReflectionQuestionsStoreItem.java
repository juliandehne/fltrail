package unipotsdam.gf.modules.reflection.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReflectionQuestionsStoreItem {

    private String question;
    private String learningGoal;

    public ReflectionQuestionsStoreItem(String question, String learningGoal) {
        this.question = question;
        this.learningGoal = learningGoal;
    }

    public ReflectionQuestionsStoreItem() {
    }

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

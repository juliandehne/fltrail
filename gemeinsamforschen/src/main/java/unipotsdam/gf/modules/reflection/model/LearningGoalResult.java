package unipotsdam.gf.modules.reflection.model;

import java.util.ArrayList;
import java.util.List;

public class LearningGoalResult {

    private LearningGoal learningGoal;
    private List<ReflectionQuestion> reflectionQuestions;

    public LearningGoalResult() {
        reflectionQuestions = new ArrayList<>();
    }

    public LearningGoal getLearningGoal() {
        return learningGoal;
    }

    public void setLearningGoal(LearningGoal learningGoal) {
        this.learningGoal = learningGoal;
    }

    public List<ReflectionQuestion> getReflectionQuestions() {
        return reflectionQuestions;
    }

    public void setReflectionQuestions(List<ReflectionQuestion> reflectionQuestions) {
        this.reflectionQuestions = reflectionQuestions;
    }
}

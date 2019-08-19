package unipotsdam.gf.modules.reflection.model;

import java.util.ArrayList;
import java.util.List;

public class LearningGoalRequestResult {

    private LearningGoal learningGoal;
    private List<SelectedReflectionQuestion> reflectionQuestions;

    public LearningGoalRequestResult() {
        reflectionQuestions = new ArrayList<>();
    }

    public LearningGoalRequestResult(LearningGoal learningGoal, List<SelectedReflectionQuestion> reflectionQuestions) {
        this.learningGoal = learningGoal;
        this.reflectionQuestions = reflectionQuestions;
    }

    public LearningGoal getLearningGoal() {
        return learningGoal;
    }

    public void setLearningGoal(LearningGoal learningGoal) {
        this.learningGoal = learningGoal;
    }

    public List<SelectedReflectionQuestion> getReflectionQuestions() {
        return reflectionQuestions;
    }

    public void setReflectionQuestions(List<SelectedReflectionQuestion> reflectionQuestions) {
        this.reflectionQuestions = reflectionQuestions;
    }
}

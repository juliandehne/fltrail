package unipotsdam.gf.modules.reflection.model;

import java.util.ArrayList;
import java.util.List;

public class AssessmentSummary {

    private LearningGoal learningGoal;
    private LearningGoalStudentResult learningGoalStudentResult;
    private List<ReflectionQuestionWithAnswer> reflectionQuestionWithAnswers;

    public AssessmentSummary() {
        this.reflectionQuestionWithAnswers = new ArrayList<>();
    }

    public LearningGoal getLearningGoal() {
        return learningGoal;
    }

    public void setLearningGoal(LearningGoal learningGoal) {
        this.learningGoal = learningGoal;
    }

    public List<ReflectionQuestionWithAnswer> getReflectionQuestionWithAnswers() {
        return reflectionQuestionWithAnswers;
    }

    public void setReflectionQuestionWithAnswers(List<ReflectionQuestionWithAnswer> reflectionQuestionWithAnswers) {
        this.reflectionQuestionWithAnswers = reflectionQuestionWithAnswers;
    }

    public LearningGoalStudentResult getLearningGoalStudentResult() {
        return learningGoalStudentResult;
    }

    public void setLearningGoalStudentResult(LearningGoalStudentResult learningGoalStudentResult) {
        this.learningGoalStudentResult = learningGoalStudentResult;
    }
}

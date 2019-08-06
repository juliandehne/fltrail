package unipotsdam.gf.modules.reflection.model;

import unipotsdam.gf.modules.user.User;

import java.util.List;

public class ReflectionPhaseProgress {
    private List<User> userUnansweredReflectionQuestions;
    private List<User> userUnchosenAssessmentMaterial;

    public ReflectionPhaseProgress(List<User> userUnansweredReflectionQuestions, List<User> userUnchosenAssessmentMaterial) {
        this.userUnansweredReflectionQuestions = userUnansweredReflectionQuestions;
        this.userUnchosenAssessmentMaterial = userUnchosenAssessmentMaterial;
    }

    public ReflectionPhaseProgress() {
    }

    public List<User> getUserUnansweredReflectionQuestions() {
        return userUnansweredReflectionQuestions;
    }

    public void setUserUnansweredReflectionQuestions(List<User> userUnansweredReflectionQuestions) {
        this.userUnansweredReflectionQuestions = userUnansweredReflectionQuestions;
    }

    public List<User> getUserUnchosenAssessmentMaterial() {
        return userUnchosenAssessmentMaterial;
    }

    public void setUserUnchosenAssessmentMaterial(List<User> userUnchosenAssessmentMaterial) {
        this.userUnchosenAssessmentMaterial = userUnchosenAssessmentMaterial;
    }
}

package unipotsdam.gf.modules.reflection.model;

import java.util.List;

public class LearningGoalRequest {

    private LearningGoalStoreItem learningGoal;
    private List<ReflectionQuestionsStoreItem> reflectionQuestions;
    private String projectName;
    private boolean endTask;


    public LearningGoalStoreItem getLearningGoal() {
        return learningGoal;
    }

    public void setLearningGoal(LearningGoalStoreItem learningGoal) {
        this.learningGoal = learningGoal;
    }

    public List<ReflectionQuestionsStoreItem> getReflectionQuestions() {
        return reflectionQuestions;
    }

    public void setReflectionQuestions(List<ReflectionQuestionsStoreItem> reflectionQuestions) {
        this.reflectionQuestions = reflectionQuestions;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public boolean isEndTask() {
        return endTask;
    }

    public void setEndTask(boolean endTask) {
        this.endTask = endTask;
    }
}

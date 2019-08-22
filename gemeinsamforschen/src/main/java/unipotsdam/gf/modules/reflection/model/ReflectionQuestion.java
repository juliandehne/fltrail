package unipotsdam.gf.modules.reflection.model;

public class ReflectionQuestion extends SelectedReflectionQuestion {

    private String fullSubmissionId;
    private String projectName;

    public ReflectionQuestion(String id, String learningGoalId, String question, String projectName, String fullSubmissionId) {
        super(id, learningGoalId, question);
        this.projectName = projectName;
        this.fullSubmissionId = fullSubmissionId;
    }

    public ReflectionQuestion(SelectedReflectionQuestion selectedReflectionQuestion, String projectName, String fullSubmissionId) {
        super(selectedReflectionQuestion);
        this.projectName = projectName;
        this.fullSubmissionId = fullSubmissionId;
    }

    public String getFullSubmissionId() {
        return fullSubmissionId;
    }

    public void setFullSubmissionId(String fullSubmissionId) {
        this.fullSubmissionId = fullSubmissionId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}

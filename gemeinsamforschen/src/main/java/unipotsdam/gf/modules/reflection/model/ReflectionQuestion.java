package unipotsdam.gf.modules.reflection.model;

public class ReflectionQuestion extends ReflectionQuestionToAnswer {

    private String question;

    public ReflectionQuestion() {
    }

    public ReflectionQuestion(String id) {
        super(id);
    }

    public ReflectionQuestion(String id, String learningGoalId, String selectedReflectionQuestionId, String fullSubmissionId, String userEmail, String projectName, String question) {
        super(id, learningGoalId, selectedReflectionQuestionId, fullSubmissionId, userEmail, projectName);
        this.question = question;
    }

    public ReflectionQuestion(ReflectionQuestionToAnswer questionToAnswer, String question) {
        super(questionToAnswer);
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}

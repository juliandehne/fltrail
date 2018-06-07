package unipotsdam.gf.modules.assessment.controller.model;

public class Quiz {
    private String type;
    private String question;
    private String[] correctAnswers;
    private String[] incorrectAnswers;

    public Quiz(String type, String question, String[] correctAnswers, String[] incorrectAnswers) {
        this.type = type;
        this.question = question;
        this.correctAnswers = correctAnswers;
        this.incorrectAnswers = incorrectAnswers;
    }

    public String[] getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(String[] incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    public String[] getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(String[] correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

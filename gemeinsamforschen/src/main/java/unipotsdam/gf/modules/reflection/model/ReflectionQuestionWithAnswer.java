package unipotsdam.gf.modules.reflection.model;

public class ReflectionQuestionWithAnswer {

    private ReflectionQuestion question;
    private ReflectionQuestionAnswer answer;

    public ReflectionQuestionWithAnswer() {
    }

    public ReflectionQuestionWithAnswer(ReflectionQuestion question, ReflectionQuestionAnswer answer) {
        this.question = question;
        this.answer = answer;
    }

    public ReflectionQuestion getQuestion() {
        return question;
    }

    public void setQuestion(ReflectionQuestion question) {
        this.question = question;
    }

    public ReflectionQuestionAnswer getAnswer() {
        return answer;
    }

    public void setAnswer(ReflectionQuestionAnswer answer) {
        this.answer = answer;
    }
}

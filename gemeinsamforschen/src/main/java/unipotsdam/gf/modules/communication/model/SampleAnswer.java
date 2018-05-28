package unipotsdam.gf.modules.communication.model;


public class SampleAnswer {

    private String answer;

    public SampleAnswer() {
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "SampleAnswer{" +
                "answer='" + answer + '\'' +
                '}';
    }
}

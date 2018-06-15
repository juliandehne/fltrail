package unipotsdam.gf.modules.assessment.controller.model;

import java.util.Arrays;

public class Performance {
    private int[] quizAnswer;
    private String feedback;
    private int[] workRating;

    public Performance(int[] quizAnswer, String feedback, int[] workRating) {
        this.quizAnswer = quizAnswer;
        this.feedback = feedback;
        this.workRating = workRating;
    }

    public Performance() {

    }

    public int[] getQuizAnswer() {
        return quizAnswer;
    }

    public void setQuizAnswer(int[] quizAnswer) {
        this.quizAnswer = quizAnswer;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public int[] getWorkRating() {
        return workRating;
    }

    public void setWorkRating(int[] workRating) {
        this.workRating = workRating;
    }

    @Override
    public String toString() {
        return "Performance{" +
                "quizAnswer=" + Arrays.toString(quizAnswer) +
                ", feedback='" + feedback + '\'' +
                ", workRating=" + Arrays.toString(workRating) +
                '}';
    }
}

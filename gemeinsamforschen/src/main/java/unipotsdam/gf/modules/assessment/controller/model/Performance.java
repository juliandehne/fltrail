package unipotsdam.gf.modules.assessment.controller.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Performance {
    private StudentIdentifier studentIdentifier;
    private int[] quizAnswer;
    private String feedback;
    private Map workRating;

    public Performance(){}

    public Performance(StudentIdentifier student, int[] quiz, String feedback, Map workRating) {
        this.studentIdentifier = student;
        this.quizAnswer = quiz;
        this.feedback=feedback;
        this.workRating=workRating;

    }

    public StudentIdentifier getStudentIdentifier() {
        return studentIdentifier;
    }

    public void setStudentIdentifier(StudentIdentifier studentIdentifier) {
        this.studentIdentifier = studentIdentifier;
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

    public Map getWorkRating() {
        return workRating;
    }

    public void setWorkRating(Map workRating) {
        this.workRating = workRating;
    }


    @Override
    public String toString() {
        return "Performance{" +
                "studentIdentifier=" + studentIdentifier +
                ", quizAnswer=" + Arrays.toString(quizAnswer) +
                ", feedback='" + feedback + '\'' +
                ", workRating=" + workRating +
                '}';
    }

}

package unipotsdam.gf.modules.assessment.controller.model;

import java.util.List;
import java.util.Map;

public class Performance {
    private StudentIdentifier studentIdentifier;
    private List<Integer> quizAnswer;
    private Map<String, Double> workRating;
    private Map<String, Double> contributionRating;

    public Performance(){}

    public Performance(StudentIdentifier student, List<Integer> quiz, Map contributionRating, Map workRating) {
        this.studentIdentifier = student;
        this.quizAnswer = quiz;
        this.workRating=workRating;
        this.contributionRating=contributionRating;

    }

    public StudentIdentifier getStudentIdentifier() {
        return studentIdentifier;
    }

    public void setStudentIdentifier(StudentIdentifier studentIdentifier) {
        this.studentIdentifier = studentIdentifier;
    }
    public Map getContributionRating() { return contributionRating;    }

    public void setContributionRating(Map contributionRating) { this.contributionRating = contributionRating;    }


    public List<Integer> getQuizAnswer() {
        return quizAnswer;
    }

    public void setQuizAnswer(List<Integer> quizAnswer) {
        this.quizAnswer = quizAnswer;
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
                ", quizAnswer=" + quizAnswer +
                ", contributionRating='" + contributionRating + '\'' +
                ", workRating=" + workRating +
                '}';
    }

}

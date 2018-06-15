package unipotsdam.gf.modules.assessment.controller.model;

import java.util.Arrays;

public class TotalPerformance {

    private StudentIdentifier[] studentIdentifier;
    private Performance[] performances;

    public TotalPerformance(StudentIdentifier[] studentIdentifier, Performance[] performances) {
        this.studentIdentifier = studentIdentifier;
        this.performances = performances;
    }

    public TotalPerformance() {
        studentIdentifier = new StudentIdentifier[3];
        performances = new Performance[3];
        studentIdentifier[0] = new StudentIdentifier("hello", "world");
        performances[0] = new Performance();
    }

    public Performance[] getPerformances() {
        return performances;
    }

    public void setPerformances(Performance[] performances) {
        this.performances = performances;
    }

    public StudentIdentifier[] getStudentIdentifier() {
        return studentIdentifier;
    }

    public void setStudentIdentifier(StudentIdentifier[] studentIdentifier) {
        this.studentIdentifier = studentIdentifier;
    }

    @Override
    public String toString() {
        return "TotalPerformance{" +
                "studentIdentifier=" + studentIdentifier +
                ", performances=" + Arrays.toString(performances) +
                '}';
    }

}

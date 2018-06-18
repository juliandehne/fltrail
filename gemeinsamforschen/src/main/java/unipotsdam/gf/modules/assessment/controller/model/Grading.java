package unipotsdam.gf.modules.assessment.controller.model;

public class Grading {
    private StudentIdentifier studentIdentifier;
    private double grade;

    public Grading(){}

    public Grading(StudentIdentifier studentIdentifier, double grade) {
        this.studentIdentifier = studentIdentifier;
        this.grade = grade;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public StudentIdentifier getStudentIdentifier() {
        return studentIdentifier;
    }

    public void setStudentIdentifier(StudentIdentifier studentIdentifier) {
        this.studentIdentifier = studentIdentifier;
    }

    @Override
    public String toString() {
        return "Grading{" +
                "studentIdentifier=" + studentIdentifier +
                ", grade=" + grade +
                '}';
    }

}

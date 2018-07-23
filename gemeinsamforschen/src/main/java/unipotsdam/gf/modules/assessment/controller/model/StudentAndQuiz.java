package unipotsdam.gf.modules.assessment.controller.model;

public class StudentAndQuiz {
    private Quiz quiz;
    private StudentIdentifier studentIdentifier;

    @Override
    public String toString() {
        return "StudentAndQuiz{" +
                "studentIdentifier=" + studentIdentifier +
                ", quiz=" + quiz +
                '}';
    }

    public StudentAndQuiz(){}

    public StudentIdentifier getStudentIdentifier() {
        return studentIdentifier;
    }

    public void setStudentIdentifier(StudentIdentifier studentIdentifier) {
        this.studentIdentifier = studentIdentifier;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public StudentAndQuiz(StudentIdentifier studentIdentifier, Quiz quiz) {
        this.studentIdentifier = studentIdentifier;
        this.quiz = quiz;
    }
}

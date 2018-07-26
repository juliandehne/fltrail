package unipotsdam.gf.modules.assessment.controller.model;


/**
 * TODO @Axel: Normally a quiz would have a student field property instead of creating a new class
 */
public class StudentAndQuiz {
    @Override
    public String toString() {
        return "StudentAndQuiz{" +
                "studentIdentifier=" + studentIdentifier +
                ", quiz=" + quiz +
                '}';
    }

    public StudentAndQuiz(){}

    private StudentIdentifier studentIdentifier;

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

    private Quiz quiz;

    public StudentAndQuiz(StudentIdentifier studentIdentifier, Quiz quiz) {
        this.studentIdentifier = studentIdentifier;
        this.quiz = quiz;
    }
}

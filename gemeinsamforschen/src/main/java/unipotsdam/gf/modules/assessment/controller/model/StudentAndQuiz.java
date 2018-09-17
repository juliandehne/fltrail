package unipotsdam.gf.modules.assessment.controller.model;


/**
 * TODO @Axel: Normally a quiz would have a student field property instead of creating a new class
 */
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

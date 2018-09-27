package unipotsdam.gf.modules.assessment.controller.model;


/**
 * TODO @Axel: Normally a quiz would have a student field property instead of creating a new class
 */
public class StudentAndQuiz {
    private Quiz quiz;
    private StudentIdentifier userNameentifier;

    @Override
    public String toString() {
        return "StudentAndQuiz{" +
                "userNameentifier=" + userNameentifier +
                ", quiz=" + quiz +
                '}';
    }

    public StudentAndQuiz(){}

    public StudentIdentifier getStudentIdentifier() {
        return userNameentifier;
    }

    public void setStudentIdentifier(StudentIdentifier userNameentifier) {
        this.userNameentifier = userNameentifier;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public StudentAndQuiz(StudentIdentifier userNameentifier, Quiz quiz) {
        this.userNameentifier = userNameentifier;
        this.quiz = quiz;
    }
}

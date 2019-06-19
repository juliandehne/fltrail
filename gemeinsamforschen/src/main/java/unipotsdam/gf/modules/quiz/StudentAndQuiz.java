package unipotsdam.gf.modules.quiz;


/**
 * TODO @Axel: Normally a quiz would have a student field property instead of creating a new class
 */
public class StudentAndQuiz {
    private Quiz quiz;
    private StudentIdentifier userIdentifier;

    @Override
    public String toString() {
        return "StudentAndQuiz{" +
                "userIdentifier=" + userIdentifier +
                ", quiz=" + quiz +
                '}';
    }

    public StudentAndQuiz(){}

    public StudentIdentifier getStudentIdentifier() {
        return userIdentifier;
    }

    public void setStudentIdentifier(StudentIdentifier userNameentifier) {
        this.userIdentifier = userNameentifier;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public StudentAndQuiz(StudentIdentifier userNameentifier, Quiz quiz) {
        this.userIdentifier = userNameentifier;
        this.quiz = quiz;
    }
}

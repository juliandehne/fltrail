package unipotsdam.gf.interfaces;

import unipotsdam.gf.modules.assessment.controller.Assessment;
import unipotsdam.gf.modules.assessment.controller.Performance;
import unipotsdam.gf.modules.assessment.controller.Quiz;
import unipotsdam.gf.modules.assessment.controller.StudentIdentifier;

/**
 * Created by dehne on 18.05.2018.
 */
public interface IPeerAssessment {

    /**
     * student and performance are written to DB
     *
     * @param assessment
     */
    void addAssessmentDataToDB(Assessment assessment);

    /**
     * will return a saved assessment from the DB
     *
     * @param student
     * @return Assessement = studentIdentifier , performance
     */
    Assessment getAssessmentDataFromDB(StudentIdentifier student);

    /**
     * writes a quiz-question into the DB so other students can benefit from another's insights.
     *
     * @param student
     * @param question
     * @param answers
     */
    void createQuiz(StudentIdentifier student, Quiz quiz);

    /**
     * calculate grades for everyone in a list.
     * either it will be overwritten by choice of co- or peer-assessment or it gets a parameter which specifies it.
     *
     * @param performanceOfAllStudents
     * @return
     */
    int[] calculateAssessment(Performance[] performanceOfAllStudents); // calculates marks for every performance and writes it to an array

    /**
     * calculates the mean value of all assessments in a project.
     *
     * @param ProjectId
     * @return
     */
    int meanOfAssessement(String ProjectId);
}

package unipotsdam.gf.interfaces;

import unipotsdam.gf.modules.assessment.controller.Assessment;
import unipotsdam.gf.modules.assessment.controller.Performance;
import unipotsdam.gf.modules.assessment.controller.StudentIdentifier;

/**
 * Created by dehne on 18.05.2018.
 */
public interface IPeerAssessment {
    void addAssessmentDataToDB(Assessment assessment); //student and performance are written to DB
    Assessment getAssessmentDataFromDB(StudentIdentifier student);
    boolean permission(int feedbackCount);
    void createQuiz(StudentIdentifier student, String question, String[] answers);     //writes a new question into the DB
    int[] calculateAssessment(Performance[] performanceOfAllStudents); // calculates marks for every performance and writes it to an array
    int meanOfAssessement(String ProjectId);
}

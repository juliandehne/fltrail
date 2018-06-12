package unipotsdam.gf.modules.assessment.controller.service;

import unipotsdam.gf.modules.assessment.controller.model.Assessment;
import unipotsdam.gf.modules.assessment.controller.model.Performance;
import unipotsdam.gf.modules.assessment.controller.model.Quiz;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;

/**
 * Created by dehne on 18.05.2018.
 */
public class FBAssessement extends AssessmentDAO {

    @Override
    public void addAssessmentDataToDB(Assessment assessment) {
        // write it to db
    }

    @Override
    public Quiz getQuiz(String projectId, String groupId) {
        return null;
    }

    @Override
    public void createQuiz(StudentIdentifier student, Quiz quiz) {

    }


    @Override
    public int[] calculateAssessment(Performance[] performanceOfAllStudents){ // calculates marks for every performance and writes it to an array
        int[] dummy = new int[4];
        dummy[0]=1;
        dummy[1]=4;
        dummy[2]=3;
        dummy[3]=2;
        return dummy;
    }

    public void createQuiz(StudentIdentifier student, String question, String[] answers){  //writes a new question into the DB

    }

    public boolean permission(int feedbackCount){
        return true;
    }

    @Override
    public int meanOfAssessement(String projectId) {
        return 0;
    }
}

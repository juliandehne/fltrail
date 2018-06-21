package unipotsdam.gf.modules.assessment.controller.service;

import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.modules.assessment.controller.model.*;

import java.util.ArrayList;
import java.util.List;

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
    public void createQuiz(StudentAndQuiz studentAndQuiz) {

    }

    @Override
    public List<Grading> calculateAssessment(ArrayList<Performance> totalPerformance) {
        return null;
    }

    @Override
    public ArrayList<Performance> getTotalAssessment(StudentIdentifier studentIdentifier) {
        return null;
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

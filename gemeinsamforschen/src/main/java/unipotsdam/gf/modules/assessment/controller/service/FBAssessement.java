package unipotsdam.gf.modules.assessment.controller.service;

import unipotsdam.gf.modules.assessment.controller.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dehne on 18.05.2018.
 */
public class FBAssessement extends AssessmentDAO {

    @Override
    public void addAssessmentDataToDB(Assessment assessment) {
        // write it to db
    }

    @Override
    public Quiz getQuiz(String projectId, String groupId, String author) {
        return null;
    }

    @Override
    public void createQuiz(StudentAndQuiz studentAndQuiz) {

    }

    @Override
    public Map<StudentIdentifier, Double> calculateAssessment(ArrayList<Performance> totalPerformance) {
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
    public int meanOfAssessment(String projectId) {
        return 0;
    }

    @Override
    public ArrayList<Quiz> getQuiz(String projectId) {
        return null;
    }

    @Override
    public void postPeerRating(ArrayList<PeerRating> peerRatings, String projectId) {

    }

    @Override
    public Integer whichGroupToRate(StudentIdentifier student) {
        return null;
    }

    @Override
    public void postContributionRating(String groupId, String fromStudent, Map<String, Integer> contributionRating) {

    }

    @Override
    public void answerQuiz(Map<String, List<String>> questions, StudentIdentifier student) {

    }

    @Override
    public void deleteQuiz(String quizId) {

    }

    @Override
    public String whatToRate(StudentIdentifier student) {
        return null;
    }

    @Override
    public Map<StudentIdentifier, Double> calculateAssessment(String projectId, String method) {
        return null;
    }
}

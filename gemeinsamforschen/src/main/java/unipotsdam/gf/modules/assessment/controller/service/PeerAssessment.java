package unipotsdam.gf.modules.assessment.controller.service;

import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.assessment.controller.model.*;

import java.util.ArrayList;
import java.util.List;

public class PeerAssessment implements IPeerAssessment {
    @Override
    public void addAssessmentDataToDB(Assessment assessment) {

    }

    @Override
    public Quiz getQuiz(String projectId, String groupId) {
        return null;
    }

    @Override
    public Assessment getAssessmentDataFromDB(StudentIdentifier student) {
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

    @Override
    public int meanOfAssessement(String ProjectId) {
        return 0;
    }

    @Override
    public ArrayList<Quiz> getQuiz(String projectId) {
        return null;
    }

    @Override
    public void postPeerRating(ArrayList<PeerRating> peerRatings, String projectId, String groupId) {

    }
}

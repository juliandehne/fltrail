package unipotsdam.gf.modules.assessment.controller.service;

import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.assessment.controller.model.*;

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
    public List<Grading> calculateAssessment(TotalPerformance totalPerformance) {

        return null;
    }

    @Override
    public TotalPerformance getTotalAssessment(StudentIdentifier studentIdentifier) {
        return null;
    }

    @Override
    public int meanOfAssessement(String ProjectId) {
        return 0;
    }
}

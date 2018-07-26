package unipotsdam.gf.modules.assessment.controller.service;

import unipotsdam.gf.core.management.ManagementImpl;
import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.assessment.QuizAnswer;
import unipotsdam.gf.modules.assessment.controller.model.*;

import java.util.ArrayList;
import java.util.List;

public class PeerAssessment implements IPeerAssessment {
    @Override
    public void addAssessmentDataToDB(Assessment assessment) {

    }

    @Override//returns one quiz
    public Quiz getQuiz(String projectId, String quizId) {
        return new ManagementImpl().getQuizByProjectGroupId(projectId,quizId);
    }

    @Override //returns all quizzes in the course
    public ArrayList<Quiz> getQuiz(String projectId) {
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
    public void postPeerRating(ArrayList<PeerRating> peerRatings, String projectId, String groupId) {

    }

    @Override
    public void answerQuiz(StudentAndQuiz studentAndQuiz, QuizAnswer quizAnswer) {

    }
}

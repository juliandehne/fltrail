package unipotsdam.gf.modules.assessment.controller.service;

import unipotsdam.gf.core.management.Management;
import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.assessment.QuizAnswer;
import unipotsdam.gf.modules.assessment.controller.model.Assessment;
import unipotsdam.gf.modules.assessment.controller.model.Grading;
import unipotsdam.gf.modules.assessment.controller.model.PeerRating;
import unipotsdam.gf.modules.assessment.controller.model.Performance;
import unipotsdam.gf.modules.assessment.controller.model.Quiz;
import unipotsdam.gf.modules.assessment.controller.model.StudentAndQuiz;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class PeerAssessment implements IPeerAssessment {

    @Inject
    private Management management;

    @Override
    public void addAssessmentDataToDB(Assessment assessment) {

    }

    @Override//returns one quiz
    public Quiz getQuiz(String projectId, String quizId) {
        return management.getQuizByProjectGroupId(projectId, quizId);
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

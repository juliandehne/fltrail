package unipotsdam.gf.modules.assessment.controller.service;

import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.assessment.controller.model.*;
import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.modules.assessment.controller.service.QuizDBCommunication;

import java.util.ArrayList;
import java.util.List;

public class PeerAssessment implements IPeerAssessment {
    @Override
    public void addAssessmentDataToDB(Assessment assessment) {

    }

    @Override//returns one quiz
    public Quiz getQuiz(String projectId, String quizId, String author) {
        return new QuizDBCommunication().getQuizByProjectQuizId(projectId, quizId, author);
    }

    @Override //returns all quizzes in the course
    public ArrayList<Quiz> getQuiz(String projectId) {
        return new QuizDBCommunication().getQuizByProjectId(projectId);
    }

    @Override
    public Assessment getAssessmentDataFromDB(StudentIdentifier student) {
        return new AssessmentDBCommunication().getAssessment(student);
    }

    @Override
    public void createQuiz(StudentAndQuiz studentAndQuiz) {
        new QuizDBCommunication().createQuiz(studentAndQuiz.getQuiz(),studentAndQuiz.getStudentIdentifier().getStudentId(), studentAndQuiz.getStudentIdentifier().getProjectId());
    }

    @Override
    public void deleteQuiz(String quizId) {
        new QuizDBCommunication().deleteQuiz(quizId);
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
    public int meanOfAssessment(String ProjectId) {
        return 0;
    }



    @Override
    public void postPeerRating(ArrayList<PeerRating> peerRatings, String projectId, String groupId) {

    }
}

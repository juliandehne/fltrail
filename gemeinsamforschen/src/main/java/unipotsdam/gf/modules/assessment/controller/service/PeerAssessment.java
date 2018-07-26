package unipotsdam.gf.modules.assessment.controller.service;

import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.assessment.QuizAnswer;
import unipotsdam.gf.modules.assessment.controller.model.*;

import java.util.ArrayList;
import java.util.Arrays;
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
        List<Grading> quizMean = meanOfQuizzes(totalPerformance);
        List<Grading> workRateMean = meanOfWorkRate(totalPerformance);
        Grading[] grading = new Grading[totalPerformance.size()];
        for (int i=0; i<quizMean.size(); i++){
            double grade = quizMean.get(i).getGrade() * workRateMean.get(i).getGrade();
            grading[i] = new Grading(totalPerformance.get(i).getStudentIdentifier(), grade);
        }

        return Arrays.asList(grading);
    }

    private List<Grading> meanOfQuizzes(ArrayList<Performance> totalPerformance){
        double[] allAssessments = new double[totalPerformance.size()];
        Grading[] grading = new Grading[totalPerformance.size()];

        for (int i = 0; i < totalPerformance.size(); i++) {
            for (int j = 0; j < totalPerformance.get(i).getQuizAnswer().length; j++) {
                allAssessments[i] += totalPerformance.get(i).getQuizAnswer()[j];
            }
            allAssessments[i] = allAssessments[i] / totalPerformance.get(i).getQuizAnswer().length;
        }
        for (int i = 0; i < totalPerformance.size(); i++) {
            Grading shuttle = new Grading(totalPerformance.get(i).getStudentIdentifier(), allAssessments[i]);
            grading[i] = shuttle;
        }
        return Arrays.asList(grading);
    }

    private List<Grading> meanOfWorkRate(ArrayList<Performance> totalPerformance){
        double[] allAssessments = new double[totalPerformance.size()];
        Grading[] grading = new Grading[totalPerformance.size()];

        for (int i = 0; i < totalPerformance.size(); i++) {
            for (int j = 0; j < totalPerformance.get(i).getWorkRating().length; j++) {
                allAssessments[i] += 6-totalPerformance.get(i).getWorkRating()[j];
            }
            allAssessments[i] = allAssessments[i] / totalPerformance.get(i).getWorkRating().length;
        }
        for (int i = 0; i < totalPerformance.size(); i++) {
            Grading shuttle = new Grading(totalPerformance.get(i).getStudentIdentifier(), allAssessments[i]);
            grading[i] = shuttle;
        }
        return Arrays.asList(grading);
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

    @Override
    public void answerQuiz(StudentAndQuiz studentAndQuiz, QuizAnswer quizAnswer) {

    }
}

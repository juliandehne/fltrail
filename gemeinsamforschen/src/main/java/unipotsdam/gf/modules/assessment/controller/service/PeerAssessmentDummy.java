package unipotsdam.gf.modules.assessment.controller.service;

import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.assessment.controller.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PeerAssessmentDummy implements IPeerAssessment {
    @Override
    public void addAssessmentDataToDB(Assessment assessment) {
    }

    @Override
    public Quiz getQuiz(String projectId, String groupId) {
        String[] correctAnswers = new String[2];
        correctAnswers[0] = "42";
        correctAnswers[1] = ""+projectId+" " + groupId;
        String[] wrongAnswers = {"a god created creature", "a sum of my mistakes"};
        if (false){ //projectId with groupId does not exist
            return null;
        }
        Quiz sampleQuiz = new Quiz("multiple","Who am I and if so, how many?", correctAnswers,wrongAnswers);
        return sampleQuiz;
    }

    @Override
    public Assessment getAssessmentDataFromDB(StudentIdentifier student) {
        int[] quizAnswer = {1,1,1,0,0,0,1,0,0,1,1};
        int[] workRating = {1,5,3,4,1,5,5};
        Performance performance = new Performance(student, quizAnswer, "what a nice guy", workRating);
        Assessment assessment = new Assessment(student, performance);
        return assessment;
    }

    @Override
    public void createQuiz(StudentAndQuiz studentAndQuiz) {
    }

    @Override
    public List<Grading> calculateAssessment(ArrayList<Performance> totalPerformance) {
        double[] allAssessements = new double[totalPerformance.size()] ;
        Grading[] grading = new Grading[totalPerformance.size()];

        for (int i=0; i< totalPerformance.size();i++) {
            for (int j=0; j< totalPerformance.get(i).getQuizAnswer().length;j++) {
                allAssessements[i] += totalPerformance.get(i).getQuizAnswer()[j];
            }
            allAssessements[i] = allAssessements[i]/totalPerformance.get(i).getQuizAnswer().length;
        }
        for (int i=0; i<totalPerformance.size(); i++){
            Grading shuttle = new Grading(totalPerformance.get(i).getStudentIdentifier(), allAssessements[i]);
            grading[i]= shuttle;
        }
        return Arrays.asList(grading);
    }

    @Override
    public ArrayList<Performance> getTotalAssessment(StudentIdentifier studentIdentifier) {
        StudentIdentifier[] students = new StudentIdentifier[2];
        StudentIdentifier student1 = new StudentIdentifier("gemeinsamForschen","Haralf");
        StudentIdentifier student2 = new StudentIdentifier("gemeinsamForschen","Regine");
        ArrayList<Performance> performances = new ArrayList<Performance>();
        int[] quiz = {1,0,1,0,0,0,1};
        int[] quiz2 = {0,1,0,1,1,1,0};
        int[] work = {5,4,3,2,1};
        int[] work2 = {1,2,3,4,5};
        Performance performance = new Performance(student1, quiz, "toller dude",work);
        performances.add(performance);
        performance = new Performance(student2, quiz2, "tolle dudine",work2);
        performances.add(performance);
        return performances;
    }

    @Override
    public int meanOfAssessement(String ProjectId) {
        return 0;
    }
}

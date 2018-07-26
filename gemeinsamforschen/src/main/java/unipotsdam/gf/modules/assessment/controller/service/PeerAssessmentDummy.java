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
    public Quiz getQuiz(String projectId, String quizId, String author) {
        ArrayList<String> correctAnswers = new ArrayList<String>();
        ArrayList<String> incorrectAnswers = new ArrayList<String>();
        Quiz sampleQuiz;
        if (quizId.equals("2")) {
            correctAnswers.add("42");
            correctAnswers.add("" + projectId + " " + quizId);

            incorrectAnswers.add("a god created creature");
            incorrectAnswers.add( "a sum of my mistakes");
            sampleQuiz = new Quiz("multiple", "Who am I and if so, how many?", correctAnswers, incorrectAnswers);
        } else {
            correctAnswers.add("ja, nicht?!");
            correctAnswers.add("nee, oder doch?!");
            incorrectAnswers.add("Mephistopheles");
            incorrectAnswers.add("Der alte Hexenmeister!?");
            incorrectAnswers.add("Der Schimmelreiter");
            incorrectAnswers.add("alle beide");
            sampleQuiz = new Quiz("multiple", "Ist das nun des Pudels wahrer Kern?", correctAnswers, incorrectAnswers);
        }

        return sampleQuiz;
    }

    public ArrayList<Quiz> getQuiz(String projectId) {
        ArrayList<String> correctAnswers = new ArrayList<String>();
        ArrayList<String> incorrectAnswers = new ArrayList<String>();
        ArrayList<Quiz> sampleQuiz = new ArrayList<Quiz>();
        correctAnswers.add("42");
        correctAnswers.add("" + projectId + " 24");
        incorrectAnswers.add("a god created creature");
        incorrectAnswers.add( "a sum of my mistakes");
        sampleQuiz.add(new Quiz("multiple", "Who am I and if so, how many?", correctAnswers, incorrectAnswers));
        correctAnswers.clear();
        incorrectAnswers.clear();
        correctAnswers.add("ja, nicht?!");
        correctAnswers.add("nee, oder doch?!");
        incorrectAnswers.add("Mephistopheles");
        incorrectAnswers.add("Der alte Hexenmeister!?");
        incorrectAnswers.add("Der Schimmelreiter");
        incorrectAnswers.add("alle beide");
        sampleQuiz.add(new Quiz("multiple", "Ist das nun des Pudels wahrer Kern?", correctAnswers, incorrectAnswers));

        return sampleQuiz;
}

    @Override
    public void postPeerRating(ArrayList<PeerRating> peerRatings, String projectId, String groupId) {
        int breakpoint = 0; //todo: print an http-answer for the ajax-request to receive
    }

    @Override
    public void deleteQuiz(String quizId) {

    }

    @Override
    public Assessment getAssessmentDataFromDB(StudentIdentifier student) {
        int[] quizAnswer = {1, 1, 1, 0, 0, 0, 1, 0, 0, 1, 1};
        int[] workRating = {1, 5, 3, 4, 1, 5, 5};
        Performance performance = new Performance(student, quizAnswer, "what a nice guy", workRating);
        Assessment assessment = new Assessment(student, performance);
        return assessment;
    }

    @Override
    public void createQuiz(StudentAndQuiz studentAndQuiz) {
    }

    @Override
    public List<Grading> calculateAssessment(ArrayList<Performance> totalPerformance) {
        double[] allAssessments = new double[totalPerformance.size()];
        Grading[] grading = new Grading[totalPerformance.size()];

        for (int i = 0; i < totalPerformance.size(); i++) {
            for (int j = totalPerformance.get(i).getQuizAnswer().length; j > 0; j--) {
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

    @Override
    public ArrayList<Performance> getTotalAssessment(StudentIdentifier studentIdentifier) {
        StudentIdentifier[] students = new StudentIdentifier[2];
        StudentIdentifier student1 = new StudentIdentifier("gemeinsamForschen", "Haralf");
        StudentIdentifier student2 = new StudentIdentifier("gemeinsamForschen", "Regine");
        ArrayList<Performance> performances = new ArrayList<Performance>();
        int[] quiz = {1, 0, 1, 0, 0, 0, 1};
        int[] quiz2 = {0, 1, 0, 1, 1, 1, 0};
        int[] work = {5, 4, 3, 2, 1};
        int[] work2 = {1, 2, 3, 4, 5};
        Performance performance = new Performance(student1, quiz, "toller dude", work);
        performances.add(performance);
        performance = new Performance(student2, quiz2, "tolle dudine", work2);
        performances.add(performance);
        return performances;
    }

    @Override
    public int meanOfAssessment(String ProjectId) {
        return 0;
    }
}

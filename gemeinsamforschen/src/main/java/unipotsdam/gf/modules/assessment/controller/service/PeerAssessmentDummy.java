package unipotsdam.gf.modules.assessment.controller.service;

import unipotsdam.gf.assignments.Assignee;
import unipotsdam.gf.assignments.NotImplementedLogger;
import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.assessment.QuizAnswer;
import unipotsdam.gf.modules.assessment.controller.model.*;

import java.util.*;

public class PeerAssessmentDummy implements IPeerAssessment {

    @Override
    public void addAssessmentDataToDB(Assessment assessment) {
    }

    @Override
    public Quiz getQuiz(String projectId, String quizId, String author) {
        ArrayList<String> correctAnswers = new ArrayList<>();
        ArrayList<String> incorrectAnswers = new ArrayList<>();
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
        ArrayList<String> correctAnswers = new ArrayList<>();
        ArrayList<String> incorrectAnswers = new ArrayList<>();
        ArrayList<Quiz> sampleQuiz = new ArrayList<>();
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
    public void postPeerRating(ArrayList<PeerRating> peerRatings, String projectId) {
        int breakpoint = 0; //todo: print an http-answer for the ajax-request to receive
    }

    @Override
    public void postContributionRating(StudentIdentifier student, String fromStudent, Map<String, Integer> contributionRating) {

    }

    @Override
    public void answerQuiz(Map<String, List<String>> questions, StudentIdentifier student) {
        NotImplementedLogger.logAssignment(Assignee.AXEL, IPeerAssessment.class);
    }

    @Override
    public void deleteQuiz(String quizId) {

    }

    @Override
    public Map<StudentIdentifier, Double> calculateAssessment(String projectId, String method) {
        return null;
    }

    @Override
    public Assessment getAssessmentDataFromDB(StudentIdentifier student) {
        List<Integer> quizAnswer = new ArrayList<>();
        quizAnswer.add(0);
        quizAnswer.add(1);
        quizAnswer.add(1);
        quizAnswer.add(1);
        quizAnswer.add(0);
        quizAnswer.add(0);
        Map workRating = new HashMap<>();
        Map contributionRating = new HashMap<>();
        Performance performance = new Performance(student, quizAnswer, contributionRating, workRating);
        return new Assessment(student, performance);
    }

    @Override
    public void createQuiz(StudentAndQuiz studentAndQuiz) {
        NotImplementedLogger.logAssignment(Assignee.AXEL, PeerAssessmentDummy.class);
    }

    @Override
    public Map<StudentIdentifier, Double> calculateAssessment(ArrayList<Performance> totalPerformance) {
        return null;
    }

    @Override
    public ArrayList<Performance> getTotalAssessment(StudentIdentifier studentIdentifier) {
        StudentIdentifier student1 = new StudentIdentifier("gemeinsamForschen", "Haralf");
        StudentIdentifier student2 = new StudentIdentifier("gemeinsamForschen", "Regine");
        ArrayList<Performance> performances = new ArrayList<>();
        List<Integer> quiz = new ArrayList<>();
        quiz.add(0);
        quiz.add(1);
        quiz.add(1);
        quiz.add(1);
        quiz.add(0);
        quiz.add(0);
        List<Integer> quiz2 = new ArrayList<>();
        quiz2.add(0);
        quiz2.add(1);
        quiz2.add(1);
        quiz2.add(1);
        quiz2.add(0);
        quiz2.add(0);
        Map<String, Double> work = new HashMap<>();
        work.put("responsibility", 1.);
        work.put("partOfWork", 1.);
        work.put("cooperation", 1.);
        work.put("communication", 1.);
        work.put("autonomous", 1.);
        Map<String, Double> work2 = new HashMap<>();
        work2.put("responsibility", 3.);
        work2.put("partOfWork", 4.);
        work2.put("cooperation", 5.);
        work2.put("communication", 3.);
        work2.put("autonomous", 4.);
        Map<String, Double> contribution1 = new HashMap<>();
        contribution1.put("Dossier", 4.);
        contribution1.put("eJournal", 2.);
        contribution1.put("research", 4.);
        Map<String, Double> contribution2 = new HashMap<>();
        contribution2.put("Dossier", 2.);
        contribution2.put("eJournal", 3.);
        contribution2.put("research", 4.);
        Performance performance = new Performance(student1, quiz, contribution1, work);
        performances.add(performance);
        Performance performance2 = new Performance(student2, quiz2, contribution2, work2);
        performances.add(performance2);
        return performances;
    }

    @Override
    public int meanOfAssessment(String ProjectId) {
        return 0;
    }
}

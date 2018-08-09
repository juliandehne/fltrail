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
    public void answerQuiz(StudentAndQuiz studentAndQuiz, QuizAnswer quizAnswer) {
        NotImplementedLogger.logAssignment(Assignee.AXEL, IPeerAssessment.class);
    }

    @Override
    public void deleteQuiz(String quizId) {

    }

    @Override
    public Map<String, Double> calculateAssessment(String projectId, String method) {
        return null;
    }

    @Override
    public Assessment getAssessmentDataFromDB(StudentIdentifier student) {
        int[] quizAnswer = {1, 1, 1, 0, 0, 0, 1, 0, 0, 1, 1};
        Map workRating = new HashMap<>();
        Performance performance = new Performance(student, quizAnswer, "what a nice guy", workRating);
        Assessment assessment = new Assessment(student, performance);
        return assessment;
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
        ArrayList<Performance> performances = new ArrayList<Performance>();
        int[] quiz = {1, 0, 1, 0, 0, 0, 1};
        int[] quiz2 = {0, 1, 0, 1, 1, 1, 0};
        Map work = new HashMap();
        work.put("responsibility", 1);
        work.put("partOfWork", 1);
        work.put("cooperation", 1);
        work.put("communication", 1);
        work.put("autonomous", 1);
        Map work2 = new HashMap();
        work2.put("responsibility", 3);
        work2.put("partOfWork", 4);
        work2.put("cooperation", 5);
        work2.put("communication", 3);
        work2.put("autonomous", 4);

        Performance performance = new Performance(student1, quiz, "toller dude", work);
        performances.add(performance);
        Performance performance2 = new Performance(student2, quiz2, "passt schon", work2);
        performances.add(performance2);
        return performances;
    }

    @Override
    public int meanOfAssessment(String ProjectId) {
        return 0;
    }
}

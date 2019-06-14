package unipotsdam.gf.modules.assessment;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.Before;
import org.junit.Test;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;
import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.assessment.controller.model.GroupEvalDataDatasets;
import unipotsdam.gf.modules.assessment.controller.model.GroupEvalDataList;
import unipotsdam.gf.modules.assessment.controller.model.Quiz;
import unipotsdam.gf.modules.assessment.controller.model.StudentAndQuiz;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.process.PeerAssessmentProcess;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;


public class TestAddAssessment {

    @Inject
    private IGroupFinding groupFinding;

    @Inject
    private PeerAssessmentProcess peerAssessmentProcess;

    @Inject
    private IPeerAssessment peer;
    private String userName = "Kevin";
    private String projectName = "test a la test";
    private String quizId = "Whats a good Test?";

    @Inject
    MysqlConnect connect;

    @Before
    public void setUp() {
        //final ServiceLocator locator = ServiceLocatorUtilities.bind(new TestGFApplicationBinder());
        final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());
        locator.inject(this);

    }

    @Test
    public void createQuiz() {
        StudentAndQuiz studentAndQuiz = new StudentAndQuiz();
        StudentIdentifier student = new StudentIdentifier(projectName, userName);
        studentAndQuiz.setStudentIdentifier(student);
        Quiz quiz = new Quiz();
        ArrayList<String> correctAnswers = new ArrayList<>();
        correctAnswers.add("1");
        correctAnswers.add("2");
        correctAnswers.add("3");
        ArrayList<String> incorrectAnswers = new ArrayList<>();
        incorrectAnswers.add("4");
        incorrectAnswers.add("5");
        incorrectAnswers.add("6");
        quiz.setQuestion(quizId);
        quiz.setIncorrectAnswers(incorrectAnswers);
        quiz.setType("mc");
        quiz.setCorrectAnswers(correctAnswers);
        studentAndQuiz.setQuiz(quiz);
        peer.createQuiz(studentAndQuiz);
    }

    @Test
    public void getAllQuizzesInProject() {
        peer.getQuiz(projectName);
    }

    @Test
    public void getQuiz() {
        peer.getQuiz(projectName, quizId, userName);
    }

    @Test
    public void answerQuiz() {
        Map<String, List<String>> questions = new HashMap<>();
        StudentIdentifier student = new StudentIdentifier(projectName, userName);
        List<String> answers = new ArrayList<>();
        answers.add("1");
        answers.add("2");
        answers.add("3");
        questions.put(quizId, answers);
        peer.answerQuiz(questions, student);
    }

    @Test
    public void deleteQuiz() {
        peer.deleteQuiz(quizId);
    }

    @Test
    public void addTestAssessment() {
        int [] quizAnswers = new int[5];
        quizAnswers[0] = 0;
        quizAnswers[1] = 1;
        quizAnswers[2] = 0;
        quizAnswers[3] = 1;
        quizAnswers[4] = 1;
        int [] workRating = new int[3];
        workRating[0] = 5;      //Führungsqualität
        workRating[1] = 1;      //Pünktlichkeit
        workRating[2] = 4;      //Hilfsbereitschaft oder so

        StudentIdentifier student = new StudentIdentifier("Spaß", "Haralf");
        //Performance performance = new Performance(student, quizAnswers,"so ein toller Typ", workRating);
        //Assessment assessment = new Assessment(student, performance);
        //iPeerAssessment.addAssessmentDataToDB(assessment);
    }

    @Test
    public void meanOfAssessments() {
        double Ergebnis = 0.0;
        double zwischenErgebnis = 0.0;
        double counter = 0.0;
        List<Double> results = new ArrayList<>();


        connect.connect();
        String mysqlRequest = "SELECT * FROM `assessments` WHERE `empfaengerId`=? AND `projektId`=?";
        String test = "fgnxn";
        String test2 = "projekt";
        VereinfachtesResultSet ausgabe = connect.issueSelectStatement(mysqlRequest, test, test2);
        while (ausgabe.next()) {
            counter++;
            zwischenErgebnis = zwischenErgebnis + ausgabe.getInt("bewertung");
            results.add((double) ausgabe.getInt("bewertung"));
        }
        results.add(zwischenErgebnis / counter);
        System.out.println(results);
        //Integer bewertung = ausgabe.getInt("bewertung");
        connect.close();
    }

    @Test
    public void groupDatafromDB() {

        List<String> userNamen = new ArrayList<>();
        GroupEvalDataDatasets datenSaetze = new GroupEvalDataDatasets();
        GroupEvalDataList datenDia = new GroupEvalDataList();
        connect.connect();

        String mysqlRequestGroupuser = "SELECT * FROM `groupuser` WHERE `groupId`=? ";

        VereinfachtesResultSet namenDerUser = connect.issueSelectStatement(mysqlRequestGroupuser, 3);
        int[] bewertungenZwischen = new int[10];
        while (namenDerUser.next()) {
            userNamen.add(namenDerUser.getString("userEmail"));
        }
        for (int i = 0; i < userNamen.size(); i++) {
            String mysqlRequestAssessment = "SELECT * FROM `assessments` WHERE `empfaengerId`=?";
            VereinfachtesResultSet bewertungDerUser = connect.issueSelectStatement(mysqlRequestAssessment, userNamen.get(i));

            while (bewertungDerUser.next()) {
                //bewertungenZwischen.add(bewertungDerUser.getInt("bewertung"));
                System.out.println("Hass");
            }
            datenSaetze.setData(bewertungenZwischen);
            //datenSaetze.setLabel(userNamen.get(i));
            datenDia.appendDataSet(datenSaetze);
            System.out.println(datenSaetze.getData());
            System.out.println(datenSaetze.getLabel());

        }
        connect.close();
    }

    @Test
    public void quickstartAssessmentPhase() {

        Project project = new Project("assessmenttest2");
        List<Group> groups = groupFinding.getGroups(project);
        assertFalse(groups.isEmpty());

        //peerAssessmentProcess.startPeerAssessmentPhase(project);

        peerAssessmentProcess.startGrading(project);


    }




}

package unipotsdam.gf.modules.assessment;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.Before;
import org.junit.Test;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.quiz.StudentIdentifier;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.quiz.Quiz;
import unipotsdam.gf.modules.quiz.StudentAndQuiz;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;
import unipotsdam.gf.process.PeerAssessmentProcess;
import unipotsdam.gf.process.tasks.TaskDAO;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;


public class TestAddAssessment {

    @Inject
    private IGroupFinding groupFinding;

    @Inject
    private TaskDAO taskDAO;

    @Inject
    private PeerAssessmentProcess peerAssessmentProcess;

    @Inject
    private IPeerAssessment peer;
    private String userName = "Kevin";
    private String projectName = "test a la test";
    private String quizId = "Whats a good Test?";

    @Inject
    private MysqlConnect connect;

    @Before
    public void setUp() {
        //final ServiceLocator locator = ServiceLocatorUtilities.bind(new TestGFApplicationBinder());
        final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());
        locator.inject(this);

    }

    @Test
    public void addTestAssessment() {
        int[] quizAnswers = new int[5];
        quizAnswers[0] = 0;
        quizAnswers[1] = 1;
        quizAnswers[2] = 0;
        quizAnswers[3] = 1;
        quizAnswers[4] = 1;
        int[] workRating = new int[3];
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
    public void quickstartAssessmentPhase() {
        Project project = new Project("test7");
        List<Group> groups = groupFinding.getGroups(project);
        assertFalse(groups.isEmpty());

        peerAssessmentProcess.startPeerAssessmentPhase(project);
        //peerAssessmentProcess.startGrading(project);
    }

    @Test
    public void quickstartGradingPhase() {
        Project project = new Project("Meine Güte");
        List<Group> groups = groupFinding.getGroups(project);
        assertFalse(groups.isEmpty());
        peerAssessmentProcess.startGrading(project);
    }

    @Test
    public void quickstartDocentGradingPhase() {
        Project project = new Project("assessmenttest3");
        List<Group> groups = groupFinding.getGroups(project);
        assertFalse(groups.isEmpty());

        //peerAssessmentProcess.startDocentGrading(project);
        peerAssessmentProcess.startGrading(project);

        //taskDAO.updateTeacherTask(project, TaskName.GIVE_EXTERNAL_ASSESSMENT_TEACHER, Progress.FINISHED);
        //taskDAO.persistTeacherTask(project, TaskName.GIVE_FINAL_GRADES, Phase.GRADING);
    }


}

package unipotsdam.gf.interfaces;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.core.database.TestGFApplicationBinder;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectConfiguration;
import unipotsdam.gf.process.constraints.ConstraintsImpl;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.Task;
import unipotsdam.gf.process.tasks.TaskDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.group.GroupfindingCriteria;
import unipotsdam.gf.modules.journal.model.Journal;
import unipotsdam.gf.modules.annotation.model.Category;
import unipotsdam.gf.modules.researchreport.ResearchReport;
import unipotsdam.gf.modules.researchreport.ResearchReportManagement;

import javax.inject.Inject;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class ActivityFlowTest {

    /**
     * Utility to creaty dummy data for students
     */
    PodamFactory factory = new PodamFactoryImpl();

    @Inject
    Management management;

    @Inject
    ResearchReportManagement researchReportManagement;

    @Inject
    IPhases phases;

    @Inject
    IGroupFinding groupFinding;

    @Inject
    ICommunication iCommunication;

    @Inject
    IJournal iJournal;

    @Inject
    TaskDAO taskDAO;

    @Inject
    private ConstraintsImpl constraints;

    @Inject
    IPeerAssessment iPeerAssessment;


    private final Project project = factory.manufacturePojo(Project.class);
    private final ArrayList<User> students = new ArrayList<>();
    private final User teacher = factory.manufacturePojo(User.class);

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();



    @Before
    public void setUp() {
        final ServiceLocator locator = ServiceLocatorUtilities.bind(new TestGFApplicationBinder());
        locator.inject(this);
        researchReportManagement = Mockito.spy(researchReportManagement);
        phases = Mockito.spy(phases);
        iCommunication = Mockito.spy(iCommunication);

        // TODO @Julian: Find out more elegant way of doing this
//        phases.setFeedback(feedback);

    }

    @Test
    public void activityPlayer() throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        // register teacher
        loginTeacher();

        // create course
        createCourse();

        // register students
        loginStudents();

        // form groups
        formGroups();

        // end first phase
        phases.endPhase(Phase.GroupFormation, project);

        // upload dossiers
        uploadDossiers();

        // end first phase
        phases.endPhase(Phase.DossierFeedback, project);

        // updateForUser reflections
        uploadReflections();

        // end execution phase
        phases.endPhase(Phase.Execution, project);
    }



    public void formGroups() {

        // form groups based on user profil
        groupFinding.formGroups(GroupFormationMechanism.UserProfilStrategy);

        // updateForUser groups manually
        groupFinding.formGroups(GroupFormationMechanism.Manual);

    }


    public void loginTeacher() {
        teacher.setStudent(false);
        management.create(teacher);
    }


    public void loginStudents() {
        for (int i=0;i<100;i++) {
            User student = factory.manufacturePojo(User.class);
            student.setStudent(true);
            students.add(student);
            management.create(student);
        }
    }

    public void uploadReflections() {
        // updateForUser single reflection
        Journal journalEntry = factory.manufacturePojo(Journal.class);

        for (User student : students) {
            iJournal.uploadJournalEntry(journalEntry, student);
        }


        // create quiz TODO@Axel this should be a quiz dependend on the student for easier initialization and
        // de-coupling
        //StudentAndQuiz studentAndQuiz = factory.manufacturePojo(StudentAndQuiz.class);
        //QuizAnswer quizAnswer = factory.manufacturePojo(QuizAnswer.class);
        //iPeerAssessment.createQuiz(studentAndQuiz);
        //iPeerAssessment.answerQuiz(studentAndQuiz, quizAnswer);

        // finales Portfolio zusammenstellen
        java.util.List<Journal> journalEntries = new ArrayList<Journal>();
        journalEntries.add(journalEntry);

        ResearchReport finalResearchReport = factory.manufacturePojo(ResearchReport.class);
        File presentation = new File("dummy.pptx");

        for (User student : students) {
            iJournal.uploadFinalPortfolio(project,journalEntries, finalResearchReport, presentation, student);
        }
        assertNotNull(true);

    }

    public void uploadDossiers() throws RocketChatDownException, UserDoesNotExistInRocketChatException {


        for (User student : students) {
            // persist dossiers
            ResearchReport researchReport = factory.manufacturePojo(ResearchReport.class);
            researchReportManagement.createResearchReport(researchReport, project, student);
        }

        // students give feedback


        // students upload updated dossier
        ArrayList<User> students2 = students;
        students2.remove(2);
        Iterator<User> students2Iterator = students2.iterator();
        while (students2Iterator.hasNext()) {
            User student = students2Iterator.next();
            // persist final dossiers -- assuming this function is intended
            // if only one time upload is intended and feedback is not incorporated into a final dossier
            // you should change this test to reflect only one time upload
            // i.e. removing one student above to reflect no compliance
            ResearchReport researchReport = factory.manufacturePojo(ResearchReport.class);
            researchReportManagement.createFinalResearchReport(researchReport, project, student);
        }

        // docent finishes phase
        phases.endPhase(Phase.DossierFeedback, project);

        // student misses mockfeedback -> reassignment
        // assert that while reports are still missing mockfeedback tasks are reassigned

        // assert that everybody has given and received mockfeedback
        assertTrue(constraints.checkIfFeedbackCanBeDistributed(project));

        // docent finishes phase
        phases.endPhase(Phase.DossierFeedback, project);

    }


    public void createCourse() {
        // add Titel
        Project project = factory.manufacturePojo(Project.class);
        project.setAuthorEmail(teacher.getEmail());
        management.create(project);

        ProjectConfiguration projectConfiguration = factory.manufacturePojo(ProjectConfiguration.class);
        management.create(projectConfiguration, project);

        GroupfindingCriteria groupfindingCriteria = factory.manufacturePojo(GroupfindingCriteria.class);
        groupFinding.selectGroupfindingCriteria(groupfindingCriteria, project);

        taskDAO.createTaskWaitForParticipants(project, teacher);
        ArrayList<Task> tasks = taskDAO.getTasks(teacher, project);
        assertTrue(tasks != null && tasks.size() > 0);


    }

}

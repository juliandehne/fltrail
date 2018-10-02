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
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectConfiguration;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.states.ProjectPhase;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.group.GroupfindingCriteria;
import unipotsdam.gf.modules.journal.model.Journal;
import unipotsdam.gf.modules.peer2peerfeedback.Category;
import unipotsdam.gf.modules.peer2peerfeedback.peerfeedback.Model.Peer2PeerFeedback;
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
    Feedback feedback;

    @Inject
    IPhases phases;

    @Inject
    IGroupFinding groupFinding;

    @Inject
    ICommunication iCommunication;

    @Inject
    IJournal iJournal;

    @Inject
    IPeerAssessment iPeerAssessment;


    private final Project project = factory.manufacturePojo(Project.class);
    private final ArrayList<User> students = new ArrayList<>();
    private final User teacher = factory.manufacturePojo(User.class);

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();



    @Before
    public void setUp() {
        final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());
        locator.inject(this);

        feedback = Mockito.spy(feedback);
        researchReportManagement = Mockito.spy(researchReportManagement);
        phases = Mockito.spy(phases);
        iCommunication = Mockito.spy(iCommunication);

        // TODO @Julian: Find out more elegant way of doing this
        researchReportManagement.setFeedback(feedback);
        phases.setFeedback(feedback);

    }

    @Test
    public void activityPlayer() {
        // register teacher
        loginTeacher();

        // create course
        createCourse();

        // register students
        loginStudents();

        // form groups
        formGroups();

        // end first phase
        phases.endPhase(ProjectPhase.CourseCreation, project);

        // upload dossiers
        uploadDossiers();

        // end first phase
        phases.endPhase(ProjectPhase.DossierFeedback, project);

        // update reflections
        uploadReflections();

        // end execution phase
        phases.endPhase(ProjectPhase.Execution, project);
    }



    public void formGroups() {

        // form groups based on user profil
        groupFinding.formGroups(GroupFormationMechanism.UserProfilStrategy);

        // update groups manually
        groupFinding.formGroups(GroupFormationMechanism.Manual);

    }


    public void loginTeacher() {
        teacher.setStudent(false);
        management.create(teacher, null);
    }


    public void loginStudents() {
        for (int i=0;i<100;i++) {
            User student = factory.manufacturePojo(User.class);
            student.setStudent(true);
            students.add(student);
            management.create(student, null);
        }
    }

    public void uploadReflections() {
        // update single reflection
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

    public void uploadDossiers() {


        for (User student : students) {
            // persist dossiers
            ResearchReport researchReport = factory.manufacturePojo(ResearchReport.class);
            researchReportManagement.createResearchReport(researchReport, project, student);
        }


        // assert that after the last report has been submitted, the feedback tasks were assigned automatically
        verify(feedback).assignFeedbackTasks();

        // students give feedback
        for (User student : students) {
            ResearchReport feedbackTask = feedback.getFeedbackTask(student);
            ProjectConfiguration projectConfiguration = management.getProjectConfiguration(project);
            HashMap<Category, Boolean> criteriaSelected = projectConfiguration.getCriteriaSelected();
            for (Category category : criteriaSelected.keySet()) {
                if (criteriaSelected.get(category)) {
                    Peer2PeerFeedback peer2PeerFeedback = factory.manufacturePojo(Peer2PeerFeedback.class);
                    peer2PeerFeedback.setFeedbackcategory(category);
                    feedback.giveFeedback(peer2PeerFeedback, feedbackTask);
                }
            }
        }

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
        phases.endPhase(ProjectPhase.DossierFeedback, project);

        // student misses mockfeedback -> reassignment
        // assert that while reports are still missing mockfeedback tasks are reassigned
        verify(feedback).assigningMissingFeedbackTasks(project);

        // assert that everybody has given and received mockfeedback
        assertEquals(0, feedback.checkFeedbackConstraints(project).size());

        // docent finishes phase
        phases.endPhase(ProjectPhase.DossierFeedback, project);

    }


    public void createCourse() {
        // add Titel
        Project project = factory.manufacturePojo(Project.class);
        management.create(project);

        ProjectConfiguration projectConfiguration = factory.manufacturePojo(ProjectConfiguration.class);
        management.create(projectConfiguration, project);

        GroupfindingCriteria groupfindingCriteria = factory.manufacturePojo(GroupfindingCriteria.class);
        groupFinding.selectGroupfindingCriteria(groupfindingCriteria);

    }

}

package unipotsdam.gf.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import unipotsdam.gf.exceptions.WrongNumberOfParticipantsException;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.group.GroupfindingCriteria;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectConfiguration;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.constraints.ConstraintsImpl;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.Task;
import unipotsdam.gf.process.tasks.TaskDAO;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.assertTrue;


@RunWith(MockitoJUnitRunner.class)
public class ActivityFlowTest {
    /**
     * Utility to creaty dummy data for students
     */

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    PodamFactory factory = new PodamFactoryImpl();
    private final ArrayList<User> students = new ArrayList<>();
    private final Project project = factory.manufacturePojo(Project.class);
    private final User teacher = factory.manufacturePojo(User.class);
    @Inject
    Management management;

    @Inject
    IPhases phases;
    @Inject
    IGroupFinding groupFinding;
    @Inject
    ICommunication iCommunication;
    @Inject
    TaskDAO taskDAO;
    @Inject
    IPeerAssessment iPeerAssessment;
    @Inject
    private ConstraintsImpl constraints;

    @Before
    public void setUp() {
        final ServiceLocator locator = ServiceLocatorUtilities.bind(new TestGFApplicationBinder());
        locator.inject(this);
        phases = Mockito.spy(phases);
        iCommunication = Mockito.spy(iCommunication);

        // TODO @Julian: Find out more elegant way of doing this
//        phases.setFeedback(feedback);

    }

    @Test
    public void activityPlayer()
            throws RocketChatDownException, UserDoesNotExistInRocketChatException, WrongNumberOfParticipantsException, JAXBException, JsonProcessingException {
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
        for (int i = 0; i < 100; i++) {
            User student = factory.manufacturePojo(User.class);
            student.setStudent(true);
            students.add(student);
            management.create(student);
        }
    }

    public void uploadDossiers()
            throws RocketChatDownException, UserDoesNotExistInRocketChatException, WrongNumberOfParticipantsException, JAXBException, JsonProcessingException {

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

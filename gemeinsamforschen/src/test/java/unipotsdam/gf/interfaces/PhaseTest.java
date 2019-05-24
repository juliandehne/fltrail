package unipotsdam.gf.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.exceptions.WrongNumberOfParticipantsException;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserProfile;
import unipotsdam.gf.process.phases.Phase;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;

public class PhaseTest {

    @Inject
    private IPhases phases;

    /**
     * Utility to creaty dummy data for students
     */
    static PodamFactory factory = new PodamFactoryImpl();

    @Inject
    Management management;


    public static String projectName = "Gemainsam Forschen";

    @Before
    public void setUp() {
        final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());
        locator.inject(this);


        User user = factory.manufacturePojo(User.class);
        user.setStudent(true);
        management.create(user);
        assert management.exists(user);

        Project project = factory.manufacturePojo(Project.class);
        project.setName(projectName);
        management.create(project);
        management.register(user, project, null);
    }



    @Test
    public void phase2() throws RocketChatDownException, UserDoesNotExistInRocketChatException, WrongNumberOfParticipantsException, JAXBException, JsonProcessingException {
        Project project = new Project();
        project.setName(projectName);
        phases.endPhase(Phase.GroupFormation, project);
    }

    @Test
    public void phase3() throws RocketChatDownException, UserDoesNotExistInRocketChatException, WrongNumberOfParticipantsException, JAXBException, JsonProcessingException {
        Project project = new Project();
        project.setName(projectName);
        phases.endPhase(Phase.DossierFeedback, project);
    }

    @Test
    public void phase4() throws RocketChatDownException, UserDoesNotExistInRocketChatException, WrongNumberOfParticipantsException, JAXBException, JsonProcessingException {
        Project project = new Project();
        project.setName(projectName);
        phases.endPhase(Phase.Execution, project);
    }

    @Test
    public void phase5() throws RocketChatDownException, UserDoesNotExistInRocketChatException, WrongNumberOfParticipantsException, JAXBException, JsonProcessingException {
        Project project = new Project();
        project.setName(projectName);
        phases.endPhase(Phase.Assessment, project);
    }
}

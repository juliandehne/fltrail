package unipotsdam.gf.modules.groupfinding.dummy.service;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.core.database.InMemoryMySqlConnect;
import unipotsdam.gf.core.database.TestGFApplicationBinder;
import unipotsdam.gf.core.management.Management;
import unipotsdam.gf.core.management.group.Group;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.project.ProjectDAO;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.user.UserDAO;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.modules.communication.service.CommunicationDummyService;
import unipotsdam.gf.core.management.group.GroupDAO;

import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class DummyProjectCreationServiceTest {

    static PodamFactory factory = new PodamFactoryImpl();


    @Inject
    private Management management;

    @Before
    public void setUp() {
        final ServiceLocator locator = ServiceLocatorUtilities.bind(new TestGFApplicationBinder());
        //final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());
        locator.inject(this);

    }


    @Test
    public void testCreateExampleProject() {
        ICommunication communication = new CommunicationDummyService();
        InMemoryMySqlConnect inMemoryMySqlConnect = new InMemoryMySqlConnect();
        GroupDAO groupDAO = new GroupDAO(inMemoryMySqlConnect);
        UserDAO userDAO = new UserDAO(inMemoryMySqlConnect);

        DummyProjectCreationService dummyProjectCreationService = new DummyProjectCreationService(communication, management, groupDAO, userDAO);

        dummyProjectCreationService.createExampleProject();

        User docentUser = dummyProjectCreationService.getDocentUser();
        assertTrue(userDAO.exists(docentUser));

        ProjectDAO projectDAO = new ProjectDAO(inMemoryMySqlConnect);
        Project project = factory.manufacturePojo(Project.class);
        assertTrue(projectDAO.exists(project));

        List<Group> dummyGroups = dummyProjectCreationService.createDummyGroups(project.getName());
        dummyGroups.forEach(group -> group.setChatRoomId("1"));
        dummyGroups.forEach(group -> {
            assertTrue(groupDAO.exists(group));
            group.getMembers().forEach(user -> assertTrue(userDAO.exists(user)));
        });
    }
}

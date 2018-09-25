package unipotsdam.gf.modules.groupfinding.dummy.service;

import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.core.database.InMemoryMySqlConnect;
import unipotsdam.gf.core.management.Management;
import unipotsdam.gf.core.management.group.Group;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.project.ProjectDAO;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.user.UserDAO;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.modules.communication.service.CommunicationDummyService;
import unipotsdam.gf.modules.groupfinding.service.GroupDAO;
import unipotsdam.gf.util.TestHelper;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class DummyProjectCreationServiceTest {

    static PodamFactory factory = new PodamFactoryImpl();

    @Test
    public void testCreateExampleProject() {
        ICommunication communication = new CommunicationDummyService();
        InMemoryMySqlConnect inMemoryMySqlConnect = new InMemoryMySqlConnect();
        Management management = TestHelper.getManagementImpl(inMemoryMySqlConnect);
        GroupDAO groupDAO = new GroupDAO(inMemoryMySqlConnect);
        UserDAO userDAO = new UserDAO(inMemoryMySqlConnect);

        DummyProjectCreationService dummyProjectCreationService = new DummyProjectCreationService(communication, management, groupDAO, userDAO);

        dummyProjectCreationService.createExampleProject();

        User docentUser = dummyProjectCreationService.getDocentUser();
        assertTrue(userDAO.exists(docentUser));

        ProjectDAO projectDAO = new ProjectDAO(inMemoryMySqlConnect);
        Project project = factory.manufacturePojo(Project.class);
        assertTrue(projectDAO.exists(project));

        List<Group> dummyGroups = dummyProjectCreationService.createDummyGroups(project.getId());
        dummyGroups.forEach(group -> group.setChatRoomId("1"));
        dummyGroups.forEach(group -> {
            assertTrue(groupDAO.exists(group));
            group.getMembers().forEach(user -> assertTrue(userDAO.exists(user)));
        });
    }
}
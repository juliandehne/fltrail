package unipotsdam.gf.interfaces;

import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.core.management.ManagementImpl;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.project.ProjectConfiguration;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.user.UserDAO;
import unipotsdam.gf.core.management.user.UserProfile;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by dehne on 01.06.2018.
 */


public class ManagementTest  {


    /**
     * Utility to creaty dummy data for students
     */
    PodamFactory factory = new PodamFactoryImpl();


    @Test
    public void testDelete() {

    }

    /**
     *  CREATE a user in the DB using the
     */
    @Test
    public void testExists() {
        ManagementImpl management = new ManagementImpl();
        User user = new User("julian", "1234", "from1123123123@stuff.com", true);
        assert !management.exists(user);
    }

    /**
     *  CREATE a user in the DB using the
     */
    @Test
    public void testCreate() {
        ManagementImpl management = new ManagementImpl();
        User user = new User("julian", "1234", "from@stuff.com", false);
        management.create(user, new UserProfile());
        assert management.exists(user);
    }

    /**
     * Test creating a user in the DB
     */
    @Test
    public void testCreate1() {
        ManagementImpl management = new ManagementImpl();
        management.create(new Project("Gemainsam Forschen", "1235", true, "me", "keins"));

    }

    @Test
    public void testRegister() {
        ManagementImpl management = new ManagementImpl();
        User user = new User("julian", "1234", "from@stuff.com", true);
        management.create(user, new UserProfile());
        assert management.exists(user);

        Project project = new Project("Gemainsam Forschen", "1235", true, "me", "keins");
        management.create(project);
        management.register(user, project, null);
    }

    @Test
    public void testUpdateUser() {
        ManagementImpl management = new ManagementImpl();
        UserDAO userDAO = new UserDAO();
        User user = new User("julian", "1234", "testUpdateUser@stuff.com", true);
        user.setToken("abc");
        management.create(user, new UserProfile());
        assertTrue(management.exists(user));

        user.setStudent(false);
        management.update(user);
        assertTrue(management.exists(user));
        User managementUser = userDAO.getUserByToken(user.getToken());
        assertEquals(user.getStudent(), managementUser.getStudent());
    }

    @Test
    public void testGetUsers() {
        ManagementImpl management = new ManagementImpl();
        User user = new User("julian", "1234", "from@stuff.com", false);
        management.create(user, new UserProfile());
        assert management.exists(user);

        Project project = new Project("Gemainsam Forschen", "1235", true, "me", "keins");
        management.create(project);
        management.register(user, project, null);

        assertTrue(management.exists(project));

        User user2 = new User("julian2", "12345", "from2@stuff.com", true);
        management.create(user2, new UserProfile());
        assert management.exists(user2);

        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.getUsers(project);
        assert users != null;
        assert !users.isEmpty();

    }

    @Test
    public void testProjectConfiguration() {
        ProjectConfiguration projectConfiguration = factory.manufacturePojo(ProjectConfiguration.class);
        Project project = factory.manufacturePojo(Project.class);

        ManagementImpl management = new ManagementImpl();
        management.create(projectConfiguration, project);

        ProjectConfiguration projectConfiguration1 = management.getProjectConfiguration(project);
        assertNotNull(projectConfiguration1.getCriteriaSelected());
        assertNotNull(projectConfiguration1.getAssessmentMechanismSelected());
        assertNotNull(projectConfiguration1.getGroupMechanismSelected());
        assertNotNull(projectConfiguration1.getPhasesSelected());

    }
}
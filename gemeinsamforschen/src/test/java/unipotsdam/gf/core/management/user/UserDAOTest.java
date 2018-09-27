package unipotsdam.gf.core.management.user;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.core.database.InMemoryMySqlConnect;
import unipotsdam.gf.core.management.ManagementImpl;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.util.TestHelper;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class UserDAOTest {

    private InMemoryMySqlConnect inMemoryMySqlConnect;

    static PodamFactory factory = new PodamFactoryImpl();

    @Before
    public void setUp() {
        inMemoryMySqlConnect = new InMemoryMySqlConnect();
    }

    @After
    public void tearDown() {
        inMemoryMySqlConnect.tearDown();
    }

    @Test
    public void testGetUsersByProjectId() {
        ManagementImpl management = TestHelper.getManagementImpl(inMemoryMySqlConnect);

        User user = new User("julian", "1234", "from@stuff.com", false);
        management.create(user, new UserProfile());
        assert management.exists(user);


        Project project = factory.manufacturePojo(Project.class);
        management.create(project);
        management.register(user, project, null);

        assertTrue(management.exists(project));

        User user2 = new User("julian2", "12345", "from2@stuff.com", true);
        management.create(user2, new UserProfile());
        management.register(user2, project, null);
        assert management.exists(user2);

        UserDAO userDAO = new UserDAO(inMemoryMySqlConnect);
        List<User> users = userDAO.getUsersByProjectId(project.getName());
        assert users != null;
        assert !users.isEmpty();
        assertThat(users, hasSize(2));
    }

    @Test
    public void testExists() {
        UserDAO userDAO = new UserDAO(inMemoryMySqlConnect);
        User user = new User("julian", "1234", "from1123123123@stuff.com", true);
        assert !userDAO.exists(user);
    }

    @Test
    public void testPersist() {
        UserDAO userDAO = new UserDAO(inMemoryMySqlConnect);
        User user = new User("julian", "1234", "from@stuff.com", false);
        userDAO.persist(user, new UserProfile());
        assert userDAO.exists(user);
    }

    @Test
    public void testUpdateUser() {
        UserDAO userDAO = new UserDAO(inMemoryMySqlConnect);
        User user = new User("julian", "1234", "testUpdateUser@stuff.com", true);
        userDAO.persist(user, new UserProfile());
        assertTrue(userDAO.exists(user));

        user.setStudent(false);
        userDAO.update(user);
        assertTrue(userDAO.exists(user));
        User managementUser = userDAO.getUserByEmail(user.getEmail());
        assertEquals(user.getStudent(), managementUser.getStudent());
    }

    @Test
    public void testDelete() {

    }


}

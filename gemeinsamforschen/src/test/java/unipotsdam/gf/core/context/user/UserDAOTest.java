package unipotsdam.gf.core.context.user;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.core.database.TestGFApplicationBinder;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.modules.user.UserProfile;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class UserDAOTest {

    @Inject
    private MysqlConnect inMemoryMySqlConnect;

    @Inject
    private Management management;

    @Inject
    private UserDAO userDAO;

    static PodamFactory factory = new PodamFactoryImpl();

    @Before
    public void setUp() {
        final ServiceLocator locator = ServiceLocatorUtilities.bind(new TestGFApplicationBinder());
        //final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());
        locator.inject(this);
    }

    @After
    public void tearDown() {
        //        ((InMemoryMySqlConnect)inMemoryMySqlConnect).tearDown();
    }

    @Test
    public void testGetUsersByProjectId() {

        User user = factory.manufacturePojo(User.class);
        management.create(user, new UserProfile());
        assert management.exists(user);


        Project project = factory.manufacturePojo(Project.class);
        project.setAuthorEmail(user.getEmail());
        management.create(project);
        management.register(user, project, null);

        assertTrue(management.exists(project));

        User user2 = factory.manufacturePojo(User.class);
        management.create(user2, new UserProfile());
        management.register(user2, project, null);
        assert management.exists(user2);


        List<User> users = userDAO.getUsersByProjectName(project.getName());
        assert users != null;
        assert !users.isEmpty();
        assertThat(users, hasSize(2));
    }

    @Test
    public void testPersistExist() {
        User user = factory.manufacturePojo(User.class);
        user.setStudent(true);
        userDAO.persist(user, new UserProfile());
        assert userDAO.exists(user);
    }

    @Test
    public void testUpdateUser() {
        User user = factory.manufacturePojo(User.class);
        userDAO.persist(user, new UserProfile());
        assertTrue(userDAO.exists(user));

        user.setStudent(false);
        userDAO.updateRocketChatUserName(user);
        assertTrue(userDAO.exists(user));
        User managementUser = userDAO.getUserByEmail(user.getEmail());
        assertEquals(user.getStudent(), managementUser.getStudent());
    }

    @Test
    public void testDelete() {

    }


}

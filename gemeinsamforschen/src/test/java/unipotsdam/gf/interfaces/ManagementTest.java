package unipotsdam.gf.interfaces;

import org.junit.Test;
import unipotsdam.gf.core.management.ManagementImpl;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.user.UserProfile;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by dehne on 01.06.2018.
 */
public class ManagementTest {

    @Test
    public void testDelete() throws Exception {

    }

    /**
     *  CREATE a user in the DB using the
     * @throws Exception
     */
    @Test
    public void testExists() throws Exception {
        ManagementImpl management = new ManagementImpl();
        User user = new User("julian", "1234", "from1123123123@stuff.com");
        assert !management.exists(user);
    }

    /**
     *  CREATE a user in the DB using the
     * @throws Exception
     */
    @Test
    public void testCreate() throws Exception {
        ManagementImpl management = new ManagementImpl();
        User user = new User("julian", "1234", "from@stuff.com");
        management.create(user, new UserProfile());
        assert management.exists(user);
    }

    /**
     * Test creating a user in the DB
     * @throws Exception
     */
    @Test
    public void testCreate1() throws Exception {
        ManagementImpl management = new ManagementImpl();
        management.create(new Project("Gemainsam Forschen", "1235", "1", "me", "keins"));

    }

    @Test
    public void testRegister() throws Exception {
        ManagementImpl management = new ManagementImpl();
        User user = new User("julian", "1234", "from@stuff.com");
        management.create(user, new UserProfile());
        assert management.exists(user);

        Project project = new Project("Gemainsam Forschen", "1235", "1", "me", "keins");
        management.create(project);
        management.register(user, project, null);
    }

    @Test
    public void testGetUsers() throws Exception {
        ManagementImpl management = new ManagementImpl();
        User user = new User("julian", "1234", "from@stuff.com");
        management.create(user, new UserProfile());
        assert management.exists(user);

        Project project = new Project("Gemainsam Forschen", "1235", "1", "me", "keins");
        management.create(project);
        management.register(user, project, null);

        User user2 = new User("julian2", "12345", "from2@stuff.com");
        management.create(user2, new UserProfile());
        assert management.exists(user2);

        List<User> users = management.getUsers(project);
        assert users != null;
        assert !users.isEmpty();

    }
}
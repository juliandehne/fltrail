package unipotsdam.gf.core.management.group;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.core.database.InMemoryMySqlConnect;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.user.UserDAO;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;

public class GroupDAOTest {

    private InMemoryMySqlConnect inMemoryMySqlConnect;
    private GroupDAO groupDAO;
    private UserDAO userDAO;
    private Group group;

    private PodamFactory factory = new PodamFactoryImpl();

    @Before
    public void setUp() {
        inMemoryMySqlConnect = new InMemoryMySqlConnect();
        groupDAO = new GroupDAO(inMemoryMySqlConnect);
        userDAO = new UserDAO(inMemoryMySqlConnect);

        User userStudent = new User("Student", "password", "testStudent@mail.com", "1",
                "1", "1", true);
        User userDocent = new User("Docent", "password", "testDocent@mail.com",
                "1", "1", "1", false);
        userDAO.persist(userStudent, null);
        userDAO.persist(userDocent, null);

        group = new Group(Arrays.asList(userStudent, userDocent), "1", "1");
    }

    @Test
    public void testPersist() {
        groupDAO.persist(group);
        Assert.assertThat(groupDAO.exists(group), is(true));
    }
}

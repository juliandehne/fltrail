package unipotsdam.gf.core.management.group;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.core.database.TestGFApplicationBinder;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.user.UserDAO;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class GroupDAOTest {

    @Inject
    private GroupDAO groupDAO;

    @Inject
    private UserDAO userDAO;

    private Group group;

    private PodamFactory factory = new PodamFactoryImpl();

    @Before
    public void setUp() {
        final ServiceLocator locator = ServiceLocatorUtilities.bind(new TestGFApplicationBinder());
        //final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());
        locator.inject(this);


        User userStudent = factory.manufacturePojo(User.class);
        userStudent.setStudent(true);
        User userDocent = factory.manufacturePojo(User.class);
        userDocent.setStudent(false);

        userDAO.persist(userStudent, null);
        userDAO.persist(userDocent, null);
        //group = new Group(Arrays.asList(userStudent, userDocent), "1", "1");
        group = factory.manufacturePojo(Group.class);
        List<User> members = group.getMembers();
        for (User member : members) {
            userDAO.persist(member, null);
        }
        assertThat (group.getMembers().isEmpty(), is(false));
    }

    @Test
    public void testExist() {
        // we have to assert that all the members of the group exist in the db first
        groupDAO.persist(group);
        assertThat(groupDAO.exists(group), is(true));
    }

    @Test
    public void testPersist() {
        assertThat(groupDAO.exists(group), is(false));
        groupDAO.persist(group);
        assertThat(groupDAO.exists(group), is(true));
    }

    @Test
    public void testUpdate() {
        groupDAO.persist(group);
        assertThat(groupDAO.exists(group), is(true));
        Group groupWithId = groupDAO.getGroupsByProjectName(group.getProjectName()).get(0);
        groupWithId.setChatRoomId("neu");
        groupDAO.update(groupWithId);
        assertThat(groupDAO.exists(groupWithId), is(true));
    }

    @Test
    public void testGetGroupsByProjectId() {
        groupDAO.persist(group);
        List<Group> groups = groupDAO.getGroupsByProjectName(group.getProjectName());
        assertThat(groups, hasSize(1));
        assertThat(groups.get(0), equalTo(group));
    }
}

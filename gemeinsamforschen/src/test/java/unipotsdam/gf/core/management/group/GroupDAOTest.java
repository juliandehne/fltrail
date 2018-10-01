package unipotsdam.gf.core.management.group;

import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.core.database.InMemoryMySqlConnect;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.user.UserDAO;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.groupfinding.service.GroupDAO;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class GroupDAOTest {

    private InMemoryMySqlConnect inMemoryMySqlConnect;
    private GroupDAO groupDAO;
    private UserDAO userDAO;
    private Group group;
    User userStudent;

    private PodamFactory factory = new PodamFactoryImpl();

    @Before
    public void setUp() {
        inMemoryMySqlConnect = new InMemoryMySqlConnect();
        groupDAO = new GroupDAO(inMemoryMySqlConnect);
        userDAO = new UserDAO(inMemoryMySqlConnect);

        userStudent = new User("Student", "password", "testStudent@mail.com", "1",
                "1", "1", "1", "1",
                true);
        User userDocent = new User("Docent", "password", "testDocent@mail.com", "1",
                "1", "1", "1", "1",
                false);
        userDAO.persist(userStudent, null);
        userDAO.persist(userDocent, null);

        group = new Group(Arrays.asList(userStudent, userDocent), "1", "1");
    }

    @Test
    public void testExist() {
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
        Group groupWithId = groupDAO.getGroupsByProjectId(group.getProjectId()).get(0);
        groupWithId.setChatRoomId("neu");
        groupDAO.update(groupWithId);
        assertThat(groupDAO.exists(groupWithId), is(true));
    }

    @Test
    public void testGetGroupsByProjectId() {
        groupDAO.persist(group);
        List<Group> groups = groupDAO.getGroupsByProjectId(group.getProjectId());
        assertThat(groups, hasSize(1));
        assertThat(groups.get(0), equalTo(group));
    }

    @Test
    public void testGetGroupChatRoomIdByStudentIdentifier() {
        groupDAO.persist(group);
        StudentIdentifier studentIdentifier = new StudentIdentifier();
        studentIdentifier.setStudentId(userStudent.getId());
        studentIdentifier.setProjectId(group.getProjectId());
        String chatRoomId = groupDAO.getGroupChatRoomIdByStudentIdentifier(studentIdentifier);
        assertFalse(chatRoomId.isEmpty());
        assertEquals(chatRoomId, group.getChatRoomId());
    }
}

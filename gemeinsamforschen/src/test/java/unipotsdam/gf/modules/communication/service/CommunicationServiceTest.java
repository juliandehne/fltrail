package unipotsdam.gf.modules.communication.service;

import ch.vorburger.exec.ManagedProcessException;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.*;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.core.database.TestGFApplicationBinder;
import unipotsdam.gf.core.database.UpdateDB;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.communication.model.EMailMessage;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.modules.user.UserProfile;
import unipotsdam.gf.process.constraints.Constraints;
import unipotsdam.gf.process.constraints.ConstraintsMessages;

import javax.inject.Inject;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static unipotsdam.gf.config.GFRocketChatConfig.ADMIN_USER;
import static unipotsdam.gf.config.GFRocketChatConfig.ROCKET_CHAT_ROOM_LINK;
import static unipotsdam.gf.config.GFRocketChatConfig.TEST_USER;

public class CommunicationServiceTest {

    @Inject
    private ICommunication iCommunication;

    private User user;

    @Inject
    private GroupDAO groupDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private ProjectDAO projectDAO;

    private List<String> createdChatRooms;

    @Inject
    Management management;


    @BeforeClass
    public static void init() throws IOException, SQLException, ManagedProcessException {
        UpdateDB.updateTestDB();
    }

    @Before
    public void setUp() {

        final ServiceLocator locator = ServiceLocatorUtilities.bind(new TestGFApplicationBinder());
        //final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());
        locator.inject(this);

        user = new User("Vorname Nachname", "password", "email@uni.de", true);
        userDAO.persist(user, null);

        createdChatRooms = new ArrayList<>();

    }

    @After
    public void tearDown() {
        createdChatRooms.forEach(createdChatRoom -> iCommunication.deleteChatRoom(createdChatRoom));
        createdChatRooms.clear();


    }

    @Test
    public void loginUser() {
        assertTrue(iCommunication.loginUser(TEST_USER));
        assertTrue(!TEST_USER.getRocketChatAuthToken().isEmpty());
        assertTrue(!TEST_USER.getRocketChatUserId().isEmpty());

        User falseLoginUser = new User("name", "password", "email", true);
        assertFalse(iCommunication.loginUser(falseLoginUser));
    }


    @Ignore
    @Test
    public void registerUser() {
        // TODO Side effect is not optimal because you need to know that before persisting the user
        boolean userCreated = iCommunication.registerUser(user);
        //userDAO.persist(user, null);
        assertTrue(userCreated);
        assertNotNull(user.getRocketChatUserId());
        assertNotNull(user.getRocketChatAuthToken());
        assertNotNull(user.getRocketChatPersonalAccessToken());

    }

    @Test
    public void createEmptyChatRoom() {
        String chatRoom = iCommunication.createEmptyChatRoom("Test", false);
        assertNotNull(chatRoom);
        assertFalse(chatRoom.isEmpty());
        assertTrue(iCommunication.exists(chatRoom));


        String chatRoomReadOnly = iCommunication.createEmptyChatRoom("Test2", true);
        assertNotNull(chatRoomReadOnly);
        assertFalse(chatRoomReadOnly.isEmpty());
        assertTrue(iCommunication.exists(chatRoomReadOnly));

        createdChatRooms.addAll(Arrays.asList(chatRoom, chatRoomReadOnly));
    }

    @Test
    public void createChatRoomWithUser() {
        List<User> userList = Arrays.asList(ADMIN_USER, TEST_USER);
        String chatRoom = iCommunication.createChatRoom("ChatWithUser", false, userList);

        assertNotNull(chatRoom);
        assertFalse(chatRoom.isEmpty());
        assertTrue(iCommunication.exists(chatRoom));

        createdChatRooms.add(chatRoom);
    }

    @Test
    public void createChatRoomWithGroup() {
        Group group = new Group();
        group.setMembers(Collections.singletonList(ADMIN_USER));
        group.setProjectName("chatWithGroup");
        boolean successful = iCommunication.createChatRoom(group, false);
        assertTrue(successful);
        createdChatRooms.add(group.getChatRoomId());
    }

    @Test
    public void getChatRoomName() {
        String expectedChatRoomName = "ChatRoomName";
        String chatRoomId = iCommunication.createEmptyChatRoom(expectedChatRoomName, false);
        assertNotNull(chatRoomId);
        assertFalse(chatRoomId.isEmpty());

        String actualChatRoomName = iCommunication.getChatRoomName(chatRoomId);
        assertEquals(expectedChatRoomName, actualChatRoomName);

        String nonExistingChatRoomName = iCommunication.getChatRoomName("1");
        assertTrue(nonExistingChatRoomName.isEmpty());

        createdChatRooms.add(chatRoomId);
    }

    @Test
    public void getChatRoomLink() {
        String projectId = "Projekt";
        Project project = new Project(projectId, user.getEmail());
        projectDAO.persist(project);

        Group group = new Group();
        userDAO.persist(ADMIN_USER, new UserProfile());
        group.setProjectName(projectId);
        group.setMembers(Collections.singletonList(ADMIN_USER));
        groupDAO.persist(group);
        iCommunication.createChatRoom(group, false);
        groupDAO.update(group);

        String chatRoomLink = iCommunication.getChatRoomLink(ADMIN_USER.getEmail(), projectId);
        assertNotNull(chatRoomLink);
        assertFalse(chatRoomLink.isEmpty());
        String expectedUrl = ROCKET_CHAT_ROOM_LINK + projectId + "-" + group.getId() + "?layout=embedded";
        assertEquals(expectedUrl, chatRoomLink);

        createdChatRooms.add(group.getChatRoomId());

    }

    @Test
    public void exists() {
        String expectedChatRoomName = "ChatRoomName";
        String chatRoomId = iCommunication.createEmptyChatRoom(expectedChatRoomName, false);
        assertNotNull(chatRoomId);
        assertFalse(chatRoomId.isEmpty());

        assertTrue(iCommunication.exists(chatRoomId));
        assertFalse(iCommunication.exists("1"));

        createdChatRooms.add(chatRoomId);
    }

    @Test
    public void addUserToChatRoom() {
        String chatRoomId = iCommunication.createEmptyChatRoom("addUser", false);
        assertNotNull(chatRoomId);
        assertFalse(chatRoomId.isEmpty());

        assertTrue(iCommunication.addUserToChatRoom(TEST_USER, chatRoomId));

        createdChatRooms.add(chatRoomId);
    }

    @Test
    public void removeUserFromChatRoom() {
        String chatRoomId = iCommunication.createEmptyChatRoom("removeUser", false);
        assertNotNull(chatRoomId);
        assertFalse(chatRoomId.isEmpty());

        assertTrue(iCommunication.addUserToChatRoom(TEST_USER, chatRoomId));

        assertTrue(iCommunication.removeUserFromChatRoom(TEST_USER, chatRoomId));

        createdChatRooms.add(chatRoomId);
    }

    @Test
    public void deleteChatRoom() {
        String chatRoomId = iCommunication.createEmptyChatRoom("deleteChatRoom", false);
        assertNotNull(chatRoomId);
        assertFalse(chatRoomId.isEmpty());
        assertTrue(iCommunication.exists(chatRoomId));

        assertTrue(iCommunication.deleteChatRoom(chatRoomId));
        assertFalse(iCommunication.exists(chatRoomId));
    }

    @Test
    @Ignore
    public void sendSingleMessage() {
        User user = new User();
        // Permalink for email-address: http://www.trashmail.de/index.php?search=javatest
        user.setEmail("javatest@trashmail.de");
        EMailMessage eMailMessage = new EMailMessage();
        eMailMessage.setSubject("Test Email");
        eMailMessage.setBody("Test Body");

        iCommunication.sendSingleMessage(eMailMessage, user);
    }

    @Test
    @Ignore
    public void informAboutMissingTasks() {
        HashMap<StudentIdentifier, ConstraintsMessages> tasks = new HashMap<>();
        Project project = new Project();
        String projectId = "Projekt";
        project.setName(projectId);
        StudentIdentifier studentIdentifier = new StudentIdentifier();
        studentIdentifier.setProjectName(projectId);
        // Permalink for email-address: http://www.trashmail.de/index.php?search=javatest
        studentIdentifier.setUserEmail("javatest@trashmail.de");
        ConstraintsMessages constraintsMessages = new ConstraintsMessages(Constraints.QuizCount, studentIdentifier);
        tasks.put(studentIdentifier, constraintsMessages);
        boolean successful = iCommunication.informAboutMissingTasks(tasks, project);
        assertTrue(successful);
    }

    @Test
    @Ignore
    public void createTestData() {
        User user = new User();
        user.setName("Martin Nachname");
        user.setPassword("test1234");
        user.setEmail("martin@test.com");
        user.setStudent(true);

        userDAO.persist(user, null);


       /* try {
            userService.login(true, user);
        } catch (URISyntaxException e) {
            Assert.fail();
        }*/
        assertTrue(userDAO.exists(user));

        PodamFactory podamFactory = new PodamFactoryImpl();
        Project project = podamFactory.manufacturePojo(Project.class);
        // for the dummy data on website
        String projectId = "gemeinsamForschen";
        project.setName(projectId);
        projectDAO.persist(project);
        assertTrue(projectDAO.exists(project));

        management.register(user, project, null);

        Group group = new Group();
        group.setProjectName(projectId);
        group.setMembers(Collections.singletonList(user));
        groupDAO.persist(group);
        assertTrue(groupDAO.exists(group));

        boolean success = iCommunication.createChatRoom(group, false);
        assertTrue(success);
    }
}

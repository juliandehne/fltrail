package unipotsdam.gf.modules.communication.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import unipotsdam.gf.core.database.InMemoryMySqlConnect;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.user.UserDAO;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.modules.communication.model.EMailMessage;
import unipotsdam.gf.modules.groupfinding.service.GroupDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static unipotsdam.gf.config.GFRocketChatConfig.ADMIN_USER;
import static unipotsdam.gf.config.GFRocketChatConfig.TEST_USER;

public class CommunicationDummyServiceTest {

    private ICommunication iCommunication;
    private User user;

    private List<String> createdChatRooms;

    @Before
    public void setUp() {
        InMemoryMySqlConnect inMemoryMySqlConnect = new InMemoryMySqlConnect();
        UserDAO userDAO = new UserDAO(inMemoryMySqlConnect);
        GroupDAO groupDAO = new GroupDAO(inMemoryMySqlConnect);
        iCommunication = new CommunicationDummyService(new UnirestService(), userDAO, groupDAO);
        user = new User("Vorname Nachname", "password", "email@uni.de", true);
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
        assertTrue(TEST_USER.getRocketChatUserId().isEmpty());

        User falseLoginUser = new User("name", "password", "email", true);
        assertFalse(iCommunication.loginUser(falseLoginUser));
    }

    @Test
    public void registerUser() {
        boolean userCreated = iCommunication.registerUser(user);
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


        String chatRoomReadOnly = iCommunication.createEmptyChatRoom("Test2", true);
        assertNotNull(chatRoomReadOnly);
        assertFalse(chatRoomReadOnly.isEmpty());

        createdChatRooms.addAll(Arrays.asList(chatRoom, chatRoomReadOnly));
    }

    @Test
    public void createChatRoomWithUser() {
        List<User> userList = Arrays.asList(ADMIN_USER, TEST_USER);
        String chatRoom = iCommunication.createChatRoom("ChatWithUser", false, userList);

        assertNotNull(chatRoom);
        assertFalse(chatRoom.isEmpty());

        createdChatRooms.add(chatRoom);
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
    public void createTestData() {
        String chatRoomId = iCommunication.createChatRoom("Testchat", false, Collections.singletonList(ADMIN_USER));

    }
}

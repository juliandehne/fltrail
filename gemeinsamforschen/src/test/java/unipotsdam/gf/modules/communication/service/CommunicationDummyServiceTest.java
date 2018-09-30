package unipotsdam.gf.modules.communication.service;

import org.junit.Before;
import org.junit.Test;
import unipotsdam.gf.core.database.InMemoryMySqlConnect;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.user.UserDAO;
import unipotsdam.gf.interfaces.ICommunication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static unipotsdam.gf.config.GFRocketChatConfig.TEST_USER;

public class CommunicationDummyServiceTest {

    private ICommunication iCommunication;
    private User user;

    @Before
    public void setUp() {
        UserDAO userDAO = new UserDAO(new InMemoryMySqlConnect());
        iCommunication = new CommunicationDummyService(new UnirestService(), userDAO);
        user = new User("Vorname Nachname", "password", "email@uni.de", true);
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
    }

    @Test
    public void exists() {
        String expectedChatRoomName = "ChatRoomName";
        String chatRoomId = iCommunication.createEmptyChatRoom(expectedChatRoomName, false);
        assertNotNull(chatRoomId);
        assertFalse(chatRoomId.isEmpty());

        assertTrue(iCommunication.exists(chatRoomId));
        assertFalse(iCommunication.exists("1"));

    }

    @Test
    public void addUserToChatRoom() {
        String chatRoomId = iCommunication.createEmptyChatRoom("addUser", false);
        assertNotNull(chatRoomId);
        assertFalse(chatRoomId.isEmpty());

        assertTrue(iCommunication.addUserToChatRoom(TEST_USER, chatRoomId));
    }

    @Test
    public void removeUserFromChatRoom() {
        String chatRoomId = iCommunication.createEmptyChatRoom("removeUser", false);
        assertNotNull(chatRoomId);
        assertFalse(chatRoomId.isEmpty());

        assertTrue(iCommunication.addUserToChatRoom(TEST_USER, chatRoomId));

        assertTrue(iCommunication.removeUserFromChatRoom(TEST_USER, chatRoomId));
    }
}

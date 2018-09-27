package unipotsdam.gf.modules.communication.service;

import org.junit.Before;
import org.junit.Test;
import unipotsdam.gf.core.database.InMemoryMySqlConnect;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.user.UserDAO;
import unipotsdam.gf.interfaces.ICommunication;

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
}

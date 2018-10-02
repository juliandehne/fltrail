package unipotsdam.gf.modules.communication.service;

import org.junit.Before;
import org.junit.Test;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.interfaces.ICommunication;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class CommunicationDummyServiceTest {

    private ICommunication iCommunication;
    private User user;

    @Before
    public void setUp() {
        iCommunication = new CommunicationDummyService();
        user = new User("name", "password", "email", true);
    }

    @Test
    public void loginUser() {
        boolean isLoggedIn = iCommunication.loginUser(user);
        assertNotNull(user.getRocketChatId());
        assertNotNull(user.getRocketChatAuthToken());
        assertTrue(isLoggedIn);
    }

    @Test
    public void registerUser() {
        boolean userCreated = iCommunication.registerUser(user);
        assertNotNull(user.getRocketChatId());
        assertNull(user.getRocketChatAuthToken());
        assertTrue(userCreated);
    }
}

package unipotsdam.gf.modules.communication.service;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.Before;
import org.junit.Test;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.exceptions.UserExistsInRocketChatException;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.modules.user.User;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Random;

public class RocketChatPerformanceTest {

    @Inject
    private ICommunication communicationService;

    @Before
    public void setUp() {
        //final ServiceLocator locator = ServiceLocatorUtilities.bind(new TestGFApplicationBinder());
        final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());
        locator.inject(this);
    }

    @Test
    public void testEnterProject()
            throws RocketChatDownException, UserDoesNotExistInRocketChatException, UserExistsInRocketChatException {
        long offset = new Random().nextLong();

        String name = "hellohendrikchatroom-" + offset;
        String user = "hendrik11-" + offset;
        User dao = new User(user, "password", name + "@uni.com", true);

        communicationService.createEmptyChatRoom(name, false);
        communicationService.registerUser(dao);
        communicationService.addUserToChatRoom(dao, name);
    }
}

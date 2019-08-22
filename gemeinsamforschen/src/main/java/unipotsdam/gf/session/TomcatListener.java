package unipotsdam.gf.session;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.modules.communication.model.RocketChatUser;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.CopyOnWriteArraySet;

public class TomcatListener implements ServletContextListener {

    private final static Logger log = LoggerFactory.getLogger(TomcatListener.class);

    public static final CopyOnWriteArraySet<RocketChatUser> RocketChatUsersLoggedIn = new CopyOnWriteArraySet();

    @Inject
    ICommunication communication;

    public TomcatListener() {
        final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());
        locator.inject(this);
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {


    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        for (RocketChatUser user : RocketChatUsersLoggedIn) {
            try {
                communication.logout(user);
                RocketChatUsersLoggedIn.remove(user);
                log.info("removed user " + user.getRocketChatAuthToken() + "from rocket chat session queue");
            } catch (RocketChatDownException e) {
                e.printStackTrace();
            } catch (UserDoesNotExistInRocketChatException e) {
                e.printStackTrace();
            }
        }

    }
}

package unipotsdam.gf.session;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.config.GFApplicationBinderFactory;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.modules.communication.model.RocketChatUser;
import unipotsdam.gf.modules.communication.service.CommunicationService;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class RocketChatSessionListener implements HttpSessionListener {


    private final static Logger log = LoggerFactory.getLogger(RocketChatSessionListener.class);

    @Inject
    GFContexts gfContexts;

    @Inject
    ICommunication communicationService;

    public RocketChatSessionListener() {
        final ServiceLocator locator = ServiceLocatorUtilities.bind(GFApplicationBinderFactory.instance());
        locator.inject(this);
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.info("destroying session");
        RocketChatUser rocketChatUserFromSession = gfContexts.getRocketChatUserFromSession(se.getSession());
        if (rocketChatUserFromSession != null) {
            try {
                communicationService.logout(rocketChatUserFromSession);
                log.info("logged out user after session termination: " + rocketChatUserFromSession
                        .getRocketChatUserId());
            } catch (RocketChatDownException e) {
                e.printStackTrace();
            } catch (UserDoesNotExistInRocketChatException e) {
                e.printStackTrace();
            }

        }
    }
}

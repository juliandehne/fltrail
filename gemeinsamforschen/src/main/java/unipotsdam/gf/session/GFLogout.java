package unipotsdam.gf.session;

import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.interfaces.ICommunication;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/logout")
public class GFLogout {

    @Inject
    private ICommunication iCommunication;

    @Inject
    private GFContexts gfContexts;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user")
    public void logoutUser(@Context HttpServletRequest req)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {

   /*     if (FLTrailConfig.rocketChatIsOnline) {
            RocketChatUser rocketChatUserFromSession = gfContexts.getRocketChatUserFromSession(req);
            if (rocketChatUserFromSession != null) {
                iCommunication.logout(rocketChatUserFromSession);
            }
        }

        req.getSession().setAttribute(GFContexts.USEREMAIL, null);
        req.getSession().setAttribute(GFContexts.PROJECTNAME, null);
        req.getSession().setAttribute(GFContexts.ISSTUDENT, null);
        req.getSession().setAttribute(GFContexts.ROCKETCHATAUTHTOKEN, null);
        req.getSession().setAttribute(GFContexts.ROCKETCHATID, null);*/
        req.getSession().invalidate();
    }
}

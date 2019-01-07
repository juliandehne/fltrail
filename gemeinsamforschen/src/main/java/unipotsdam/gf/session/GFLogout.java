package unipotsdam.gf.session;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/logout")
public class GFLogout {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user")
    public void logoutUser(@Context HttpServletRequest req) {
        req.getSession().setAttribute(GFContexts.USEREMAIL, null);
        req.getSession().setAttribute(GFContexts.PROJECTNAME, null);
        req.getSession().setAttribute(GFContexts.ISSTUDENT, null);
        req.getSession().setAttribute(GFContexts.ROCKETCHATAUTHTOKEN, null);
        req.getSession().setAttribute(GFContexts.ROCKETCHATID, null);
    }
}

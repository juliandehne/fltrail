package unipotsdam.gf.core.management.user;

import unipotsdam.gf.core.management.ManagementImpl;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.interfaces.IMunschkin;
import unipotsdam.gf.modules.munchkin.controller.MunchkinImpl;
import unipotsdam.gf.modules.munchkin.model.Munschkin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/user")
public class UserService {

    /**
     * creates a user with given credentials
     * @param name
     * @param password
     * @param email
     * @param isStudent
     * @return
     * @throws URISyntaxException
     */
    // This method is called if HTML is request
    @POST
    @Produces(MediaType.TEXT_HTML)
    @Path("/create")
    public Response createUser(@FormParam("name") String name, @FormParam("password") String password,
                               @FormParam("email") String email, @FormParam("isStudent") String isStudent)
            throws URISyntaxException {
        ManagementImpl management = new ManagementImpl();
        User user = new User(name, password, email, isStudent == null);
        return login(true, user);
    }


    /**
     * checks if a user exists in order to log him in
     * @param name
     * @param password
     * @param email
     * @return
     * @throws URISyntaxException
     */
    // This method is called if HTML is request
    @POST
    @Produces(MediaType.TEXT_HTML)
    @Path("/exists")
    public Response existsUser(@FormParam("name") String name, @FormParam("password") String password,
                               @FormParam("email") String email)
            throws URISyntaxException {

        ManagementImpl management = new ManagementImpl();
        User user = new User(name, password, email,  null);
        return login(false, user);
    }

    /**
     * if create User is true, the user is created and logged in if he does not exist
     * @param createUser
     * @param user
     * @return
     * @throws URISyntaxException
     */
    protected Response login(boolean createUser, User user) throws URISyntaxException {
        ManagementImpl management = new ManagementImpl();
        if (management.exists(user)) {
            if (!createUser) {
                return redirectToProjectPage(user, management);
            }
            String existsUrl = "../register.jsp?userExists=true";
            return forwardToLocation(existsUrl);
        } else {
            if (createUser) {
                management.create(user, null);
            } else {
                String existsUrl = "../index.jsp?userExists=false";
                return forwardToLocation(existsUrl);
            }
            return redirectToProjectPage(user, management);
        }
    }

    /**
     * helper function for redirecting to the right project page
     * @param user
     * @param management
     * @return
     * @throws URISyntaxException
     */
    private Response redirectToProjectPage(User user, ManagementImpl management) throws URISyntaxException {
        String successUrl;
        if (user.getStudent() != null && user.getStudent()) {
            successUrl = "../pages/overview-student.html?token=";
        } else {
            successUrl = "../pages/overview-docent.html?token=";
        }
        successUrl += management.getUserToken(user);
        return forwardToLocation(successUrl);
    }

    /**
     *   * helper function for redirecting to a new page
     * @param existsUrl
     * @return
     * @throws URISyntaxException
     */
    private Response forwardToLocation(String existsUrl) throws URISyntaxException {
        return Response.seeOther(new URI(existsUrl)).build();
    }
}

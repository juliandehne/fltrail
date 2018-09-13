package unipotsdam.gf.core.management.user;

import org.glassfish.grizzly.streams.BufferedInput;
import unipotsdam.gf.core.management.ManagementImpl;
import unipotsdam.gf.core.states.ProjectPhase;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.modules.communication.service.CommunicationDummyService;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/user")
@ManagedBean
public class UserService {

    @Inject
    private ICommunication communicationService;

    /**
     * creates a user with given credentials
     *
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
     *
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
        User user = new User(name, password, email, null);
        ICommunication iCommunication = new CommunicationDummyService();
        boolean isLoggedIn = iCommunication.loginUser(user);
        if (isLoggedIn) {
            return login(false, user);
        } else {
            return loginError();
        }
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/student/{studentId}")
    public String getQuiz(@FormParam("image") File file, @PathParam("studentId") String studentId) {
        ManagementImpl management = new ManagementImpl();
        try{
            FileInputStream fis = new FileInputStream(file);

            return management.saveProfilePicture(fis, studentId);
        }catch(Exception e){
            return e.toString();
        }

    }

    /**
     * if create User is true, the user is created and logged in if he does not exist
     *
     * @param createUser
     * @param user
     * @return
     * @throws URISyntaxException
     */
    protected Response login(boolean createUser, User user) throws URISyntaxException {
        ManagementImpl management = new ManagementImpl();
        if (management.exists(user)) {
            if (!createUser) {
                user = fillUserFields(user);
                return redirectToProjectPage(user, management);
            }
            String existsUrl = "../register.jsp?userExists=true";
            return forwardToLocation(existsUrl);
        } else {
            if (createUser) {
                boolean isRegisteredAndLoggedIn = communicationService.registerAndLoginUser(user);
                if (!isRegisteredAndLoggedIn) {
                    return registrationError();
                }
                management.create(user, null);
                user = fillUserFields(user);
                return redirectToProjectPage(user, management);
            } else {
                String existsUrl = "../index.jsp?userExists=false";
                return forwardToLocation(existsUrl);
            }

        }
    }

    private User fillUserFields(User user) {
        ManagementImpl m = new ManagementImpl();
        String token = m.getUserToken(user);
        user = m.getUserByToken(token);
        return user;
    }

    private Response registrationError() throws URISyntaxException {
        String existsUrl = "../register.jsp?registrationError=true";
        return forwardToLocation(existsUrl);
    }

    private Response loginError() throws URISyntaxException {
        String existsUrl = "../index.jsp?loginError=true";
        return forwardToLocation(existsUrl);
    }

    /**
     * helper function for redirecting to the right project page
     *
     * @param user
     * @param management
     * @return
     * @throws URISyntaxException
     */
    private Response redirectToProjectPage(User user, ManagementImpl management) throws URISyntaxException {
        String successUrl;
        if (user.getStudent() != null && user.getStudent()) {
            successUrl = "../overview-student.jsp?token=";
        } else {
            successUrl = "../overview-docent.jsp?token=";
        }
        successUrl += management.getUserToken(user);
        return forwardToLocation(successUrl);
    }

    /**
     * * helper function for redirecting to a new page
     *
     * @param existsUrl
     * @return
     * @throws URISyntaxException
     */
    private Response forwardToLocation(String existsUrl) throws URISyntaxException {
        return Response.seeOther(new URI(existsUrl)).build();
    }
}

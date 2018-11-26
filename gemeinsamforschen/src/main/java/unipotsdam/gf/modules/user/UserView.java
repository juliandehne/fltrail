package unipotsdam.gf.modules.user;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import unipotsdam.gf.exceptions.*;
import unipotsdam.gf.healthchecks.HealthChecks;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.process.ProjectCreationProcess;
import unipotsdam.gf.session.GFContexts;
import unipotsdam.gf.interfaces.ICommunication;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/user")
@ManagedBean
public class UserView {



    @Inject
    private ICommunication communicationService;

    @Inject
    private UserDAO userDAO;

    @Inject
    private Management management;

    @Inject
    private ProjectCreationProcess projectCreationProcess;

    @Inject
    public UserView(ICommunication communicationService, UserDAO userDAO, Management management) {
        this.communicationService = communicationService;
        this.userDAO = userDAO;
        this.management = management;
    }

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
    public Response createUser(
            @Context HttpServletRequest req, @FormParam("name") String name, @FormParam("password") String password,
            @FormParam("email") String email, @FormParam("isStudent") String isStudent) throws URISyntaxException {

        User user = new User(name, password, email, isStudent == null);
        try {
            projectCreationProcess.createUser(user);
        } catch (UserExistsInRocketChatException e) {
            return registrationError();
        } catch (UserExistsInMysqlException e) {
            String existsUrl = "../register.jsp?userExists=true";
            return forwardToLocation(existsUrl);
        } catch (RocketChatDownException e) {
            e.printStackTrace();
            return registrationError();
        }

        try {
            projectCreationProcess.authenticateUser(user, req);
        } catch (UserDoesNotExistInRocketChatException e) {
            loginError();
        } catch (RocketChatDownException e) {
            e.printStackTrace();
            return registrationError();
        }
        return redirectToProjectPage(user);

    }

    private Response redirectToUserExists() throws URISyntaxException {
        String existsUrl = "../index.jsp?userExists=false";
        return forwardToLocation(existsUrl);
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
    public Response authenticate(
            @Context HttpServletRequest req, @FormParam("name") String name, @FormParam("password") String password,
            @FormParam("email") String email) throws URISyntaxException {

        User user = new User(name, password, email, null);
        try {
            Boolean exists = projectCreationProcess.authenticateUser(user, req);
            if (exists) {
                user = fillUserFields(user);
                return redirectToProjectPage(user);
            } else {
                return loginError();
            }
        } catch (UserDoesNotExistInRocketChatException e) {
            return loginError();
        } catch (RocketChatDownException e) {
            return loginError();
        }

    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/student/{userName}")
    public String getQuiz(@FormParam("image") File file, @PathParam("userName") String userName) {
        try {
            FileInputStream fis = new FileInputStream(file);

            return management.saveProfilePicture(fis, userName);
        } catch (Exception e) {
            return e.toString();
        }

    }

    private User fillUserFields(User user) {
        user = userDAO.getUserByEmail(user.getEmail());
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
     * @return
     * @throws URISyntaxException
     */
    private Response redirectToProjectPage(User user) throws URISyntaxException {
        String successUrl;

        if (user.getStudent() != null && user.getStudent()) {
            successUrl = "../project/courses-student.jsp";
        } else {
            successUrl = "../project/overview-docent.jsp";
        }
        Response result = forwardToLocation(successUrl);
        return result;
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

package unipotsdam.gf.modules.user;

import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.session.GFContexts;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.modules.communication.service.CommunicationDummyService;

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

    private ICommunication communicationService;
    private UserDAO userDAO;

    @Inject
    private Management management;

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
        return login(req, true, user);

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
    public Response existsUser(
            @Context HttpServletRequest req, @FormParam("name") String name, @FormParam("password") String password,
            @FormParam("email") String email) throws URISyntaxException {

        User user = new User(name, password, email, null);
        ICommunication iCommunication = new CommunicationDummyService();
        boolean isLoggedIn = iCommunication.loginUser(user);
        if (isLoggedIn) {
            return login(req, false, user);
        } else {
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

    /**
     * if create User is true, the user is created and logged in if he does not exist
     *
     * @param createUser
     * @param user
     * @return
     * @throws URISyntaxException
     */
    protected Response login(HttpServletRequest req, boolean createUser, User user) throws URISyntaxException {

        if (management.exists(user)) {
            if (!createUser) {
                user = fillUserFields(user);
                return redirectToProjectPage(req, user);
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
                return redirectToProjectPage(req, user);
            } else {
                String existsUrl = "../index.jsp?userExists=false";
                return forwardToLocation(existsUrl);
            }

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
    private Response redirectToProjectPage(HttpServletRequest req, User user) throws URISyntaxException {
        String successUrl;

        if (user.getStudent() != null && user.getStudent()) {
            successUrl = "../project/myCourses-student.jsp";
        } else {
            successUrl = "../project/overview-docent.jsp";
        }
        req.getSession().setAttribute(GFContexts.USEREMAIL, user.getEmail());
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

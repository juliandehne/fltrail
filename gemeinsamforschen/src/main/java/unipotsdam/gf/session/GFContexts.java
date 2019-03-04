package unipotsdam.gf.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.modules.communication.model.RocketChatUser;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import java.io.IOException;

public class GFContexts {

    @Inject
    UserDAO userDAO;

    public static final String USEREMAIL = "userEmail";
    public static final String ISSTUDENT = "isStudent";
    public static final String PROJECTNAME = "projectName";
    public static final String ROCKETCHATAUTHTOKEN = "rocketchatauthtoken";
    public static final String ROCKETCHATID = "rocketchatid";


    private final static Logger log = LoggerFactory.getLogger(GFContexts.class);



    public String getUserEmail(HttpServletRequest req) throws IOException {
        Object userEmail = req.getSession().getAttribute(GFContexts.USEREMAIL);
        if (userEmail == null) {
            throw new IOException("No user session");
        } else {
            return userEmail.toString();
        }
    }

    public User getUserFromSession(HttpServletRequest req) throws IOException {
        String userEmail = getUserEmail(req);
        return userDAO.getUserByEmail(userEmail);
    }

    public void updateUserSessionWithRocketChat(HttpServletRequest req, RocketChatUser user) {
        req.getSession().setAttribute(GFContexts.ROCKETCHATAUTHTOKEN, user.getRocketChatAuthToken());
        req.getSession().setAttribute(GFContexts.ROCKETCHATID, user.getRocketChatUserId());
    }

    public void updateUserWithEmail(HttpServletRequest req, User user) {
        log.debug("setting user email ssession:" + user.getEmail());
        req.getSession().setAttribute(GFContexts.USEREMAIL, user.getEmail());
        log.debug("user session email:" + req.getSession().getAttribute(GFContexts.USEREMAIL));
    }

    public void updateUserSessionWithStatus(HttpServletRequest req, User user) {
        if (user.getStudent()){
            req.getSession().setAttribute(GFContexts.ISSTUDENT, "isStudent");
        }else{
            req.getSession().setAttribute(GFContexts.ISSTUDENT, "isDocent");
        }
    }
}

package unipotsdam.gf.session;

import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class GFContexts {

    @Inject
    UserDAO userDAO;

    public static final String USEREMAIL = "userEmail";
    public static final String PROJECTNAME = "projectName";
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
}

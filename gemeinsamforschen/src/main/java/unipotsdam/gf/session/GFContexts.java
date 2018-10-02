package unipotsdam.gf.session;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class GFContexts {
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
}

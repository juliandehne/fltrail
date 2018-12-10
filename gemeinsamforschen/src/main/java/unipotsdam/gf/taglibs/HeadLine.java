package unipotsdam.gf.taglibs;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.session.GFContexts;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class HeadLine extends SimpleTagSupport {

    @Inject
    UserDAO userDAO;

    public void doTag() throws IOException {

        final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());
        locator.inject(this);


        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String userEmail = request.getSession().getAttribute(GFContexts.USEREMAIL).toString();
        String projectName;
        try{
            projectName = request.getSession().getAttribute(GFContexts.PROJECTNAME).toString();
        }catch ( Exception e){
            projectName = null;
        }

        JspWriter out = getJspContext().getOut();

        User user = userDAO.getUserByEmail(userEmail);
        Boolean isStudent = user.getStudent();
        out.println("<h1 id=\"headLineProject\">");
        if (projectName != null) {
            out.println(projectName);
        } else {
            if (isStudent) {
                out.println("Studentenübersicht " + user.getName());
            } else {
                out.println("Dozentenübersicht " + user.getName());
            }
        }
        out.println("</h1>\n");
    }
}
package unipotsdam.gf.taglibs;

import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.session.GFContexts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;


public class Menu extends SimpleTagSupport {

    private Integer hierarchyLevel = 0;

    public void doTag() throws IOException {
        hierarchyLevel = getHierarchy();
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        if (request.getSession().getAttribute(GFContexts.USEREMAIL) == null) {
            throw new IOException("No User Session");
        }

        String userEmail = request.getSession().getAttribute(GFContexts.USEREMAIL).toString();
        String projectName=request.getParameter(GFContexts.PROJECTNAME);
        JspWriter out = getJspContext().getOut();
        UserDAO userDAO = new UserDAO(new MysqlConnect());
        if (userEmail != null) {
            User user = userDAO.getUserByEmail(userEmail);
            Boolean isStudent = user.getStudent();
            String menuString = "" +
                    "    <nav class=\"navbar navbar-dark\" style=\"background-color:#2020D0;\">\n" +
                    "        <ul class=\"nav navbar-nav navbar-left\">\n" +
                    "            <li class=\"nav-item\"><a class=\"nav-link\" href=\"" + OmniDependencies.hierarchyToString(hierarchyLevel) + "overview-student.jsp?projectName=" + projectName + "\">meine Projekte</a></li>\n" +
                    "            <li class=\"nav-item\"><a class=\"nav-link\" href=\"" + OmniDependencies.hierarchyToString(hierarchyLevel) + "profile/profile.jsp?projectName=" + projectName + "\">Profil</a></li>\n"+
                    "        </ul>" +
                    "        <ul class=\"nav navbar-nav navbar-right\">" +
                    "            <li class=\"nav-item\"><a class=\"nav-link\" id=\"logout\" style=\"cursor:pointer\">Logout</a></li>\n" +
                    "            <li class=\"nav-item\"><a>    </a></li>"+
                    "        </ul>\n" +
                    "    </nav>";
            out.println(menuString);
            if (isStudent) {
                out.println("<p id=\"userRole\" hidden>isStudent</p>");
            } else {
                out.println("<p id=\"userRole\" hidden>isDocent</p>");
            }
        } else {
            out.println("<div class='alert alert-warning'>" +
                    "You probably did not give the token to the url" +
                    "</div>");
            //in active System this will be the point to redirect to index.jsp, because token is "wrong"
        }
        if (projectName != null)
            out.println("<p id=\"projectName\" hidden>" + projectName + "</p>");
        User user = userDAO.getUserByEmail(userEmail);
        if (user != null)
            out.println("<p id=\"userEmail\" hidden>" + user.getEmail() + "</p>");
        out.println("<p id=\"hierarchyLevel\" hidden>" + hierarchyLevel.toString() + "</p>");


    }

    public Integer getHierarchy() {
        return hierarchyLevel;
    }

    public void setHierarchy(Integer hierarchyLevel) {
        this.hierarchyLevel = hierarchyLevel;
    }
}
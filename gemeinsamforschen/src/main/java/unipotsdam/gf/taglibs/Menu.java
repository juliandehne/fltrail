package unipotsdam.gf.taglibs;

import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.states.ProjectPhase;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.session.GFContexts;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;


public class Menu extends SimpleTagSupport {

    //@Inject
    //private ProjectDAO projectDAO;

    private Integer hierarchyLevel = 0;

    public void doTag() throws IOException {
        ProjectDAO projectDAO = new ProjectDAO(new MysqlConnect());
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
                    "    <header>\n" +
                    "        <div class=\"row\">\n" +
                    "            <div class=\"nav-group-left\">" +
                    "                <a class=\"nav-link\" href=\"" + OmniDependencies.hierarchyToString(hierarchyLevel) + "project/overview-student.jsp?projectName=" + projectName + "\">meine Projekte</a>\n" +
                    "                <a class=\"nav-link\" href=\"" + OmniDependencies.hierarchyToString(hierarchyLevel) + "profile/profile.jsp?projectName=" + projectName + "\">Profil</a>\n"+
                    "        </div>" +
                    "        <div class=\"nav-group-right\">" +
                    "            <a class=\"nav-link\" id=\"logout\" style=\"cursor:pointer\">Logout</a>\n" +
                    "        </div>\n" +
                    "    </div>" +
                    "    </header>";
            out.println(menuString);
            if (isStudent) {
                out.println("<p id=\"userRole\" hidden>isStudent</p>");
            } else {
                out.println("<p id=\"userRole\" hidden>isDocent</p>");
            }
        }
        ProjectPhase projectPhase;
        try {
            projectPhase = projectDAO.getProjectByName(projectName).getPhase();
        } catch (Exception e) {
            projectPhase = null;
        }
        String phaseViewString = "" +
                "<main>\n" +
                "    <div class=\"row group\">\n" +
                "        <div class=\"titlerow\">\n" +
                "\n" +
                "        </div>\n" +
                "    </div>\n" +
                "\n" +
                "    <div class=\"row group nav\">\n" +
                "        <a href=\"\" >[<i class=\"fas fa-chevron-left\"> zurueck ]</i></a>\n" +
                "    </div>\n" +
                "\n" +
                "    <div class=\"row group\">" +
                "<div class=\"col span_s_of_2 .timeline\">\n" +
                "        <ul>\n";
        if (projectPhase != null)
            switch (projectPhase) {
                case CourseCreation: {
                    phaseViewString += "  <li class=\"neutral icon\">Projektinitialisierung</li>\n" +
                            "          <li class=\"icon inactive\">Entwurfsphase</li>\n" +
                            "          <li class=\"icon inactive\">Feedbackphase</li>\n" +
                            "          <li class=\"icon inactive\">Reflextionsphase</li>\n" +
                            "          <li class=\"icon inactive\" >Assessment</li>\n" +
                            "          <li class=\"icon inactive\">Noten</li>\n";
                    break;
                }
                case GroupFormation:{
                    phaseViewString += "  <li class=\"neutral icon closed\">Projektinitialisierung</li>\n" +
                            "          <li class=\"draft icon \">Entwurfsphase</li>\n" +
                            "          <li class=\"icon inactive\">Feedbackphase</li>\n" +
                            "          <li class=\"icon inactive\">Reflextionsphase</li>\n" +
                            "          <li class=\"icon inactive\" >Assessment</li>\n" +
                            "          <li class=\"icon inactive\">Noten</li>\n";
                    break;
                }
                case Execution:{
                    phaseViewString += "  <li class=\"neutral icon closed\">Projektinitialisierung</li>\n" +
                            "          <li class=\"draft icon closed\">Entwurfsphase</li>\n" +
                            "          <li class=\"feedback icon\">Feedbackphase</li>\n" +
                            "          <li class=\"icon inactive\">Reflextionsphase</li>\n" +
                            "          <li class=\"icon inactive\" >Assessment</li>\n" +
                            "          <li class=\"icon inactive\">Noten</li>\n";
                    break;
                }
                case DossierFeedback:{
                    phaseViewString += "  <li class=\"neutral icon closed\">Projektinitialisierung</li>\n" +
                            "          <li class=\"draft icon closed\">Entwurfsphase</li>\n" +
                            "          <li class=\"feedback icon closed\">Feedbackphase</li>\n" +
                            "          <li class=\"icon\">Reflextionsphase</li>\n" +
                            "          <li class=\"icon inactive\" >Assessment</li>\n" +
                            "          <li class=\"icon inactive\">Noten</li>\n";
                    break;
                }
                case Assessment:{
                    phaseViewString += "  <li class=\"neutral icon closed\">Projektinitialisierung</li>\n" +
                            "          <li class=\"draft icon closed\">Entwurfsphase</li>\n" +
                            "          <li class=\"feedback icon closed\">Feedbackphase</li>\n" +
                            "          <li class=\"icon closed\">Reflextionsphase</li>\n" +
                            "          <li class=\"icon\" >Assessment</li>\n" +
                            "          <li class=\"icon inactive\">Noten</li>\n";
                    break;
                }
                case Projectfinished:{
                    phaseViewString += "  <li class=\"neutral icon closed\">Projektinitialisierung</li>\n" +
                            "          <li class=\"draft icon closed\">Entwurfsphase</li>\n" +
                            "          <li class=\"feedback icon closed\">Feedbackphase</li>\n" +
                            "          <li class=\"icon closed\">Reflextionsphase</li>\n" +
                            "          <li class=\"icon closed\" >Assessment</li>\n" +
                            "          <li class=\"icon\">Noten</li>\n";
                    break;
                }
            }
        phaseViewString += "" +
                "        </ul>\n" +
                "    </div>";
        out.println(phaseViewString);

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
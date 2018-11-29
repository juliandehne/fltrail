package unipotsdam.gf.taglibs;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.process.phases.Phase;
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

    @Inject
    private UserDAO userDAO;

    @Inject
    private ProjectDAO projectDAO;

    //@Inject
    //private ProjectDAO projectDAO;

    private Integer hierarchyLevel = 0;

    public void doTag() throws IOException {

        final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());
        locator.inject(this);

        hierarchyLevel = getHierarchy();
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        if (request.getSession().getAttribute(GFContexts.USEREMAIL) == null) {
            throw new IOException("No User Session");
        }

        String userEmail = request.getSession().getAttribute(GFContexts.USEREMAIL).toString();
        String projectName=request.getParameter(GFContexts.PROJECTNAME);
        JspWriter out = getJspContext().getOut();
        User user = userDAO.getUserByEmail(userEmail);
        if (userEmail != null) {
            Boolean isStudent = user.getStudent();
            String menuString = "" +
                    "    <header>\n" +
                    "        <div class=\"row\">\n" +
                    "            <div class=\"nav-group-left\">";
            if (isStudent){
                menuString+="<a class=\"nav-link\" style=\"color:white;\" href=\"" + OmniDependencies.hierarchyToString(hierarchyLevel) + "project/courses-student.jsp\">Home</a>\n"+
                        "<a class=\"nav-link\" style=\"color:white;\" href=\"" + OmniDependencies.hierarchyToString(hierarchyLevel) + "project/courses-student.jsp?all=true\">suche Kurs</a>\n";
            }else{
                menuString+="                <a class=\"nav-link\" style=\"color:white;\" href=\"" + OmniDependencies.hierarchyToString(hierarchyLevel) + "project/overview-docent.jsp\">meine Projekte</a>\n";
            }
            menuString+="                <a class=\"nav-link\" href=\"" + OmniDependencies.hierarchyToString(hierarchyLevel) + "profile/profile.jsp?projectName=" + projectName + "\">"+user.getName()+"</a>\n"+
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
        Phase phase;
        try {
            phase = projectDAO.getProjectByName(projectName).getPhase();
        } catch (Exception e) {
            phase = null;
        }
        String phaseViewString = "" +
                "<main>\n" +
                "    <div class=\"row group\">\n";
        if (projectName != null){
            phaseViewString += "<div class=\"titlerow\">\n <h1 id=\"projectHeadline\">\n"
                    +projectName+ "  </h1>      </div>\n";
        }
        phaseViewString += "" +
                "    </div>\n" +
                "\n" +
                "    <div class=\"row nav\">\n" +
                "    </div>\n" +
                "\n" +
                "    <div class=\"row group\">" +
                "<div class=\"col span_timeline .timeline\">\n" +
                "        <ul>\n";
        if (phase != null)
            switch (phase) {
                case GroupFormation:{
                    phaseViewString += "  <li class=\"neutral icon closed\">Projektinitialisierung</li>\n" +
                            "          <li class=\"draft icon \">Entwurfsphase</li>\n" +
                            "          <li class=\"icon inactive\">Feedbackphase</li>\n" +
                            "          <li class=\"icon inactive\">Reflextionsphase</li>\n" +
                            "          <li class=\"icon inactive\" >Assessment</li>\n" +
                            "          <li class=\"icon inactive\">Noten</li>\n";
                    break;
                }
                case DossierFeedback:{
                    phaseViewString += "  <li class=\"neutral icon closed\">Projektinitialisierung</li>\n" +
                            "          <li class=\"draft icon closed\">Entwurfsphase</li>\n" +
                            "          <li class=\"feedback icon\">Feedbackphase</li>\n" +
                            "          <li class=\"icon inactive\">Reflextionsphase</li>\n" +
                            "          <li class=\"icon inactive\" >Assessment</li>\n" +
                            "          <li class=\"icon inactive\">Noten</li>\n";
                    break;
                }
                case Execution:{
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
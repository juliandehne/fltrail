package unipotsdam.gf.core.management.pageAppearance;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.management.project.ProjectDAO;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.user.UserDAO;
import unipotsdam.gf.core.session.GFContexts;
import unipotsdam.gf.core.states.model.ProjectPhase;

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
        String userEmail = request.getSession().getAttribute(GFContexts.USEREMAIL).toString();
        String projectName;
        try {
            projectName = request.getSession().getAttribute(GFContexts.PROJECTNAME).toString();
        } catch(Exception e){
            projectName = "";
        }
        ProjectPhase projectPhase;
        try {
            ProjectDAO projectDAO = new ProjectDAO(new MysqlConnect());
            projectPhase = projectDAO.getProjectById(projectName).getPhase();
        } catch (Exception e) {
            projectPhase = null;
        }
        JspWriter out = getJspContext().getOut();
        UserDAO userDAO = new UserDAO(new MysqlConnect());
        if (userEmail != null) {
            User user = userDAO.getUserByEmail(userEmail);
            Boolean isStudent = user.getStudent();
            if (isStudent) {
                String menuString = "<div id=\"sidebar-wrapper\">\n" +
                        "        <ul class=\"sidebar-nav\">\n" +
                        "            <li class=\"sidebar-brand\"><a href=\"" + OmniDependencies.hierarchyToString(hierarchyLevel) + "overview-student.jsp?token=" + userEmail + "&projectId=" + projectName + "\">overview</a></li>\n" +
                        "            <li><a href=\"" + OmniDependencies.hierarchyToString(hierarchyLevel) + "profile/profile.jsp?token=" + userEmail + "&projectId=" + projectName + "\">Profil</a></li>\n";
                if (projectPhase != null) {
                    switch (projectPhase){
                        case CourseCreation:{
                            menuString += "      <li><p>Quizfrage</p></li>\n" +
                                    "            <li><a href=\"" + OmniDependencies.hierarchyToString(hierarchyLevel) + "journal/eportfolio.jsp?token=" + userEmail + "&projectId=" + projectName + "\">ePortfolio</a></li>\n" +
                                    "            <li><p>Beitrag</p></li>\n" +
                                    "            <li><p>Bewertung</p></li>\n";
                            break;
                        }
                        case GroupFormation:{
                            menuString += "      <li><p>Quizfrage</p></li>\n" +
                                    "            <li><a href=\"" + OmniDependencies.hierarchyToString(hierarchyLevel) + "journal/eportfolio.jsp?token=" + userEmail + "&projectId=" + projectName + "\">ePortfolio</a></li>\n" +
                                    "            <li><p>Beitrag</p></li>\n" +
                                    "            <li><p>Bewertung</p></li>\n";
                            break;
                        }
                        case DossierFeedback:{
                            menuString += "      <li><p>Quizfrage</p></li>\n" +
                                    "            <li><a href=\"" + OmniDependencies.hierarchyToString(hierarchyLevel) + "journal/eportfolio.jsp?token=" + userEmail + "&projectId=" + projectName + "\">ePortfolio</a></li>\n" +
                                    "            <li><a href=\"" + OmniDependencies.hierarchyToString(hierarchyLevel) + "researchReport/create-title.jsp?token=" + userEmail + "&projectId=" + projectName + "\">Beitrag</a></li>\n" +
                                    "            <li><p>Bewertung</p></li>\n";
                            break;
                        }
                        case Execution:{
                            menuString += "      <li><a href=\"" + OmniDependencies.hierarchyToString(hierarchyLevel) + "assessment/Quiz.jsp?token=" + userEmail + "&projectId=" + projectName + "\">Quizfrage</a></li>\n" +
                                    "            <li><a href=\"" + OmniDependencies.hierarchyToString(hierarchyLevel) + "journal/eportfolio.jsp?token=" + userEmail + "&projectId=" + projectName + "\">ePortfolio</a></li>\n" +
                                    "            <li><a href=\"" + OmniDependencies.hierarchyToString(hierarchyLevel) + "researchReport/create-title.jsp?token=" + userEmail + "&projectId=" + projectName + "\">Beitrag</a></li>\n" +
                                    "            <li><p>Bewertung</p></li>\n";
                            break;
                        }
                        case Assessment:{
                            menuString += "      <li><p>Quizfrage</p></li>\n" +
                                    "            <li><p>ePortfolio</p></li>\n" +
                                    "            <li><p>Beitrag</p></li>\n" +
                                    "            <li><a id=\"assessment\" style=\"cursor:pointer\">Bewertung</a></li>\n";
                            break;
                        }
                        case Projectfinished:{
                            menuString += "      <li><p>Quizfrage</p></li>\n" +
                                    "            <li><p>ePortfolio</p></li>\n" +
                                    "            <li><p>Beitrag</p></li>\n" +
                                    "            <li><p>Bewertung</p></li>\n";
                            break;
                        }
                    }
                }

                menuString += "<li>" +
                        "<a id=\"logout\" style=\"cursor:pointer\">Logout</a></li>\n" +
                        "</ul>\n" +
                        "    </div>";
                out.println(menuString);
                out.println("<p id=\"userRole\" hidden>isStudent</p>");
            } else {
                String menuString = "<div id=\"sidebar-wrapper\">\n" +
                        "        <ul class=\"sidebar-nav\">\n" +
                        "            <li class=\"sidebar-brand\"><a href=\"" + OmniDependencies.hierarchyToString(hierarchyLevel) + "overview-docent.jsp?token=" + userEmail + "&projectId=" + projectName + "\">overview</a></li>\n";
                if (projectPhase != null) {
                    if (!projectPhase.equals(ProjectPhase.GroupFormation)) {
                        menuString += "<li><a href=\"" + OmniDependencies.hierarchyToString(hierarchyLevel) + "assessment/Quiz-docent.jsp?token=" + userEmail + "&projectId=" + projectName + "\">Quizfrage</a></li>\n" +
                                "      <li><p>Gruppen erstellen</p></li>\n" +
                                "      <li><a href=\"" + OmniDependencies.hierarchyToString(hierarchyLevel) + "management/change-phase.jsp?token=" + userEmail + "&projectId=" + projectName + "\">Projektphase ändern</a></li>\n";
                    } else {
                        menuString += "<li><a href=\"" + OmniDependencies.hierarchyToString(hierarchyLevel) + "assessment/quiz-docent.jsp?token=" + userEmail + "&projectId=" + projectName + "\">Quizfrage</a></li>\n" +
                                "      <li><a href=\"" + OmniDependencies.hierarchyToString(hierarchyLevel) + "groupfinding/create-groups.jsp?token=" + userEmail + "&projectId=" + projectName + "\">Gruppen erstellen</a></li>\n" +
                                "      <li><a href=\"" + OmniDependencies.hierarchyToString(hierarchyLevel) + "management/change-phase.jsp?token=" + userEmail + "&projectId=" + projectName + "\">Projektphase ändern</a></li>\n";
                    }
                }
                menuString += "<li><a id=\"logout\" style=\"cursor:pointer\">Logout</a></li>\n" +
                        "</ul>\n" +
                        "    </div>";
                out.println(menuString);
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
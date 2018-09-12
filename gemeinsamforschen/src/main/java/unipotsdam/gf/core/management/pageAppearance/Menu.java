package unipotsdam.gf.core.management.pageAppearance;

import unipotsdam.gf.core.management.ManagementImpl;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.states.ProjectPhase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class Menu extends SimpleTagSupport {
    public void doTag() throws IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String token = request.getParameter("token");
        String projectId = request.getParameter("projectId");
        ManagementImpl management = new ManagementImpl();
        ProjectPhase projectPhase;
        try{
            projectPhase = management.getProjectById(projectId).getPhase();
        }catch(Exception e){
            projectPhase = null;
        }
        JspWriter out = getJspContext().getOut();
        if (token!=null){
            User user =  management.getUserByToken(token);
            Boolean isStudent = user.getStudent();
            if (isStudent){
                String menuString = "<div id=\"sidebar-wrapper\">\n" +
                        "        <ul class=\"sidebar-nav\">\n" +
                        "            <li class=\"sidebar-brand\"><a href=\"overview-student.jsp?token="+token+"&projectId="+projectId+"\">overview</a></li>\n" +
                        "            <li><a href=\"profile.jsp?token="+token+"&projectId="+projectId+"\">Profil</a></li>\n";
                if (projectPhase!=null){
                    if (projectPhase.equals(ProjectPhase.CourseCreation)){
                        menuString += "      <li><p>Quizfrage</p></li>\n" +
                                "            <li><a href=\"eportfolio.jsp?token="+token+"&projectId="+projectId+"\">ePortfolio</a></li>\n" +
                                "            <li><p>Beitrag</p></li>\n" +
                                "            <li><p>Bewertung</p></li>\n";
                    }
                    if (projectPhase.equals(ProjectPhase.GroupFormation)){
                        menuString += "      <li><p>Quizfrage</p></li>\n" +
                                "            <li><a href=\"eportfolio.jsp?token="+token+"&projectId="+projectId+"\">ePortfolio</a></li>\n" +
                                "            <li><p>Beitrag</p></li>\n" +
                                "            <li><p>Bewertung</p></li>\n";
                    }
                    if (projectPhase.equals(ProjectPhase.DossierFeedback)){
                        menuString += "      <li><p>Quizfrage</p></li>\n" +
                                "            <li><a href=\"eportfolio.jsp?token="+token+"&projectId="+projectId+"\">ePortfolio</a></li>\n" +
                                "            <li><a href=\"researchReportTitle.jsp?token="+token+"&projectId="+projectId+"\">Beitrag</a></li>\n" +
                                "            <li><p>Bewertung</p></li>\n";
                    }
                    if (projectPhase.equals(ProjectPhase.Execution)){
                        menuString += "      <li><a href=\"Quiz.jsp?token="+token+"&projectId="+projectId+"\">Quizfrage</a></li>\n" +
                                "            <li><a href=\"eportfolio.jsp?token="+token+"&projectId="+projectId+"\">ePortfolio</a></li>\n" +
                                "            <li><a href=\"researchReportTitle.jsp?token="+token+"&projectId="+projectId+"\">Beitrag</a></li>\n" +
                                "            <li><p>Bewertung</p></li>\n";
                    }
                    if (projectPhase.equals(ProjectPhase.Assessment)){
                        menuString += "      <li><p>Quizfrage</p></li>\n" +
                                "            <li><p>ePortfolio</p></li>\n" +
                                "            <li><p>Beitrag</p></li>\n" +
                                "            <li><a id=\"assessment\" style=\"cursor:pointer\">Bewertung</a></li>\n";
                    }
                }

                menuString +="<li>" +
                        "<a id=\"logout\" style=\"cursor:pointer\">Logout</a></li>\n" +
                        "</ul>\n" +
                        "    </div>";
                out.println(menuString);
                out.println("<p id=\"userRole\" hidden>isStudent</p>");
            } else {
                String menuString ="<div id=\"sidebar-wrapper\">\n" +
                        "        <ul class=\"sidebar-nav\">\n" +
                        "            <li class=\"sidebar-brand\"><a href=\"overview-docent.jsp?token="+token+"&projectId="+projectId+"\">overview</a></li>\n";
                if (projectPhase!=null) {
                    if (!projectPhase.equals(ProjectPhase.GroupFormation)) {
                        menuString += "<li><a href=\"Quiz-docent.jsp?token="+token+"&projectId="+projectId+"\">Quizfrage</a></li>\n" +
                                "      <li><p>Gruppen erstellen</p></li>\n" +
                                "      <li><a href=\"changePhase.jsp?token="+token+"&projectId="+projectId+"\">Projektphase ändern</a></li>\n";
                    }else {
                        menuString += "<li><a href=\"Quiz-docent.jsp?token="+token+"&projectId="+projectId+"\">Quizfrage</a></li>\n" +
                                "      <li><a href=\"createGroups.jsp?token="+token+"&projectId="+projectId+"\">Gruppen erstellen</a></li>\n" +
                                "      <li><a href=\"changePhase.jsp?token="+token+"&projectId="+projectId+"\">Projektphase ändern</a></li>\n";
                    }
                }
                menuString +="<li><a id=\"logout\" style=\"cursor:pointer\">Logout</a></li>\n" +
                        "</ul>\n" +
                        "    </div>";
                out.println(menuString);
                out.println("<p id=\"userRole\" hidden>isDocent</p>");
            }
        }else{
            out.println("<div class='alert alert-warning'>" +
                    "You probably did not give the token to the url" +
                    "</div>");
            //in active System this will be the point to redirect to index.jsp, because token is "wrong"
        }
        if (projectId!=null)
            out.println("<p id=\"projectId\" hidden>"+projectId+"</p>");
        User user = management.getUserByToken(token);
        if (user != null)
            out.println("<p id=\"user\" hidden>"+user.getName()+"</p>");



    }


}
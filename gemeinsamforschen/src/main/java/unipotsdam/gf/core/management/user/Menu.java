package unipotsdam.gf.core.management.user;

import unipotsdam.gf.core.management.ManagementImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;


// TODO: please move this to a view package at the top of the hierarchy as this is not part of the user package
public class Menu extends SimpleTagSupport {
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String token = request.getParameter("token");
        String projectId = request.getParameter("projectId");
        ManagementImpl management = new ManagementImpl();
        JspWriter out = getJspContext().getOut();
        if (token!=null){
            User user =  management.getUserByToken(token);
            Boolean isStudent = user.getStudent();
            if (isStudent){
                out.println("<div id=\"sidebar-wrapper\">\n" +
                        "        <ul class=\"sidebar-nav\">\n" +
                        "            <li class=\"sidebar-brand\"><a href=\"overview-student.jsp?token="+token+"&projectId="+projectId+"\">overview</a></li>\n" +
                        "            <li><a href=\"profile.jsp?token="+token+"&projectId="+projectId+"\">Profil</a></li>\n" +
                        "            <li><a href=\"Quiz.jsp?token="+token+"&projectId="+projectId+"\">Quizfrage</a></li>\n" +
                        "            <li><a href=\"eportfolio.jsp?token="+token+"&projectId="+projectId+"\">ePortfolio</a></li>\n" +
                        "            <li><a href=\"researchReportTitle.jsp?token="+token+"&projectId="+projectId+"\">Beitrag</a></li>\n" +
                        "            <li><a href=\"finalAssessments.jsp?token="+token+"&projectId="+projectId+"\">Bewertung</a></li>\n" +
                        "            <li><a href=\"../index.jsp\">Logout</a></li>\n" +
                        "        </ul>\n" +
                        "    </div>");
            } else {
                out.println("<div id=\"sidebar-wrapper\">\n" +
                        "        <ul class=\"sidebar-nav\">\n" +
                        "            <li class=\"sidebar-brand\"><a href=\"overview-docent.jsp?token="+token+"&projectId="+projectId+"\">overview</a></li>\n" +
                        "            <li><a href=\"Quiz-docent.jsp?token="+token+"&projectId="+projectId+"\">Quizfrage</a></li>\n" +
                        "            <li><a href=\"#\">ePortfolio</a></li>\n" +
                        "            <li><a href=\"#\">Beitrag</a></li>\n" +
                        "            <li><a href=\"#\">Gruppen erstellen</a></li>\n" +
                        "            <li><a href=\"#\">Projektphase ändern</a></li>\n" +
                        "            <li><a href=\"finalAssessments.jsp?token="+token+"\">Bewertung</a></li>\n" +
                        "            <li><a href=\"../index.jsp\">Logout</a></li>\n" +
                        "        </ul>\n" +
                        "    </div>");
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
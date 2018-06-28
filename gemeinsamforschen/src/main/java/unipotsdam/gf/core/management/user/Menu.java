package unipotsdam.gf.core.management.user;

import unipotsdam.gf.core.management.ManagementImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class Menu extends SimpleTagSupport {
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String token = request.getParameter("token");
        ManagementImpl management = new ManagementImpl();
        JspWriter out = getJspContext().getOut();
        if (token!=null){
            User user =  management.getUserByToken(token);
            Boolean isStudent = user.getStudent();
            if (isStudent){
                out.println("<div id=\"sidebar-wrapper\">\n" +
                        "        <ul class=\"sidebar-nav\">\n" +
                        "            <li class=\"sidebar-brand\"><a href=\"overview-student.jsp?token="+token+"\">overview</a></li>\n" +
                        "            <li><a href=\"Quiz.jsp?token="+token+"\">Quizfrage</a></li>\n" +
                        "            <li><a href=\"#\">ePortfolio</a></li>\n" +
                        "            <li><a href=\"#\">Beitrag</a></li>\n" +
                        "            <li><a href=\"finalAssessments.jsp?token="+token+"\">Bewertung</a></li>\n" +
                        "            <li><a href=\"../index.jsp\">Logout</a></li>\n" +
                        "        </ul>\n" +
                        "    </div>");
            } else {
                out.println("<div id=\"sidebar-wrapper\">\n" +
                        "        <ul class=\"sidebar-nav\">\n" +
                        "            <li class=\"sidebar-brand\"><a href=\"overview-docent.jsp?token="+token+"\">overview</a></li>\n" +
                        "            <li><a href=\"Quiz.jsp?token="+token+"\">Quizfrage</a></li>\n" +
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


    }


};
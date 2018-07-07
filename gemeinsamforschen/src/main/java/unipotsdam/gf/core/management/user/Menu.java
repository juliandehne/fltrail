package unipotsdam.gf.core.management.user;

import unipotsdam.gf.core.management.Management;
import unipotsdam.gf.core.management.ManagementImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class Menu extends SimpleTagSupport {
    public void doTag() throws JspException, IOException {
        Management management = new ManagementImpl();
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String token = request.getParameter("token");
        User user =  management.getUserByToken(token);
        JspWriter out = getJspContext().getOut();
        Boolean isStudent = user.getStudent();
        if (isStudent){
            out.println("<div id=\"sidebar-wrapper\">\n" +
                    "        <ul class=\"sidebar-nav\">\n" +
                    "            <li class=\"sidebar-brand\"><a href=\"overview-student.html\">overview</a></li>\n" +
                    "            <li><a href=\"takeQuiz.jsp\">Quizfrage</a></li>\n" +
                    "            <li><a href=\"eportfolio.jsp\">ePortfolio</a></li>\n" +
                    "            <li><a href=\"#\">Beitrag</a></li>\n" +
                    "            <li><a href=\"finalAssessments.jsp\">Bewertung</a></li>\n" +
                    "            <li><a href=\"../index.jsp\">Logout</a></li>\n" +
                    "        </ul>\n" +
                    "    </div>");
        } else {
            out.println("<div id=\"sidebar-wrapper\">\n" +
                    "        <ul class=\"sidebar-nav\">\n" +
                    "            <li class=\"sidebar-brand\"><a href=\"overview-docent.html\">overview</a></li>\n" +
                    "            <li><a href=\"createQuiz.jsp\">Quizfrage</a></li>\n" +
                    "            <li><a href=\"#\">ePortfolio</a></li>\n" +
                    "            <li><a href=\"#\">Beitrag</a></li>\n" +
                    "            <li><a href=\"#\">Gruppen erstellen</a></li>\n" +
                    "            <li><a href=\"#\">Projektphase ändern</a></li>\n" +
                    "            <li><a href=\"finalAssessments.jsp\">Bewertung</a></li>\n" +
                    "            <li><a href=\"../index.jsp\">Logout</a></li>\n" +
                    "        </ul>\n" +
                    "    </div>");
        }

    }
};
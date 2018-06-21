package unipotsdam.gf.core.management.user;

import unipotsdam.gf.core.management.ManagementImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
                                            //todo: isStudent is false for docents. use this for dynamic menus
public class Menu extends SimpleTagSupport {
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        out.println("<div id=\"sidebar-wrapper\">\n" +
                "        <ul class=\"sidebar-nav\">\n" +
                "            <li class=\"sidebar-brand\"><a href=\"project-student.jsp\">overview</a></li>\n" +
                "            <li><a href=\"takeQuiz.jsp\">Quizfrage</a></li>\n" +
                "            <li><a href=\"#\">ePortfolio</a></li>\n" +
                "            <li><a href=\"#\">Beitrag</a></li>\n" +
                "            <li><a href=\"finalAssessments.jsp\">Bewertung</a></li>\n" +
                "            <li><a href=\"../index.jsp\">Logout</a></li>\n" +
                "        </ul>\n" +
                "    </div>");
    }
}

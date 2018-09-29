package unipotsdam.gf.core.management.pageAppearance;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.user.UserDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class HeadLine extends SimpleTagSupport {

    public void doTag() throws IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String projectId = request.getParameter("projectId");
        String token = request.getParameter("token");
        JspWriter out = getJspContext().getOut();
        UserDAO userDAO = new UserDAO(new MysqlConnect());
        User user = userDAO.getUserByToken(token);
        Boolean isStudent = user.getStudent();
        out.println("<div class=\"container-fluid\">\n" +
                "            <table style=\"width:100%\">\n" +
                "                <tr>\n" +
                "                    <td style=\"width:70%\"><h2 id=\"headLineProject\">");
        if (projectId != null) {
            out.println(projectId);
        } else {
            if (isStudent) {
                out.println("Studentenübersicht " + user.getName());
            } else {
                out.println("Dozentenübersicht " + user.getName());
            }

        }
        out.println("</h2></td>\n" +
                "                    <td style=\"width:30%\">\n" +
                "                        <div align=\"right\" class=\"dropdown\">\n" +
                "                        <a href=\"../feedback/view-feedback.jsp?token="+token+"\">\n" +
                "                    <span class=\"glyphicon glyphicon-envelope\"\n" +
                "                          style=\"font-size:29px;margin-right:30px;\"></span>\n" +
                "                        </a>\n" +
                "                    </div>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "        </div>");
    }
}
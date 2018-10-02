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

public class HeadLine extends SimpleTagSupport {

    public void doTag() throws IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String userEmail = request.getSession().getAttribute(GFContexts.USEREMAIL).toString();
        String projectName;
        try{
            projectName = request.getSession().getAttribute(GFContexts.PROJECTNAME).toString();
        }catch ( Exception e){
            projectName = null;
        }

        JspWriter out = getJspContext().getOut();
        UserDAO userDAO = new UserDAO(new MysqlConnect());
        User user = userDAO.getUserByEmail(userEmail);
        Boolean isStudent = user.getStudent();
        out.println("<div class=\"container-fluid\">\n" +
                "            <table style=\"width:100%\">\n" +
                "                <tr>\n" +
                "                    <td style=\"width:70%\"><h2 id=\"headLineProject\">");
        if (projectName != null) {
            out.println(projectName);
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
                "                        <button style=\"right: 50px;margin-top:-4px;\" class=\"btn btn-primary dropdown-toggle\" type=\"button\"\n" +
                "                                data-toggle=\"dropdown\">\n" +
                "\n" +
                "                            <i class=\"glyphicon glyphicon-envelope\"></i>\n" +
                "                        </button>\n" +
                "\n" +
                "                        <ul class=\"dropdown-menu\">\n" +
                "                            <li><a class=\"viewfeedback\" role=\"button\">Feedback A</a></li>\n" +
                "                            <li><a class=\"viewfeedback\" role=\"button\">Feedback B</a></li>\n" +
                "                            <li><a class=\"viewfeedback\" role=\"button\">Feedback C</a></li>\n" +
                "                        </ul>\n" +
                "\n" +
                "                        <a href=\"#\">\n" +
                "                    <span class=\"glyphicon glyphicon-cog\"\n" +
                "                          style=\"font-size:29px;margin-right:30px;\"></span>\n" +
                "                        </a>\n" +
                "                    </div>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "        </div>");
    }
}
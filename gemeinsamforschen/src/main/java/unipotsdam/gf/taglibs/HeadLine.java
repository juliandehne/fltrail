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
        out.println("<h1 id=\"headLineProject\">");
        if (projectName != null) {
            out.println(projectName);
        } else {
            if (isStudent) {
                out.println("Studentenübersicht " + user.getName());
            } else {
                out.println("Dozentenübersicht " + user.getName());
            }

        }
        out.println("</h1>\n");
    }
}
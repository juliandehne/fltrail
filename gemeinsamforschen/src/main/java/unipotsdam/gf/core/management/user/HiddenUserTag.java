package unipotsdam.gf.core.management.user;

import unipotsdam.gf.core.management.ManagementImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * implemented while porting the login page. It might be useful to have a hidden user field on the page in order to
 * manipulate the user data with jquery
 */
public class HiddenUserTag extends SimpleTagSupport {
    public void doTag() throws IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String token = request.getParameter("token");

        ManagementImpl management = new ManagementImpl();
        User user = management.getUserByToken(token);
        JspWriter out = getJspContext().getOut();
        out.println("<p id=\"user\" hidden>"+user.getName()+"</p>");
    }
}

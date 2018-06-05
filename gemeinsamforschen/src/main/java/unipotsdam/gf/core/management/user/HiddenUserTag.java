package unipotsdam.gf.core.management.user;

import unipotsdam.gf.core.management.ManagementImpl;
import unipotsdam.gf.core.management.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import java.io.*;

/**
 * implemented while porting the login page. It might be useful to have a hidden user field on the page in order to
 * manipulate the user data with jquery
 */
public class HiddenUserTag extends SimpleTagSupport {
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String token = request.getParameter("token");
        ManagementImpl management = new ManagementImpl();
        User user = management.getUser(token);
        JspWriter out = getJspContext().getOut();
        out.println("<p id=\"user\" hidden>"+user.getName()+"</p>");
    }
}

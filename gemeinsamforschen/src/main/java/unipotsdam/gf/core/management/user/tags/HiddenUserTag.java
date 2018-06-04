package unipotsdam.gf.core.management.user.tags;

import unipotsdam.gf.core.management.ManagementImpl;
import unipotsdam.gf.core.management.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import java.io.*;

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

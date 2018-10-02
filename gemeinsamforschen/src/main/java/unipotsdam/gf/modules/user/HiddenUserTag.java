package unipotsdam.gf.modules.user;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * implemented while porting the login page. It might be useful to have a hidden user field on the page in order to
 * manipulate the user data with jquery
 */
@ManagedBean
public class HiddenUserTag extends SimpleTagSupport {

    @Inject
    UserDAO userDAO;

    public void doTag() throws IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String token = request.getParameter("token");

        User user = userDAO.getUserByEmail(token);
        JspWriter out = getJspContext().getOut();
        out.println("<p id=\"user\" hidden>" + user.getName() + "</p>");
    }
}

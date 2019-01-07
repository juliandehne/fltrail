package unipotsdam.gf.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.xml.ws.WebServiceException;

public class RoleValidator extends SimpleTagSupport {

    private String role = "public";

    public void doTag(){

        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String userEmail = request.getSession().getAttribute(GFContexts.USEREMAIL).toString();
        String projectName = request.getParameter(GFContexts.PROJECTNAME);

        SessionValidator sessionValidator = new SessionValidator();
        Boolean valid = sessionValidator.validate(userEmail, projectName, getRole());
        if (!valid) {
            throw new WebServiceException("Not authenticated");
        }

    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

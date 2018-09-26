package unipotsdam.gf.core.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class ContextTag extends SimpleTagSupport {
    public void doTag() throws IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        // sessionID is created with first call and persisted throughout the user's session<
        JspWriter out = getJspContext().getOut();
        // lets add some context to the site

        /**
         * We assume that the project context is added to the session, when a project is selected
         * in the view, then the project is loaded from db and added via setAttribute like below
         */
        GFContext gfContext = (GFContext) request.getSession().getAttribute("gf_context");
        out.println("<p>project:" + gfContext.getProject().toString() + "</p>");

    }
}

package unipotsdam.gf.core.session;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.core.management.project.Project;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class SessionTag extends SimpleTagSupport {

    /**
     * Utility to creaty dummy data for students
     */
    PodamFactory factory = new PodamFactoryImpl();


    public void doTag() throws IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        // sessionID is created with first call and persisted throughout the user's session<
        JspWriter out = getJspContext().getOut();
        out.println("<p id=\"sessionId\"> SessionId:" + request.getSession().getId() + "</p>");

        // lets add some context to the site

        /**
         * We assume that the project context is added to the session, when a project is selected
         * in the view, then the project is loaded from db and added via setAttribute like below
         * this is only done here for the purpose of example
         */

        // create dummy context
        String context1 = factory.manufacturePojo(GFContext.class).toString();
        // set dummy context in sessions
        request.getSession().setAttribute("gf_context", context1);

        // you can update it
        Project project = factory.manufacturePojo(Project.class);
        GFContext context2 = (GFContext) request.getSession().getAttribute("gf_context");
        context2.setProject(project);
        // updated context set in session
        request.getSession().setAttribute("gf_context", context2);

    }
}

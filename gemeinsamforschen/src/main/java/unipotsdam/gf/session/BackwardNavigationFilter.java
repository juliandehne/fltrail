package unipotsdam.gf.session;

import fr.opensagres.xdocreport.utils.StringEscapeUtils;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unbescape.html.HtmlEscape;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.tasks.TaskDAO;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class BackwardNavigationFilter extends FlTrailfilter implements Filter {


    private final static Logger log = LoggerFactory.getLogger(BackwardNavigationFilter.class);
    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.encoding = filterConfig.getInitParameter("requestEncoding");
        if (encoding == null) encoding = "UTF-8";
    }

    @Override
    public void doFilter(
            ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request1 = (HttpServletRequest) request;
        log.debug("user session email in filter:" + request1.getSession().getAttribute(GFContexts.USEREMAIL));
        Object userEmail = request1.getSession().getAttribute(GFContexts.USEREMAIL);
        if (userEmail == null) {
            redirectToLogin(request, response);
            //request1.getSession().setAttribute(GFContexts.USEREMAIL, "vodkas@yolo.com");
            chain.doFilter(request, response);
        } else {
            contraintNotFullFilled(request, userEmail, response);
        }
        chain.doFilter(request, response);

    }

    protected abstract boolean contraintNotFullFilled(ServletRequest request, Object userEmail, ServletResponse
            response);


    protected void redirectToTasks(Project project, Boolean isStudent, ServletRequest request, ServletResponse
            response) {

     /*   String projectName = project.getName();
        String taskJSP = "../project/tasks-student.jsp?projectName="+projectName;
        if (!isStudent) {
            taskJSP = "../project/tasks-docent.jsp?projectName="+projectName;
        }*/
     // TODO fix this for umlaute
        String taskJSP = "../project/courses-student.jsp?all=true";
        ((HttpServletResponse) response).setHeader("Location", taskJSP);
        response.setContentType("text/html");
        //response.setContentType("UTF-8");
        response.setContentType(encoding);
        ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_FOUND);
    }

    @Override
    public void destroy() {

    }
}


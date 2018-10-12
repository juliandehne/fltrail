package unipotsdam.gf.session;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * this filter can be applied to a given space in order to validate, that the tag in the url is a valid one
 * not applied to to a folder yet (because might lead to confusing experiences in debugging)
 */
@ManagedBean
public class SessionExistsFilter implements Filter {

    private final static Logger log = LoggerFactory.getLogger(SessionExistsFilter.class);

    @Inject
    private UserDAO userDAO;

    private void redirectToLogin(ServletRequest request, ServletResponse response) {
        log.debug("redirecting user to login because user does not exist");
        String loginJSP = "../index.jsp?session=false";
        ((HttpServletResponse) response).setHeader("Location", loginJSP);
        response.setContentType("text/html");
        ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_FOUND); // SC_FOUND = 302
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(
            ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request1 = (HttpServletRequest) request;
        Object attribute = request1.getSession().getAttribute(GFContexts.USEREMAIL);

        if (attribute == null) {
            //redirectToLogin(request, response);
            request1.getSession().setAttribute(GFContexts.USEREMAIL, "vodkas@yolo.com");
            chain.doFilter(request,response);
        } else {
            final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());
            locator.inject(this);
            User user = userDAO.getUserByEmail(attribute.toString());
            if (user == null) {
                redirectToLogin(request, response);
            }
            chain.doFilter(request, response);
        }
    }



    @Override
    public void destroy() {

    }
}

package unipotsdam.gf.core.management.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * this filter can be applied to a given space in order to validate, that the tag in the url is a valid one
 * not applied to to a folder yet (because might lead to confusing experiences in debugging)
 */
@ManagedBean
public class SessionValidator implements Filter {

    private final static Logger log = LoggerFactory.getLogger(SessionValidator.class);

    @Inject
    private UserDAO userDAO;

    private void redirectToLogin(ServletRequest request, ServletResponse response) {
        log.debug("redirecting user to login because token does not exist");
        String loginJSP = "../../index.jsp";
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
        String token = request.getParameter("token");
        if (token == null) {
            redirectToLogin(request, response);
        }

        User user = userDAO.getUserByEmail(token);
        if (user == null) {
            redirectToLogin(request, response);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}

package unipotsdam.gf.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public abstract class FlTrailfilter {

    private final static Logger log = LoggerFactory.getLogger(FlTrailfilter.class);

    protected void redirectToLogin(ServletRequest request, ServletResponse response) {
        log.debug("redirecting user to login because user does not exist");
        String loginJSP = "../index.jsp?session=false";
        ((HttpServletResponse) response).setHeader("Location", loginJSP);
        response.setContentType("text/html");
        ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_FOUND); // SC_FOUND = 302
    }
}

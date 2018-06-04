package unipotsdam.gf.core.management.user.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.core.management.ManagementImpl;
import unipotsdam.gf.core.management.user.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SessionValidator implements Filter {

    Logger log = LoggerFactory.getLogger(SessionValidator.class);

    private void redirectToLogin(ServletRequest request, ServletResponse response) {
        log.debug("redirecting user to login because token does not exist");
        String loginJSP = "../../index.jsp";
        ((HttpServletResponse) response).setHeader("Location", loginJSP);
        ((HttpServletResponse) response).setContentType("text/html");
        ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_FOUND); // SC_FOUND = 302
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(
            ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getParameter("token");
        if (token == null) {
            redirectToLogin(request, response);
        }
        ManagementImpl management = new ManagementImpl();
        User user = management.getUser(token);
        if (user == null) {
            redirectToLogin(request, response);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}

package unipotsdam.gf.core.management.user.servlets;

import unipotsdam.gf.core.management.ManagementImpl;
import unipotsdam.gf.core.management.user.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserHttpServlet extends HttpServlet {
    protected User parseUser(HttpServletRequest request) {
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        return new User(null, password, email, null);
    }

    protected User parseFullUser(HttpServletRequest request) {
        User part1 = parseUser(request);
        String name = request.getParameter("name");
        String isStudentRaw = request.getParameter("isStudent");
        Boolean isStudent = isStudentRaw == null;
        part1.setName(name);
        part1.setStudent(isStudent);
        return part1;
    }

    protected void forwardToLocation(HttpServletResponse response, String nextJSP) {
        response.setHeader("Location", nextJSP);
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_FOUND); // SC_FOUND = 302
    }

    protected void login(boolean createUser, HttpServletRequest request, HttpServletResponse response) {
        User user = parseFullUser(request);
        ManagementImpl management = new ManagementImpl();
        if (management.exists(user)) {
            String existsUrl = "../register.jsp?userExists=true";
            forwardToLocation(response, existsUrl);
        } else {
            if (createUser) {
                management.create(user, null);
            }
            String successUrl;
            if (user.getStudent()) {
                successUrl = "../pages/overview-student.html?token=";
            } else {
                successUrl = "../pages/overview-docent.html?token=";
            }
            successUrl += management.getUserToken(user);
            forwardToLocation(response, successUrl);

        }
    }


}
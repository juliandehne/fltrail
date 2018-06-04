package unipotsdam.gf.core.management.user.servlets;

import unipotsdam.gf.core.management.ManagementImpl;
import unipotsdam.gf.core.management.user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CreateUser")
public class CreateUser extends HttpServlet
{
    protected void doPost(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handeRequest(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handeRequest(request, response);
    }

    private void handeRequest(HttpServletRequest request, HttpServletResponse response) {

        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String name = request.getParameter("name");
        User user = new User(name, password, email);
        ManagementImpl management = new ManagementImpl();
        if (management.exists(user)) {
            String existsUrl = "../register.jsp?userExists=true";
            response.setHeader("Location", existsUrl);
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_FOUND); // SC_FOUND = 302
        } else {
            management.create(user, null);
            String successUrl = "../core/pages/projects.jsp?token=";
            successUrl+=management.getUserToken(user);
            response.setHeader("Location", successUrl);
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_FOUND); // SC_FOUND = 302
        }

    }
}

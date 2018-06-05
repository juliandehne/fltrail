package unipotsdam.gf.core.management.user.servlets;

import unipotsdam.gf.core.management.ManagementImpl;
import unipotsdam.gf.core.management.user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CreateUser")
public class CreateUser extends UserHttpServlet
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
        User user = parseFullUser(request);
        ManagementImpl management = new ManagementImpl();
        if (management.exists(user)) {
            String existsUrl = "../register.jsp?userExists=true";
            forwardToLocation(response, existsUrl);
        } else {
            management.create(user, null);
            String successUrl = "../core/pages/projects.jsp?token=";
            successUrl+=management.getUserToken(user);
            forwardToLocation(response, successUrl);
        }

    }
}

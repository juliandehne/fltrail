package unipotsdam.gf.core.management.user.servlets;

import unipotsdam.gf.core.management.ManagementImpl;
import unipotsdam.gf.core.management.user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserExists")
public class UserExists extends UserHttpServlet {
    protected void doPost(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handeRequest(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handeRequest(request, response);
        return;



    }

    private void handeRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = parseUser(request);

        String nextJSP = "../core/pages/projects.jsp?token=";
        String errorJSP = "../index.jsp?userExists=false";

        ManagementImpl management = new ManagementImpl();
        Boolean exists = management.exists(user);

        /**
         * if user exists the page is forwarded to the project page
         */
        if (exists) {
            nextJSP+= management.getUserToken(user);
            forwardToLocation(response, nextJSP);
        } else {
            // if the user does not exist the page is forwarded to itself with a GET-Variable indicating an error
            forwardToLocation(response, errorJSP);
        }

    }



}

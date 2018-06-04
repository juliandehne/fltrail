package unipotsdam.gf.core.management.user.servlets;

import unipotsdam.gf.core.management.ManagementImpl;
import unipotsdam.gf.core.management.user.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UserExists")
public class UserExists extends HttpServlet {
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

        String password = request.getParameter("password");
        String email = request.getParameter("email");
        User user = new User(null, password, email);

        String nextJSP = "../core/pages/projects.jsp?token=";
        String errorJSP = "../index.jsp?userExists=false";

        ManagementImpl management = new ManagementImpl();
        Boolean exists = management.exists(user);

        /**
         * if user exists the page is forwarded to the project page
         */
        if (exists) {
            nextJSP+= management.getUserToken(user);
            response.setHeader("Location", nextJSP);
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_FOUND); // SC_FOUND = 302
        } else {
            // if the user does not exist the page is forwarded to itself with a GET-Variable indicating an error
            response.setHeader("Location", errorJSP);
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_FOUND); // SC_FOUND = 302
        }

    }


}

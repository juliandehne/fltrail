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
       login(false, request, response);
    }



}

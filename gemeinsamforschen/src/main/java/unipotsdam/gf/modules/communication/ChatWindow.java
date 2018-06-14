package unipotsdam.gf.modules.communication;

import unipotsdam.gf.core.management.ManagementImpl;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.modules.communication.service.CommunicationDummyService;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import java.io.*;

public class ChatWindow extends SimpleTagSupport {

    private String orientation;

    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String token = request.getParameter("token");
        ManagementImpl management = new ManagementImpl();
        //User user = management.getUser(token);
        String groupToken = request.getParameter("groupToken");
        String projectToken = request.getParameter("projectToken");
        //get ProjetbyToken
        ICommunication communicationService = new CommunicationDummyService();
        String chatRoomLink = communicationService.getChatRoomLink(token,projectToken,groupToken);

        JspWriter out = getJspContext().getOut();
        out.println("<iframe width=\"30%\" height=\"100%\" src=\""+chatRoomLink+"\"/>");
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getOrientation() {
        return orientation;
    }
}

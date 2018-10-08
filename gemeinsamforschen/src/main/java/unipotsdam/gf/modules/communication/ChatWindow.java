package unipotsdam.gf.modules.communication;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.management.user.UserDAO;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.modules.communication.service.CommunicationService;
import unipotsdam.gf.modules.communication.service.UnirestService;
import unipotsdam.gf.modules.groupfinding.service.GroupDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class ChatWindow extends SimpleTagSupport {

    private String orientation;

    public void doTag() throws IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String token = request.getParameter("token");
        String projectToken = request.getParameter("projectToken");
        UserDAO userDAO = new UserDAO(new MysqlConnect());
        GroupDAO groupDAO = new GroupDAO(new MysqlConnect());
        ICommunication communicationService = new CommunicationService(new UnirestService(), userDAO, groupDAO);
        String chatRoomLink = communicationService.getChatRoomLink(token, projectToken);

        JspWriter out = getJspContext().getOut();
        out.println("<iframe width=\"30%\" height=\"100%\" src=\"" + chatRoomLink + "\"/>");
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getOrientation() {
        return orientation;
    }
}

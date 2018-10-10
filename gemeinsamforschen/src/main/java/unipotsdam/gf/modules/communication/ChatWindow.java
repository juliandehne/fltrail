package unipotsdam.gf.modules.communication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(ChatWindow.class);

    private String orientation;

    public void doTag() throws IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String token = request.getParameter("token");
        String projectId = request.getParameter("projectId");
        UserDAO userDAO = new UserDAO(new MysqlConnect());
        GroupDAO groupDAO = new GroupDAO(new MysqlConnect());
        ICommunication communicationService = new CommunicationService(new UnirestService(), userDAO, groupDAO);
        String chatRoomLink = communicationService.getChatRoomLink(token, projectId);
        log.debug("ChatRoomLink for ChatWindow: {}", chatRoomLink);
        JspWriter out = getJspContext().getOut();
        out.println("<iframe height=\"400px\" src=\"" + chatRoomLink + "\"/>");
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getOrientation() {
        return orientation;
    }
}

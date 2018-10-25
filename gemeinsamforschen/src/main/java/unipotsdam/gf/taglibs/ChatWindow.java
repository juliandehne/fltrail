package unipotsdam.gf.taglibs;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.modules.communication.service.CommunicationService;
import unipotsdam.gf.session.GFContexts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class ChatWindow extends SimpleTagSupport {

    private static final Logger log = LoggerFactory.getLogger(ChatWindow.class);

    private String orientation;

    public void doTag() throws IOException {

        final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());
        locator.inject(this);

        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        /*String token = request.getParameter("token"); */
        String projectId = request.getParameter("projectName");

        ICommunication communicationService = new CommunicationService();
        String chatRoomLink = communicationService.getChatRoomLink(request.getSession().getAttribute(GFContexts
                        .USEREMAIL).toString(), projectId);
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

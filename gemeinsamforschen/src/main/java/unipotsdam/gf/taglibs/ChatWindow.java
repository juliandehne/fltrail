package unipotsdam.gf.taglibs;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.config.GFRocketChatConfig;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.modules.communication.service.CommunicationService;
import unipotsdam.gf.session.GFContext;
import unipotsdam.gf.session.GFContexts;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class ChatWindow extends SimpleTagSupport {

    private static final Logger log = LoggerFactory.getLogger(ChatWindow.class);

    private String orientation;

    private String scope;

    @Inject
    private ICommunication communicationService;

    public void doTag() throws IOException {

        final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());
        locator.inject(this);

        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        /*String token = request.getParameter("token"); */
        String projectName = request.getParameter("projectName");
        String userEmail = request.getSession().getAttribute(GFContexts.USEREMAIL).toString();

        /**
         * create project chatroom
         */
        if (getScope() == "project") {
            String chatRoomLink = communicationService.getProjectChatRoomLink(projectName);
            writeIframe(request, chatRoomLink);
            /**
             * create group chatroom
             */
        } else {
            // scope is group
            String projectChatRoomLink = null;
            try {
                projectChatRoomLink = communicationService.getChatRoomLink(userEmail, projectName);
            } catch (RocketChatDownException e) {
                e.printStackTrace();
            } catch (UserDoesNotExistInRocketChatException e) {
                e.printStackTrace();
            }
            if (projectChatRoomLink != null) {
                writeIframe(request, projectChatRoomLink);
            }
        }

    }

    private void writeIframe(HttpServletRequest request, String chatRoomLink) throws
            IOException {
        String getAuthToken = request.getSession().getAttribute(GFContexts.ROCKETCHATAUTHTOKEN).toString();
        String getId = request.getSession().getAttribute(GFContexts.ROCKETCHATID).toString();
        JspWriter out = getJspContext().getOut();

        log.debug("chatroom links for ChatWindow: {}", chatRoomLink);

        out.println("<iframe height=\"400px\" src=\"" + chatRoomLink + "\">");
        out.println("</iframe>");

    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getScope() {
        return scope;
    }
}

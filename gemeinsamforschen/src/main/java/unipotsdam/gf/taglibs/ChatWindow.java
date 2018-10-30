package unipotsdam.gf.taglibs;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.config.GFRocketChatConfig;
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

        if (getScope() == "project") {
            String chatRoomLink = communicationService.getProjectChatRoomLink(projectName);
            writeIframe(request, chatRoomLink);
        } else {
            // scope is group
            String chatRoomLink = communicationService
                    .getChatRoomLink(request.getSession().getAttribute(GFContexts.USEREMAIL).toString(), projectName);
            writeIframe(request, chatRoomLink);
        }

    }

    private void writeIframe(HttpServletRequest request, String chatRoomLink) throws IOException {
        String getAuthToken = request.getSession().getAttribute(GFContexts.ROCKETCHATAUTHTOKEN).toString();

        log.debug("ChatRoomLink for ChatWindow: {}", chatRoomLink);
        JspWriter out = getJspContext().getOut();
        out.println("<iframe height=\"400px\" src=\"" + chatRoomLink + "\">");
        String rocketChatIntegration = "<script> window.parent.postMessage({event: 'login-with-token',loginToken:" +
                " '"+getAuthToken+"'}, '"+GFRocketChatConfig.ROCKET_CHAT_LINK +"');</script>";
        out.println(rocketChatIntegration);

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

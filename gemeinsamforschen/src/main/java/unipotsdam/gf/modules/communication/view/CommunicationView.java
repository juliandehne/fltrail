package unipotsdam.gf.modules.communication.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.modules.communication.model.chat.ChatMessage;
import unipotsdam.gf.modules.communication.model.chat.ChatRoom;
import unipotsdam.gf.modules.communication.service.CommunicationDummyService;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static java.util.Objects.isNull;

@Path("/chat")
@ManagedBean
public class CommunicationView {

    private static final Logger log = LoggerFactory.getLogger(SampleView.class);

    @Inject
    private ICommunication communicationService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/info/{roomId}")
    public Response getChatRoomInformation(@PathParam("roomId") String roomId) {
        ChatRoom chatRoom = communicationService.getChatRoomInfo(roomId);
        if (isNull(chatRoom)) {
            log.error("chatRoom not found for roomId: {}", roomId);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        log.debug("getChatRoomInformationResponse: {}", chatRoom);
        return Response.ok(chatRoom).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/history/{roomId}")
    public Response getChatHistory(@PathParam("roomId") String roomId) {
        List<ChatMessage> chatMessages = communicationService.getChatHistory(roomId);
        if (isNull(chatMessages)) {
            log.error("chatRoom not found for roomId: {}", roomId);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        log.debug("response for getChatHistory: {}", chatMessages);
        return Response.ok(chatMessages).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response createChatRoom(@QueryParam("name") String name, List<User> users) {
        if (isNull(name)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("no name is not allowed").build();
        }
        String chatId = communicationService.createChatRoom(name, users);
        if (isNull(chatId)) {
            log.error("error while creating chatRoom for: name: {}, users: {}", name, users);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        log.debug("response for createChatRoom: {}", chatId);
        return Response.ok(chatId).build();
    }

    // Temp: just get user as json
    // TODO: remove after done implementing
    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser() {
        User user = ((CommunicationDummyService) communicationService).getUser();
        return Response.ok(user).build();
    }


}

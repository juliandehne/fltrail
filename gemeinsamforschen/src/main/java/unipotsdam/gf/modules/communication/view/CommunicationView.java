package unipotsdam.gf.modules.communication.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.modules.communication.model.Message;
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

    private static final Logger log = LoggerFactory.getLogger(CommunicationView.class);

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
        log.trace("getChatRoomInformationResponse: {}", chatRoom);
        return Response.ok(chatRoom).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/history/{roomId}")
    public Response getChatHistory(@PathParam("roomId") String roomId) {
        List<ChatMessage> chatMessages = communicationService.getChatHistory(roomId);
        if (isNull(chatMessages)) {
            log.error("getChatHistory: chatRoom not found for roomId: {}", roomId);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        log.trace("response for getChatHistory: {}", chatMessages);
        return Response.ok(chatMessages).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/send/{roomId}")
    public Response sendMessage(Message message, @PathParam("roomId") String roomId) {
        if (isNull(message)) {
            log.trace("sendMessage message object was null");
            return Response.status(Response.Status.BAD_REQUEST).entity("must provide message").build();
        }
        boolean wasSend = communicationService.sendMessageToChat(message, roomId);
        Response response;
        if (wasSend) {
            log.trace("response for sendMessage: {}", wasSend);
            response = Response.ok(wasSend).build();
        } else {
            log.error("error while sending message for message: {}", message);
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("error while sending message").build();
        }
        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/addUser/{roomId}")
    public Response addUserToChatRoom(@PathParam("roomId") String roomId, User user) {
        if (isNull(user)) {
            log.trace("addUser user object was null");
            return Response.status(Response.Status.BAD_REQUEST).entity("must provide user").build();
        }
        boolean wasAdded = communicationService.addUserToChatRoom(roomId, user);
        if (isNull(wasAdded)) {
            log.error("addUserToChatRoom: chatRoom not found for roomId: {}, user: {}", roomId, user);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Response response;
        if (wasAdded) {
            log.trace("response for addUser: {}", wasAdded);
            response = Response.ok(wasAdded).build();
        } else {
            log.error("error while adding user to chat room");
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("error while adding user to chatRoom").build();
        }
        return response;
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/removeUser/{roomId}")
    public Response removeUserFromChatRoom(User user, @PathParam("roomId") String roomId) {
        if (isNull(user)) {
            log.trace("removeUser user object was null");
            return Response.status(Response.Status.BAD_REQUEST).entity("must provide user").build();
        }
        boolean wasRemoved = communicationService.removeUserFromChatRoom(user, roomId);
        if (isNull(wasRemoved)) {
            log.error("removeUserToChatRoom: chatRoom not found for roomId: {}, user: {}", roomId, user);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Response response;
        if (wasRemoved) {
            log.trace("response for removeUser: {}", wasRemoved);
            response = Response.ok(wasRemoved).build();
        } else {
            log.error("error while adding user to chat room");
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("error while adding user to chatRoom").build();
        }
        return response;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/setTopic/{roomId}")
    public Response setChatRoomTopic(@PathParam("roomId") String roomId, @QueryParam("topic") String topic) {
        if (isNull(topic)) {
            log.trace("setTopic param not given");
            return Response.status(Response.Status.BAD_REQUEST).entity("topic must be not empty").build();
        }
        boolean wasSet = communicationService.setChatRoomTopic(roomId, topic);
        if (isNull(wasSet)) {
            log.error("addChatRoomTopic: chatRoom not found for roomId: {}, topic: {}", roomId, topic);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Response response;
        if (wasSet) {
            log.trace("response for setTopic: {}", wasSet);
            response = Response.ok(wasSet).build();
        } else {
            log.error("error while setting topic to chat room");
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("error while setting topic to chat room").build();
        }
        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response createChatRoom(@QueryParam("name") String name, List<User> users) {
        if (isNull(name)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("must provide name as queryParam").build();
        }
        String chatId = communicationService.createChatRoom(name, users);
        if (isNull(chatId)) {
            log.error("error while creating chatRoom for: name: {}, users: {}", name, users);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        log.trace("response for createChatRoom: {}", chatId);
        return Response.status(Response.Status.CREATED).entity(chatId).build();
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

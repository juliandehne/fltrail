package unipotsdam.gf.modules.communication.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.interfaces.ICommunication;

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
        String chatRoomName = communicationService.getChatRoomName(roomId);
        if (chatRoomName.isEmpty()) {
            log.error("chatRoom not found for roomId: {}", roomId);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        log.trace("getChatRoomInformationResponse: {}", chatRoomName);
        return Response.ok(chatRoomName).build();
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
        boolean wasAdded = communicationService.addUserToChatRoom(user, roomId);
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/room/create/{name}")
    public Response createChatRoom(@PathParam("name") String name, List<User> users, @QueryParam("readOnly") boolean readOnly) {
        if (isNull(name)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("must provide name as queryParam").build();
        }
        String chatId = communicationService.createChatRoom(name, readOnly, users);
        if (chatId.isEmpty()) {
            log.error("error while creating chatRoom for: name: {}, users: {}", name, users);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        log.trace("response for createChatRoom: {}", chatId);
        return Response.status(Response.Status.CREATED).entity(chatId).build();
    }
}

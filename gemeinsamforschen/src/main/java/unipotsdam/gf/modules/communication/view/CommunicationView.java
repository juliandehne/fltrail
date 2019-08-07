package unipotsdam.gf.modules.communication.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.config.GFRocketChatConfig;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.healthchecks.HealthChecks;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.modules.communication.model.LoginToken;
import unipotsdam.gf.modules.communication.model.RocketChatUser;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.session.GFContexts;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static java.util.Objects.isNull;

@Path("/chat")
@ManagedBean
public class CommunicationView {

    private static final Logger log = LoggerFactory.getLogger(CommunicationView.class);

    @Inject
    GFContexts gfContexts;

    @Inject
    private ICommunication communicationService;

/*    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/info/{roomId}")
    public Response getChatRoomInformation(@PathParam("roomId") String roomId)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        String chatRoomName = communicationService.getChatRoomName(roomId);
        if (chatRoomName.isEmpty()) {
            log.error("chatRoom not found for roomId: {}", roomId);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        log.trace("getChatRoomInformationResponse: {}", chatRoomName);
        return Response.ok(chatRoomName).build();
    }*/

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/addUser/{roomId}")
    public Response addUserToChatRoom(@PathParam("roomId") String roomId, User user)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {
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
            response =
                    Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("error while adding user to chatRoom")
                            .build();
        }
        return response;
    }


   /* @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/removeUser/{roomId}")
    public Response removeUserFromChatRoom(User user, @PathParam("roomId") String roomId)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {
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
            response =
                    Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("error while adding user to chatRoom")
                            .build();
        }
        return response;
    }*/

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/room/create/{name}")
    public Response createChatRoom(
            @PathParam("name") String name, List<User> users, @QueryParam("readOnly") boolean readOnly)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {
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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/sso")
    public Response provideLoginToken(@Context HttpServletRequest req, Object payload)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        if (req.getSession().getAttribute(GFContexts.ROCKETCHATAUTHTOKEN) != null) {
            String token = getAuthToken(req);
            return Response.status(Response.Status.OK).entity(new LoginToken(token)).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        /*else {
            RocketChatUser user = communicationService.loginUser(GFRocketChatConfig.ADMIN_USER);
            gfContexts.updateUserSessionWithRocketChat(req, user);
            return new LoginToken(user.getRocketChatAuthToken());
        }*/
    }

    private String getAuthToken(@Context HttpServletRequest req) {
        return req.getSession().getAttribute(GFContexts.ROCKETCHATAUTHTOKEN).toString();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/login")
    public String provideLoginHTML(@Context HttpServletRequest req) {
        String rocketChatIntegration = "<script> window.parent.postMessage({event: 'login-with-token',loginToken:" +
                " '" + getAuthToken(req) + "'}, '" + GFRocketChatConfig.ROCKET_CHAT_LINK_0 + "');</script>";
        return rocketChatIntegration;
    }
}

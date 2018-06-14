package unipotsdam.gf.modules.communication.view;

import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.util.runner.ConcurrentRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import unipotsdam.gf.config.GFResourceConfig;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.modules.communication.model.Message;
import unipotsdam.gf.modules.communication.model.chat.ChatRoom;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(ConcurrentRunner.class)
public class CommunicationViewTest extends JerseyTest {

    private final static String ENDPOINT = "chat/";

    @Override
    protected Application configure() {
        CommunicationView communicationView = new CommunicationView();
        GFResourceConfig gfResourceConfig = new GFResourceConfig();
        gfResourceConfig.register(communicationView);
        return gfResourceConfig;
    }

    @Test
    public void getChatRoomInformation() {
        String path = ENDPOINT + "info/";
        Response responseOk = target().path(path + "1").request().get();
        assertThat(responseOk.getStatus(), is(OK.getStatusCode()));
        assertNotNull(responseOk.readEntity(ChatRoom.class));

        Response responseNotFound = target().path(path).request().get();
        assertThat(responseNotFound.getStatus(), is(NOT_FOUND.getStatusCode()));
    }

    @Test
    public void getChatHistory() {
        String path = ENDPOINT + "history/";
        Response responseOk = target().path(path + "1").request().get();
        assertThat(responseOk.getStatus(), is(OK.getStatusCode()));
        assertNotNull(responseOk.readEntity(List.class));

        Response responseNotFound = target().path(path).request().get();
        assertThat(responseNotFound.getStatus(), is(NOT_FOUND.getStatusCode()));
    }

    @Test
    public void createChatRoom() {
        String path = ENDPOINT + "create";
        Response responseOk = target().path(path).queryParam("name", "test").request().post(null);
        assertThat(responseOk.getStatus(), is(CREATED.getStatusCode()));
        assertNotNull(responseOk.readEntity(String.class));

        ArrayList<User> users = new ArrayList<>();
        users.add(new User("test", "test", "test", true));
        Response responseOk2 = target().path(path).queryParam("name", "test").request().post(Entity.json(users));
        assertThat(responseOk2.getStatus(), is(CREATED.getStatusCode()));
        assertNotNull(responseOk2.readEntity(String.class));

        Response responseBadRequest = target().path(path).request().post(Entity.json(users));
        assertThat(responseBadRequest.getStatus(), is(BAD_REQUEST.getStatusCode()));
    }

    @Test
    public void sendMessage() {
        String path = ENDPOINT + "send";
        Message message = new Message();
        message.setMessage("test");
        message.setRoomIdOrChannel("1");
        Response responseOk = target().path(path + "/1").request().post(Entity.json(message));
        assertThat(responseOk.getStatus(), is(OK.getStatusCode()));
        assertNotNull(responseOk.readEntity(String.class));

        Response responseBadRequest = target().path(path + "/1").request().post(Entity.json(null));
        assertThat(responseBadRequest.getStatus(), is(BAD_REQUEST.getStatusCode()));
    }

    @Test
    public void addUserToChatRoom() {
        String fullPath = ENDPOINT + "addUser" + "/1";

        User user = new User("test", "test", "test", true);
        Response responseOk = target().path(fullPath).request().post(Entity.json(user));
        assertThat(responseOk.getStatus(), is(OK.getStatusCode()));
        assertNotNull(responseOk.readEntity(String.class));

        Response responseBadRequest = target().path(fullPath).request().post(Entity.json(null));
        assertThat(responseBadRequest.getStatus(), is(BAD_REQUEST.getStatusCode()));
    }

    @Test
    public void removeUserFromChatRoom() {
        String fullPath = ENDPOINT + "removeUser" + "/1";

        User user = new User("test2", "test2", "test", true);
        Response responseOk = target().path(fullPath).request().post(Entity.json(user));
        assertThat(responseOk.getStatus(), is(OK.getStatusCode()));
        assertNotNull(responseOk.readEntity(String.class));

        Response responseBadRequest = target().path(fullPath).request().post(Entity.json(null));
        assertThat(responseBadRequest.getStatus(), is(BAD_REQUEST.getStatusCode()));
    }

    @Test
    public void setChatRoomTopic() {
        String fullPath = ENDPOINT + "setTopic" + "/1";
        Response responseOk = target().path(fullPath).queryParam("topic", "test").request().post(null);
        assertThat(responseOk.getStatus(), is(OK.getStatusCode()));
        assertNotNull(responseOk.readEntity(String.class));

        Response responseBadRequest = target().path(fullPath).request().post(Entity.json(null));
        assertThat(responseBadRequest.getStatus(), is(BAD_REQUEST.getStatusCode()));
    }
}

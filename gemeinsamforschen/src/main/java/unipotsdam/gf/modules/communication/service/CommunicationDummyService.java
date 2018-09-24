package unipotsdam.gf.modules.communication.service;

import io.github.openunirest.http.HttpResponse;
import unipotsdam.gf.assignments.Assignee;
import unipotsdam.gf.assignments.NotImplementedLogger;
import unipotsdam.gf.config.GFRocketChatConfig;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.states.model.ConstraintsMessages;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.communication.model.Message;
import unipotsdam.gf.modules.communication.model.chat.ChatMessage;
import unipotsdam.gf.modules.communication.model.chat.ChatRoom;
import unipotsdam.gf.modules.communication.model.rocketChat.RocketChatResponse;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Resource
@ManagedBean
@Singleton
public class CommunicationDummyService implements ICommunication {

    private UnirestService unirestService;

    @Inject
    public CommunicationDummyService(UnirestService unirestService) {
        this.unirestService = unirestService;
    }

    @Override
    public List<ChatMessage> getChatHistory(String roomId) {
        ArrayList<ChatMessage> chatMessages = new ArrayList<>();
        int maxValue = 6;
        for (int i = 1; i <= maxValue; i++) {
            chatMessages.add(new ChatMessage(String.valueOf(i), "Dies ist ein Test " + i + ".",
                    Instant.now().minusSeconds(maxValue * 10 - i * 10), "testUser" + i));
        }
        return chatMessages;
    }

    @Override
    public boolean sendMessageToChat(Message message, String roomId) {
        NotImplementedLogger.logAssignment(Assignee.MARTIN, CommunicationDummyService.class);
        return false;
    }

    @Override
    public String createChatRoom(String name, List<User> userList) {
        if (Objects.isNull(userList)) {
            return "2";
        }

        return "1";
    }

    @Override
    public boolean addUserToChatRoom(String roomId, User user) {
        NotImplementedLogger.logAssignment(Assignee.MARTIN, CommunicationDummyService.class, "addUserToChatRoom");
        return false;
    }

    @Override
    public boolean removeUserFromChatRoom(User user, String roomId) {
        NotImplementedLogger.logAssignment(Assignee.MARTIN, CommunicationDummyService.class, "removing user from chat " +
                "room");
        return false;
    }

    @Override
    public boolean setChatRoomTopic(String roomId, String topic) {
        NotImplementedLogger.logAssignment(Assignee.MARTIN, CommunicationDummyService.class, "setting chat room topic");
        return false;
    }

    @Override
    public ChatRoom getChatRoomInfo(String roomId) {
        return new ChatRoom("1", "test");
    }

    @Override
    public boolean loginUser(User user) {
        if (user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
            return false;
        }

        HashMap<String, String> rocketChatAuth = new HashMap<>();
        rocketChatAuth.put("user", user.getEmail());
        rocketChatAuth.put("password", user.getPassword());

        HttpResponse<RocketChatResponse> response =
                unirestService
                        .post(GFRocketChatConfig.ROCKET_CHAT_API_LINK + "login")
                        .body(rocketChatAuth)
                        .asObject(RocketChatResponse.class);
        int status = response.getStatus();
        if (status == Response.Status.UNAUTHORIZED.getStatusCode() || status == Response.Status.BAD_REQUEST.getStatusCode()) {
            return false;
        }
        RocketChatResponse rocketChatResponse = response.getBody();
        user.setRocketChatUserId(rocketChatResponse.getUserId());
        user.setRocketChatAuthToken(rocketChatResponse.getAuthToken());
        return true;
    }

    @Override
    public boolean registerUser(User user) {
        // register User

        // login User and fill information

        // create custom access token with authToken
        user.setRocketChatUserId("1");
        return true;
    }

    @Override
    public boolean registerAndLoginUser(User user) {
        // TODO: try to login user first --> if it fails there is no user, register afterwards or add exists function
        if (!registerUser(user)) {
            return false;
        }
        return loginUser(user);

    }

    public String getChatRoomLink(String userToken, String projectToken, String groupToken) {
        //User user = managementService.getUserByToken(userToken);
        // TODO: Implement getProjectbyToken and getGroupByToken
        //Project project = managementService.getProject(projectToken
        String channelName = "general";
        return GFRocketChatConfig.ROCKET_CHAT_API_LINK + "/channel/" + channelName + "?layout=embedded";
    }

    @Override
    public void sendSingleMessage(Message message, User user) {
        // TODO implement as email or directed message, popup after login or whatever
        String message2 = "sending email with message: " + message.getMessage() + " to: " + user.getEmail();
        NotImplementedLogger.logAssignment(Assignee.MARTIN, CommunicationDummyService.class, message2);
    }

    @Override
    public void informAboutMissingTasks(Map<StudentIdentifier, ConstraintsMessages> tasks, Project project) {

    }

    @Override
    public void sendMessageToUsers(Project project, String message) {
        // TODO implement as email or directed message, popup after login or whatever
        String message2 = "sending email with message: " + message + " to: " + project.getId();
        NotImplementedLogger.logAssignment(Assignee.MARTIN, CommunicationDummyService.class, message2);
    }

    // TODO: remove after done implementing
    // just for postman testing
    public User getUser() {
        return new User("Martin Stähr", "test", "test@test.com", true);
    }
}

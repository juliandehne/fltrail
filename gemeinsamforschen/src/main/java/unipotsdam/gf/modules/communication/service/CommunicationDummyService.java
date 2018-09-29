package unipotsdam.gf.modules.communication.service;

import io.github.openunirest.http.HttpResponse;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.assignments.Assignee;
import unipotsdam.gf.assignments.NotImplementedLogger;
import unipotsdam.gf.core.management.group.Group;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.user.UserDAO;
import unipotsdam.gf.core.states.model.ConstraintsMessages;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.communication.model.Message;
import unipotsdam.gf.modules.communication.model.chat.ChatMessage;
import unipotsdam.gf.modules.communication.model.chat.ChatRoom;
import unipotsdam.gf.modules.communication.model.rocketChat.RocketChatLoginResponse;
import unipotsdam.gf.modules.communication.model.rocketChat.RocketChatRegisterResponse;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static unipotsdam.gf.config.GFRocketChatConfig.ADMIN_USER;
import static unipotsdam.gf.config.GFRocketChatConfig.ROCKET_CHAT_API_LINK;

@Resource
@ManagedBean
@Singleton
public class CommunicationDummyService implements ICommunication {

    private Logger log = LoggerFactory.getLogger(CommunicationDummyService.class);

    private UnirestService unirestService;
    private UserDAO userDAO;

    @Inject
    public CommunicationDummyService(UnirestService unirestService, UserDAO userDAO) {
        this.unirestService = unirestService;
        this.userDAO = userDAO;
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
    public String createEmptyChatRoom(String name, boolean readOnly) {
        return createChatRoom(name, readOnly, new ArrayList<>());
    }

    @Override
    public String createChatRoom(String name, boolean readOnly, List<User> users) {
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("X-Auth-Token", ADMIN_USER.getRocketChatPersonalAccessToken());
        headerMap.put("X-User-Id", ADMIN_USER.getRocketChatUserId());

        List<String> usernameList = users.stream().map(User::getRocketChatPersonalAccessToken).collect(Collectors.toList());
        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("name", name);
        bodyMap.put("readOnly", readOnly);
        bodyMap.put("members", usernameList);

        HttpResponse<Map> response =
                unirestService
                        .post(ROCKET_CHAT_API_LINK + "groups.create")
                        .headers(headerMap)
                        .body(bodyMap)
                        .asObject(Map.class);

        if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
            return Strings.EMPTY;
        }

        Map responseMap = response.getBody();
        log.debug("responseMap: {}", responseMap);
        if (responseMap.containsKey("error")) {
            return Strings.EMPTY;
        }

        Map groupMap = (Map) responseMap.get("group");
        return groupMap.get("_id").toString();
    }

    @Override
    public boolean createChatRoom(Group group, boolean readOnly) {
        // chatRoom name: projectId - GroupId
        String chatRoomName = String.join(" - ", group.getProjectId(), String.valueOf(group.getId()));
        String chatRoomId = createChatRoom(chatRoomName, readOnly, group.getMembers());
        if (chatRoomId.isEmpty()) {
            return false;
        }
        group.setChatRoomId(chatRoomId);
        return true;
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
        // TODO: not needed at the moment, possibly remove
        return false;
    }

    @Override
    public ChatRoom getChatRoomInfo(String roomId) {
        return new ChatRoom("1", "test");
    }

    @Override
    public boolean loginUser(User user) {
        if (hasEmptyParameter(user.getEmail(), user.getPassword())) {
            return false;
        }

        HashMap<String, String> rocketChatAuth = new HashMap<>();
        rocketChatAuth.put("user", user.getEmail());
        rocketChatAuth.put("password", user.getPassword());

        HttpResponse<RocketChatLoginResponse> response =
                unirestService
                        .post(ROCKET_CHAT_API_LINK + "login")
                        .body(rocketChatAuth)
                        .asObject(RocketChatLoginResponse.class);

        int status = response.getStatus();
        if (status == Response.Status.UNAUTHORIZED.getStatusCode() || status == Response.Status.BAD_REQUEST.getStatusCode()) {
            return false;
        }

        RocketChatLoginResponse rocketChatLoginResponse = response.getBody();
        user.setRocketChatUserId(rocketChatLoginResponse.getUserId());
        user.setRocketChatAuthToken(rocketChatLoginResponse.getAuthToken());
        return true;
    }

    @Override
    public boolean registerUser(User user) {

        if (hasEmptyParameter(user.getEmail(), user.getName(), user.getPassword())) {
            return false;
        }

        HashMap<String, String> rocketChatRegister = new HashMap<>();
        String rocketChatUsername = createRocketChatUsername(user);
        rocketChatRegister.put("username", rocketChatUsername);
        rocketChatRegister.put("email", user.getEmail());
        rocketChatRegister.put("pass", user.getPassword());
        rocketChatRegister.put("name", user.getName());

        HttpResponse<RocketChatRegisterResponse> response =
                unirestService
                        .post(ROCKET_CHAT_API_LINK + "users.register")
                        .body(rocketChatRegister)
                        .asObject(RocketChatRegisterResponse.class);

        if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
            return false;
        }

        RocketChatRegisterResponse registerResponse = response.getBody();
        if (!registerResponse.isSuccessful()) {
            return false;
        }

        user.setRocketChatUsername(rocketChatUsername);
        user.setRocketChatUserId(registerResponse.getUserId());

        return createCustomAccessToken(user);
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
        return ROCKET_CHAT_API_LINK + "/channel/" + channelName + "?layout=embedded";
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

    private boolean hasEmptyParameter(String... parameters) {
        return Arrays.stream(parameters).anyMatch(String::isEmpty);
    }

    private String createRocketChatUsername(User user) {
        // TODO: eventually add username to normal registration
        String possibleUsername = user.getName().replaceAll(" ", "");
        int counter = 1;
        while (userDAO.existsByRocketChatUsername(possibleUsername)) {
            possibleUsername = user.getName().replaceAll(" ", "") + counter;
            counter++;
        }
        return possibleUsername;
    }

    private boolean createCustomAccessToken(User user) {
        if (hasEmptyParameter(user.getRocketChatAuthToken(), user.getRocketChatUserId())) {
            return false;
        }

        if (!loginUser(user)) {
            // TODO: eventually consider rollback because of error or something
            return false;
        }

        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("tokenName", "fltrailToken");
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("X-Auth-Token", user.getRocketChatAuthToken());
        headerMap.put("X-User-Id", user.getRocketChatUserId());

        HttpResponse<Map> response = unirestService
                .post(ROCKET_CHAT_API_LINK + "users.generatePersonalAccessToken")
                .headers(headerMap)
                .body(bodyMap)
                .asObject(Map.class);

        if (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
            return false;
        }

        Map responseBody = response.getBody();
        if (responseBody.containsKey("status")) {
            return false;
        }
        user.setRocketChatPersonalAccessToken(responseBody.get("token").toString());
        return true;

    }
}

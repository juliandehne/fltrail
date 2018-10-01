package unipotsdam.gf.modules.communication.service;

import io.github.openunirest.http.HttpResponse;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.config.GFRocketChatConfig;
import unipotsdam.gf.core.management.group.Group;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.user.UserDAO;
import unipotsdam.gf.core.states.model.ConstraintsMessages;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.communication.model.EMailMessage;
import unipotsdam.gf.modules.communication.model.chat.ChatMessage;
import unipotsdam.gf.modules.communication.model.rocketChat.RocketChatLoginResponse;
import unipotsdam.gf.modules.communication.model.rocketChat.RocketChatRegisterResponse;
import unipotsdam.gf.modules.communication.util.RocketChatHeaderMapBuilder;
import unipotsdam.gf.modules.groupfinding.service.GroupDAO;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import static unipotsdam.gf.config.GFMailConfig.SMTP_HOST;
import static unipotsdam.gf.config.GFMailConfig.SMTP_PASSWORD;
import static unipotsdam.gf.config.GFMailConfig.SMTP_PORT;
import static unipotsdam.gf.config.GFMailConfig.SMTP_USERNAME;
import static unipotsdam.gf.config.GFRocketChatConfig.ROCKET_CHAT_API_LINK;
import static unipotsdam.gf.config.GFRocketChatConfig.ROCKET_CHAT_ROOM_LINK;

@Resource
@ManagedBean
@Singleton
public class CommunicationService implements ICommunication {

    private final static Logger log = LoggerFactory.getLogger(CommunicationService.class);

    private UnirestService unirestService;
    private UserDAO userDAO;
    private GroupDAO groupDAO;

    // TODO: refactor error handling and add maybe some descriptions

    @Inject
    public CommunicationService(UnirestService unirestService, UserDAO userDAO, GroupDAO groupDAO) {
        this.unirestService = unirestService;
        this.userDAO = userDAO;
        this.groupDAO = groupDAO;
    }

    @Override
    public List<ChatMessage> getChatHistory(String roomId) {
        //TODO : not needed at the moment, possibly remove
        return null;
    }

    @Override
    public boolean sendMessageToChat(EMailMessage EMailMessage, String roomId) {
        // TODO: not needed at the moment, possibly remove
        return false;
    }

    @Override
    public String createEmptyChatRoom(String name, boolean readOnly) {
        return createChatRoom(name, readOnly, new ArrayList<>());
    }

    @Override
    public String createChatRoom(String name, boolean readOnly, List<User> member) {
        Map<String, String> headerMap = new RocketChatHeaderMapBuilder().withRocketChatAdminAuth().build();

        List<String> usernameList = member.stream().map(User::getRocketChatUsername).collect(Collectors.toList());
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
        String chatRoomName = String.join("-", group.getProjectId(), String.valueOf(group.getId()));
        String chatRoomId = createChatRoom(chatRoomName, readOnly, group.getMembers());
        if (chatRoomId.isEmpty()) {
            return false;
        }
        group.setChatRoomId(chatRoomId);
        return true;
    }

    @Override
    public boolean deleteChatRoom(String roomId) {
        // TODO: maybe add lock for getChatRoomName, so synchronized access doesn't create errors while deleting
        Map<String, String> headerMap = new RocketChatHeaderMapBuilder().withRocketChatAdminAuth().build();
        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("roomId", roomId);

        HttpResponse<Map> response =
                unirestService
                        .post(ROCKET_CHAT_API_LINK + "groups.delete")
                        .headers(headerMap)
                        .body(bodyMap)
                        .asObject(Map.class);

        if (isBadRequest(response)) {
            return false;
        }
        Map responseMap = response.getBody();
        if (responseMap.get("success").equals("false") || responseMap.containsKey("error")) {
            return false;
        }
        groupDAO.clearChatRoomIdOfGroup(roomId);

        return true;
    }

    @Override
    public boolean addUserToChatRoom(User user, String roomId) {
        return modifyChatRoom(user, roomId, true);
    }

    @Override
    public boolean removeUserFromChatRoom(User user, String roomId) {
        return modifyChatRoom(user, roomId, false);
    }

    private boolean modifyChatRoom(User user, String roomId, boolean addUser) {
        if (hasEmptyParameter(user.getRocketChatUserId(), user.getRocketChatPersonalAccessToken(), roomId)) {
            return false;
        }
        Map<String, String> headerMap = new RocketChatHeaderMapBuilder().withRocketChatAdminAuth().build();
        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("roomId", roomId);
        bodyMap.put("userId", user.getRocketChatUserId());

        String groupUrl = addUser ? "groups.invite" : "groups.kick";

        HttpResponse<Map> response = unirestService
                .post(GFRocketChatConfig.ROCKET_CHAT_API_LINK + groupUrl)
                .headers(headerMap)
                .body(bodyMap)
                .asObject(Map.class);

        if (isBadRequest(response)) {
            return false;
        }

        Map responseMap = response.getBody();
        return !responseMap.containsKey("error") && !responseMap.get("success").equals("false");
    }

    @Override
    public boolean setChatRoomTopic(String roomId, String topic) {
        // TODO: not needed at the moment, possibly remove
        return false;
    }

    @Override
    public String getChatRoomName(String roomId) {
        Map<String, String> headerMap = new RocketChatHeaderMapBuilder().withRocketChatAdminAuth().build();

        HttpResponse<Map> response =
                unirestService
                        .get(ROCKET_CHAT_API_LINK + "groups.info")
                        .headers(headerMap)
                        .queryString("roomId", roomId).asObject(Map.class);

        if (isBadRequest(response)) {
            return Strings.EMPTY;
        }

        Map responseMap = response.getBody();
        if (responseMap.containsKey("error")) {
            return Strings.EMPTY;
        }

        Map groupMap = (Map) responseMap.get("group");
        return groupMap.get("name").toString();
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

        if (isBadRequest(response)) {
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

        if (isBadRequest(response)) {
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

    public String getChatRoomLink(String userToken, String projectId) {
        User user = userDAO.getUserByToken(userToken);
        StudentIdentifier studentIdentifier = new StudentIdentifier(projectId, user.getId());
        String chatRoomId = groupDAO.getGroupChatRoomIdByStudentIdentifier(studentIdentifier);
        if (chatRoomId.isEmpty()) {
            return Strings.EMPTY;
        }

        String chatRoomName = getChatRoomName(chatRoomId);
        if (chatRoomName.isEmpty()) {
            return Strings.EMPTY;
        }

        return ROCKET_CHAT_ROOM_LINK + chatRoomName + "?layout=embedded";
    }

    // TODO: Think about splitting email and chat communication into different
    @Override
    public boolean sendSingleMessage(EMailMessage eMailMessage, User user) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SMTP_USERNAME, "FLTrail Messagebot"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
            message.setSubject(eMailMessage.getSubject());
            message.setText(eMailMessage.getBody());

            Transport.send(message);
            return true;
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("Exception while sending an email: {}", e);
            return false;
        }
    }

    @Override
    public boolean informAboutMissingTasks(Map<StudentIdentifier, ConstraintsMessages> tasks, Project project) {
        HashMap<StudentIdentifier, ConstraintsMessages> notSentEMailMap = new HashMap<>();
        tasks.entrySet().stream().filter(entry -> {
            User user = new User();
            user.setEmail(entry.getKey().getStudentId());
            EMailMessage eMailMessage = new EMailMessage();
            eMailMessage.setSubject("Benachrichtigung über nicht erledigte Aufgaben im Projekt " + project.getId());
            eMailMessage.setBody(entry.getValue().toString());
            return !sendSingleMessage(eMailMessage, user);
        }).forEach(entry -> notSentEMailMap.put(entry.getKey(), entry.getValue()));
        return notSentEMailMap.isEmpty();
    }

    @Override
    public boolean sendMessageToUsers(Project project, EMailMessage eMailMessage) {
        List<User> users = userDAO.getUsersByProjectId(project.getId());
        List<User> userEmailProblemList = users
                .stream()
                .filter(user -> !sendSingleMessage(eMailMessage, user))
                .collect(Collectors.toList());
        return userEmailProblemList.isEmpty();
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
            return false;
        }

        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("tokenName", "fltrailToken");
        Map<String, String> headerMap = new RocketChatHeaderMapBuilder()
                .withAuthTokenHeader(user.getRocketChatAuthToken())
                .withRocketChatUserId(user.getRocketChatUserId()).build();

        HttpResponse<Map> response = unirestService
                .post(ROCKET_CHAT_API_LINK + "users.generatePersonalAccessToken")
                .headers(headerMap)
                .body(bodyMap)
                .asObject(Map.class);

        if (isBadRequest(response)) {
            return false;
        }

        Map responseBody = response.getBody();
        if (responseBody.containsKey("status")) {
            return false;
        }
        user.setRocketChatPersonalAccessToken(responseBody.get("token").toString());
        return true;
    }

    private boolean isBadRequest(HttpResponse response) {
        int status = response.getStatus();
        return status == Response.Status.BAD_REQUEST.getStatusCode() ||
                status == Response.Status.UNAUTHORIZED.getStatusCode();
    }


    @Override
    public boolean exists(String roomId) {
        return !getChatRoomName(roomId).isEmpty();
    }
}

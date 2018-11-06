package unipotsdam.gf.modules.communication.service;

import io.github.openunirest.http.HttpResponse;
import io.github.openunirest.request.body.RequestBodyEntity;
import org.apache.http.HttpEntity;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.config.GFRocketChatConfig;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.exceptions.UserExistsInRocketChatException;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.communication.model.EMailMessage;
import unipotsdam.gf.modules.communication.model.RocketChatUser;
import unipotsdam.gf.modules.communication.model.chat.ChatMessage;
import unipotsdam.gf.modules.communication.model.rocketChat.RocketChatLoginResponse;
import unipotsdam.gf.modules.communication.model.rocketChat.RocketChatRegisterResponse;
import unipotsdam.gf.modules.communication.model.rocketChat.RocketChatSuccessResponse;
import unipotsdam.gf.modules.communication.util.RocketChatHeaderMapBuilder;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.constraints.ConstraintsMessages;

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
import static unipotsdam.gf.config.GFRocketChatConfig.ADMIN_USER;
import static unipotsdam.gf.config.GFRocketChatConfig.ROCKET_CHAT_API_LINK;
import static unipotsdam.gf.config.GFRocketChatConfig.ROCKET_CHAT_ROOM_LINK;

@Resource
@ManagedBean
@Singleton
public class CommunicationService implements ICommunication {

    private final static Logger log = LoggerFactory.getLogger(CommunicationService.class);

    @Inject
    private UnirestService unirestService;
    @Inject
    private UserDAO userDAO;
    @Inject
    private GroupDAO groupDAO;

    public CommunicationService() {

    }

/*    private static Boolean isUpdated;

    private static synchronized Boolean setAdminToken() {
        if (isUpdated == null) {
            isUpdated = true;
        } else {
            return isUpdated;
        }
        return null;
    }


    private static synchronized Boolean unsetAdminToken() {
        if (isUpdated == null) {
        } else {
            isUpdated = false;
        }
        return null;
    }*/

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
    public String createEmptyChatRoom(String name, boolean readOnly)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        return createChatRoom(name, readOnly, new ArrayList<>());
    }

    @Override
    public String createChatRoom(String name, boolean readOnly, List<User> member)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        //loginUser(ADMIN_USER);

        Map<String, String> headerMap = new RocketChatHeaderMapBuilder().withRocketChatAdminAuth(this).build();

        List<String> usernameList = member.stream().map(User::getRocketChatUsername).collect(Collectors.toList());
        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("name", name);
        bodyMap.put("readOnly", readOnly);
        bodyMap.put("members", usernameList);


        RequestBodyEntity body =
                unirestService.post(ROCKET_CHAT_API_LINK + "groups.create").headers(headerMap).body(bodyMap);

        HttpEntity entity = body.getEntity();

        HttpResponse<Map> response = body.asObject(Map.class);

        if (isBadRequest(response)) {
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
    public boolean createChatRoom(Group group, boolean readOnly)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        //loginUser(ADMIN_USER);

        // chatRoom name: projectId - GroupId
        String chatRoomName = getChatRoomName(group);
        if (exists(chatRoomName)) {
            return true;
        }
        String chatRoomId = createChatRoom(chatRoomName, readOnly, group.getMembers());
        if (chatRoomId.isEmpty()) {
            return false;
        }
        group.setChatRoomId(chatRoomId);
        groupDAO.update(group);
        return true;
    }

    @Override
    public void deleteChatRoom(Group group) throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        deleteChatRoom(getChatRoomName(group));
    }

    @Override
    public void deleteChatRoom(Project project) throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        deleteChatRoom(project.getName());
    }

    private String getChatRoomName(Group group) {
        return String.join("-", group.getProjectName(), String.valueOf(group.getId()));
    }

    @Override
    public boolean deleteChatRoom(String roomId) throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        // TODO: maybe add lock for getChatRoomName, so synchronized access doesn't create errors while deleting
        //loginUser(ADMIN_USER);

        Map<String, String> headerMap = new RocketChatHeaderMapBuilder().withRocketChatAdminAuth(this).build();
        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("roomId", roomId);

        HttpResponse<Map> response =
                unirestService.post(ROCKET_CHAT_API_LINK + "groups.delete").headers(headerMap).body(bodyMap)
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
    public boolean addUserToChatRoom(User user, String roomId)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        return modifyChatRoom(user, roomId, false);
    }

    @Override
    public boolean removeUserFromChatRoom(User user, String roomId)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        return modifyChatRoom(user, roomId, false);
    }

    private boolean modifyChatRoom(User user, String roomId, boolean addUser)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        //loginUser(ADMIN_USER);
        RocketChatUser student = loginUser(user);

        if (hasEmptyParameter(user.getRocketChatUsername(), roomId)) {
            return false;
        }
        Map<String, String> headerMap = new RocketChatHeaderMapBuilder().withRocketChatAdminAuth(this).build();
        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("roomId", roomId);
        bodyMap.put("userId", student.getRocketChatUserId());

        String groupUrl = addUser ? "groups.invite" : "groups.kick";

        HttpResponse<Map> response =
                unirestService.post(GFRocketChatConfig.ROCKET_CHAT_API_LINK + groupUrl).headers(headerMap).body(bodyMap)
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
    public String getChatRoomName(String roomId) throws RocketChatDownException, UserDoesNotExistInRocketChatException {

        //loginUser(ADMIN_USER);

        Map<String, String> headerMap = new RocketChatHeaderMapBuilder().withRocketChatAdminAuth(this).build();

        HttpResponse<Map> response = unirestService.get(ROCKET_CHAT_API_LINK + "groups.info").headers(headerMap)
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
    public RocketChatUser loginUser(User user) throws RocketChatDownException, UserDoesNotExistInRocketChatException {

        if (hasEmptyParameter(user.getEmail(), user.getPassword())) {
            return null;
        }

        //
        HashMap<String, String> rocketChatAuth = new HashMap<>();
        rocketChatAuth.put("user", user.getEmail());
        rocketChatAuth.put("password", user.getPassword());

        HttpResponse<RocketChatLoginResponse> response =
                unirestService.post(ROCKET_CHAT_API_LINK + "login").body(rocketChatAuth)
                        .asObject(RocketChatLoginResponse.class);

        if (isBadRequest(response)) {
            throw new UserDoesNotExistInRocketChatException();
        } else {
            /*if (ADMIN_USER.equals(user)) {
                setAdminToken();
            }*/
        }

        RocketChatLoginResponse rocketChatLoginResponse = response.getBody();

        RocketChatUser rocketChatUser = new RocketChatUser();
        rocketChatUser.setEmail(user.getEmail());
        rocketChatUser.setPassword(user.getPassword());
        rocketChatUser.setRocketChatUserId(rocketChatLoginResponse.getUserId());
        rocketChatUser.setRocketChatAuthToken(rocketChatLoginResponse.getAuthToken());

        return rocketChatUser;
    }

    @Override
    public boolean registerUser(User user) throws RocketChatDownException, UserExistsInRocketChatException {

        if (hasEmptyParameter(user.getEmail(), user.getName(), user.getPassword())) {
            return false;
        }

        HashMap<String, String> rocketChatRegister = new HashMap<>();
        if (user.getRocketChatUsername() == null) {
            String rocketChatUsername = createRocketChatUsername(user);
            user.setRocketChatUsername(rocketChatUsername);
        }
        rocketChatRegister.put("username", user.getRocketChatUsername());
        rocketChatRegister.put("email", user.getEmail());
        rocketChatRegister.put("pass", user.getPassword());
        rocketChatRegister.put("name", user.getName());

        HttpResponse<RocketChatRegisterResponse> response =
                unirestService.post(ROCKET_CHAT_API_LINK + "users.register").body(rocketChatRegister)
                        .asObject(RocketChatRegisterResponse.class);

        Boolean badRequest = isBadRequest(response);
        if (badRequest) {
            throw new UserExistsInRocketChatException();
        }

        RocketChatRegisterResponse registerResponse = response.getBody();

        // not sure we need this test
        if (!registerResponse.isSuccessful()) {
            return false;
        }

        // updateRocketChatUserName user with rocket chat data
        userDAO.updateRocketChatUserName(user);
        /**
         * TODO with higher rocket chat version a personal access tokens exist and this function can be used
         */
        //return createCustomAccessToken(user);
        return true;
    }

    public String getChatRoomLink(String userEmail, String projectName)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {

        //loginUser(ADMIN_USER);

        String chatRoomId = groupDAO.getGroupChatRoomId(new User(userEmail), new Project(projectName));
        if (chatRoomId.isEmpty()) {
            return Strings.EMPTY;
        }

        String chatRoomName = getChatRoomName(chatRoomId);
        if (chatRoomName.isEmpty()) {
            return Strings.EMPTY;
        }

        return ROCKET_CHAT_ROOM_LINK + chatRoomName + "?layout=embedded";
    }

    @Override
    public String getProjectChatRoomLink(String projectName) {
        return ROCKET_CHAT_ROOM_LINK + projectName + "?layout=embedded";
    }


    // TODO: Think about splitting email and chat communication into different
    @Override
    public boolean sendSingleMessage(EMailMessage eMailMessage, User user) {

        //loginUser(ADMIN_USER);

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
            user.setEmail(entry.getKey().getUserEmail());
            EMailMessage eMailMessage = new EMailMessage();
            eMailMessage.setSubject("Benachrichtigung über nicht erledigte Aufgaben im Projekt " + project.getName());
            eMailMessage.setBody(entry.getValue().toString());
            return !sendSingleMessage(eMailMessage, user);
        }).forEach(entry -> notSentEMailMap.put(entry.getKey(), entry.getValue()));
        return notSentEMailMap.isEmpty();
    }

    @Override
    public boolean sendMessageToUsers(Project project, EMailMessage eMailMessage) {
        List<User> users = userDAO.getUsersByProjectName(project.getName());
        List<User> userEmailProblemList =
                users.stream().filter(user -> !sendSingleMessage(eMailMessage, user)).collect(Collectors.toList());
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

    /**
     * TODO with higher rocket chat version a personal access tokens exist and this function can be used (ab 0.69
     * präferiert 0.70)
     */
/*    private boolean createCustomAccessToken(User user) {
        if (hasEmptyParameter(user.getRocketChatUserId())) {
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
    }*/
    private Boolean isBadRequest(HttpResponse response) throws RocketChatDownException {
        int status = response.getStatus();
        if (Response.Status.OK.getStatusCode() == status) {
            return false;
        }
        if (Response.Status.UNAUTHORIZED.getStatusCode() == status) {
            //unsetAdminToken();
            return true;
        }
        if (Response.Status.NOT_FOUND.getStatusCode() == status) {
            //unsetAdminToken();
            throw new RocketChatDownException();
        } else {
            return true;
        }
    }


    @Override
    public boolean exists(String roomId) throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        return !getChatRoomName(roomId).isEmpty();
    }

    public void delete(User user) throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        //loginUser(ADMIN_USER);
        // we need the rocketchatid
        if (!(user instanceof RocketChatUser)) {
            // we need the password to delete the user
            if (user.getPassword() == null) {
                user = userDAO.getUserByEmail(user.getEmail());
            }
            // fetchign the rocketchat id
            try {
                RocketChatUser rocketLeagueUser = loginUser(user);
                // the actual delete
                Map<String, String> headerMap = new RocketChatHeaderMapBuilder().withRocketChatAdminAuth(this).build();
                Map<String, String> bodyMap = new HashMap<>();
                bodyMap.put("userId", rocketLeagueUser.getRocketChatUserId());

                HttpResponse<RocketChatSuccessResponse> response =
                        unirestService.post(ROCKET_CHAT_API_LINK + "users.delete").headers(headerMap).body(bodyMap).asObject(RocketChatSuccessResponse.class);

                Boolean badRequest = isBadRequest(response);
                if (badRequest) {
                    //throw new UserDoesNotExistInRocketChatException();
                }
            } catch (UserDoesNotExistInRocketChatException e) {
                // wenn der Nutzer nicht existiert, brauchen wir ihn auch nicht löschen
            }
        }


    }
}

package unipotsdam.gf.modules.communication.service;

import io.github.openunirest.http.HttpResponse;
import io.github.openunirest.request.body.RequestBodyEntity;
import org.apache.http.HttpEntity;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.config.IConfig;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.exceptions.UserExistsInRocketChatException;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.modules.communication.model.EMailMessage;
import unipotsdam.gf.modules.communication.model.RocketChatUser;
import unipotsdam.gf.modules.communication.model.chat.ChatMessage;
import unipotsdam.gf.modules.communication.model.rocketChat.RocketChatLoginResponse;
import unipotsdam.gf.modules.communication.model.rocketChat.RocketChatRegisterResponse;
import unipotsdam.gf.modules.communication.model.rocketChat.RocketChatSuccessResponse;
import unipotsdam.gf.modules.communication.util.RocketChatHeaderMapBuilder;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.performance.PerformanceCandidates;
import unipotsdam.gf.modules.performance.PerformanceUtil;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.stream.Collectors;



@Resource
@ManagedBean
@Singleton
public class CommunicationService implements ICommunication {

    private final static Logger log = LoggerFactory.getLogger(CommunicationService.class);

    @Inject
    IConfig iConfig;

    @Inject
    private UnirestService unirestService;
    @Inject
    private UserDAO userDAO;
    @Inject
    private GroupDAO groupDAO;

    String ROCKET_CHAT_API_LINK;
    String ROCKET_CHAT_ROOM_LINK;
    RocketChatUser admin;

    public CommunicationService() {
        ROCKET_CHAT_API_LINK = iConfig.ROCKET_CHAT_API_LINK();
        ROCKET_CHAT_ROOM_LINK = iConfig.ROCKET_CHAT_ROOM_LINK();
        admin = iConfig.ADMIN_USER();
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

        Map<String, String> headerMap = new RocketChatHeaderMapBuilder().withRocketChatAdminAuth(this, admin).build();
        List<String> usernameList = member.stream().map(User::getRocketChatUsername).collect(Collectors.toList());
        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("name", specialFreeName(name));
        bodyMap.put("readOnly", readOnly);
        bodyMap.put("members", usernameList);


        RequestBodyEntity body =
                unirestService.post(ROCKET_CHAT_API_LINK + "groups.create").headers(headerMap).body(bodyMap);
        //HttpEntity entity = body.getEntity();
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

    boolean deleteChatRoom(String roomId) throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        // TODO: maybe add lock for getChatRoomName, so synchronized access doesn't create errors while deleting
        //loginUser(ADMIN_USER);

        Map<String, String> headerMap = new RocketChatHeaderMapBuilder().withRocketChatAdminAuth(this, admin).build();
        HashMap<String, String> bodyMap = new HashMap<>();
        addRoomIdToParamMap(roomId, bodyMap);

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

    private void addRoomIdToParamMap(String roomId, Map<String, String> bodyMap) {
        bodyMap.put("roomId", specialFreeName(roomId));
    }

    @Override
    public boolean addUserToChatRoom(User user, String roomId)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        return modifyChatRoom(user, roomId, true);
    }

    @Override
    public boolean removeUserFromChatRoom(User user, String roomId)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        return modifyChatRoom(user, roomId, false);
    }

    private boolean modifyChatRoom(User user, String roomId, boolean addUser)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        PerformanceUtil.start(PerformanceCandidates.MODIFY_CHAT_ROOM);

        //loginUser(ADMIN_USER);
        PerformanceUtil.start(PerformanceCandidates.LOGIN_USER);
        RocketChatUser student = loginUser(user);
        PerformanceUtil.stop(PerformanceCandidates.LOGIN_USER);

        if (hasEmptyParameter(user.getRocketChatUsername(), roomId)) {
            return false;
        }
        Map<String, String> headerMap = new RocketChatHeaderMapBuilder().withRocketChatAdminAuth(this, admin).build();
        Map<String, String> bodyMap = new HashMap<>();
        //bodyMap.put("roomId", specialFreeName(roomId));
        addRoomIdToParamMap(roomId, bodyMap);
        bodyMap.put("userId", student.getRocketChatUserId());

        String groupUrl = addUser ? "groups.invite" : "groups.kick";

        PerformanceUtil.start(PerformanceCandidates.MODIFY_CHAT_ROOM_REQUEST);
        HttpResponse<Map> response =
                unirestService.post(ROCKET_CHAT_API_LINK + groupUrl).headers(headerMap).body(bodyMap)
                        .asObject(Map.class);

        if (isBadRequest(response)) {
            return false;
        }

        Map responseMap = response.getBody();
        PerformanceUtil.stop(PerformanceCandidates.MODIFY_CHAT_ROOM_REQUEST);

        boolean result = !responseMap.containsKey("error") && !responseMap.get("success").equals("false");
        PerformanceUtil.stop(PerformanceCandidates.MODIFY_CHAT_ROOM);
        return  result;
    }

    @Override
    public boolean setChatRoomTopic(String roomId, String topic) {
        // TODO: not needed at the moment, possibly remove
        return false;
    }

    /**
     * api: https://rocket.chat/docs/developer-guides/rest-api/groups/info/
     * get information about the chat room
     *
     * @param roomId chat room id
     * @return chat room information
     */
    String getChatRoomName(String roomId) throws RocketChatDownException, UserDoesNotExistInRocketChatException {

        //loginUser(ADMIN_USER);

        Map<String, String> headerMap = new RocketChatHeaderMapBuilder().withRocketChatAdminAuth(this, admin).build();

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

        PerformanceUtil.start(PerformanceCandidates.ROCKET_REGISTER_USER_REQUEST);

        HttpResponse<RocketChatRegisterResponse> response =
                unirestService.post(ROCKET_CHAT_API_LINK + "users.register").body(rocketChatRegister)
                        .asObject(RocketChatRegisterResponse.class);
        PerformanceUtil.stop(PerformanceCandidates.ROCKET_REGISTER_USER_REQUEST);

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
        PerformanceUtil.start(PerformanceCandidates.ROCKET_REGISTER_USER_DAO);
        userDAO.updateRocketChatUserName(user);
        PerformanceUtil.stop(PerformanceCandidates.ROCKET_REGISTER_USER_DAO);
        /**
         * TODO with higher rocket chat version a personal access tokens exist and this function can be used
         */
        //return createCustomAccessToken(user);
        return true;
    }

    /**
     *
     * @param userEmail
     * @param projectName
     * @return
     * @throws RocketChatDownException
     * @throws UserDoesNotExistInRocketChatException
     */
    public String getGroupChatRoomLink(String userEmail, String projectName)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {

        //loginUser(ADMIN_USER);
        String chatRoomId = groupDAO.getGroupChatRoomId(new User(userEmail), projectName);
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
        return ROCKET_CHAT_ROOM_LINK + specialFreeName(projectName) + "?layout=embedded";
    }




    private boolean hasEmptyParameter(String... parameters) {
        return Arrays.stream(parameters).anyMatch(String::isEmpty);
    }

    private String createRocketChatUsername(User user) {
        PerformanceUtil.start(PerformanceCandidates.ROCKET_CREATE_USER);
        // TODO: eventually add username to normal registration
        String possibleUsername = user.getName().replaceAll(" ", "");
        int counter = 1;
        while (userDAO.existsByRocketChatUsername(possibleUsername)) {
            possibleUsername = user.getName().replaceAll(" ", "") + counter;
            counter++;
            System.out.println("rocket_create_user while loop executed");
        }
        PerformanceUtil.stop(PerformanceCandidates.ROCKET_CREATE_USER);
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
                Map<String, String> headerMap = new RocketChatHeaderMapBuilder().withRocketChatAdminAuth(this, admin)
                        .build();
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

    private String specialFreeName(String name) {
        String result =  String.valueOf(name.hashCode());
        return result;
    }


    public void logout(RocketChatUser user) throws RocketChatDownException, UserDoesNotExistInRocketChatException {

        // the actual delete
        Map<String, String> headerMap =
                new RocketChatHeaderMapBuilder()
                        .withRocketChatUserId(user.getRocketChatUserId())
                        .withAuthTokenHeader(user.getRocketChatAuthToken()).build();
        Map<String, String> bodyMap = new HashMap<>();
        //bodyMap.put("userId", user.getRocketChatUserId());

        HttpResponse<RocketChatSuccessResponse> response =
                unirestService.post(ROCKET_CHAT_API_LINK + "logout")
                        .headers(headerMap).body(bodyMap).asObject(RocketChatSuccessResponse.class);

        Boolean badRequest = isBadRequest(response);
        if (badRequest) {
            log.error(response.getStatusText().toString());
        }
    }
}

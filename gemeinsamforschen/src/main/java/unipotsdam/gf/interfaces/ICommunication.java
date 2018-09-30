package unipotsdam.gf.interfaces;

import unipotsdam.gf.core.management.group.Group;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.states.model.ConstraintsMessages;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.communication.model.EMailMessage;
import unipotsdam.gf.modules.communication.model.chat.ChatMessage;

import java.util.List;
import java.util.Map;

/**
 * Provides connection to rocket chat
 */
public interface ICommunication {

    /**
     * related endpoint: https://rocket.chat/docs/developer-guides/rest-api/groups/history/
     * get last 20 chat messages of specific chatroom
     *
     * @param roomId ID of room of user
     * @return List of Chat Messages
     */
    @Deprecated
    List<ChatMessage> getChatHistory(String roomId);

    @Deprecated
    boolean sendMessageToChat(EMailMessage EMailMessage, String roomId);

    /**
     * endpoint: https://rocket.chat/docs/developer-guides/rest-api/groups/create/
     * creates chatroom
     *
     * @param name chat room name
     * @return chat room id
     */
    String createChatRoom(String name, boolean readOnly, List<User> users);

    /**
     * creates chatRoom with name "group.projectId - group.id" and set chatRoomId for group
     *
     * @param group Object for information
     * @return true if chatRoom was created, otherwise false
     */
    boolean createChatRoom(Group group, boolean readOnly);

    String createEmptyChatRoom(String name, boolean readOnly);

    boolean deleteChatRoom(String roomId);

    /**
     * endpoint: https://rocket.chat/docs/developer-guides/rest-api/groups/invite/
     *
     * @param roomId chat room the user should be add to
     * @param user   information about user
     * @return if user was added successfully
     */
    boolean addUserToChatRoom(User user, String roomId);

    boolean removeUserFromChatRoom(User user, String roomId);

    /**
     * endpoint: https://rocket.chat/docs/developer-guides/rest-api/groups/settopic/
     *
     * @param roomId chat room where topic should be set
     * @param topic  topic of chat room
     * @return true, if topic was set correctly
     */
    @Deprecated
    boolean setChatRoomTopic(String roomId, String topic);


    /**
     * api: https://rocket.chat/docs/developer-guides/rest-api/groups/info/
     * get information about the chat room
     *
     * @param roomId chat room id
     * @return chat room information
     */
    String getChatRoomName(String roomId);

    boolean exists(String roomId);

    /**
     * api: https://rocket.chat/docs/developer-guides/rest-api/authentication/login/
     *
     * @param user username and password
     * @return information about user, especially authtoken for later use of endpoints
     */
    boolean loginUser(User user);

    /**
     * api 1: https://rocket.chat/docs/developer-guides/rest-api/users/register/
     * api 2: https://rocket.chat/docs/developer-guides/rest-api/users/generatepersonalaccesstoken/
     * api 3: https://rocket.chat/docs/developer-guides/rest-api/users/getpersonalaccesstokens/
     *
     * registers new user to rocket chat
     *
     * @param user registers user to rocket.chat
     * @return user id
     */
    boolean registerUser(User user);

    boolean registerAndLoginUser(User user);

    String getChatRoomLink(String userToken, String projectId);

    // TODO implement as Email or whatever
    void sendSingleMessage(EMailMessage EMailMessage, User user);

    //added by Axel.
    void informAboutMissingTasks(Map<StudentIdentifier, ConstraintsMessages> tasks, Project project);

    // TODO implement as Email or whatever
    void sendMessageToUsers(Project project, String message);
}

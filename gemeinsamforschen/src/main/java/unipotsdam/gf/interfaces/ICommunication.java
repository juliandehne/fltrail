package unipotsdam.gf.interfaces;

import unipotsdam.gf.modules.communication.model.chat.ChatMessage;
import unipotsdam.gf.modules.communication.model.chat.ChatRoom;
import unipotsdam.gf.modules.communication.model.user.User;
import unipotsdam.gf.modules.communication.model.user.UserCredentials;
import unipotsdam.gf.modules.communication.model.user.UserRegistrationInformation;

import java.util.List;

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
    List<ChatMessage> getChatHistory(String roomId);


    /**
     * endpoint: https://rocket.chat/docs/developer-guides/rest-api/groups/create/
     * creates chatroom
     *
     * @param name    chat room name
     * @param userIds member of chat by id; can be empty
     * @return chat room id
     */
    String createChatRoom(String name, List<String> userIds);


    /**
     * endpoint: https://rocket.chat/docs/developer-guides/rest-api/groups/invite/
     *
     * @param roomId chat room the user should be add to
     * @param userId userID to add
     * @return if user was added successfully
     */
    boolean addUserToChatRoom(String roomId, String userId);

    /**
     * endpoint: https://rocket.chat/docs/developer-guides/rest-api/groups/settopic/
     *
     * @param roomId chat room where topic should be set
     * @param topic  topic of chat room
     * @return true, if topic was set correctly
     */
    boolean setChatRoomTopic(String roomId, String topic);


    /**
     * api: https://rocket.chat/docs/developer-guides/rest-api/groups/info/
     * get information about the chat room
     *
     * @param roomId chat room id
     * @return chat room information
     */
    ChatRoom getChatRoomInfo(String roomId);

    /**
     * api: https://rocket.chat/docs/developer-guides/rest-api/authentication/login/
     *
     * @param userCredentials username and password
     * @return information about user, especially authtoken for later use of endpoints
     */
    User loginUser(UserCredentials userCredentials);

    /**
     * registers new user to rocket chat
     *
     * @param userRegistrationInformation registers user to rocket.chat
     * @return user id
     */
    String registerUser(UserRegistrationInformation userRegistrationInformation);

}

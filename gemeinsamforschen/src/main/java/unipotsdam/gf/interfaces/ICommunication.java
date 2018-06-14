package unipotsdam.gf.interfaces;

import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.modules.communication.model.Message;
import unipotsdam.gf.modules.communication.model.chat.ChatMessage;
import unipotsdam.gf.modules.communication.model.chat.ChatRoom;

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


    boolean sendMessageToChat(Message message, String roomId);

    /**
     * endpoint: https://rocket.chat/docs/developer-guides/rest-api/groups/create/
     * creates chatroom
     *
     * @param name                  chat room name
     * @param studentIdentifierList member of chat by id
     * @return chat room id
     */
    String createChatRoom(String name, List<User> studentIdentifierList);


    /**
     * endpoint: https://rocket.chat/docs/developer-guides/rest-api/groups/invite/
     *
     * @param roomId chat room the user should be add to
     * @param user   information about user
     * @return if user was added successfully
     */
    boolean addUserToChatRoom(String roomId, User user);

    boolean removeUserFromChatRoom(User user, String roomId);

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
     * @param user username and password
     * @return information about user, especially authtoken for later use of endpoints
     */
    boolean loginUser(User user);

    /**
     * registers new user to rocket chat
     *
     * @param user registers user to rocket.chat
     * @return user id
     */
    boolean registerUser(User user);

    String getChatRoomLink(String userToken, String projectToken, String groupToken);

}

package unipotsdam.gf.modules.communication.service;

import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.modules.communication.model.Message;
import unipotsdam.gf.modules.communication.model.chat.ChatMessage;
import unipotsdam.gf.modules.communication.model.chat.ChatRoom;

import java.util.List;

public class CommunicationDummyService implements ICommunication {

    @Override
    public List<ChatMessage> getChatHistory(String roomId) {
        return null;
    }

    @Override
    public boolean sendMessageToChat(Message message) {
        return false;
    }

    @Override
    public String createChatRoom(String name, List<User> studentIdentifierList) {
        return null;
    }

    @Override
    public boolean addUserToChatRoom(String roomId, User user) {
        return false;
    }

    @Override
    public boolean setChatRoomTopic(String roomId, String topic) {
        return false;
    }

    @Override
    public ChatRoom getChatRoomInfo(String roomId) {
        return null;
    }

    @Override
    public boolean loginUser(User user) {
        user.setRocketChatId("1");
        user.setRocketChatAuthToken("abc");
        return true;
    }

    @Override
    public boolean registerUser(User user) {
        user.setRocketChatId("1");
        return true;
    }
}

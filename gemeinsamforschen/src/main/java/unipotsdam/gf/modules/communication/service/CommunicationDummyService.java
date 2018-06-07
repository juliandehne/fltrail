package unipotsdam.gf.modules.communication.service;

import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.modules.communication.model.Message;
import unipotsdam.gf.modules.communication.model.chat.ChatMessage;
import unipotsdam.gf.modules.communication.model.chat.ChatRoom;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Singleton;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Resource
@ManagedBean
@Singleton
public class CommunicationDummyService implements ICommunication {

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
    public boolean sendMessageToChat(Message message) {
        throw new RuntimeException("Do not call this method without implementation");
    }

    @Override
    public String createChatRoom(String name, List<User> users) {
        if (Objects.isNull(users)) {
            return "2";
        }

        return "1";
    }

    @Override
    public boolean addUserToChatRoom(String roomId, User user) {
        throw new RuntimeException("Do not call this method without implementation");
    }

    @Override
    public boolean setChatRoomTopic(String roomId, String topic) {
        throw new RuntimeException("Do not call this method without implementation");
    }

    @Override
    public ChatRoom getChatRoomInfo(String roomId) {
        return new ChatRoom("1", "test");
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

    // TODO: remove after done implementing
    // just for postman testing
    public User getUser() {
        return new User("Martin Stähr", "test", "test@test.com", true);
    }
}

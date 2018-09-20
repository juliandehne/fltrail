package unipotsdam.gf.modules.communication.service;

import unipotsdam.gf.assignments.Assignee;
import unipotsdam.gf.assignments.NotImplementedLogger;
import unipotsdam.gf.config.Constants;
import unipotsdam.gf.core.management.Management;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.modules.communication.model.Message;
import unipotsdam.gf.modules.communication.model.chat.ChatMessage;
import unipotsdam.gf.modules.communication.model.chat.ChatRoom;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Resource
@ManagedBean
@Singleton
public class CommunicationDummyService implements ICommunication {

    @Inject
    Management managementService;

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
    public boolean addUserToChatRoom(String roomId, User user)  {
        NotImplementedLogger.logAssignment(Assignee.MARTIN, CommunicationDummyService.class, "addUserToChatRoom");
        return false;
    }

    @Override
    public boolean removeUserFromChatRoom(User user, String roomId)  {
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
        user.setRocketChatAuthToken("abc");
        return true;
    }

    @Override
    public boolean registerUser(User user) {
        user.setRocketChatId("1");
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
        return Constants.ROCKET_CHAT_URL + "/channel/" + channelName + "?layout=embedded";
    }

    @Override
    public void sendSingleMessage(Message message, User user)  {
        // TODO implement as email or directed message, popup after login or whatever
        String message2 = "sending email with message: "+ message.getMessage() + " to: "+ user.getEmail();
        NotImplementedLogger.logAssignment(Assignee.MARTIN, CommunicationDummyService.class, message2);
    }

    @Override
    public void sendMessageToUsers(Project project, String message) {
        // TODO implement as email or directed message, popup after login or whatever
        String message2 = "sending email with message: "+ message + " to: "+ project.getId();
        NotImplementedLogger.logAssignment(Assignee.MARTIN, CommunicationDummyService.class, message2);
    }

    // TODO: remove after done implementing
    // just for postman testing
    public User getUser() {
        return new User("Martin Stähr", "test", "test@test.com", true);
    }
}

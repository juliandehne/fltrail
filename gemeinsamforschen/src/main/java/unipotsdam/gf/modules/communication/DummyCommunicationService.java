package unipotsdam.gf.modules.communication;

import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.exceptions.UserExistsInRocketChatException;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.modules.communication.model.EMailMessage;
import unipotsdam.gf.modules.communication.model.RocketChatUser;
import unipotsdam.gf.modules.communication.model.chat.ChatMessage;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.constraints.ConstraintsMessages;

import java.util.List;
import java.util.Map;

public class DummyCommunicationService implements ICommunication {

    @Override
    public List<ChatMessage> getChatHistory(
            String roomId) {
        return null;
    }

    @Override
    public boolean sendMessageToChat(
            EMailMessage EMailMessage, String roomId) {
        return true;
    }

    @Override
    public String createChatRoom(String name, boolean readOnly, List<User> users)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        return null;
    }

    @Override
    public boolean createChatRoom(Group group, boolean readOnly)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        return true;
    }

    @Override
    public String createEmptyChatRoom(String name, boolean readOnly)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        return null;
    }

    @Override
    public void deleteChatRoom(Group group) throws RocketChatDownException, UserDoesNotExistInRocketChatException {

    }

    @Override
    public void deleteChatRoom(Project project) throws RocketChatDownException, UserDoesNotExistInRocketChatException {

    }

    @Override
    public boolean addUserToChatRoom(User user, String roomId)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        return false;
    }

    @Override
    public boolean removeUserFromChatRoom(User user, String roomId)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        return true;
    }

    @Override
    public boolean setChatRoomTopic(String roomId, String topic) {
        return true;
    }


    @Override
    public boolean exists(String roomId) throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        return false;
    }

    @Override
    public RocketChatUser loginUser(User user) throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        return null;
    }

    @Override
    public boolean registerUser(User user) throws RocketChatDownException, UserExistsInRocketChatException {
        return true;
    }

    @Override
    public String getGroupChatRoomLink(String userEmail, String projectId)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        return null;
    }

    @Override
    public String getProjectChatRoomLink(String projectName) {
        return null;
    }

    public boolean sendSingleMessage(EMailMessage EMailMessage, User user) {
        return true;
    }

    public boolean informAboutMissingTasks(
            Map<User, ConstraintsMessages> tasks, Project project) {
        return true;
    }

    public boolean sendMessageToUsers(Project project, EMailMessage eMailMessage) {
        return true;
    }

    @Override
    public void delete(User user) throws RocketChatDownException, UserDoesNotExistInRocketChatException {

    }
}

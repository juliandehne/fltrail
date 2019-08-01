package unipotsdam.gf.session;

import unipotsdam.gf.modules.communication.model.chat.ChatRoom;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.phases.Phase;

public class GFContext {
    private Project project;
    private User user;
    private Phase phase;
    private ChatRoom chatRoom;


    public GFContext(
            Project project, User user, Phase phase, ChatRoom chatRoom) {
        this.project = project;
        this.user = user;
        this.phase = phase;
        this.chatRoom = chatRoom;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GFContext{");
        sb.append("project=").append(project);
        sb.append(", user=").append(user);
        sb.append(", phase=").append(phase);
        sb.append(", chatRoom=").append(chatRoom);
        sb.append('}');
        return sb.toString();
    }
}

package unipotsdam.gf.session;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.modules.assessment.controller.model.Quiz;
import unipotsdam.gf.modules.communication.model.chat.ChatRoom;

public class GFContext {
    private Project project;
    private User user;
    private Phase phase;
    private ChatRoom chatRoom;
    // could be quizState ....
    private Quiz quiz;

    public GFContext(
            Project project, User user, Phase phase, ChatRoom chatRoom, Quiz quiz) {
        this.project = project;
        this.user = user;
        this.phase = phase;
        this.chatRoom = chatRoom;
        this.quiz = quiz;
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

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GFContext{");
        sb.append("project=").append(project);
        sb.append(", user=").append(user);
        sb.append(", phase=").append(phase);
        sb.append(", chatRoom=").append(chatRoom);
        sb.append(", quiz=").append(quiz);
        sb.append('}');
        return sb.toString();
    }
}

package unipotsdam.gf.core.management.group;

import unipotsdam.gf.core.management.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Group {

    private int id; // do not set yourself, autoincrement
    private List<User> members;
    private String projectId;
    private String chatRoomId;

    public Group() {
    }

    public Group(String projectId) {
        this(new ArrayList<>(), projectId);
    }

    public Group(List<User> members, String projectId) {
        this(members, projectId, "");
    }

    public Group(List<User> members, String projectId, String chatRoomId) {
        this.members = members;
        this.projectId = projectId;
        this.chatRoomId = chatRoomId;
    }

    public Group(int id, List<User> members, String projectId, String chatRoomId) {
        this.id = id;
        this.members = members;
        this.projectId = projectId;
        this.chatRoomId = chatRoomId;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public void addMember(User user) {
        members.add(user);
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        List<User> newMemberList = members.stream()
                .filter(member -> group.members.contains(member))
                .collect(Collectors.toList());
        return Objects.equals(members.size(), newMemberList.size()) &&
                Objects.equals(members.size(), group.members.size()) &&
                Objects.equals(projectId, group.projectId) &&
                Objects.equals(chatRoomId, group.chatRoomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(members, projectId, chatRoomId);
    }
}

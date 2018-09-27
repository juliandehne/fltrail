package unipotsdam.gf.core.management.group;

import unipotsdam.gf.core.management.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Group {

    private int id; // do not set yourself, autoincrement
    private List<User> members;
    private String projectName;
    private String chatRoomId;

    public Group() {
    }


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Group(String projectName) {
        this(new ArrayList<>(), projectName);
    }

    public Group(List<User> members, String projectName) {
        this(members, projectName, "");
    }

    public Group(int id, String projectName) {
        this(id, new ArrayList<>(), projectName, "");
    }

    public Group(List<User> members, String projectName, String chatRoomId) {
        this(0, members, projectName, chatRoomId);
    }

    public Group(int id, List<User> members, String projectName, String chatRoomId) {
        this.id = id;
        this.members = members;
        this.projectName = projectName;
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



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                Objects.equals(projectName, group.projectName) &&
                Objects.equals(chatRoomId, group.chatRoomId) &&
                Objects.equals(id, group.id) || Objects.equals(id, 0) || Objects.equals(group.id, 0);
    }

    @Override
    public int hashCode() {
        return Objects.hash(members, projectName, chatRoomId);
    }
}

package unipotsdam.gf.modules.group;

import unipotsdam.gf.modules.user.User;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private int id; // do not set yourself, autoincrement
    private List<User> members;
    private String projectName;
    private String chatRoomId;
    private String name;

    public Group() {
        members = new ArrayList<>();
    }


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Group(String projectName) {
        this(new ArrayList<>(), projectName);
        this.chatRoomId = projectName;
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
        if (Object.class.equals(Group.class)){
            Group group = (Group) o;
            return this.getId() == group.getId();
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        return getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

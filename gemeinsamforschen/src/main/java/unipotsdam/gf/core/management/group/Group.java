package unipotsdam.gf.core.management.group;

import unipotsdam.gf.core.management.user.User;

import java.util.*;

public class Group {



    List<User> members;

    public Group(List<User> members) {
        this.members = members;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }


}

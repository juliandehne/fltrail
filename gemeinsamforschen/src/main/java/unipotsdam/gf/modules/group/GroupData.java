package unipotsdam.gf.modules.group;

import java.util.ArrayList;
import java.util.List;

public class GroupData {
    List<Group> groups;

    public GroupData() {
    }

    public GroupData(List<Group> groups) {
        this.groups = groups;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }
}


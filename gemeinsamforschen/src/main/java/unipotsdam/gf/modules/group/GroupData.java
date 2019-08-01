package unipotsdam.gf.modules.group;

import java.util.ArrayList;
import java.util.List;



public class GroupData {
    private List<Group> groups;

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


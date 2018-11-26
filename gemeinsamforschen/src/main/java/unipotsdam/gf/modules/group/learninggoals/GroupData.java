package unipotsdam.gf.modules.group.learninggoals;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dehne on 04.12.2017.
 */

@XmlRootElement(name = "GroupData")
public class GroupData {

    public GroupData() {
        groups = new ArrayList<>();
    }

    public List<LearningGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<LearningGroup> groups) {
        this.groups = groups;
    }

    List<LearningGroup> groups;
}

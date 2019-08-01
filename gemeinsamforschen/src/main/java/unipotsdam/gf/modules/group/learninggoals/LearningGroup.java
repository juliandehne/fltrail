package unipotsdam.gf.modules.group.learninggoals;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dehne on 04.12.2017.
 */

@XmlRootElement(name = "LearningGroup")
public class LearningGroup {
    private List<String> users;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer id;

    public LearningGroup() {
        users = new ArrayList<>();
    }

    public LearningGroup(Integer id) {
        users = new ArrayList<>();
        this.id = id;
    }

    public LearningGroup(List<String> users) {
        this.users = users;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        LearningGroup that = (LearningGroup) o;

        return getId().equals(that.getId());

    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}

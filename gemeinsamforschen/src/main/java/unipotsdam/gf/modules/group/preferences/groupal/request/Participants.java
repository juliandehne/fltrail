package unipotsdam.gf.modules.group.preferences.groupal.request;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

public class Participants {

    private int id;
    private java.util.List<Criterion> criterion;

    public Participants() {
        criterion = new ArrayList<>();

    }

    @XmlAttribute
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement(name = "Criterion")
    public List<Criterion> getCriterion() {
        return criterion;
    }

    public void setCriterion(List<Criterion> criterion) {
        this.criterion = criterion;
    }
}

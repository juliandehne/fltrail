package unipotsdam.gf.modules.group.preferences.groupal.request;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "Participants")
public class ParticipantsHolder {

    private UsedCriteria usedCriteria;
    private java.util.List<Participants> participants;
    private int version;

    public ParticipantsHolder(
            UsedCriteria usedCriteria, List<Participants> participants) {
        this.usedCriteria = usedCriteria;
        this.participants = participants;
        setVersion(1);
    }

    public ParticipantsHolder() {
        participants = new ArrayList<>();
        setVersion(1);
    }

    @XmlElement(name = "UsedCriteria")
    public UsedCriteria getUsedCriteria() {
        return usedCriteria;
    }

    public void setUsedCriteria(UsedCriteria usedCriteria) {
        this.usedCriteria = usedCriteria;
    }

    @XmlElement(name = "participant")
    public List<Participants> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participants> participants) {
        this.participants = participants;
    }

    @XmlAttribute
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }



}

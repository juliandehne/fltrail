package unipotsdam.gf.modules.assessment;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
public class UserAssessmentDataHolder {
    private ArrayList<UserPeerAssessmentData> data;
    private Boolean isFinal;

    public UserAssessmentDataHolder() {
    }

    public UserAssessmentDataHolder(ArrayList<UserPeerAssessmentData> data) {
        this.data = data;
    }

    public ArrayList<UserPeerAssessmentData> getData() {
        return data;
    }

    public void setData(ArrayList<UserPeerAssessmentData> data) {
        this.data = data;
    }

    public Boolean getFinal() {
        return isFinal;
    }

    public void setFinal(Boolean aFinal) {
        isFinal = aFinal;
    }
}

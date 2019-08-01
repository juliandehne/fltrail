package unipotsdam.gf.modules.assessment;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserAssessmentDataHolder {
    private java.util.List<UserPeerAssessmentData> data;
    private Boolean isFinal;

    UserAssessmentDataHolder() {
    }

    public UserAssessmentDataHolder(java.util.List<UserPeerAssessmentData> data) {
        this.data = data;
    }

    public java.util.List<UserPeerAssessmentData> getData() {
        return data;
    }

    public void setData(java.util.List<UserPeerAssessmentData> data) {
        this.data = data;
    }

    public Boolean getFinal() {
        return isFinal;
    }

    public void setFinal(Boolean aFinal) {
        isFinal = aFinal;
    }
}

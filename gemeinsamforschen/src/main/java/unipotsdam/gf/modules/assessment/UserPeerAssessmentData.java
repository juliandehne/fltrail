package unipotsdam.gf.modules.assessment;

import unipotsdam.gf.modules.fileManagement.ContributionStorage;
import unipotsdam.gf.modules.user.User;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Dient der Kommunikation mit der FINAL GRADES Seite des Dozenten
 */
@XmlRootElement
public class UserPeerAssessmentData {
    private User user;
    private String groupName;
    private List<ContributionStorage> files;
    private Double groupProductRating;
    private Double groupWorkRating;
    //beyondStdDeviation is positive if groupWorkRating is too high, negative if it's too low, zero otherwise
    private Integer beyondStdDeviation;
    private Double cleanedGroupWorkRating;
    // not really used at the moment
    private Double selfAssessment;
    private Double docentProductRating;
    private Double suggestedRating;
    private Double finalRating;

    UserPeerAssessmentData() {
    }

    public UserPeerAssessmentData(User user, Double groupProductRating, Double groupWorkRating) {
        this.user = user;
        this.groupProductRating = groupProductRating;
        this.groupWorkRating = groupWorkRating;
    }

    public Double getGroupProductRating() {
        return groupProductRating;
    }

    void setGroupProductRating(Double groupProductRating) {
        this.groupProductRating = groupProductRating;
    }

    public Double getGroupWorkRating() {
        return groupWorkRating;
    }

    void setGroupWorkRating(Double groupWorkRating) {
        this.groupWorkRating = groupWorkRating;
    }

    public Integer getBeyondStdDeviation() {
        return beyondStdDeviation;
    }

    void setBeyondStdDeviation(Integer beyondStdDeviation) {
        this.beyondStdDeviation = beyondStdDeviation;
    }

    public Double getCleanedGroupWorkRating() {
        return cleanedGroupWorkRating;
    }

    void setCleanedGroupWorkRating(Double cleanedGroupWorkRating) {
        this.cleanedGroupWorkRating = cleanedGroupWorkRating;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<ContributionStorage> getFiles() {
        return files;
    }

    public void setFiles(List<ContributionStorage> files) {
        this.files = files;
    }

    public Double getSelfAssessment() {
        return selfAssessment;
    }

    public void setSelfAssessment(Double selfAssessment) {
        this.selfAssessment = selfAssessment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getDocentProductRating() {
        return docentProductRating;
    }

    void setDocentProductRating(Double docentProductRating) {
        this.docentProductRating = docentProductRating;
    }

    public Double getSuggestedRating() {
        return suggestedRating;
    }

    public Double getFinalRating() {
        return finalRating;
    }

    public void setFinalRating(Double finalRating) {
        this.finalRating = finalRating;
    }

    void setSuggestedRating(Double suggestedRating) {
        this.suggestedRating = suggestedRating;
    }
}

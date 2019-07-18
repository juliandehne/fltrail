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
    private Integer groupId;
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

    public UserPeerAssessmentData() {
    }

    public UserPeerAssessmentData(User user, Double groupProductRating, Double groupWorkRating) {
        this.user = user;
        this.groupProductRating = groupProductRating;
        this.groupWorkRating = groupWorkRating;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public List<ContributionStorage> getFiles() {
        return files;
    }

    public void setFiles(List<ContributionStorage> files) {
        this.files = files;
    }

    public Integer getBeyondStdDeviation() {
        return beyondStdDeviation;
    }

    public Double getCleanedGroupWorkRating() {
        return cleanedGroupWorkRating;
    }

    public void setCleanedGroupWorkRating(Double cleanedGroupWorkRating) {
        this.cleanedGroupWorkRating = cleanedGroupWorkRating;
    }

    public void setBeyondStdDeviation(Integer beyondStdDeviation) {
        this.beyondStdDeviation = beyondStdDeviation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getGroupProductRating() {
        return groupProductRating;
    }

    public void setGroupProductRating(Double groupProductRating) {
        this.groupProductRating = groupProductRating;
    }

    public Double getGroupWorkRating() {
        return groupWorkRating;
    }

    public void setGroupWorkRating(Double groupWorkRating) {
        this.groupWorkRating = groupWorkRating;
    }

    public Double getSelfAssessment() {
        return selfAssessment;
    }

    public void setSelfAssessment(Double selfAssessment) {
        this.selfAssessment = selfAssessment;
    }

    public Double getDocentProductRating() {
        return docentProductRating;
    }

    public void setDocentProductRating(Double docentProductRating) {
        this.docentProductRating = docentProductRating;
    }

    public Double getFinalRating() {
        return finalRating;
    }

    public void setFinalRating(Double finalRating) {
        this.finalRating = finalRating;
    }

    public Double getSuggestedRating() {
        return suggestedRating;
    }

    public void setSuggestedRating(Double suggestedRating) {
        this.suggestedRating = suggestedRating;
    }
}

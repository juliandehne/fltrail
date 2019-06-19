package unipotsdam.gf.modules.assessment;

import unipotsdam.gf.modules.user.User;

import java.util.HashMap;

/**
 * TODO Ev. wegschmeißen, mal schauen, was da abgeht
 * Dient der Kommunikation mit der FINAL GRADES Seite des Dozenten
 */
public class UserPeerAssessmentData {
    private User user;
    private Double groupProductRating;
    private Double groupWorkRating;
    // not really used at the moment
    private Double selfAssessment;

    public UserPeerAssessmentData(User user, Double groupProductRating, Double groupWorkRating) {
        this.user = user;
        this.groupProductRating = groupProductRating;
        this.groupWorkRating = groupWorkRating;
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
}

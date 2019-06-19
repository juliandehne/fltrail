package unipotsdam.gf.modules.assessment;

import unipotsdam.gf.modules.user.User;

public class UserAssessmentData extends UserPeerAssessmentData {

    private Double docentProductRating;
    private Double suggestedRating;
    private Double finalRating;


    public UserAssessmentData(
            User user, Double groupProductRating, Double groupWorkRating) {
        super(user, groupProductRating, groupWorkRating);
    }

    public UserAssessmentData(
            User user, Double groupProductRating, Double groupWorkRating, Double docentProductRating,
            Double finalRating) {
        super(user, groupProductRating, groupWorkRating);
        this.docentProductRating = docentProductRating;
        this.finalRating = finalRating;
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

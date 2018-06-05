package unipotsdam.gf.interfaces;

/**
 * Interface that defines a review for a students/groups contribution
 *
 * A review contains a feedback text and several scales which can be rated
 * It can also be exported to certain formats.
 */
public interface IContributionReview {

    /**
     * sets the reviews feedbackText.
     *
     * @param feedbackText
     */
    void setFeedbackText(String feedbackText);

    /**
     * returns the reviews feedbackText.
     *
     * @return feedbackText
     */
    String getFeedbackText();

    /**
     * adds a rating scale to the review. The scales name and the maximum value
     * that can be selected must be provided. The function returns an ID by
     * which the scale can be accessed.
     *
     * TODO: maybe address scales by their name instead?
     *
     * @param description
     * @param maxValue
     * @return scaleID
     */
    int addRatingScale(String description, int maxValue);

    /**
     * removes an existing scale from the review. The scale must be addressed by
     * a scaleID that was returned when it was created.
     *
     * TODO: maybe not necessary anyway?
     *
     * @param scaleID
     */
    void removeRatingScale(int scaleID);

    /**
     * sets the rating of an existing scale. Will throw an
     * IllegalArgumentException if the rating is higher than possible.
     *
     * @param scaleID
     * @param rating
     */
    void setRatingForScale(int scaleID, int rating) throws IllegalArgumentException;

    /**
     * returns the rating of an existing scale, addressed by it's scaleID.
     *
     * @param scaleID
     * @return rating
     */
    int getRatingOfScale(int scaleID);

    /**
     * exports (or rather serializes) the review as an object so that it can be
     * used by other applications.
     *
     * TODO: think of exportTypes
     * @param exportType
     * @return this object in another format
     */
    String exportAs(String exportType);

    /**
     * override for toString. Might just call .exportAs() internally
     * @return this objects String representation. Maybe not necessary, but nice
     * for sure.
     */
    @Override
    String toString();


}

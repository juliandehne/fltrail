package unipotsdam.gf.modules.assessment;

import unipotsdam.gf.modules.group.preferences.survey.LocalizedText;

/**
 * this is the holder object for the internal group peer assessment
 */
public class QuestionData {
    private GroupWorkDimensions groupWorkDimensions;
    private String key;
    private String question;
    private LocalizedText minRating;
    private LocalizedText maxRating;

    public LocalizedText getMinRating() {
        return minRating;
    }

    public void setMinRating(LocalizedText minRating) {
        this.minRating = minRating;
    }

    public LocalizedText getMaxRating() {
        return maxRating;
    }

    public void setMaxRating(LocalizedText maxRating) {
        this.maxRating = maxRating;
    }

    public QuestionData(GroupWorkDimensions groupWorkDimensions, String key, String question) {
        this.groupWorkDimensions = groupWorkDimensions;
        this.key = key;
        this.question = question;
    }

    public GroupWorkDimensions getGroupWorkDimensions() {
        return groupWorkDimensions;
    }

    public void setGroupWorkDimensions(GroupWorkDimensions groupWorkDimensions) {
        this.groupWorkDimensions = groupWorkDimensions;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}

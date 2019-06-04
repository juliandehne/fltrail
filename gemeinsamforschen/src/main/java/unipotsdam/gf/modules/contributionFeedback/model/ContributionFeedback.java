package unipotsdam.gf.modules.contributionFeedback.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContributionFeedback {

    private String id;
    private int groupId;
    private String fullSubmissionId;
    private String fullSubmissionPartCategory;
    private String text;

    public ContributionFeedback() {
    }

    public ContributionFeedback(String id, int groupId, String fullSubmissionId, String fullSubmissionPartCategory, String text) {
        this(groupId, fullSubmissionId, fullSubmissionPartCategory, text);
        this.id = id;
    }

    public ContributionFeedback(int groupId, String fullSubmissionId, String fullSubmissionPartCategory, String text) {
        this.groupId = groupId;
        this.fullSubmissionId = fullSubmissionId;
        this.fullSubmissionPartCategory = fullSubmissionPartCategory;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getFullSubmissionId() {
        return fullSubmissionId;
    }

    public void setFullSubmissionId(String fullSubmissionId) {
        this.fullSubmissionId = fullSubmissionId;
    }

    public String getFullSubmissionPartCategory() {
        return fullSubmissionPartCategory;
    }

    public void setFullSubmissionPartCategory(String fullSubmissionPartCategory) {
        this.fullSubmissionPartCategory = fullSubmissionPartCategory;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

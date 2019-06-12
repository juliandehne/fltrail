package unipotsdam.gf.modules.submission.model;

import unipotsdam.gf.modules.assessment.controller.model.ContributionCategory;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public class FullSubmission {

    // variables
    private String id;
    private long timestamp;
    private Integer groupId;
    private String userEmail;
    private String text;
    private ContributionCategory contributionCategory;
    private String projectName;
    private Visibility visibility;

    public FullSubmission(String id, long timestamp, Integer groupId, String text, ContributionCategory contributionCategory, String projectName, Visibility visibility) {
        this(id, timestamp, groupId, null, text, contributionCategory, projectName, visibility);
    }

    public FullSubmission(String id, long timestamp, Integer groupId, String userEmail, String text, ContributionCategory contributionCategory, String projectName, Visibility visibility) {
        this.id = id;
        this.timestamp = timestamp;
        this.groupId = groupId;
        this.userEmail = userEmail;
        this.text = text;
        this.contributionCategory = contributionCategory;
        this.projectName = projectName;
        this.visibility = visibility;
    }

    public FullSubmission(String submissionId) {
        this.id = submissionId;
    }

    // methods
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ContributionCategory getContributionCategory() {
        return contributionCategory;
    }

    public void setContributionCategory(ContributionCategory contributionCategory) {
        this.contributionCategory = contributionCategory;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    @Override
    public String toString() {
        return "FullSubmission{" +
                "id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", groupId=" + groupId +
                ", userEmail='" + userEmail + '\'' +
                ", text='" + text + '\'' +
                ", contributionCategory=" + contributionCategory +
                ", projectName='" + projectName + '\'' +
                ", visibility=" + visibility +
                '}';
    }

}

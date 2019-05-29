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
    private String text;
    private ContributionCategory contributionCategory;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    private String projectName;

    public FullSubmission(String id, long timestamp, Integer groupId, String text, ContributionCategory contributionCategory, String projectName) {
        this.id = id;
        this.timestamp = timestamp;
        this.groupId = groupId;
        this.text = text;
        this.contributionCategory = contributionCategory;
        this.projectName = projectName;
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

    @Override
    public String toString() {
        return "FullSubmission{" +
                "id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", groupId=" + groupId +
                ", text='" + text + '\'' +
                ", contributionCategory=" + contributionCategory +
                ", projectName='" + projectName + '\'' +
                '}';
    }

}

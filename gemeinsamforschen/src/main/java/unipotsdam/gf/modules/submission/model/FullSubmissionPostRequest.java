package unipotsdam.gf.modules.submission.model;

import unipotsdam.gf.modules.assessment.controller.model.ContributionCategory;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public class FullSubmissionPostRequest {

    // variables
    private Integer groupId;
    private String text;
    private String html;
    private String projectName;
    private ContributionCategory contributionCategory;

    public FullSubmissionPostRequest() {
    }

    // methods
    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupdId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public ContributionCategory getContributionCategory() {
        return contributionCategory;
    }

    public void setContributionCategory(ContributionCategory contributionCategory) {
        this.contributionCategory = contributionCategory;
    }

    @Override
    public String toString() {
        return "FullSubmissionPostRequest{" +
                "groupId=" + groupId +
                ", text='" + text + '\'' +
                ", html='" + html + '\'' +
                ", projectName='" + projectName + '\'' +
                ", contributionCategory='" + contributionCategory + '\'' +
                '}';
    }

}

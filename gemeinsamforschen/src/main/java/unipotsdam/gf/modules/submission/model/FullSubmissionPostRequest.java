package unipotsdam.gf.modules.submission.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import unipotsdam.gf.modules.assessment.controller.model.ContributionCategory;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FullSubmissionPostRequest {

    // variables
    private Integer groupId;
    private boolean personal;
    private String text;
    private String html;
    private String projectName;
    private ContributionCategory contributionCategory;

    // have to be set in backend
    private String userEMail;
    private Visibility visibility;

    public FullSubmissionPostRequest() {
    }

    // methods
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

    public boolean isPersonal() {
        return personal;
    }

    public void setPersonal(boolean personal) {
        this.personal = personal;
    }

    public String getUserEMail() {
        return userEMail;
    }

    public void setUserEMail(String userEMail) {
        this.userEMail = userEMail;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    @Override
    public String toString() {
        return "FullSubmissionPostRequest{" +
                "groupId=" + groupId +
                ", personal=" + personal +
                ", text='" + text + '\'' +
                ", html='" + html + '\'' +
                ", projectName='" + projectName + '\'' +
                ", contributionCategory=" + contributionCategory +
                '}';
    }
}

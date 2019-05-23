package unipotsdam.gf.modules.submission.model;

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

    public FullSubmissionPostRequest() {
    }

    // methods
    public Integer getGroupId() {
        return groupId;
    }

    public void setUser(Integer groupId) {
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

    @Override
    public String toString() {
        return "FullSubmissionPostRequest{" +
                "user='" + groupId + '\'' +
                ", text='" + text + '\'' +
                ", html='" + html + '\'' +
                ", projectName='" + projectName + '\'' +
                '}';
    }

}

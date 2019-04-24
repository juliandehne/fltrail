package unipotsdam.gf.modules.submission.model;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public class FullSubmissionPostRequest {

    // variables
    private String user;
    private String text;
    private String html;
    private String projectName;

    public FullSubmissionPostRequest() {
    }

    // methods
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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
                "user='" + user + '\'' +
                ", text='" + text + '\'' +
                ", html='" + html + '\'' +
                ", projectName='" + projectName + '\'' +
                '}';
    }

}

package unipotsdam.gf.modules.submission.model;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public class FullSubmissionPostRequest {

    // variables
    private String user;
    private String text;
    private String projectName;

    // constructors
    public FullSubmissionPostRequest(String user, String text, String projectName) {
        this.user = user;
        this.text = text;
        this.projectName = projectName;
    }

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

    public String getProjectId() {
        return projectName;
    }

    public void setProjectId(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        return "FullSubmissionPostRequest{" +
                "user='" + user + '\'' +
                ", text='" + text + '\'' +
                ", projectName='" + projectName + '\'' +
                '}';
    }

}

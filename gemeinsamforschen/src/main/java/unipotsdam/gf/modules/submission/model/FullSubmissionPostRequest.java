package unipotsdam.gf.modules.submission.model;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public class FullSubmissionPostRequest {

    // variables
    private String user;
    private String text;
    private String projectId;

    // constructors
    public FullSubmissionPostRequest(String user, String text, String projectId) {
        this.user = user;
        this.text = text;
        this.projectId = projectId;
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
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "FullSubmissionPostRequest{" +
                "user='" + user + '\'' +
                ", text='" + text + '\'' +
                ", projectId='" + projectId + '\'' +
                '}';
    }

}

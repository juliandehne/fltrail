package unipotsdam.gf.modules.submission.model;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public class FullSubmission {

    // variables
    private String id;
    private long timestamp;
    private String user;
    private String text;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    private String projectName;

    // constructor
    public FullSubmission(String id, long timestamp, String user, String text, String projectName) {
        this.id = id;
        this.timestamp = timestamp;
        this.user = user;
        this.text = text;
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


    @Override
    public String toString() {
        return "FullSubmission{" +
                "id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", user='" + user + '\'' +
                ", text='" + text + '\'' +
                ", projectName='" + projectName + '\'' +
                '}';
    }

}

package unipotsdam.gf.modules.submission.model;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public class FullSubmissionPostRequest {

    // variables
    private String user;
    private String text;

    // constructors
    public FullSubmissionPostRequest(String user, String text) {
        this.user = user;
        this.text = text;
    }

    public FullSubmissionPostRequest() {}

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

    @Override
    public String toString() {
        return "FullSubmissionPostRequest{" +
                "user='" + user + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

}

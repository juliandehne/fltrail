package unipotsdam.gf.modules.submission.model;

import java.util.ArrayList;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public class SubmissionPart {

    // variables
    private long timestamp;
    private String userEmail;
    private String fullSubmissionId;
    private String category;
    private ArrayList<SubmissionPartBodyElement> body;

    // constructor
    public SubmissionPart(long timestamp, String userEmail, String fullSubmissionId, String category, ArrayList<SubmissionPartBodyElement> body) {
        this.timestamp = timestamp;
        this.userEmail = userEmail;
        this.fullSubmissionId = fullSubmissionId;
        this.category = category;
        this.body = body;
    }

    public SubmissionPart() {
    }

    // methods
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getFullSubmissionId() {
        return fullSubmissionId;
    }

    public void setFullSubmissionId(String fullSubmissionId) {
        this.fullSubmissionId = fullSubmissionId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<SubmissionPartBodyElement> getBody() {
        return body;
    }

    public void setBody(ArrayList<SubmissionPartBodyElement> body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "SubmissionPart{" +
                "timestamp=" + timestamp +
                ", userEmail='" + userEmail + '\'' +
                ", fullSubmissionId='" + fullSubmissionId + '\'' +
                ", category=" + category +
                ", body=" + body +
                '}';
    }

}

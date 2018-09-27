package unipotsdam.gf.modules.submission.model;

import unipotsdam.gf.modules.peer2peerfeedback.Category;

import java.util.ArrayList;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public class SubmissionPartPostRequest {

    // variables
    private String userEmail;
    private String fullSubmissionId;
    private Category category;
    private ArrayList<SubmissionPartBodyElement> body;

    // constructors
    public SubmissionPartPostRequest(String userEmail, String fullSubmissionId, Category category, ArrayList<SubmissionPartBodyElement> body) {
        this.userEmail = userEmail;
        this.fullSubmissionId = fullSubmissionId;
        this.category = category;
        this.body = body;
    }

    public SubmissionPartPostRequest() {
    }

    // methods
    public String getUserId() {
        return userEmail;
    }

    public void setUserId(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getFullSubmissionId() {
        return fullSubmissionId;
    }

    public void setFullSubmissionId(String fullSubmissionId) {
        this.fullSubmissionId = fullSubmissionId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
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
        return "SubmissionPartPostRequest{" +
                "userEmail='" + userEmail + '\'' +
                ", fullSubmissionId='" + fullSubmissionId + '\'' +
                ", category=" + category +
                ", body=" + body +
                '}';
    }

}

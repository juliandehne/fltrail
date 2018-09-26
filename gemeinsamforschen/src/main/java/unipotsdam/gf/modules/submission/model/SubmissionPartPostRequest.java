package unipotsdam.gf.modules.submission.model;

import unipotsdam.gf.modules.peer2peerfeedback.Category;

import java.util.ArrayList;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public class SubmissionPartPostRequest {

    // variables
    private String userId;
    private String fullSubmissionId;
    private Category category;
    private ArrayList<SubmissionPartBodyElement> body;

    // constructors
    public SubmissionPartPostRequest(String userId, String fullSubmissionId, Category category, ArrayList<SubmissionPartBodyElement> body) {
        this.userId = userId;
        this.fullSubmissionId = fullSubmissionId;
        this.category = category;
        this.body = body;
    }

    public SubmissionPartPostRequest() {
    }

    // methods
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
                "userId='" + userId + '\'' +
                ", fullSubmissionId='" + fullSubmissionId + '\'' +
                ", category=" + category +
                ", body=" + body +
                '}';
    }

}

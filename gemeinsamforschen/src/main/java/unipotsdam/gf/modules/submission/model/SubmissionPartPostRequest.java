package unipotsdam.gf.modules.submission.model;

import unipotsdam.gf.modules.peer2peerfeedback.Category;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public class SubmissionPartPostRequest {
    // variables
    private String userId;
    private String fullSubmissionId;
    private String text;
    private Category category;

    // constructors
    public SubmissionPartPostRequest(String userId, String fullSubmissionId, String text, Category category) {
        this.userId = userId;
        this.fullSubmissionId = fullSubmissionId;
        this.text = text;
        this.category = category;
    }

    public SubmissionPartPostRequest(){}

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "SubmissionPartPostRequest{" +
                "userId='" + userId + '\'' +
                ", fullSubmissionId='" + fullSubmissionId + '\'' +
                ", text='" + text + '\'' +
                ", category=" + category +
                '}';
    }
}

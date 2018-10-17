package unipotsdam.gf.modules.submission.model;

import unipotsdam.gf.modules.feedback.Category;

public class SubmissionProjectRepresentation {

    // variables
    private String user;
    private Category category;
    private String fullSubmissionId;

    // constructor
    public SubmissionProjectRepresentation(String user, Category category, String fullSubmissionId) {
        this.user = user;
        this.category = category;
        this.fullSubmissionId = fullSubmissionId;
    }

    public SubmissionProjectRepresentation() {
    }

    // methods
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getFullSubmissionId() {
        return fullSubmissionId;
    }

    public void setFullSubmissionId(String fullSubmissionId) {
        this.fullSubmissionId = fullSubmissionId;
    }

    @Override
    public String toString() {
        return "SubmissionProjectRepresentation{" +
                "user='" + user + '\'' +
                ", category=" + category +
                ", fullSubmissionId='" + fullSubmissionId + '\'' +
                '}';
    }

}

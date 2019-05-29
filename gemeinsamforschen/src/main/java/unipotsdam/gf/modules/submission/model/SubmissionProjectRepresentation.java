package unipotsdam.gf.modules.submission.model;

import unipotsdam.gf.modules.annotation.model.Category;

public class SubmissionProjectRepresentation {

    // variables
    private Integer groupId;
    private Category category;
    private String fullSubmissionId;

    // constructor
    public SubmissionProjectRepresentation(Integer groupId, Category category, String fullSubmissionId) {
        this.groupId = groupId;
        this.category = category;
        this.fullSubmissionId = fullSubmissionId;
    }

    public SubmissionProjectRepresentation() {
    }

    // methods
    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(String user) {
        this.groupId = groupId;
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
                "groupId='" + groupId + '\'' +
                ", category=" + category +
                ", fullSubmissionId='" + fullSubmissionId + '\'' +
                '}';
    }

}

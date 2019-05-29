package unipotsdam.gf.modules.submission.model;

import unipotsdam.gf.modules.annotation.model.Category;

import java.util.ArrayList;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public class SubmissionPartPostRequest {

    // variables
    private Integer groupId;
    private String fullSubmissionId;
    private Category category;
    private ArrayList<SubmissionPartBodyElement> body;

    // constructors
    public SubmissionPartPostRequest(Integer groupId, String fullSubmissionId, Category category, ArrayList<SubmissionPartBodyElement> body) {
        this.groupId = groupId;
        this.fullSubmissionId = fullSubmissionId;
        this.category = category;
        this.body = body;
    }

    public SubmissionPartPostRequest() {
    }

    // methods
    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
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
                "groupId='" + groupId + '\'' +
                ", fullSubmissionId='" + fullSubmissionId + '\'' +
                ", category=" + category +
                ", body=" + body +
                '}';
    }

}

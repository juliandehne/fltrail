package unipotsdam.gf.process.tasks;

import unipotsdam.gf.modules.annotation.model.Category;
import unipotsdam.gf.modules.submission.model.FullSubmission;
import unipotsdam.gf.modules.user.User;

public class GroupFeedbackTaskData {
    private Integer targetGroupId;
    private String submissionId;
    private Category category;


    public GroupFeedbackTaskData() {
    }


    public GroupFeedbackTaskData(Integer targetGroupId, FullSubmission fullSubmission, Category category) {
        this.targetGroupId = targetGroupId;
        this.fullSubmission = fullSubmission;
        this.category = category;
    }

    private FullSubmission fullSubmission;

    public Integer getTarget() {
        return targetGroupId;
    }

    public void setTarget(Integer targetGroupId) {
        this.targetGroupId = targetGroupId;
    }


    public String getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(String submissionId) {
        this.submissionId = submissionId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    public FullSubmission getFullSubmission() {
        return fullSubmission;
    }

    public void setFullSubmission(FullSubmission fullSubmission) {
        this.fullSubmission = fullSubmission;
    }
}

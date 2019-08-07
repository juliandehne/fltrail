package unipotsdam.gf.process.tasks;

import unipotsdam.gf.modules.submission.model.FullSubmission;

public class GroupFeedbackTaskData {
    private Integer targetGroupId;
    private String submissionId;
    private String category;


    public GroupFeedbackTaskData() {
    }


    public GroupFeedbackTaskData(Integer targetGroupId, FullSubmission fullSubmission, String category) {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public FullSubmission getFullSubmission() {
        return fullSubmission;
    }

    public void setFullSubmission(FullSubmission fullSubmission) {
        this.fullSubmission = fullSubmission;
    }
}

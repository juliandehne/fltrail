package unipotsdam.gf.process.tasks;

import unipotsdam.gf.modules.feedback.Category;
import unipotsdam.gf.modules.submission.model.FullSubmission;
import unipotsdam.gf.modules.user.User;

public class FeedbackTaskData {



    private User target;

    private String submissionId;


    private Category category;

    public FeedbackTaskData() {
    }


    public FeedbackTaskData(User target, FullSubmission fullSubmission, Category category) {
        this.target = target;
        this.fullSubmission = fullSubmission;
        this.category = category;
    }

    private FullSubmission fullSubmission;

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
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

package unipotsdam.gf.modules.reflection.model;

import unipotsdam.gf.modules.submission.model.FullSubmission;

public class ReflectionQuestionAnswerDB {

    private String id;
    private String selectedReflectionQuestionId;
    private String fullSubmissionId;

    public ReflectionQuestionAnswerDB() {
    }

    public ReflectionQuestionAnswerDB(String id) {
        this.id = id;
    }

    public ReflectionQuestionAnswerDB(String id, String selectedReflectionQuestionId, String fullSubmissionId) {
        this(id);
        this.selectedReflectionQuestionId = selectedReflectionQuestionId;
        this.fullSubmissionId = fullSubmissionId;
    }

    public ReflectionQuestionAnswerDB(ReflectionQuestionAnswerDB other) {
        this(other.id, other.selectedReflectionQuestionId, other.fullSubmissionId);
    }

    public ReflectionQuestionAnswerDB(FullSubmission fullSubmission, SelectedReflectionQuestion selectedReflectionQuestion) {
        this.selectedReflectionQuestionId = selectedReflectionQuestion.getId();
        this.fullSubmissionId = fullSubmission.getId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSelectedReflectionQuestionId() {
        return selectedReflectionQuestionId;
    }

    public void setSelectedReflectionQuestionId(String selectedReflectionQuestionId) {
        this.selectedReflectionQuestionId = selectedReflectionQuestionId;
    }

    public String getFullSubmissionId() {
        return fullSubmissionId;
    }

    public void setFullSubmissionId(String fullSubmissionId) {
        this.fullSubmissionId = fullSubmissionId;
    }
}


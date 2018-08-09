package unipotsdam.gf.modules.submission.model;

import unipotsdam.gf.modules.peer2peerfeedback.Category;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public class SubmissionPart {
    // variables
    private String id;
    private long timestamp;
    private String userId;
    private String fullSubmissionId;
    private String text;
    private Category category;

    // constructor
    public SubmissionPart(String id, long timestamp, String userId, String fullSubmissionId, String text, Category category) {
        this.id = id;
        this.timestamp = timestamp;
        this.userId = userId;
        this.fullSubmissionId = fullSubmissionId;
        this.text = text;
        this.category = category;
    }

    public SubmissionPart(){}

    // methods
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

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
        return "SubmissionPart{" +
                "id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", userId='" + userId + '\'' +
                ", fullSubmissionId='" + fullSubmissionId + '\'' +
                ", text='" + text + '\'' +
                ", category=" + category +
                '}';
    }
}

package unipotsdam.gf.modules.annotation.model;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public class Annotation {

    // variables
    private String id;
    private long timestamp;
    private String userEmail;
    private String targetId;
    private String targetCategory;
    private AnnotationBody body;

    // constructor
    public Annotation(String id, long timestamp, String userEmail, String targetId, String targetCategory, AnnotationBody body) {
        this.id = id;
        this.timestamp = timestamp;
        this.userEmail = userEmail;
        this.targetId = targetId;
        this.targetCategory = targetCategory;
        this.body = body;
    }

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

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getTargetCategory() {
        return targetCategory;
    }

    public void setTargetCategory(String targetCategory) {
        this.targetCategory = targetCategory;
    }

    public AnnotationBody getBody() {
        return body;
    }

    public void setBody(AnnotationBody body) {
        this.body = body;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public String toString() {
        return "Annotation{" +
                "id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", userEmail='" + userEmail + '\'' +
                ", targetId=" + targetId +
                ", targetCategory=" + targetCategory +
                ", body=" + body +
                '}';
    }

}

package unipotsdam.gf.modules.annotation.model;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public class Annotation {

    // variables
    private String id;
    private long timestamp;
    private String userToken;
    private int targetId;
    private AnnotationBody body;

    // constructor
    public Annotation(String id, long timestamp, String userToken, int targetId, AnnotationBody body) {
        this.id = id;
        this.timestamp = timestamp;
        this.userToken = userToken;
        this.targetId = targetId;
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

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public AnnotationBody getBody() {
        return body;
    }

    public void setBody(AnnotationBody body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Annotation{" +
                "id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", userToken='" + userToken + '\'' +
                ", targetId=" + targetId +
                ", body=" + body +
                '}';
    }

}

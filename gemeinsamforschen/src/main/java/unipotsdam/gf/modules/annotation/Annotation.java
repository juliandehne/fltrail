package unipotsdam.gf.modules.annotation;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public class Annotation {

    // variables
    private int id;
    private long timestamp;
    private int userId;
    private int targetId;
    private String body;
    private int startCharacter;
    private int endCharacter;

    // constructor
    public Annotation(int id, long timestamp, int userId, int targetId, String body, int startCharacter, int endCharacter) {
        this.id = id;
        this.timestamp = timestamp;
        this.userId = userId;
        this.targetId = targetId;
        this.body = body;
        this.startCharacter = startCharacter;
        this.endCharacter = endCharacter;
    }

    // methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getStartCharacter() {
        return startCharacter;
    }

    public void setStartCharacter(int startCharacter) {
        this.startCharacter = startCharacter;
    }

    public int getEndCharacter() {
        return endCharacter;
    }

    public void setEndCharacter(int endCharacter) {
        this.endCharacter = endCharacter;
    }

    @Override
    public String toString() {
        return "Annotation{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", userId=" + userId +
                ", targetId=" + targetId +
                ", body='" + body + '\'' +
                ", startCharacter=" + startCharacter +
                ", endCharacter=" + endCharacter +
                '}';
    }

}

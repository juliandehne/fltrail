package unipotsdam.gf.modules.annotation.model;

public class AnnotationMessage {
    // variables
    private String from;
    private String targetId;
    private AnnotationMessageType type;
    private String annotationId;

    public enum AnnotationMessageType {
        CREATE,
        DELETE,
        EDIT
    }

    // methods
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public AnnotationMessageType getType() {
        return type;
    }

    public void setType(AnnotationMessageType type) {
        this.type = type;
    }

    public String getAnnotationId() {
        return annotationId;
    }

    public void setAnnotationId(String annotationId) {
        this.annotationId = annotationId;
    }

    @Override
    public String toString() {
        return "AnnotationMessage{" +
                "from='" + from + '\'' +
                ", targetId='" + targetId + '\'' +
                ", type=" + type +
                ", annotationId='" + annotationId + '\'' +
                '}';
    }
}

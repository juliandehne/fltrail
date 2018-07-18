package unipotsdam.gf.modules.annotation.model;

public class AnnotationMessage {
    // variables
    private String from;
    private AnnotationMessageType type;
    private String annotationId;

    public enum AnnotationMessageType {
        GET,
        DELETE
    }

    // methods
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
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
                ", type=" + type +
                ", annotationId='" + annotationId + '\'' +
                '}';
    }
}

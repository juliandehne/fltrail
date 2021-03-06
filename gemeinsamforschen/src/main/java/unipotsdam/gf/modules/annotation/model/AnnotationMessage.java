package unipotsdam.gf.modules.annotation.model;

public class AnnotationMessage {
    // variables
    private String from;
    private String targetId;
    private Category targetCategory;
    private AnnotationMessageType type;

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

    public Category getTargetCategory() {
        return targetCategory;
    }

    public void setTargetCategory(Category targetCategory) {
        this.targetCategory = targetCategory;
    }

    public AnnotationMessageType getType() {
        return type;
    }

    public void setType(AnnotationMessageType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AnnotationMessage{" +
                "from='" + from + '\'' +
                ", targetId='" + targetId + '\'' +
                ", targetCategory=" + targetCategory +
                ", type=" + type +
                '}';
    }

}

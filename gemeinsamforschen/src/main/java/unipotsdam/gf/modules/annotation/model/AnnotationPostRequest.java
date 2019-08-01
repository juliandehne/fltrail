package unipotsdam.gf.modules.annotation.model;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public class AnnotationPostRequest {

    // variables
    private String userEmail;
    private String targetId;
    private Category targetCategory;
    private AnnotationBody body;

    // constructors
    public AnnotationPostRequest(String userEmail, String targetId, Category targetCategory, AnnotationBody body) {
        this.userEmail = userEmail;
        this.targetId = targetId;
        this.targetCategory = targetCategory;
        this.body = body;
    }

    // methods
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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

    public AnnotationBody getBody() {
        return body;
    }

    public void setBody(AnnotationBody body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "AnnotationPostRequest{" +
                "userEmail='" + userEmail + '\'' +
                ", targetId=" + targetId +
                ", targetCategory=" + targetCategory +
                ", body=" + body +
                '}';
    }

}

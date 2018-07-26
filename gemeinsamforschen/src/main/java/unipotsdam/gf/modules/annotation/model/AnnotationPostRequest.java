package unipotsdam.gf.modules.annotation.model;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public class AnnotationPostRequest {

    // variables
    private String userToken;
    private int targetId;
    private AnnotationBody body;

    // constructors
    public AnnotationPostRequest(String userToken, int targetId, AnnotationBody body) {
        this.userToken = userToken;
        this.targetId = targetId;
        this.body = body;
    }

    public AnnotationPostRequest() {
    }

    // methods
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
        return "AnnotationPostRequest{" +
                "userToken='" + userToken + '\'' +
                ", targetId=" + targetId +
                ", body=" + body.toString() +
                '}';
    }

}

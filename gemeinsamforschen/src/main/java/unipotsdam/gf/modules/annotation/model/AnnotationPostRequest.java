package unipotsdam.gf.modules.annotation.model;

import unipotsdam.gf.modules.peer2peerfeedback.Category;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public class AnnotationPostRequest {

    // variables
    private String userToken;
    private String targetId;
    private Category targetCategory;
    private AnnotationBody body;

    // constructors
    public AnnotationPostRequest(String userToken, String targetId, Category targetCategory, AnnotationBody body) {
        this.userToken = userToken;
        this.targetId = targetId;
        this.targetCategory = targetCategory;
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
                "userToken='" + userToken + '\'' +
                ", targetId=" + targetId +
                ", targetCategory=" + targetCategory +
                ", body=" + body +
                '}';
    }

}

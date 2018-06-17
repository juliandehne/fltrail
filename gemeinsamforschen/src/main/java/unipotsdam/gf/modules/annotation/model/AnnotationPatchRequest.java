package unipotsdam.gf.modules.annotation.model;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public class AnnotationPatchRequest {

    // variables
    private String body;

    // constructors
    public AnnotationPatchRequest(String body) {
        this.body = body;
    }

    public AnnotationPatchRequest() {}

    // methods
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "AnnotationPatchRequest{" +
                "body='" + body + '\'' +
                '}';
    }

}

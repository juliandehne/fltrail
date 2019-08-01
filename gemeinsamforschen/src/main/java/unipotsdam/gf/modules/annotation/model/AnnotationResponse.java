package unipotsdam.gf.modules.annotation.model;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public class AnnotationResponse {

    // variables
    private String message;

    public AnnotationResponse() {

    }

    // methods
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AnnotationResponse{" +
                "message='" + message + '\'' +
                '}';
    }

}

package unipotsdam.gf.modules.submission.model;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public class SubmissionResponse {

    // variables
    String message;

    // constructors
    public SubmissionResponse(String message) {
        this.message = message;
    }

    public SubmissionResponse() {
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
        return "SubmissionResponse{" +
                "message='" + message + '\'' +
                '}';
    }

}

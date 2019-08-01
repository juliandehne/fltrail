package unipotsdam.gf.modules.annotation.model;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public class AnnotationPatchRequest {

    // variables
    private String title;
    private String comment;

    // constructors
    public AnnotationPatchRequest(String title, String comment) {
        this.title = title;
        this.comment = comment;
    }

    // methods
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "AnnotationPatchRequest{" +
                "title='" + title + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

}

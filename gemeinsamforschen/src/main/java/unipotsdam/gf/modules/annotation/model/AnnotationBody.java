package unipotsdam.gf.modules.annotation.model;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public class AnnotationBody {

    // variables
    private String title;
    private String comment;
    private int startCharacter;
    private int endCharacter;

    // constructor
    public AnnotationBody(String title, String comment, int startCharacter, int endCharacter) {
        this.title = title;
        this.comment = comment;
        this.startCharacter = startCharacter;
        this.endCharacter = endCharacter;
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

    public int getStartCharacter() {
        return startCharacter;
    }

    public void setStartCharacter(int startCharacter) {
        this.startCharacter = startCharacter;
    }

    public int getEndCharacter() {
        return endCharacter;
    }

    public void setEndCharacter(int endCharacter) {
        this.endCharacter = endCharacter;
    }

    @Override
    public String toString() {
        return "AnnotationBody{" +
                "title='" + title + '\'' +
                ", comment='" + comment + '\'' +
                ", startCharacter=" + startCharacter +
                ", endCharacter=" + endCharacter +
                '}';
    }

}

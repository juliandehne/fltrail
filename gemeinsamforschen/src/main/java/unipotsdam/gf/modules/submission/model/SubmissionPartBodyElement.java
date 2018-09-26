package unipotsdam.gf.modules.submission.model;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public class SubmissionPartBodyElement {

    // variables
    private String text;
    private int startCharacter;
    private int endCharacter;

    // constructors
    public SubmissionPartBodyElement(String text, int startCharacter, int endCharacter) {
        this.text = text;
        this.startCharacter = startCharacter;
        this.endCharacter = endCharacter;
    }

    public SubmissionPartBodyElement() {
    }

    // methods
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
        return "SubmissionPartBodyElement{" +
                ", text='" + text + '\'' +
                ", startCharacter=" + startCharacter +
                ", endCharacter=" + endCharacter +
                '}';
    }

}

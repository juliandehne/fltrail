package unipotsdam.gf.modules.group;

public class ProfileQuestion {
    private int id;
    private int scaleSize;
    private String question;

    public ProfileQuestion() {
    }

    public ProfileQuestion(int id, int scaleSize, String question) {
        this.id = id;
        this.scaleSize = scaleSize;
        this.question = question;
    }

    public ProfileQuestion(int firstQuestionId) {
        this.id= firstQuestionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScaleSize() {
        return scaleSize;
    }

    public void setScaleSize(int scaleSize) {
        this.scaleSize = scaleSize;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    //pr
}

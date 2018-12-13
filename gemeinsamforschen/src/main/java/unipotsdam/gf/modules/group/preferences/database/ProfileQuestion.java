package unipotsdam.gf.modules.group.preferences.database;

public class ProfileQuestion {
    private int id;
    private int scaleSize;
    private String question;
    private String question_en;
    private String subvariable;

    public ProfileQuestion() {
    }

    public ProfileQuestion(int scaleSize, String question, String question_en, String subvariable) {
        this.scaleSize = scaleSize;
        this.question = question;
        this.question_en = question_en;
        this.subvariable = subvariable;
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

    public String getQuestion_en() {
        return question_en;
    }

    public void setQuestion_en(String question_en) {
        this.question_en = question_en;
    }


    public String getSubvariable() {
        return subvariable;
    }

    public void setSubvariable(String subvariable) {
        this.subvariable = subvariable;
    }

}

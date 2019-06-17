package unipotsdam.gf.modules.assessment;

public class QuestionData {
    private GroupWorkDimensions groupWorkDimensions;
    private String key;
    private String question;

    public QuestionData(GroupWorkDimensions groupWorkDimensions, String key, String question) {
        this.groupWorkDimensions = groupWorkDimensions;
        this.key = key;
        this.question = question;
    }

    public GroupWorkDimensions getGroupWorkDimensions() {
        return groupWorkDimensions;
    }

    public void setGroupWorkDimensions(GroupWorkDimensions groupWorkDimensions) {
        this.groupWorkDimensions = groupWorkDimensions;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}

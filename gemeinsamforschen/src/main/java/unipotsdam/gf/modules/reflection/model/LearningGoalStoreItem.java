package unipotsdam.gf.modules.reflection.model;

public class LearningGoalStoreItem {

    private String text;

    public LearningGoalStoreItem(String text) {
        this.text = text;
    }

    public LearningGoalStoreItem() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

package unipotsdam.gf.modules.reflection.model;

import unipotsdam.gf.modules.project.Project;

public class LearningGoal {

    private String id;
    private String text;
    private String projectName;

    public LearningGoal(String id, String text, String projectName) {
        this.id = id;
        this.text = text;
        this.projectName = projectName;
    }

    public LearningGoal(LearningGoalStoreItem item, Project project) {
        this.text = item.getText();
        this.projectName = project.getName();
    }

    public LearningGoal() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}

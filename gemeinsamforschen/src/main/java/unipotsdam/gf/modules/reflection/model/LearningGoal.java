package unipotsdam.gf.modules.reflection.model;

import unipotsdam.gf.modules.project.Project;

public class LearningGoal {

    private String id;
    private String text;
    private String projectName;
    private boolean finished = false;

    public LearningGoal(String id) {
        this.id = id;
    }

    public LearningGoal(String id, String text, String projectName, boolean finished) {
        this(id);
        this.text = text;
        this.projectName = projectName;
        this.finished = finished;
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

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}

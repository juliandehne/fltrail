package unipotsdam.gf.modules.researchreport.model;

import unipotsdam.gf.modules.peer2peerfeedback.Category;

public class ResearchReportSection {

    private int id;
    private String content;
    private Category category;
    private int groupId;
    private String projectName;

    public ResearchReportSection() {
    }

    public ResearchReportSection(String content, Category category, int groupId, String projectName) {
        this.content = content;
        this.category = category;
        this.groupId = groupId;
        this.projectName = projectName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        return "ResearchReportSection{" +
                "content='" + content + '\'' +
                ", category=" + category +
                ", groupId=" + groupId +
                ", projectName='" + projectName + '\'' +
                '}';
    }
}

package unipotsdam.gf.modules.journal.model;

import unipotsdam.gf.core.management.project.Project;

import java.util.ArrayList;
import java.util.Map;

import static unipotsdam.gf.view.MarkdownUtils.convertMarkdownToHtml;

/**
 * Model class for the project descriptionHTML of the e portfolio
 */

public class ProjectDescription {

    private long id;
    private String name;
    private String descriptionHTML;
    private String descriptionMD;
    private boolean open;
    private Project project;
    private Map<String,String> links;
    private ArrayList<String> group;
    private long timestamp;

    public ProjectDescription() {
    }

    public ProjectDescription(long id, String name, String description, Project project, Map<String, String> links, ArrayList<String> group, long timestamp) {
        this.id = id;
        this.name = name;
        this.descriptionHTML = convertMarkdownToHtml(description);
        this.descriptionMD =description;
        this.project = project;
        this.links = links;
        this.group = group;
        this.timestamp = timestamp;
        this.open =true;
    }

    public void setDescription (String description){
        this.descriptionMD = description;
        this.descriptionHTML = convertMarkdownToHtml(description);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptionHTML() {
        return descriptionHTML;
    }

    public void setDescriptionHTML(String descriptionHTML) {
        this.descriptionHTML = descriptionHTML;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Map<String, String> getLinks() {
        return links;
    }

    public void setLinks(Map<String, String> links) {
        this.links = links;
    }

    public ArrayList<String> getGroup() {
        return group;
    }

    public void setGroup(ArrayList<String> group) {
        this.group = group;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getDescriptionMD() {
        return descriptionMD;
    }

    public void setDescriptionMD(String descriptionMD) {
        this.descriptionMD = descriptionMD;
    }


    @Override
    public String toString() {
        return "ProjectDescription{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", descriptionHTML='" + descriptionHTML + '\'' +
                ", descriptionMD='" + descriptionMD + '\'' +
                ", open=" + open +
                ", project=" + project +
                ", links=" + links +
                ", group=" + group +
                ", timestamp=" + timestamp +
                '}';
    }
}

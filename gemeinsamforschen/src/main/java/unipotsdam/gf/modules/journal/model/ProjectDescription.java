package unipotsdam.gf.modules.journal.model;

import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;

import java.util.ArrayList;
import java.util.Map;

import static unipotsdam.gf.view.MarkdownUtils.convertMarkdownToHtml;

/**
 * Model class for the project descriptionHTML of the e portfolio
 */

public class ProjectDescription {

    private String id;
    private StudentIdentifier student;
    private String descriptionHTML;
    private String descriptionMD;
    private boolean open;
    private ArrayList<Link> links;
    private ArrayList<String> group;
    private long timestamp;

    public ProjectDescription() {
    }

    public ProjectDescription(String id, String name, String description, String project, ArrayList<Link> links, ArrayList<String> group, long timestamp) {
        this.id = id;
        this.student = new StudentIdentifier(project,name);
        this.descriptionHTML = convertMarkdownToHtml(description);
        this.descriptionMD =description;
        this.links = links;
        this.group = group;
        this.timestamp = timestamp;
        this.open =true;
    }
    public ProjectDescription(String id, String name, String description, String project, ArrayList<Link> links, ArrayList<String> group, long timestamp, boolean open) {
        this.id = id;
        this.student = new StudentIdentifier(project,name);
        this.descriptionHTML = convertMarkdownToHtml(description);
        this.descriptionMD =description;
        this.links = links;
        this.group = group;
        this.timestamp = timestamp;
        this.open = open;
    }

    public void setDescription (String description){
        this.descriptionMD = description;
        this.descriptionHTML = convertMarkdownToHtml(description);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StudentIdentifier getStudent() {
        return student;
    }

    public void setStudent(StudentIdentifier student) {
        this.student = student;
    }

    public String getDescriptionHTML() {
        return descriptionHTML;
    }

    public void setDescriptionHTML(String descriptionHTML) {
        this.descriptionHTML = descriptionHTML;
    }

    public String getDescriptionMD() {
        return descriptionMD;
    }

    public void setDescriptionMD(String descriptionMD) {
        this.descriptionMD = descriptionMD;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public ArrayList<Link> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<Link> links) {
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

    @Override
    public String toString() {
        return "ProjectDescription{" +
                "id='" + id + '\'' +
                ", student=" + student +
                ", descriptionHTML='" + descriptionHTML + '\'' +
                ", descriptionMD='" + descriptionMD + '\'' +
                ", open=" + open +
                ", links=" + links +
                ", group=" + group +
                ", timestamp=" + timestamp +
                '}';
    }
}

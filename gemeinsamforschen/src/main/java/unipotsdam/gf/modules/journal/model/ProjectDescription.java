package unipotsdam.gf.modules.journal.model;

import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.modules.assessment.controller.StudentIdentifier;

import java.util.ArrayList;
import java.util.Map;

/**
 * Model class for the project description of the e portfolio
 */

public class ProjectDescription {

    long id;
    String name;
    String description;
    Project project;
    ArrayList<Map<String,String>> links ;
    ArrayList<StudentIdentifier> group;
    long timestamp;

    public ProjectDescription() {
    }

    public ProjectDescription(long id, String name, String description, Project project, ArrayList<Map<String, String>> links, ArrayList<StudentIdentifier> group, long timestamp) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.project = project;
        this.links = links;
        this.group = group;
        this.timestamp = timestamp;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public ArrayList<Map<String, String>> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<Map<String, String>> links) {
        this.links = links;
    }

    public ArrayList<StudentIdentifier> getGroup() {
        return group;
    }

    public void setGroup(ArrayList<StudentIdentifier> group) {
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", project=" + project +
                ", links=" + links +
                ", group=" + group +
                ", timestamp=" + timestamp +
                '}';
    }
}

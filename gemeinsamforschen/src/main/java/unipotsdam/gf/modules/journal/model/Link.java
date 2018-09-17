package unipotsdam.gf.modules.journal.model;

public class Link {

    private String id;
    private String projectDescription;
    private String name;
    private String link;

    public Link() {
    }

    public Link(String id, String projectDescription, String name, String link) {
        this.id = id;
        this.projectDescription = projectDescription;
        this.name = name;
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Link{" +
                "id='" + id + '\'' +
                ", projectDescription='" + projectDescription + '\'' +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}

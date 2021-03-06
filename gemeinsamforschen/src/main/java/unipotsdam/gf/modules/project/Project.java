package unipotsdam.gf.modules.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import unipotsdam.gf.modules.group.preferences.survey.GroupWorkContext;
import unipotsdam.gf.process.phases.Phase;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

/**
 * Created by dehne on 31.05.2018.
 */
@XmlRootElement(name = "Project")
public class Project {

    private String name;
    private String password;
    private Boolean active;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long timecreated; //timestamp macht zu viele Probleme
    // the id of the authorEmail (not the token)
    private String authorEmail;
    private Phase phase;
    private String[] tags;
    private String description;
    private Boolean isSurvey;
    private List<String> categories;
    private Integer groupSize;

    public Project(String name, String password, Boolean active, String author, String[] tags, List<String> categories, Integer groupSize) {
        this.name = name;
        this.password = password;
        this.active = active;
        this.authorEmail = author;
        this.timecreated = System.currentTimeMillis();
        // default starting at course creation if new
        this.setPhase(Phase.GroupFormation);
        this.tags = tags;
        this.isSurvey = false;
        this.categories = categories;
        this.groupSize = groupSize;
    }

    public Integer getGroupSize() {
        return groupSize;
    }

    public Project(String name, String password, Boolean active, String author, String[] tags, List<String> categories) {
        this.name = name;
        this.password = password;
        this.active = active;
        this.authorEmail = author;
        this.timecreated = System.currentTimeMillis();
        // default starting at course creation if new
        this.setPhase(Phase.GroupFormation);
        this.tags = tags;
        this.isSurvey = false;
        this.categories = categories;
    }

    public void setGroupSize(Integer groupSize) {
        this.groupSize = groupSize;
    }

    public List<String> getCategories() {
        return categories;
    }




    private GroupWorkContext groupWorkContext;

    public Project() {
        tags = new String[0];
        this.timecreated = System.currentTimeMillis();
        this.active = true;

    }

    public Project(String name, String password, Boolean active, String author, String[] tags) {
        this.name = name;
        this.password = password;
        this.active = active;
        this.authorEmail = author;
        this.timecreated = System.currentTimeMillis();
        // default starting at course creation if new
        this.setPhase(Phase.GroupFormation);
        this.tags = tags;
        this.isSurvey = false;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }


    public Project(
            String name, String password, Boolean active,
            Long timecreated, String authorEmail,
            Phase phase, String[] tags, String description) {
        this.name = name;
        this.password = password;
        this.active = active;
        this.authorEmail = authorEmail;
        this.timecreated = timecreated;
        this.phase = phase;
        this.tags = tags;
        this.description = description;
        this.isSurvey = false;
    }

    public Project(String projectName, String authorEmail) {
        this.name = projectName;
        this.authorEmail = authorEmail;
        this.active = true;
        this.timecreated = System.currentTimeMillis();
        tags = new String[0];
        this.isSurvey = false;
    }

    public Project(String projectName) {
        this.name = projectName;
        this.active = true;
        this.isSurvey = false;
        this.timecreated = System.currentTimeMillis();
        tags = new String[0];
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Boolean getActive() {
        return active;
    }

    Long getTimecreated() {
        return timecreated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getSurvey() {
        return isSurvey;
    }

    public void setSurvey(Boolean survey) {
        isSurvey = survey;
    }

    public GroupWorkContext getGroupWorkContext() {
        return groupWorkContext;
    }

    public void setGroupWorkContext(GroupWorkContext groupWorkContext) {
        this.groupWorkContext = groupWorkContext;
    }

    @Override
    public String toString() {
        return "Project{name='" + name + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Project project = (Project) o;
        return Objects.equals(getName(), project.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
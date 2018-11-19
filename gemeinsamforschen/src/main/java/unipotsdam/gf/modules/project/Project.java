package unipotsdam.gf.modules.project;

import unipotsdam.gf.process.phases.Phase;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;

/**
 * Created by dehne on 31.05.2018.
 */
@XmlRootElement(name = "Project")
public class Project {

    private String name;
    private String password;
    private Boolean active;
    private Long timecreated; //timestamp macht zu viele Probleme
    // the id of the authorEmail (not the token)
    private String authorEmail;
    private Phase phase;
    private String[] tags;
    private String description;

    public Project() {
        tags = new String[0];
    }

    public Project(String name, String password, Boolean active, String author, String[] tags) {
        this.name = name;
        this.password = password;
        this.active = active;
        this.authorEmail = author;
        this.timecreated = System.currentTimeMillis();
        // default starting at course creation if new
        this.setPhase(Phase.CourseCreation);
        this.tags = tags;
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
    }

    public Project(String projectName, String authorEmail) {
        this.name = projectName;
        this.authorEmail = authorEmail;
        this.active = true;
        this.timecreated = System.currentTimeMillis();
        tags = new String[0];
    }

    public Project(String projectName) {
        this.name = projectName;
        this.active = true;
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

    /**
     * setting phase only with enum
     *
     * @param phase
     */
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

    public Long getTimecreated() {
        return timecreated;
    }

    public void setTimecreated(Long timecreated) {
        this.timecreated = timecreated;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Project{");
        sb.append("name='").append(name).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", active=").append(active);
        sb.append(", timecreated=").append(timecreated);
        sb.append(", authorEmail='").append(authorEmail).append('\'');
        sb.append(", phase=").append(phase);
        sb.append(", tags=").append(Arrays.toString(tags));
        sb.append('}');
        return sb.toString();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
package unipotsdam.gf.core.management.project;

import unipotsdam.gf.core.states.model.ProjectPhase;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by dehne on 31.05.2018.
 */
@XmlRootElement(name = "Project")
public class Project {

    private String id;
    private String password;
    private Boolean active;
    private Timestamp timecreated;
    // the id of the authorEmail (not the token)
    private String authorEmail;
    private String adminPassword;
    private String token;
    private ProjectPhase phase;
    private String[] tags;

    public Project() {
    }

    public Project(String id, String password, Boolean active, String author, String adminPassword, String[] tags) {
        this.id = id;
        this.password = password;
        this.active = active;
        this.authorEmail = author;
        this.adminPassword = adminPassword;
        this.timecreated = Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC")));
        // default starting at course creation if new
        this.setPhase(ProjectPhase.CourseCreation);
        this.tags = tags;
    }

    public Project(String id, String password, Boolean active,
                   Timestamp timecreated, String author, String adminPassword,
                   String token, ProjectPhase phase) {
        this.id = id;
        this.password = password;
        this.active = active;
        this.timecreated = timecreated;
        this.authorEmail = author;
        this.adminPassword = adminPassword;
        this.token = token;
        this.phase = phase;
        this.tags = tags;
    }

    public Project(String projectName) {
        setId(projectName);
    }

    public String getName() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getTimecreated() {
        return timecreated;
    }

    public ProjectPhase getPhase() {
        return phase;
    }

    /**
     * setting phase only with enum
     *
     * @param phase
     */
    public void setPhase(ProjectPhase phase) {
        this.phase = phase;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Project{");
        sb.append("id='").append(id).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", active=").append(active);
        sb.append(", timecreated=").append(timecreated);
        sb.append(", authorEmail='").append(authorEmail).append('\'');
        sb.append(", adminPassword='").append(adminPassword).append('\'');
        sb.append(", phase='").append(phase).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
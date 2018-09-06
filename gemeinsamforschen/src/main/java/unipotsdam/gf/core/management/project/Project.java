package unipotsdam.gf.core.management.project;

import unipotsdam.gf.core.states.ProjectPhase;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by dehne on 31.05.2018.
 */
public class Project {

    private String id;
    private String password;
    private Boolean active;
    private Timestamp timecreated;
    private String author;
    private String adminPassword;
    private String token;
    // TODO: using enum ProjectPhase
    private String phase;

    public Project() {
    }

    public Project(String id, String password, Boolean active, String author, String adminPassword) {
        this.id = id;
        this.password = password;
        this.active = active;
        this.author = author;
        this.adminPassword = adminPassword;
        this.timecreated = Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC")));
        // default starting at course creation if new
        this.setPhase(ProjectPhase.CourseCreation);
    }

    public Project(String id, String password, Boolean active, String author, String adminPassword, String token, ProjectPhase projectPhase) {
        this.id = id;
        this.password = password;
        this.active = active;
        this.author = author;
        this.adminPassword = adminPassword;
        this.token = token;
        this.phase = projectPhase.name();

        this.timecreated = Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC")));
    }

    public Project(String id, String password, Boolean active, Timestamp timecreated, String author, String adminPassword, String token, String phase) {
        this.id = id;
        this.password = password;
        this.active = active;
        this.timecreated = timecreated;
        this.author = author;
        this.adminPassword = adminPassword;
        this.token = token;
        this.phase = phase;
    }

    public String getId() {
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public String getPhase() {
        return phase;
    }

    /**
     * setting phase only with enum
     *
     * @param phase
     */
    public void setPhase(ProjectPhase phase) {
        this.phase = phase.name();
    }


}
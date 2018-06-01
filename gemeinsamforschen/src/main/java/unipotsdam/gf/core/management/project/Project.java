package unipotsdam.gf.core.management.project;

import org.glassfish.grizzly.http.util.TimeStamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by dehne on 31.05.2018.
 */
public class Project {

    private String id;
    private String password;
    private String activ;
    private Timestamp timecreated;
    private String author;
    private String adminpassword;
    private String token;

    public Project() {
    }

    public Project(
            String id, String password, String activ, String author, String adminpassword) {
        this.id = id;
        this.password = password;
        this.activ = activ;
        this.author = author;
        this.adminpassword = adminpassword;

        Timestamp ts = Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC")));
        this.timecreated = ts;
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

    public String getActiv() {
        return activ;
    }

    public void setActiv(String activ) {
        this.activ = activ;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAdminpassword() {
        return adminpassword;
    }

    public void setAdminpassword(String adminpassword) {
        this.adminpassword = adminpassword;
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


}
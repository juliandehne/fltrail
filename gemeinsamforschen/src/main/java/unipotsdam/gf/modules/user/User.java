package unipotsdam.gf.modules.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

/**
 * Created by dehne on 31.05.2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private String name;
    private String password;
    private String email;
    // the speaking name must unique in rocket chat
    private String rocketChatUsername;

    private Boolean isStudent;

    private String discordid;

    private Integer id;


    public User() {
        this.discordid = "";
    }

    public User(String name, String password, String email, String rocketChatUsername, Boolean isStudent) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.rocketChatUsername = rocketChatUsername;
        this.setStudent(isStudent);
        this.discordid = "";
    }

    public User(String name, String password, String email, String rocketChatUsername, Boolean isStudent, String discordId) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.rocketChatUsername = rocketChatUsername;
        this.setStudent(isStudent);
        this.discordid=discordId;
    }


    public User(String userEmail) {
        this.email = userEmail;
        this.discordid = "";
    }

    public User(String name, String password, String email, Boolean isStudent) {
        this.name = name;
        this.password= password;
        this.email = email;
        this.isStudent = isStudent;
        this.discordid = "";
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getStudent() {
        return isStudent;
    }

    public void setStudent(Boolean student) {
        isStudent = student;
    }

    public String getRocketChatUsername() {
        return rocketChatUsername;
    }

    public void setRocketChatUsername(String rocketChatUsername) {
        this.rocketChatUsername = rocketChatUsername;
    }

    public void setDiscordid(String discordid) {
        this.discordid = discordid;
    }


    public String getDiscordid() {
        return discordid;
    }



    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", rocketChatUsername='" + rocketChatUsername + '\'' +
                ", isStudent=" + isStudent +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) ;
    }

    @Override
    public int hashCode() {
        return getEmail().hashCode();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

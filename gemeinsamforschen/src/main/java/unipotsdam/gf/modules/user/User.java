package unipotsdam.gf.modules.user;

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
    private String rocketChatAuthToken;
    private String rocketChatId;
    private Boolean isStudent;

    public User() {
    }

    public User(String name, String password, String email, Boolean isStudent) {
        this(name, password, email, "", "", isStudent);
    }

    public User(String name, String password, String email,  String rocketChatAuthToken, String rocketChatId, Boolean isStudent) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.rocketChatAuthToken = rocketChatAuthToken;
        this.rocketChatId = rocketChatId;
        this.isStudent = isStudent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getRocketChatId() {
        return rocketChatId;
    }

    public void setRocketChatId(String rocketChatId) {
        this.rocketChatId = rocketChatId;
    }

    public String getRocketChatAuthToken() {
        return rocketChatAuthToken;
    }

    public void setRocketChatAuthToken(String rocketChatAuthToken) {
        this.rocketChatAuthToken = rocketChatAuthToken;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", rocketChatAuthToken='" + rocketChatAuthToken + '\'' +
                ", rocketChatId='" + rocketChatId + '\'' +
                ", isStudent=" + isStudent +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(rocketChatAuthToken, user.rocketChatAuthToken) &&
                Objects.equals(rocketChatId, user.rocketChatId) &&
                Objects.equals(isStudent, user.isStudent);
    }
}

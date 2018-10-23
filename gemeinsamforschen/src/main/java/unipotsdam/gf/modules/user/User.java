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
    private String rocketChatUsername;
    private String rocketChatAuthToken;
    private String rocketChatPersonalAccessToken;
    private String rocketChatUserId;
    private Boolean isStudent;

    public User() {
    }



    public User(String name, String password, String email, String rocketChatUsername, String rocketChatPersonalAccessToken, String rocketChatUserId) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.rocketChatUsername = rocketChatUsername;
        this.rocketChatPersonalAccessToken = rocketChatPersonalAccessToken;
        this.rocketChatUserId = rocketChatUserId;

    }

    public User(String name, String password, String email, String rocketChatUsername,
                String rocketChatAuthToken, String rocketChatPersonalAccessToken, String rocketChatUserId,
                Boolean isStudent) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.rocketChatUsername = rocketChatUsername;
        this.rocketChatAuthToken = rocketChatAuthToken;
        this.rocketChatPersonalAccessToken = rocketChatPersonalAccessToken;
        this.rocketChatUserId = rocketChatUserId;
        this.isStudent = isStudent;
    }

    public User(String authorEmail) {
        this.email = authorEmail;
    }

    public User(String name, String password, String email, Boolean isStudent) {
        this.name = name;
        this.password= password;
        this.email = email;
        this.isStudent = isStudent;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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

    public String getRocketChatUserId() {
        return rocketChatUserId;
    }

    public void setRocketChatUserId(String rocketChatUserId) {
        this.rocketChatUserId = rocketChatUserId;
    }

    public String getRocketChatAuthToken() {
        return rocketChatAuthToken;
    }

    public void setRocketChatAuthToken(String rocketChatAuthToken) {
        this.rocketChatAuthToken = rocketChatAuthToken;
    }

    public String getRocketChatUsername() {
        return rocketChatUsername;
    }

    public void setRocketChatUsername(String rocketChatUsername) {
        this.rocketChatUsername = rocketChatUsername;
    }

    public String getRocketChatPersonalAccessToken() {
        return rocketChatPersonalAccessToken;
    }

    public void setRocketChatPersonalAccessToken(String rocketChatPersonalAccessToken) {
        this.rocketChatPersonalAccessToken = rocketChatPersonalAccessToken;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", rocketChatUsername='" + rocketChatUsername + '\'' +
                ", rocketChatAuthToken='" + rocketChatAuthToken + '\'' +
                ", rocketChatPersonalAccessToken='" + rocketChatPersonalAccessToken + '\'' +
                ", rocketChatUserId='" + rocketChatUserId + '\'' +
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
}

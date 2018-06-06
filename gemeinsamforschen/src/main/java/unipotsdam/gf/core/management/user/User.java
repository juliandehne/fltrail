package unipotsdam.gf.core.management.user;

/**
 * Created by dehne on 31.05.2018.
 */
public class User {

    // the email is the id!
    public String getId() {
        return this.email;
    }
    private String name;
    private String password;
    private String email;
    private String token;
    private String rocketChatAuthToken;
    private String rocketChatId;
    private Boolean isStudent;

    public User() {
    }

    public User(String name, String password, String email, Boolean isStudent) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.isStudent = isStudent;
    }

    public User(String name, String password, String email, String rocketChatId, Boolean isStudent) {
        this.name = name;
        this.password = password;
        this.email = email;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
                ", token='" + token + '\'' +
                ", rocketChatAuthToken='" + rocketChatAuthToken + '\'' +
                ", rocketChatId='" + rocketChatId + '\'' +
                ", isStudent=" + isStudent +
                '}';
    }
}

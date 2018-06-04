package unipotsdam.gf.core.management.user;

/**
 * Created by dehne on 31.05.2018.
 */
public class User {

    public User() {
    }

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
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

    // the email is the id!
    public String getId() {
        return this.email;
    }
    private String name;
    private String password;
    private String email;
    private String token;
}
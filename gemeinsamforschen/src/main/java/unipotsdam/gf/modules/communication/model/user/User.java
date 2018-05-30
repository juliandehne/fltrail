package unipotsdam.gf.modules.communication.model.user;

public class User {

    String id;
    String authToken;

    public User() {}

    public User(String id, String authToken) {
        this.id = id;
        this.authToken = authToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}

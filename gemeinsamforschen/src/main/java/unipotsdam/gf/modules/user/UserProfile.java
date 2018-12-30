package unipotsdam.gf.modules.user;

import java.util.HashMap;

/**
 * Created by dehne on 31.05.2018.
 */
// User data specific to Group Formation or Asessment
public class UserProfile {

    public User getUser() {
        return user;
    }

    private final User user;

    public UserProfile(HashMap<String, String> data, User user, String projectId) {
        this.user = user;
        this.data = data;
    }

    public HashMap<String, String> getData() {
        return data;
    }

    private final HashMap<String,String> data;



}

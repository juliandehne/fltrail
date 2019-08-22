package unipotsdam.gf.modules.communication.model;

import unipotsdam.gf.modules.user.User;

import java.util.Objects;

public class RocketChatUser extends User {
    private String rocketChatAuthToken;
    // not in this version
    private String rocketChatPersonalAccessToken;
    // the actual id that oculd be used instead of the email with the queries
    private String rocketChatUserId;

    public RocketChatUser(String name, String password, String email, String rocketChatUsername,
                String rocketChatAuthToken, String rocketChatPersonalAccessToken, String rocketChatUserId,
                Boolean isStudent) {
        super(name, password,email,rocketChatUsername, isStudent);
        this.rocketChatAuthToken = rocketChatAuthToken;
        this.rocketChatPersonalAccessToken = rocketChatPersonalAccessToken;
        this.rocketChatUserId = rocketChatUserId;

    }

    public RocketChatUser() {

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

    public String getRocketChatPersonalAccessToken() {
        return rocketChatPersonalAccessToken;
    }

    public void setRocketChatPersonalAccessToken(String rocketChatPersonalAccessToken) {
        this.rocketChatPersonalAccessToken = rocketChatPersonalAccessToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        RocketChatUser that = (RocketChatUser) o;
        return Objects.equals(getRocketChatAuthToken(), that.getRocketChatAuthToken()) && Objects
                .equals(getRocketChatUserId(), that.getRocketChatUserId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getRocketChatAuthToken(), getRocketChatUserId());
    }
}

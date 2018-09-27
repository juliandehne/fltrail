package unipotsdam.gf.modules.communication.model.rocketChat;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.logging.log4j.util.Strings;

import java.util.Map;

public class RocketChatRegisterResponse {

    private Map user;
    private String error;

    private boolean successful;

    public Map getUser() {
        return user;
    }

    public void setUser(Map user) {
        this.user = user;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @JsonProperty("success")
    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getUserId() {
        if (!isSuccessful()) {
            // TODO: throw error
            return Strings.EMPTY;
        }
        return user.get("_id").toString();
    }

    public boolean isEmailExistError() {
        boolean emailError = false;
        if (!isSuccessful()) {
            emailError = error.contains("Email already exists.");
        }
        return emailError;
    }
}

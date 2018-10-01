package unipotsdam.gf.modules.communication.model.rocketChat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RocketChatLoginResponse {

    private String status;

    // for success data
    private Map data;

    // for error data
    private String error;
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RocketChatLoginResponse{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public boolean isSuccessful() {
        return status.equals("success");
    }

    public boolean hasError() {
        return status.equals("error");
    }

    public String getAuthToken() {
        // TODO: throw exception for error
        return isSuccessful() ? data.get("authToken").toString() : "";
    }

    public String getUserId() {
        // TODO: throw exception for error
        return isSuccessful() ? data.get("userId").toString() : "";
    }
}

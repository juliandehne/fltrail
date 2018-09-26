package unipotsdam.gf.modules.communication.model.rocketChat;

import java.util.Map;

public class RocketChatResponse {

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
        return "RocketChatResponse{" +
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

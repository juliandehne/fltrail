package unipotsdam.gf.modules.communication.model.rocketChat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RocketChatSuccessResponse {
    private String success;

    public RocketChatSuccessResponse() {
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}

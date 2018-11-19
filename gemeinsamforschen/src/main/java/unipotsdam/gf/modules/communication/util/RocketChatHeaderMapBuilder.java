package unipotsdam.gf.modules.communication.util;

import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.modules.communication.model.RocketChatUser;
import unipotsdam.gf.modules.communication.service.CommunicationService;

import java.util.HashMap;
import java.util.Map;

import static unipotsdam.gf.config.GFRocketChatConfig.ADMIN_USER;

public class RocketChatHeaderMapBuilder {

    private Map<String, String> headerMap;

    public RocketChatHeaderMapBuilder() {
        headerMap = new HashMap<>();
    }

    public RocketChatHeaderMapBuilder withAuthTokenHeader(String authToken) {
        headerMap.put("X-Auth-Token", authToken);
        return this;
    }

    public RocketChatHeaderMapBuilder withRocketChatUserId(String userId) {
        headerMap.put("X-User-Id", userId);
        return this;
    }

    public RocketChatHeaderMapBuilder withRocketChatAdminAuth(CommunicationService communicationService)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        // with new version of rocketChat: RocketChatHeaderMapBuilder rocketChatHeaderMapBuilder = withAuthTokenHeader(ADMIN_USER.getRocketChatPersonalAccessToken());

        RocketChatUser admin = communicationService.loginUser(ADMIN_USER);

        RocketChatHeaderMapBuilder rocketChatHeaderMapBuilder = withAuthTokenHeader(admin.getRocketChatAuthToken());
        return rocketChatHeaderMapBuilder.withRocketChatUserId(admin.getRocketChatUserId());
    }

    public Map<String, String> build() {
        return headerMap;
    }
}

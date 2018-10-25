package unipotsdam.gf.modules.communication.util;

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

    public RocketChatHeaderMapBuilder withRocketChatAdminAuth() {
        // with new version of rocketChat: RocketChatHeaderMapBuilder rocketChatHeaderMapBuilder = withAuthTokenHeader(ADMIN_USER.getRocketChatPersonalAccessToken());
        RocketChatHeaderMapBuilder rocketChatHeaderMapBuilder = withAuthTokenHeader(ADMIN_USER.getRocketChatAuthToken());
        return rocketChatHeaderMapBuilder.withRocketChatUserId(ADMIN_USER.getRocketChatUserId());
    }

    public Map<String, String> build() {
        return headerMap;
    }
}

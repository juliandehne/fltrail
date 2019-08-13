package unipotsdam.gf.modules.communication.util;

import org.apache.maven.profiles.ProfilesConversionUtils;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.modules.communication.model.RocketChatUser;
import unipotsdam.gf.modules.communication.service.CommunicationService;
import unipotsdam.gf.modules.performance.PerformanceCandidates;
import unipotsdam.gf.modules.performance.PerformanceUtil;

import java.util.HashMap;
import java.util.Map;

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

    public RocketChatHeaderMapBuilder withRocketChatAdminAuth(CommunicationService communicationService,
                                                              RocketChatUser ADMIN_USER)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        RocketChatHeaderMapBuilder rocketChatHeaderMapBuilder = withAuthTokenHeader(ADMIN_USER.getRocketChatPersonalAccessToken());
/*
        PerformanceUtil.start(PerformanceCandidates.ROCKET_LOGIN);

        RocketChatUser admin = communicationService.loginUser(ADMIN_USER);

        RocketChatHeaderMapBuilder rocketChatHeaderMapBuilder = withAuthTokenHeader(admin.getRocketChatAuthToken());
        RocketChatHeaderMapBuilder result =
                rocketChatHeaderMapBuilder.withRocketChatUserId(admin.getRocketChatUserId());

        PerformanceUtil.stop(PerformanceCandidates.ROCKET_LOGIN);*/

        RocketChatHeaderMapBuilder result =
                rocketChatHeaderMapBuilder.withRocketChatUserId(ADMIN_USER.getRocketChatUserId());
        return result;
    }

    public Map<String, String> build() {
        return headerMap;
    }
}

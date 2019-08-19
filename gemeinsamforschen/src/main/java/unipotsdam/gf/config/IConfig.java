package unipotsdam.gf.config;

import unipotsdam.gf.modules.communication.model.RocketChatUser;

public interface IConfig {
    // module group
    String getCompBaseUrl();

    // module largefilestorage
    String getLargeFileStoragePath();

    String getDBUserName();
    String getDBPassword();
    String getDBName();
    String getDBURL();

    String ROCKET_CHAT_LINK_0();
    String ROCKET_CHAT_LINK();
    RocketChatUser ADMIN_USER();
    String ROCKET_CHAT_ROOM_LINK();
    String ROCKET_CHAT_API_LINK();
    RocketChatUser TEST_USER();
}

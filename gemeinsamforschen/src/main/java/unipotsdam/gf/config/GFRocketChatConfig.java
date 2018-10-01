package unipotsdam.gf.config;

import unipotsdam.gf.core.management.user.User;

public class GFRocketChatConfig {

    private static final String ROCKET_CHAT_LINK = "http://rocketchat.westeurope.cloudapp.azure.com/";

    public static final String ROCKET_CHAT_ROOM_LINK = ROCKET_CHAT_LINK + "group/";

    public static final String ROCKET_CHAT_API_LINK = ROCKET_CHAT_LINK + "api/v1/";

    public static final User ADMIN_USER = new User("admin nachname", "passwort",
            "email", "token", "rocketChatUsername", "rocketChatAuthToken",
            "rocketChatPersonalAccessToken", "rocketChatUserId", false);

    public static final User TEST_USER = new User("test nachname", "passwort",
            "email", "token", "rocketChatUsername", "rocketChatAuthToken",
            "rocketChatPersonalAccessToken", "rocketChatUserId", false);
}

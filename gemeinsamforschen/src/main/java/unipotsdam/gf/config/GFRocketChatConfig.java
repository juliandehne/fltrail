package unipotsdam.gf.config;

import unipotsdam.gf.core.management.user.User;

public class GFRocketChatConfig {

    public static final String ROCKET_CHAT_API_LINK = "http://rocketchat.westeurope.cloudapp.azure.com/api/v1/";

    public static final String ADMIN_USERNAME = "";
    public static final String ADMIN_TOKEN = "";

    public static final User TEST_USER = new User("username", "password", "email",
            "rocketChatUserName", false);
}

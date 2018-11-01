package unipotsdam.gf.config;


import unipotsdam.gf.modules.user.User;

public class GFRocketChatConfig {

    //private static final String ROCKET_CHAT_LINK = "http://rocketchat.westeurope.cloudapp.azure.com/";

    public static final String ROCKET_CHAT_LINK_0 = "https://rocket.farm-test.rz.uni-potsdam.de";
    public static final String ROCKET_CHAT_LINK = "https://rocket.farm-test.rz.uni-potsdam.de/";

    // or https://rocket.farm.uni-potsdam.de/
    // https://rocket.farm-test.rz.uni-potsdam.de/home

    /**
     * username: fltrailadmin pw: GEbCM1Rso6TUGGMKtGmg6c5EydMQEu61K9zdD10F
     */

    public static final String ROCKET_CHAT_ROOM_LINK = ROCKET_CHAT_LINK + "group/";

    public static final String ROCKET_CHAT_API_LINK = ROCKET_CHAT_LINK + "api/v1/";

   public static final User TEST_USER = new User("student1", "egal",
            "student1@yolo.com", "student1", "",
            "", "6ofqfp8J9ynfvspBJ", false);

    /* public static final User ADMIN_USER = new User("admin nachname", "passwort",
              "email", "rocketChatUsername", "rocketChatAuthToken",
              "rocketChatPersonalAccessToken", "rocketChatUserId", false);*/
    public static final User ADMIN_USER = new User("fltrailadmin", "GEbCM1Rso6TUGGMKtGmg6c5EydMQEu61K9zdD10F",
            "julian.dehne@uni-potsdam.de",  "rocketChatUsername", "rocketChatAuthToken",
            "rocketChatPersonalAccessToken", "SuFbpF3P9aYEo634W", false);



    /**
     * curl -H "Content-type:application/json" \
     *       https://rocket.farm-test.rz.uni-potsdam.de/api/v1/login \
     *       -d '{ "user": "test@stuff.com", "password": "passwort" }'
     *       um
     */
}

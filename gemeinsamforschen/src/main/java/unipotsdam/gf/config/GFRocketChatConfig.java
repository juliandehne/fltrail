package unipotsdam.gf.config;


import unipotsdam.gf.healthchecks.HealthChecks;
import unipotsdam.gf.modules.communication.model.RocketChatUser;
import unipotsdam.gf.modules.user.User;

/**
 * ROCKET CHAT NEEDS TO KNOW THE ENDPOINT FOR THE SSO CONFIGURE LIKE IN README
 *
 * 1. FOR API URL: http://141.89.53.195:8080/gemeinsamforschen/rest/chat/sso
 * localhost:8080/gemeinsamforschen/rest/chat/sso
 *
 * 1. FOR IFRAME URL: http://141.89.53.195:8080/gemeinsamforschen/rest/chat/login
 * localhost:8080/gemeinsamforschen/rest/chat/login
 */
public class GFRocketChatConfig {

    //private static final String ROCKET_CHAT_LINK = "http://rocketchat.westeurope.cloudapp.azure.com/";
    // public static final String ROCKET_CHAT_LINK_0 = "https://rocket.farm-test.rz.uni-potsdam.de";
    // public static final String ROCKET_CHAT_LINK = "https://rocket.farm-test.rz.uni-potsdam.de/";

    //public static final String ROCKET_CHAT_LINK_0 = "http://fleckenroller.cs.uni-potsdam.de:3000";
    //public static final String ROCKET_CHAT_LINK = "http://fleckenroller.cs.uni-potsdam.de:3000/";

    public static final String ROCKET_CHAT_LINK_0 = "http://fleckenroller.cs.uni-potsdam.de/chat";
    public static final String ROCKET_CHAT_LINK = "http://fleckenroller.cs.uni-potsdam.de/chat/";

    /**
     * username: fltrailadmin pw: GEbCM1Rso6TUGGMKtGmg6c5EydMQEu61K9zdD10F
     */

    public static final String ROCKET_CHAT_ROOM_LINK = ROCKET_CHAT_LINK + "group/";

    public static final String ROCKET_CHAT_API_LINK = ROCKET_CHAT_LINK + "api/v1/";

   public static final RocketChatUser TEST_USER = new RocketChatUser("student1", "egal",
            "student1@yolo.com", "student1", "",
            "", "6ofqfp8J9ynfvspBJ", false);

    /* public static final User ADMIN_USER = new User("admin nachname", "passwort",
              "email", "rocketChatUsername", "rocketChatAuthToken",
              "rocketChatPersonalAccessToken", "rocketChatUserId", false);*/
    public static final RocketChatUser ADMIN_USER = new RocketChatUser("fltrailadmin", "GEbCM1Rso6TUGGMKtGmg6c5EydMQEu61K9zdD10F",
            "julian.dehne@uni-potsdam.de",  "rocketChatUsername", "rocketChatAuthToken",
            "c_K41JLX1nqsTnh2hT9iJk8Zo_NhSKPimt685ycJDG4", "wHNs42JEZCsYAqdE9", false);



    /**
     * curl -H "Content-type:application/json" \
     *       https://rocket.farm-test.rz.uni-potsdam.de/api/v1/login \
     *       -d '{ "user": "test@stuff.com", "password": "passwort" }'
     *       um
     */


}

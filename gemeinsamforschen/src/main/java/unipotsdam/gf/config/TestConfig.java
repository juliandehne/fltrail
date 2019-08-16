package unipotsdam.gf.config;

import unipotsdam.gf.modules.communication.model.RocketChatUser;

public class TestConfig extends GeneralConfig {
    //    public static final String COMPBASE_URL= "http://fleckenroller.cs.uni-potsdam.de/app/competence-database-prod";
    //    public static final String COMPBASE_LOCAL = "http://localhost:8080/competence-base";
    //    public static final String COMPBASE_URL = "http://localhost:8081/competence-base";
    public static final String COMPBASE_URL= "https://apiup.uni-potsdam.de/endpoints/competenceAPI";
    public static final String FOLDER_NAME = "userFilesFLTrail/";

    public static final String PASS = "";
    public static final String DB_NAME = "fltrail";
    public static final String USER = "root";


    //private static final String ROCKET_CHAT_LINK = "http://rocketchat.westeurope.cloudapp.azure.com/";
    // public static final String ROCKET_CHAT_LINK_0 = "https://rocket.farm-test.rz.uni-potsdam.de";
    // public static final String ROCKET_CHAT_LINK = "https://rocket.farm-test.rz.uni-potsdam.de/";

    //public static final String ROCKET_CHAT_LINK_0 = "http://fleckenroller.cs.uni-potsdam.de:3000";
    //public static final String ROCKET_CHAT_LINK = "http://fleckenroller.cs.uni-potsdam.de:3000/";

    //public static final String ROCKET_CHAT_LINK_0 = "http://fleckenroller.cs.uni-potsdam.de/chat";
    //public static final String ROCKET_CHAT_LINK = "http://fleckenroller.cs.uni-potsdam.de/chat/";

    // Testeinstellungen
    public static final String ROCKET_CHAT_LINK_0 = "https://fl-testing.soft.cs.uni-potsdam.de";
    public static final String ROCKET_CHAT_LINK = "https://fl-testing.soft.cs.uni-potsdam.de/";


    public static final RocketChatUser
            ADMIN_USER = new RocketChatUser("fltrailadmin", "GEbCM1Rso6TUGGMKtGmg6c5EydMQEu61K9zdD10F",
            "julian.dehne@uni-potsdam.de",  "rocketChatUsername", "rocketChatAuthToken",
            "9lmocCmfZmp0QZjxK3snZ7mAnwFZoIYT4TIS_zcKcoC", "8SvhAuKnkax6rumPn", false);

    /**
     * username: fltrailadmin pw: GEbCM1Rso6TUGGMKtGmg6c5EydMQEu61K9zdD10F
     */

    public static final String ROCKET_CHAT_ROOM_LINK = ROCKET_CHAT_LINK + "group/";

    public static final String ROCKET_CHAT_API_LINK = ROCKET_CHAT_LINK + "api/v1/";

    public static final RocketChatUser TEST_USER = new RocketChatUser("student1", "egal",
            "student1@yolo.com", "student1", "",
            "", "6ofqfp8J9ynfvspBJ", false);


    /**
     * curl -H "Content-type:application/json" \
     *       https://rocket.farm-test.rz.uni-potsdam.de/api/v1/login \
     *       -d '{ "user": "test@stuff.com", "password": "passwort" }'
     *       um
     */

    @Override
    public String ROCKET_CHAT_LINK_0() {
        return ROCKET_CHAT_LINK_0;
    }

    @Override
    public String ROCKET_CHAT_LINK() {
        return ROCKET_CHAT_LINK;
    }

    @Override
    public RocketChatUser ADMIN_USER() {
        return ADMIN_USER;
    }

    @Override
    public String ROCKET_CHAT_ROOM_LINK() {
        return ROCKET_CHAT_ROOM_LINK;
    }

    @Override
    public String ROCKET_CHAT_API_LINK() {
        return ROCKET_CHAT_API_LINK;
    }

    @Override
    public RocketChatUser TEST_USER() {
        return TEST_USER;
    }

    @Override
    public String getCompBaseUrl() {
        return COMPBASE_URL;
    }

    @Override
    public String getLargeFileStoragePath() {
        return FOLDER_NAME;
    }

    @Override
    public String getDBUserName() {
        return USER;
    }

    @Override
    public String getDBPassword() {
        return PASS;
    }

    @Override
    public String getDBName() {
        return DB_NAME;
    }
}

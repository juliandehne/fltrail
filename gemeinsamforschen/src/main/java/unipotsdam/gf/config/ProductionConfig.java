package unipotsdam.gf.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.modules.communication.model.RocketChatUser;

import javax.inject.Singleton;

@Singleton
public class ProductionConfig extends GeneralConfig implements IConfig {

    private final static Logger log = LoggerFactory.getLogger(ProductionConfig.class);


    //    public static final String COMPBASE_URL= "http://fleckenroller.cs.uni-potsdam.de/app/competence-database-prod";
    //    public static final String COMPBASE_LOCAL = "http://localhost:8080/competence-base";
    //public static final String COMPBASE_URL = "http://localhost:8081/competence-base";

    private static final String FOLDER_NAME = "/opt/up/userFilesFLTrail/";


    // produktive Einstellungen
    private static String ROCKET_CHAT_LINK_0 = "https://fl.soft.cs.uni-potsdam.de";
    private static String ROCKET_CHAT_LINK = "https://fl.soft.cs.uni-potsdam.de/";


    private static RocketChatUser ADMIN_USER =
            new RocketChatUser("fltrailadmin", "GEbCM1Rso6TUGGMKtGmg6c5EydMQEu61K9zdD10F",
                    "julian.dehne@uni-potsdam.de", "rocketChatUsername", "rocketChatAuthToken",
                    "chY-tRPI4CN2Z5YZ-W0txHacDzINTjzu0do-9PzbHmy", "Amo7NRAah5JwSYX2y", false);


    public ProductionConfig() {
        //log.info("using production config with " + ROCKET_CHAT_LINK);
    }

    /**
     * curl -H "Content-type:application/json" \
     * https://rocket.farm-test.rz.uni-potsdam.de/api/v1/login \
     * -d '{ "user": "test@stuff.com", "password": "passwort" }'
     * um
     */


    @Override
    public String getLargeFileStoragePath() {
        return FOLDER_NAME;
    }

    @Override
    public String getDBURL() {
        return "jdbc:mysql://localhost";
        //return "jdbc:mysql://fltrail.cs.uni-potsdam.de:3306";
    }

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
}

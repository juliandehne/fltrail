package unipotsdam.gf.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.modules.communication.model.RocketChatUser;
import unipotsdam.gf.modules.communication.service.CommunicationService;

import javax.inject.Singleton;


public class StagingConfig extends GeneralConfig implements IConfig {

    private final static Logger log = LoggerFactory.getLogger(StagingConfig.class);

    private static final String FOLDER_NAME = "/opt/up/userFilesFLTrail/";


    private String ROCKET_CHAT_LINK_0 = "https://fl-staging.soft.cs.uni-potsdam.de";
    private String ROCKET_CHAT_LINK = "https://fl-staging.soft.cs.uni-potsdam.de/";

    private RocketChatUser ADMIN_USER = new RocketChatUser("fltrailadmin", "GEbCM1Rso6TUGGMKtGmg6c5EydMQEu61K9zdD10F",
                                            "julian.dehne@uni-potsdam.de",  "rocketChatUsername", "rocketChatAuthToken",
                                            "lposgbpL9Op6MLM8afwrMJmNAeQ4Ql5GGuc5cktPmRm", "SbgdEXGzvruMYA49Q", false);


    public static final RocketChatUser TEST_USER = new RocketChatUser("student1", "egal",
            "student1@yolo.com", "student1", "",
            "", "6ofqfp8J9ynfvspBJ", false);

    public StagingConfig() {
        //log.debug("using staging config with " + ROCKET_CHAT_LINK);
    }

    @Override
    public String getLargeFileStoragePath() {
        return FOLDER_NAME;
    }

    @Override
    public String getDBURL() {
        return "jdbc:mysql://localhost";
        //return "jdbc:mysql://fleckenroller.cs.uni-potsdam.de:3306";
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

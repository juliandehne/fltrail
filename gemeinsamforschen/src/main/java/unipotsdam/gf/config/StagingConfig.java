package unipotsdam.gf.config;

import unipotsdam.gf.modules.communication.model.RocketChatUser;

public class StagingConfig extends ProductionConfig {

    public StagingConfig() {
        ROCKET_CHAT_LINK_0 = "http://fl-staging.soft.cs.uni-potsdam.de";
        ROCKET_CHAT_LINK = "http://fl-staging.soft.cs.uni-potsdam.de/";
        ADMIN_USER = new RocketChatUser("fltrailadmin", "GEbCM1Rso6TUGGMKtGmg6c5EydMQEu61K9zdD10F",
                "julian.dehne@uni-potsdam.de",  "rocketChatUsername", "rocketChatAuthToken",
                "lposgbpL9Op6MLM8afwrMJmNAeQ4Ql5GGuc5cktPmRm", "SbgdEXGzvruMYA49Q", false);

    }

}

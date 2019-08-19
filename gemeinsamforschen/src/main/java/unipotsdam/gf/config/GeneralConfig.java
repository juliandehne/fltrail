package unipotsdam.gf.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.modules.communication.model.RocketChatUser;

public abstract class GeneralConfig implements IConfig {

    private final static Logger log = LoggerFactory.getLogger(GeneralConfig.class);

    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";


    public final static String SMTP_HOST = "smtp.web.de";
    public final static String SMTP_PORT = "587";
    public final static String SMTP_USERNAME = "julian.dehne";
    public final static String SMTP_PASSWORD = "voyager19";
    public final static String EMAIL_ADRESS = "julian.dehne@web.de";


    private static final String COMPBASE_URL= "https://apiup.uni-potsdam.de/endpoints/competenceAPI";

    private static final String PASS = "voyager";
    private static final String DB_NAME = "fltrail_gf";
    private static final String USER = "root";


    public static String GROUPAl_URL = "http://fleckenroller.cs.uni-potsdam.de:12345/users/preferences/";
    public static String GROUPAl_BASE_URL = "http://fleckenroller.cs.uni-potsdam.de";
    public static String GROUPAl_LOCAL_URL = "http://localhost:12345/users/preferences/";
    public static int GROUPAL_SURVEY_COHORT_SIZE = 30; //should be 30.
    public static  String GROUPFINDING_ITEM_FILE = "groupfindingitems_selected_final1.xls";


    /**
     * username: fltrailadmin pw: GEbCM1Rso6TUGGMKtGmg6c5EydMQEu61K9zdD10F
     */

    //private final String ROCKET_CHAT_ROOM_LINK = ROCKET_CHAT_LINK() + "group/";
    //private final String ROCKET_CHAT_API_LINK = ROCKET_CHAT_LINK() + "api/v1/";

    private static final RocketChatUser TEST_USER = new RocketChatUser("student1", "egal",
            "student1@yolo.com", "student1", "",
            "", "6ofqfp8J9ynfvspBJ", false);


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



    @Override
    public String getCompBaseUrl() {
        return COMPBASE_URL;
    }

    @Override
    public String ROCKET_CHAT_ROOM_LINK() {
        String result = ROCKET_CHAT_LINK() + "group/";
        return result;
    }

    @Override
    public String ROCKET_CHAT_API_LINK() {
        String result = ROCKET_CHAT_LINK() + "api/v1/";
        return result;
    }

    @Override
    public RocketChatUser TEST_USER() {
        return TEST_USER;
    }
}

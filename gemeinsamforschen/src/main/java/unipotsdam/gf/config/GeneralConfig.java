package unipotsdam.gf.config;

import unipotsdam.gf.modules.communication.model.RocketChatUser;

public abstract class GeneralConfig implements IConfig {
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost";

    public final static String SMTP_HOST = "smtp.web.de";
    public final static String SMTP_PORT = "587";
    public final static String SMTP_USERNAME = "julian.dehne";
    public final static String SMTP_PASSWORD = "voyager19";
    public final static String EMAIL_ADRESS = "julian.dehne@web.de";


    public static String GROUPAl_URL = "http://fleckenroller.cs.uni-potsdam.de:12345/users/preferences/";
    public static String GROUPAl_BASE_URL = "http://fleckenroller.cs.uni-potsdam.de";
    public static String GROUPAl_LOCAL_URL = "http://localhost:12345/users/preferences/";
    public static int GROUPAL_SURVEY_COHORT_SIZE = 30; //should be 30.
    public static  String GROUPFINDING_ITEM_FILE = "groupfindingitems_selected_final1.xls";



}

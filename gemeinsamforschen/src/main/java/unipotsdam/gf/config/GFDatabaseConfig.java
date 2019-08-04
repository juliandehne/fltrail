package unipotsdam.gf.config;

/**
 * Created by dehne on 31.05.2018.
 */
public class GFDatabaseConfig {
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost";

    public static final String USER = "root";

    //  Database credentials

    // dev details
    public static final String PASS = "";
    public static final String DB_NAME = "fltrail";

    // prod details
    //public static final String PASS = "voyager";
    //public static final String DB_NAME = "fltrail_gf";
    public static final String TEST_DB_NAME = "fltrail_test";
}

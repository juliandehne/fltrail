package unipotsdam.gf.core.database;

import ch.vorburger.exec.ManagedProcessException;
import unipotsdam.gf.config.GFDatabaseConfig;
import unipotsdam.gf.mysql.MysqlConnectImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlGeneralConnect extends MysqlConnectImpl {

    @Override
    public Connection getConnection() throws ManagedProcessException, SQLException {
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println(ex); //logger?
            }
            return DriverManager.getConnection(createConnectionString());

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            throw new Error("could not connect to mysql");
        }
    }

    private static String createConnectionString() {

        String connString =
                "jdbc:mysql://" + "localhost" + "/" + "mysql" + "?user=" + GFDatabaseConfig.USER + "&password=" +
                        GFDatabaseConfig.PASS;
        return String.format(connString, GFDatabaseConfig.DB_NAME);
    }


}

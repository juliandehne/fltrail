package unipotsdam.gf.core.database;

import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import org.apache.commons.dbutils.DbUtils;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.MysqlConnectImpl;
import unipotsdam.gf.mysql.VereinfachtesResultSet;

import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;


@Singleton
public class InMemoryMySqlConnect extends MysqlConnectImpl {

    public InMemoryMySqlConnect() {

    }



    @Override
    public Connection getConnection() throws SQLException {

        DBConfigurationBuilder config = DBConfigurationBuilder.newBuilder();
        // set port for testing statically
        config.setPort(0);
        try {
            DB db = DB.newEmbeddedDB(config.build());
            db.start();
            db.source("database/fltrail.sql");
        } catch (ManagedProcessException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(config.getURL("fltrail"));

    }

    @Override
    public void connect() {
        //
        if (conn == null) {
            try {
                conn = getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() {
        // do nothing, because close shouldn't close inMemoryDatabase while tests are running, tearDown() replaces close in tests

    }

    @Override
    public VereinfachtesResultSet issueSelectStatement(String statement, Object... args) {
        return null;
    }



    public void tearDown() {
        super.close();
    }
}

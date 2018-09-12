package unipotsdam.gf.core.database;

import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import org.apache.commons.dbutils.DbUtils;
import unipotsdam.gf.core.database.mysql.MysqlConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class InMemoryMySqlConnect extends MysqlConnect {

    public InMemoryMySqlConnect() {
        connect();
    }

    private Connection getDatabaseConnection() throws ManagedProcessException, SQLException {
        DBConfigurationBuilder config = DBConfigurationBuilder.newBuilder();
        config.setPort(0);
        DB db = DB.newEmbeddedDB(config.build());

        db.start();
        db.source("database/fltrail.sql");

        return DriverManager.getConnection(config.getURL("fltrail"));

    }

    @Override
    public void connect() {
        try {
            if (Objects.isNull(conn)) {
                conn = getDatabaseConnection();
            }
        } catch (SQLException | ManagedProcessException e) {
            DbUtils.closeQuietly(conn);
        }
    }

    @Override
    public void close() {
        // do nothing, because close shouldn't close inMemoryDatabase while tests are running, tearDown() replaces close in tests
    }

    public void tearDown() {
        super.close();
    }
}

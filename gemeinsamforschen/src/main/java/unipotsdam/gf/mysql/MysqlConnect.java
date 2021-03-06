package unipotsdam.gf.mysql;

import ch.vorburger.exec.ManagedProcessException;

import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.SQLException;


public interface MysqlConnect {
    void connect();

    void close();

    VereinfachtesResultSet issueSelectStatement(String statement, Object... args);

    int issueInsertStatementWithAutoincrement(String sql, Object... args);

    Integer issueUpdateStatement(String statement, Object... args);

    void issueInsertOrDeleteStatement(String statement, Object... args);

    Connection getConnection() throws ManagedProcessException, SQLException;


}

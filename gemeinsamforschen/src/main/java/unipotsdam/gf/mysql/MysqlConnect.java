package unipotsdam.gf.mysql;

import ch.vorburger.exec.ManagedProcessException;

import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public interface MysqlConnect {
    abstract void connect();

    void close();

    VereinfachtesResultSet issueSelectStatement(String statement, Object... args);

    int issueInsertStatementWithAutoincrement(String sql, Object... args);

    void otherStatements(String statement);

    Integer issueUpdateStatement(String statement, Object... args);

    void issueInsertOrDeleteStatement(String statement, Object... args);

    Connection getConnection() throws ManagedProcessException, SQLException;


}

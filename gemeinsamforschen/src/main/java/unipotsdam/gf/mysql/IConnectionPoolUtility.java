package unipotsdam.gf.mysql;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

public interface IConnectionPoolUtility {
    ComboPooledDataSource constructC3PO() throws PropertyVetoException;

    ComboPooledDataSource getDataSource() throws PropertyVetoException;

    String getConnectionStatus() throws SQLException, PropertyVetoException;
}

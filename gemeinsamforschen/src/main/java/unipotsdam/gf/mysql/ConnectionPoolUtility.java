package unipotsdam.gf.mysql;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import unipotsdam.gf.config.GeneralConfig;
import unipotsdam.gf.config.IConfig;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

public class ConnectionPoolUtility {
    public static synchronized ComboPooledDataSource constructC3PO(IConfig iConfig) throws PropertyVetoException {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass(GeneralConfig.JDBC_DRIVER); //loads the jdbc driver
        cpds.setJdbcUrl( iConfig.getDBURL()+"/"+iConfig.getDBName());
        cpds.setUser(iConfig.getDBUserName());
        cpds.setPassword(iConfig.getDBPassword());

        // the settings below are optional -- c3p0 can work with defaults
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(20);
        return cpds;
    }

    public static String getConnectionStatus(IConfig iConfig) throws SQLException, PropertyVetoException {
        ComboPooledDataSource cpds = constructC3PO(iConfig);
        String result =
                "[num_connections: "      + cpds.getNumConnectionsDefaultUser() +
                        ", num_busy_connections: " + cpds.getNumBusyConnectionsDefaultUser() +
                        ", num_idle_connections: " + cpds.getNumIdleConnectionsDefaultUser()+ "]";
        return result;
    }
}

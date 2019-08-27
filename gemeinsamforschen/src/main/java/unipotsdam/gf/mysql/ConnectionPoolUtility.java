package unipotsdam.gf.mysql;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.config.*;
import unipotsdam.gf.modules.communication.service.CommunicationService;

import javax.inject.Singleton;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

//
//@ApplicationScoped
//@javax.ejb.Singleton
public class ConnectionPoolUtility implements IConnectionPoolUtility {


    private ComboPooledDataSource dataSource;

    //private final static Logger log = LoggerFactory.getLogger(ConnectionPoolUtility.class);

    public ConnectionPoolUtility() {
        //System.err.println("constructor of class" + this);
    }

    @Override
    public ComboPooledDataSource constructC3PO() throws PropertyVetoException {
        IConfig iConfig = getiConfigForStatic();

        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass(GeneralConfig.JDBC_DRIVER); //loads the jdbc driver
        cpds.setJdbcUrl(iConfig.getDBURL() + "/" + iConfig.getDBName());
        cpds.setUser(iConfig.getDBUserName());
        cpds.setPassword(iConfig.getDBPassword());

        // the settings below are optional -- c3p0 can work with defaults
        cpds.setMinPoolSize(20);
        cpds.setAcquireIncrement(10);
        cpds.setMaxPoolSize(150);
        // timeout connections
        cpds.setMaxConnectionAge(3000);
        cpds.setMaxIdleTime(3000);
        cpds.setMaxIdleTimeExcessConnections(300);
        //cpds.setTim
        return cpds;
    }

    public static IConfig getiConfigForStatic() {
        IConfig iConfig;
        if (FLTrailConfig.productionContext) {
            if (FLTrailConfig.staging) {
                iConfig = new StagingConfig();
            } else {
                iConfig = new ProductionConfig();
            }
        } else {
            iConfig = new TestConfig();
        }
        return iConfig;
    }

    @Override
    public synchronized ComboPooledDataSource getDataSource() throws PropertyVetoException {
        if (dataSource == null) {
            //log.info("CREATING C3PO Datasource");
            dataSource = constructC3PO();
            //log.info("FINISHED creating C3PO Datasource");
        }
        return dataSource;
    }

    @Override
    public String getConnectionStatus() throws SQLException, PropertyVetoException {

        String result = "[num_connections: " + getDataSource()
                .getNumConnectionsDefaultUser() + ", num_busy_connections: " + getDataSource()
                .getNumBusyConnectionsDefaultUser() + ", num_idle_connections: " + getDataSource()
                .getNumIdleConnectionsDefaultUser() + "]";
        return result;
    }
}

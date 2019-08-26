package unipotsdam.gf.session;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import unipotsdam.gf.config.GFApplicationBinderFactory;
import unipotsdam.gf.config.IConfig;
import unipotsdam.gf.mysql.ConnectionPoolUtility;
import unipotsdam.gf.mysql.IConnectionPoolUtility;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.PoolingMysqlConnectImpl;

import javax.inject.Inject;
import java.beans.PropertyVetoException;
import java.sql.Connection;

public class LockCronJob implements Job {

    public LockCronJob() {

    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        deleteLockInDB();
    }

    private void deleteLockInDB() {
        IConfig iConfig = ConnectionPoolUtility.getiConfigForStatic();
        IConnectionPoolUtility iConnectionPoolUtility = new ConnectionPoolUtility();
        MysqlConnect mysqlConnect = new PoolingMysqlConnectImpl(iConnectionPoolUtility, iConfig);
        mysqlConnect.connect();

        // build and execute request
        String request = "DELETE FROM tasklock WHERE timeStamp < CURRENT_TIMESTAMP - INTERVAL 5 MINUTE";
        mysqlConnect.issueInsertOrDeleteStatement(request);
        // close connection
        mysqlConnect.close();
        try {
            iConnectionPoolUtility.getDataSource().close();
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }
}

package unipotsdam.gf.session;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import unipotsdam.gf.config.FLTrailConfig;
import unipotsdam.gf.config.IConfig;
import unipotsdam.gf.config.ProductionConfig;
import unipotsdam.gf.config.TestConfig;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.MysqlConnectImpl;

import javax.annotation.ManagedBean;

@ManagedBean
public class LockCronJob implements Job {



    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        deleteLockInDB();
    }

    private void deleteLockInDB() {
        IConfig iConfig = null;
        if (FLTrailConfig.productionContext) {
            iConfig = new ProductionConfig();
        } else {
            iConfig = new TestConfig();
        }
        MysqlConnect connection = new MysqlConnectImpl(iConfig);

        connection.connect();
        // build and execute request
        String request = "DELETE FROM tasklock WHERE timeStamp < CURRENT_TIMESTAMP - INTERVAL 5 MINUTE";
        connection.issueInsertOrDeleteStatement(request);
        // close connection
        connection.close();
    }
}

package unipotsdam.gf.session;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.MysqlConnectImpl;

import javax.annotation.ManagedBean;

@ManagedBean
public class LockCronJob implements Job {

    private MysqlConnect connection = new MysqlConnectImpl();


    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        deleteLockInDB();
    }

    private void deleteLockInDB() {
        connection.connect();
        // build and execute request
        String request = "DELETE FROM tasklock WHERE timeStamp < CURRENT_TIMESTAMP - INTERVAL 5 MINUTE";
        connection.issueInsertOrDeleteStatement(request);
        // close connection
        connection.close();
    }
}

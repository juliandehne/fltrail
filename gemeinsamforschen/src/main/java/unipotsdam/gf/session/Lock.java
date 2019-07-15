package unipotsdam.gf.session;

import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;
import unipotsdam.gf.process.tasks.TaskName;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.Date;


public class Lock {

    @Inject
    MysqlConnect connection;


    /**
     * TODO @Axel this should take a group and not the group id,
     * @param taskName
     * @param groupId
     */
    public void lock(TaskName taskName, Integer groupId) {
        lockTaskInDB(taskName, groupId);
    }

    public boolean checkIfLocked(TaskName taskName, Integer groupId) {
        return isTaskLockedInDB(taskName, groupId);
    }

    private void lockTaskInDB(TaskName taskName, Integer groupId) {
        connection.connect();
        // build and execute request
        String request = "REPLACE INTO tasklock (`taskName`, `groupId`) VALUES (?,?);";
        connection.issueInsertOrDeleteStatement(request, taskName, groupId);
        // close connection
        connection.close();
    }

    private boolean isTaskLockedInDB(TaskName taskName, Integer groupId) {
        boolean result = false;
        connection.connect();
        // build and execute request
        String request = "SELECT * FROM tasklock WHERE taskName= ? AND groupId = ?";
        VereinfachtesResultSet vrs = connection.issueSelectStatement(request, taskName, groupId);
        while (vrs.next()) {
            Timestamp timeStamp = vrs.getTimestamp("timeStamp");
            Date date = new Date(timeStamp.getTime());
            Date now = new Date();
            long delay = now.getTime() - date.getTime();
            result = delay <= 5 * 1000 * 60;    //delay is given in milliseconds. so * 1000 gives seconds, * 60 minutes, *5 5 minutes
        }
        // close connection
        connection.close();
        return result;
    }

    public void deleteLockInDB(TaskName taskName, Integer groupId) {
        connection.connect();
        // build and execute request
        String request = "DELETE FROM tasklock WHERE taskName = ? AND groupId = ?";
        connection.issueInsertOrDeleteStatement(request, taskName, groupId);
        // close connection
        connection.close();
    }
}
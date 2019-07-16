package unipotsdam.gf.session;

import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;
import unipotsdam.gf.process.tasks.TaskName;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Lock {

    @Inject
    MysqlConnect connection;

    /**
     * @param taskName represents the task, that is about to get blocked
     * @param group represents the group, which works on the task already
     */
    public synchronized boolean lock(TaskName taskName, Group group) {
        if (checkIfLocked(taskName, group.getId())) {
            return true;
        } else {
            lockTaskInDB(taskName, group.getId());
            return false;
        }
    }

    private boolean checkIfLocked(TaskName taskName, Integer groupId) {
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

            result = true;
        }
        // close connection
        connection.close();
        return result;
    }

    public void deleteLockInDB(TaskName taskName, Group group) {
        connection.connect();
        // build and execute request
        String request = "DELETE FROM tasklock WHERE taskName = ? AND groupId = ?";
        connection.issueInsertOrDeleteStatement(request, taskName, group.getId());
        // close connection
        connection.close();
    }
}
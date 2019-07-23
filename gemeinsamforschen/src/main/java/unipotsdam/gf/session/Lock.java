package unipotsdam.gf.session;

import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.user.User;
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
    public synchronized boolean lock(TaskName taskName, Group group, User user) {
        if (checkIfLocked(taskName, group.getId(), user.getEmail())) {
            return true;
        } else {
            lockTaskInDB(taskName, group.getId(), user.getEmail());
            return false;
        }
    }

    private boolean checkIfLocked(TaskName taskName, Integer groupId, String userEmail) {
        return isTaskLockedInDB(taskName, groupId, userEmail);
    }

    private void lockTaskInDB(TaskName taskName, Integer groupId, String userEmail) {
        connection.connect();

        // build and execute request
        String request = "REPLACE INTO tasklock (`taskName`, `groupId`, `owner`) VALUES (?,?,?);";
        connection.issueInsertOrDeleteStatement(request, taskName, groupId, userEmail);
        // close connection
        connection.close();
    }

    private boolean isTaskLockedInDB(TaskName taskName, Integer groupId, String userEmail) {
        boolean result = false;
        connection.connect();
        // build and execute request
        String request = "SELECT * FROM tasklock WHERE taskName= ? AND groupId = ? AND owner <> ?";
        VereinfachtesResultSet vrs = connection.issueSelectStatement(request, taskName, groupId, userEmail);
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
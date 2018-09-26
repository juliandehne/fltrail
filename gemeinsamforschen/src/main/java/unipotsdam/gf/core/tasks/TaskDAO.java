package unipotsdam.gf.core.tasks;

import com.mysql.jdbc.NotImplemented;
import unipotsdam.gf.core.database.mysql.MysqlConnect;

import javax.annotation.ManagedBean;
import javax.inject.Inject;

@ManagedBean
public class TaskDAO {

    @Inject
    MysqlConnect connect;

    public Task[] getTasks(String userToken, String projectToken) throws NotImplemented {
        throw new NotImplemented();
    }

    public void persist(Task task) throws NotImplemented {
        connect.connect();
        String query = "INSERT INTO fltrail.tasks (userId, projectId, taskUrl, speakingName, technicalName, " +
                "linkedMode, groupTask, importance, progress, phase, created, due) VALUES ('?', '?', '?', '?', '?', " +
                "'?', ?, '?', '?', '?', '?', '?')";

        connect.issueInsertOrDeleteStatement(query, task.getUserToken());
        connect.close();
        throw new NotImplemented();
    }

    public void update(Task task) throws NotImplemented {
        throw new NotImplemented();
    }
}

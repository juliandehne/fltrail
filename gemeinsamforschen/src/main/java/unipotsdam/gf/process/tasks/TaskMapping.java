package unipotsdam.gf.process.tasks;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class TaskMapping {

    @Inject
    private MysqlConnect connect;

    public List<Integer> groupToFeedback(List<Task> tasks, Task task, int howMany) {
        List<Integer> result = new ArrayList<>();
        int position = tasks.indexOf(task);
        for (int i = 1; i <= howMany; i++) {
            result.add(tasks.get((i + position) % tasks.size()).getGroupTask());  //modulo builds a circle in users
        }
        return result;
    }

    public Integer getWhichGroupToRate(Project project, User user) {
        Integer result;
        List<Integer> groups = new ArrayList<>();
        connect.connect();
        String mysqlRequest1 = "SELECT groupId FROM `groupuser` gu JOIN groups g on " +
                "gu.groupid=g.id AND g.projectName=? WHERE `userEmail`=?";
        VereinfachtesResultSet vereinfachtesResultSet1 =
                connect.issueSelectStatement(mysqlRequest1, project.getName(), user.getEmail());
        vereinfachtesResultSet1.next();
        Integer groupId = vereinfachtesResultSet1.getInt("groupId");

        String mysqlRequest2 = "SELECT DISTINCT id FROM `groups` WHERE `projectName`=? ";
        VereinfachtesResultSet vereinfachtesResultSet2 =
                connect.issueSelectStatement(mysqlRequest2, project.getName());
        boolean next = vereinfachtesResultSet2.next();
        while (next) {
            groups.add(vereinfachtesResultSet2.getInt("id"));
            next = vereinfachtesResultSet2.next();
        }
        if (groups.indexOf(groupId) + 1 == groups.size()) {
            result = groups.get(0);
        } else {
            result = groups.get(groups.indexOf(groupId) + 1);
        }
        connect.close();
        return result;
    }
}

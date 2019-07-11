package unipotsdam.gf.process.tasks;

import unipotsdam.gf.modules.assessment.AssessmentDAO;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class TaskMapper {

    @Inject
    private MysqlConnect connect;

    @Inject
    private AssessmentDAO assessmentDAO;

    /**
     *
     * @param tasks represent all groups in a project that have task "GIVE_FEEDBACK"
     * @param task represents the student / group itself
     * @param howMany how many groups shall get feedbacked by student / group
     * @return list of all groups that shall be feedbacked by student/group
     */
    public List<Integer> groupToFeedback(List<Task> tasks, Task task, int howMany) {
        List<Integer> result = new ArrayList<>();
        int position = tasks.indexOf(task);
        for (int i = 1; i <= howMany; i++) {
            result.add(tasks.get((i + position) % tasks.size()).getGroupTask());  //modulo builds a circle in users
        }
        return result;
    }

    /**
     * WARNING. CALLING THIS METHOD SEQUENTIALLY
     * ASSUMES THAT SQL WILL ALWAYS RETURN THE DATA IN THE SAME ORDER (because of SQL "ORDER BY id")
     * THIS IS NOT DEFINED FOR A STREAM
     * gets all groups in a project, looks for group of user and returns the following groupId. In case
     * the user group is last in this cycle, it will return the first element of the list.
     * @param project of interest
     * @return the id of the group that is rated
     */
    public Integer getWhichGroupToRate(Project project, Integer groupId) {
        Integer result;
        List<Integer> groups = buildGroupIndexes(project);
        // every user gets the next group in the cycle
        if (groups.indexOf(groupId) + 1 == groups.size()) {
            result = groups.get(0);
        } else {
            result = groups.get(groups.indexOf(groupId) + 1);
        }
        return result;
    }

    /**
     * returns the groupId the user is in and a list of groupIds of the project is filled
     * @param project of interest

     * @return groupId of user in project
     */
    private List<Integer> buildGroupIndexes(Project project) {
        connect.connect();
        List<Integer> result = new ArrayList<>();
        String mysqlRequest2 = "SELECT DISTINCT id FROM `groups` WHERE `projectName`=? ORDER BY id ASC";
        VereinfachtesResultSet vereinfachtesResultSet2 =
                connect.issueSelectStatement(mysqlRequest2, project.getName());
        while (vereinfachtesResultSet2.next()) {
            result.add(vereinfachtesResultSet2.getInt("id"));
        }
        connect.close();
        return result;
    }

    public void persistTaskMapping(Project project, User user, Integer groupId, TaskName taskName) {
        Integer groupResult;
        List<Integer> groups = buildGroupIndexes(project);
        // every user gets the next group in the cycle
        if (groups.indexOf(groupId) + 1 == groups.size()) {
            groupResult = groups.get(0);
        } else {
            groupResult = groups.get(groups.indexOf(groupId) + 1);
        }
        Group group = new Group(project.getName());
        group.setId(groupResult);

        assessmentDAO.persistMapping(new TaskMapping(user, group, null, taskName, project));
    }
}

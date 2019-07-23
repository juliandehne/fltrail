package unipotsdam.gf.modules.wizard;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;
import unipotsdam.gf.process.tasks.Progress;
import unipotsdam.gf.process.tasks.TaskName;

import javax.inject.Inject;
import java.util.*;

public class WizardDao {

    @Inject
    ProjectDAO projectDAO;

    @Inject
    private MysqlConnect connect;

    /**
     * SELECT p.name, p.phase, t.taskName FROM projects
     * p join tasks t on p.name = t.projectName
     * where t.progress = "JUSTSTARTED"
     * Group By p.phase ORDER by t.created ASC
     * @return gives a list with all projects and their latest unfinished task
     */
    public List<WizardProject> getProjects() {
        connect.connect();
        String mysqlRequest =
                " SELECT p.name, p.phase, t.taskName FROM projects " +
                " p join tasks t on p.name = t.projectName" +
                        " where NOT t.progress = ? " +
                " Group By p.phase, p.name ORDER by t.created ASC " ;
        List<WizardProject> result = new ArrayList<>();
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, Progress.FINISHED);
        while (vereinfachtesResultSet.next()) {
            String project = vereinfachtesResultSet.getString("name");
            String taskName = vereinfachtesResultSet.getString("taskName");
            String phase = vereinfachtesResultSet.getString("phase");
            result.add(new WizardProject(project, taskName, phase));
        }
        connect.close();
        return result;
    }

    Set<TaskName> getWizardrelevantTaskStatus(Project project) {
        //relevantTasks.add(TaskName.WAITING_FOR_GROUP);
        Set<TaskName> relevantTaskList = getRelevantTaskSet();
        String query = "SELECT t.taskName from tasks t" +
                " WHERE t.progress= ? AND t.projectName = ? " +
                "AND t.taskName NOT IN (SELECT t2.taskName from tasks t2 WHERE t2.progress<>? " +
                "AND t2.projectName=?) group by t.taskName";

        Set<TaskName> result = new HashSet<>();
        connect.connect();
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query,
                Progress.FINISHED.name(), project.getName(), Progress.FINISHED.name(), project.getName());
        while (vereinfachtesResultSet.next()) {
            TaskName taskName = TaskName.valueOf(vereinfachtesResultSet.getString("taskName"));
            if (relevantTaskList.contains(taskName)) {
                result.add(taskName);
            }
        }
        connect.close();

        /*
         * we are currently rarely using INPROGRESS as a state, that is the reson for the workaround
         */
        if (projectDAO.getParticipantCount(project).getParticipants() > 5) {
            result.add(TaskName.WAIT_FOR_PARTICPANTS);
        }

        return result;
    }

    HashMap<TaskName, Progress> getWizardrelevantTaskMap(Project project) {
        HashMap<TaskName, Progress> result = new HashMap<>();
        //relevantTasks.add(TaskName.WAITING_FOR_GROUP);
        Set<TaskName> relevantTaskList = getRelevantTaskSet();

        String query = "SELECT * from tasks t" +
                " where and t.projectName = ? GROUP by (t.taskName)";

        connect.connect();
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(query, project.getName());
        while (vereinfachtesResultSet.next()) {
            TaskName taskName = TaskName.valueOf(vereinfachtesResultSet.getString("taskName"));
            Progress progress = Progress.valueOf(vereinfachtesResultSet.getString("t.progress"));
            if (relevantTaskList.contains(taskName))
                result.put(taskName, progress);
        }
        connect.close();

        return result;
    }

    private Set<TaskName> getRelevantTaskSet() {
        Set<TaskName> relevantTasks = new HashSet<>();
        relevantTasks.add(TaskName.WAIT_FOR_PARTICPANTS);
        relevantTasks.add(TaskName.UPLOAD_DOSSIER);
        relevantTasks.add(TaskName.ANNOTATE_DOSSIER);
        relevantTasks.add(TaskName.GIVE_FEEDBACK);
        relevantTasks.add(TaskName.REEDIT_DOSSIER);
        relevantTasks.add(TaskName.UPLOAD_PRESENTATION);
        relevantTasks.add(TaskName.UPLOAD_FINAL_REPORT);
        relevantTasks.add(TaskName.GIVE_EXTERNAL_ASSESSMENT);
        relevantTasks.add(TaskName.GIVE_INTERNAL_ASSESSMENT);
        relevantTasks.add(TaskName.GIVE_EXTERNAL_ASSESSMENT_TEACHER);

        /*
        List<String> stringList = relevantTasks.stream().map(Enum::name).collect(Collectors.toList());

        MysqlUtil mysqlUtil = new MysqlUtil();
        return mysqlUtil.createConcatenatedString(stringList);
        */
        return relevantTasks;
    }
}

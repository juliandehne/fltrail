package unipotsdam.gf.modules.wizard;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.MysqlUtil;
import unipotsdam.gf.mysql.VereinfachtesResultSet;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.Progress;
import unipotsdam.gf.process.tasks.TaskName;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WizardDao {

    @Inject
    private MysqlConnect connect;

    /**
     * SELECT p.name, p.phase, t.taskName FROM projects
     * p join tasks t on p.name = t.projectName
     * where t.progress = "JUSTSTARTED"
     * Group By p.phase ORDER by t.created ASC
     * @return
     */
    public List<WizardProject> getProjects() {
        connect.connect();
        String mysqlRequest =
                " SELECT p.name, p.phase, t.taskName FROM projects " +
                " p join tasks t on p.name = t.projectName" +
                " where t.progress = ? " +
                " Group By p.phase, p.name ORDER by t.created ASC " ;
        List<WizardProject> result = new ArrayList<>();
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, Progress.JUSTSTARTED);
        while (vereinfachtesResultSet.next()) {
            String project = vereinfachtesResultSet.getString("name");
            String taskName = vereinfachtesResultSet.getString("taskName");
            String phase = vereinfachtesResultSet.getString("phase");
            result.add(new WizardProject(project, taskName, phase));
        }
        connect.close();
        return result;
    }

    public List<TaskName> getWizardrelevantTaskStatus(Project project) {
        ArrayList<TaskName> relevantTasks = new ArrayList<>();
        relevantTasks.add(TaskName.UPLOAD_DOSSIER);
        relevantTasks.add(TaskName.ANNOTATE_DOSSIER);
        relevantTasks.add(TaskName.GIVE_FEEDBACK);
        relevantTasks.add(TaskName.REEDIT_DOSSIER);
        relevantTasks.add(TaskName.UPLOAD_PRESENTATION);
        relevantTasks.add(TaskName.UPLOAD_FINAL_REPORT);
        relevantTasks.add(TaskName.GIVE_EXTERNAL_ASSESSMENT);
        relevantTasks.add(TaskName.GIVE_INTERNAL_ASSESSMENT);
        relevantTasks.add(TaskName.GIVE_EXTERNAL_ASSESSMENT_TEACHER);

        List<String> stringList = relevantTasks.stream().map(Enum::name).collect(Collectors.toList());

        MysqlUtil mysqlUtil = new MysqlUtil();
        String concatenatedString = mysqlUtil.createConcatenatedString(stringList);

        String query = "SELECT * from tasks t" +
                " where t.taskName in ("+concatenatedString+")" +
                " and t.progress = ?" +
                " and t.projectName = ? GROUP by (t.taskName)";

        ArrayList<TaskName> result = new ArrayList<>();
        connect.connect();
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(query, Progress.FINISHED.name(), project.getName());
        while (vereinfachtesResultSet.next())
            relevantTasks.add(TaskName.valueOf(vereinfachtesResultSet.getString("taskName")));
        connect.close();
        return result;
    }
}

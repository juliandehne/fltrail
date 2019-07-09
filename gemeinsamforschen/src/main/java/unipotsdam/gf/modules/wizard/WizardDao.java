package unipotsdam.gf.modules.wizard;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.Progress;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

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
}

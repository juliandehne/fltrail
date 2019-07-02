package unipotsdam.gf.modules.wizard;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;
import unipotsdam.gf.process.phases.Phase;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class WizardDao {

    @Inject
    private MysqlConnect connect;

    public List<WizardProject> getProjects() {
        connect.connect();
        String mysqlRequest =
                "SELECT name, phase, taskName FROM projects p join tasks t on p.name = t.projectName" ;
        List<WizardProject> result = new ArrayList<>();
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest);
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

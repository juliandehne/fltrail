package unipotsdam.gf.modules.project;

import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;
import unipotsdam.gf.modules.states.ProjectPhase;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@Resource
@Singleton
public class ProjectDAO {

    private MysqlConnect connect;

    @Inject
    public ProjectDAO(MysqlConnect connect) {
        this.connect = connect;
    }

    public void persist(Project project) {

        connect.connect();
        String mysqlRequest =
                "INSERT INTO projects (`name`, `password`, `active`, `timecreated`, `author`, " + "`adminPassword`, `phase`) values (?,?,?,?,?,?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, project.getName(), project.getPassword(), project.isActive(),
                project.getTimecreated(), project.getAuthorEmail(), project.getAdminPassword(),
                project.getPhase() == null ? ProjectPhase.CourseCreation : project.getPhase());

        connect.close();

        connect.connect();
        String[] tags = project.getTags();
        for (String tag : tags) {
            connect.issueInsertOrDeleteStatement(
                    "INSERT INTO tags (`projectName`, `tag`) values (?,?)", project.getName(), tag);
        }

        connect.close();


    }

    public void delete(Project project) {
        connect.connect();
        String mysqlRequest = "DELETE FROM projects where name = (?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, project.getName());
        connect.close();
        // TODO: delete all groups of project?

    }

    public Boolean exists(Project project) {
        Boolean result;
        connect.connect();
        String mysqlRequest = "SELECT * FROM projects where name = ? and adminPassword = ?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, project.getName(), project.getAdminPassword());
        result = vereinfachtesResultSet.next();
        connect.close();
        return result;
    }

    public Project getProjectByName(String name) {
        connect.connect();
        String mysqlRequest = "SELECT * FROM projects where name = ?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, name);
        boolean next = vereinfachtesResultSet.next();
        Project result = getProject(vereinfachtesResultSet, next);
        List<String> tags = getTags(result);
        result.setTags(tags.toArray(new String[0]));
        return result;
    }


    private Project getProject(VereinfachtesResultSet vereinfachtesResultSet, boolean next) {
        if (next) {
            Project project = getProjectFromResultSet(vereinfachtesResultSet);
            connect.close();
            return project;
        } else {
            connect.close();
            return null;
        }
    }

    private Project getProjectFromResultSet(VereinfachtesResultSet vereinfachtesResultSet) {
        String id = vereinfachtesResultSet.getString("name");
        String password = vereinfachtesResultSet.getString("password");
        boolean active = vereinfachtesResultSet.getBoolean("active");
        long timestamp = vereinfachtesResultSet.getLong("timecreated");
        String author = vereinfachtesResultSet.getString("author");
        String adminPassword = vereinfachtesResultSet.getString("adminpassword");
        String phase = vereinfachtesResultSet.getString("phase");


        return new Project(id, password, active, timestamp, author, adminPassword, ProjectPhase.valueOf(phase), null);
    }

    public java.util.List<String> getTags(Project project) {
        connect.connect();
        String mysqlRequest = "SELECT t.tag from tags t where t.projectName = ?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, project.getName());

        java.util.List<String> result = new ArrayList<>();

        while (vereinfachtesResultSet.next()) {
            result.add(vereinfachtesResultSet.getString("tag"));
        }
        connect.close();
        return result;
    }
}

package unipotsdam.gf.core.management.project;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.core.states.model.ProjectPhase;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

@ManagedBean
@Resource
@Singleton
public class ProjectDAO {

    private MysqlConnect connect;

    @Inject
    public ProjectDAO(MysqlConnect connect) {
        this.connect = connect;
    }

    public String persist(Project project) {
        UUID uuid = UUID.randomUUID();
        String token = uuid.toString();

        connect.connect();
        String mysqlRequest =
                "INSERT INTO projects (`id`, `password`, `active`, `timecreated`, `authorEmail`, "
                        + "`adminPassword`, `token`, `phase`) values (?,?,?,?,?,?,?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, project.getId(), project.getPassword(), project.isActive(),
                project.getTimecreated(), project.getAuthorEmail(), project.getAdminPassword(), token, project.getPhase()
                        == null ? ProjectPhase.CourseCreation : project.getPhase());
        connect.close();
        return token;
    }

    public void delete(Project project) {
        connect.connect();
        String mysqlRequest = "DELETE FROM projects where id = (?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, project.getId());
        connect.close();
        // TODO: delete all groups of project?

    }

    public Boolean exists(Project project) {
        Boolean result;
        connect.connect();
        String mysqlRequest = "SELECT * FROM projects where id = ? and adminPassword = ?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, project.getId(), project.getAdminPassword());
        result = vereinfachtesResultSet.next();
        connect.close();
        return result;
    }

    public Project getProjectById(String id) {
        connect.connect();
        String mysqlRequest = "SELECT * FROM projects where id = ?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, id);
        boolean next = vereinfachtesResultSet.next();
        return getProject(vereinfachtesResultSet, next);
    }

    public Project getProjectByToken(String token) {
        connect.connect();
        String mysqlRequest = "SELECT * FROM projects where token = ?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, token);
        boolean next = vereinfachtesResultSet.next();
        return getProject(vereinfachtesResultSet, next);
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
        String id = vereinfachtesResultSet.getString("id");
        String password = vereinfachtesResultSet.getString("password");
        boolean active = vereinfachtesResultSet.getBoolean("activ");
        Timestamp timestamp = vereinfachtesResultSet.getTimestamp("timecreated");
        String author = vereinfachtesResultSet.getString("author");
        String adminPassword = vereinfachtesResultSet.getString("adminpassword");
        String token = vereinfachtesResultSet.getString("token");
        String phase = vereinfachtesResultSet.getString("phase");

        return new Project(id, password, active, timestamp, author, adminPassword, token, ProjectPhase.valueOf(phase));
    }

    public java.util.List<String> getTags(Project project) {
        connect.connect();
        String mysqlRequest =
                "SELECT t.tag from tags t where t.projectId = ?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, project.getId());

        java.util.List<String> result = new ArrayList<>();

        while (vereinfachtesResultSet.next()) {
            result.add(vereinfachtesResultSet.getString("tag"));
        }
        connect.close();
        return  result;
    }
}

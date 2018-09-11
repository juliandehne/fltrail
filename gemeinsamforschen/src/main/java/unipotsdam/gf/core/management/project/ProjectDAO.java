package unipotsdam.gf.core.management.project;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.core.states.ProjectPhase;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Timestamp;
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

    public void persist(Project project) {
        UUID uuid = UUID.randomUUID();
        String token = uuid.toString();

        connect.connect();
        String mysqlRequest =
                "INSERT INTO projects (`id`, `password`, `active`, `timecreated`, `author`, "
                        + "`adminPassword`, `token`, `phase`) values (?,?,?,?,?,?,?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, project.getId(), project.getPassword(), project.isActive(),
                project.getTimecreated(), project.getAuthor(), project.getAdminPassword(), token, project.getPhase()
                        == null ? ProjectPhase.CourseCreation : project.getPhase());
        connect.close();
    }

    public void delete(Project project) {
        connect.connect();
        String mysqlRequest = "DELETE FROM projects where id = (?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, project.getId());

        // TODO: delete all groups of project?

        connect.close();
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
        boolean active = vereinfachtesResultSet.getBoolean("active");
        Timestamp timestamp = vereinfachtesResultSet.getTimestamp("timecreated");
        String author = vereinfachtesResultSet.getString("author");
        String adminPassword = vereinfachtesResultSet.getString("adminpassword");
        String token = vereinfachtesResultSet.getString("token");
        String phase = vereinfachtesResultSet.getString("phase");

        return new Project(id, password, active, timestamp, author, adminPassword, token, phase);
    }
}

package unipotsdam.gf.modules.project;

import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.tasks.ParticipantsCount;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;
import unipotsdam.gf.process.phases.Phase;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Array;
import java.security.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import static java.util.TimeZone.getDefault;

@ManagedBean
@Resource
@Singleton
public class ProjectDAO {

    @Inject
    private MysqlConnect connect;

    @Inject
    private GroupDAO groupDAO;

    @Inject
    private IGroupFinding groupFinding;

    @Inject
    public ProjectDAO(MysqlConnect connect) {
        this.connect = connect;
    }

    public void persist(Project project) {

        if (!exists(project)) {
            java.sql.Timestamp timestamp = new java.sql.Timestamp(project.getTimecreated());
            connect.connect();
            String mysqlRequest =
                    "INSERT INTO projects (`name`, `password`, `active`, `timecreated`, `author`, `phase`, `description`) " +
                            "values (?,?,?,?,?,?,?)";
            connect.issueInsertOrDeleteStatement(mysqlRequest, project.getName(), project.getPassword(),
                    project.isActive(), timestamp, project.getAuthorEmail(),
                    project.getPhase() == null ? Phase.GroupFormation : project.getPhase(), project.getDescription());

            connect.close();

            connect.connect();
            String[] tags = project.getTags();
            if (tags.length > 5) {
                tags = Arrays.copyOfRange(tags, 0, 4);
            }
            for (String tag : tags) {
                connect.issueInsertOrDeleteStatement(
                        "INSERT INTO tags (`projectName`, `tag`) values (?,?)", project.getName(), tag);
            }
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
        String mysqlRequest = "SELECT * FROM projects where name = ?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, project.getName());
        if (vereinfachtesResultSet == null) {
            return false;
        }
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
        String phase = vereinfachtesResultSet.getString("phase");
        String description = vereinfachtesResultSet.getString("description");

        return new Project(id, password, active, timestamp, author, Phase.valueOf(phase), null, description);
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

    public ParticipantsCount getParticipantCount(Project project) {
        connect.connect();
        String mysqlRequest = "SELECT COUNT(userEmail) FROM projectuser where projectName = ?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, project.getName());

        int count = 0;

        while (vereinfachtesResultSet.next()) {
            count = vereinfachtesResultSet.getInt(1);
        }

        connect.close();

        return new ParticipantsCount(count);
    }

    public void changeGroupFormationMechanism(
            GroupFormationMechanism groupFormationMechanism, Project project) {
        connect.connect();
        String mysql = "UPDATE groupfindingmechanismselected set gfmSelected = ? where projectName = ? ";
        connect.issueUpdateStatement(mysql, groupFormationMechanism.name(), project.getName());
        connect.close();
    }

    public void setGroupFormationMechanism(GroupFormationMechanism groupFormationMechanism, Project project) {
        connect.connect();
        String mysql = "INSERT INTO groupfindingmechanismselected (`projectName`, `gfmSelected`) values (?,?)";
        connect.issueUpdateStatement(mysql, project.getName(), groupFormationMechanism.name());
        connect.close();
    }

    public List<Project> getProjectsLike(String searchString) {
        ArrayList<Project> projects = new ArrayList<>();
        connect.connect();
        String mysqlRequest = "SELECT * from `projects` where name like ?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, searchString);
        while (vereinfachtesResultSet.next()) {
            Project projectFromResultSet = getProjectFromResultSet(vereinfachtesResultSet);
            projects.add(projectFromResultSet);
        }
        connect.close();
        return projects;
    }

    public List<Project> getAllProjectsExceptStudents(User user) {
        ArrayList<Project> projects = new ArrayList<>();
        ArrayList<String> allProjectsName = new ArrayList<>();
        connect.connect();
        //get all projectNames with the Student in GroupFormation Phase
        String allStudentsRequest = "SELECT name FROM projects p left join projectuser pu on pu.projectName=p.name " +
                "WHERE phase='GroupFormation' and pu.userEmail=?";
        VereinfachtesResultSet resultSetAllStudents = connect.issueSelectStatement(allStudentsRequest, user.getEmail());
        while (resultSetAllStudents.next()) {
            allProjectsName.add(resultSetAllStudents.getString("name"));
        }
        //get all projects in groupFormation Phase
        String mysqlRequest = "SELECT * from projects WHERE phase='GroupFormation'";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest);
        while (vereinfachtesResultSet.next()) {
            //discard all projects the student is participated in
            if (!allProjectsName.contains(vereinfachtesResultSet.getString("name"))){
                Project projectFromResultSet = getProjectFromResultSet(vereinfachtesResultSet);
                List<String> tagsList = getTags(projectFromResultSet);
                String[] tags = new String[tagsList.size()];
                projectFromResultSet.setTags(tagsList.toArray(tags));
                projects.add(projectFromResultSet);
            }
        }
        connect.close();
        return projects;
    }
}

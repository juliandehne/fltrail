package unipotsdam.gf.modules.project;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.group.preferences.survey.GroupWorkContext;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.tasks.ParticipantsCount;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;
import unipotsdam.gf.process.phases.Phase;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ManagedBean
@Resource
@Singleton
public class ProjectDAO {

    @Inject
    private MysqlConnect connect;

    public void persist(Project project) {

        if (!exists(project)) {
            java.sql.Timestamp timestamp = new java.sql.Timestamp(project.getTimecreated());
            if (project.getPassword() == null) {
                project.setPassword("");
            }
            if(project.getAuthorEmail() == null) {
                project.setAuthorEmail("julian.dehne@uni-potsdam.de");
            }
            if (project.getGroupWorkContext() == null) {
                project.setGroupWorkContext(GroupWorkContext.fl);
            }
            connect.connect();
            String mysqlRequest =
                    "INSERT INTO projects (`name`, `password`, `active`, `timecreated`, `author`, `phase`, " +
                            "`description`, `isSurvey`, `context`) values (?,?,?,?,?,?,?,?,?)";
            connect.issueInsertOrDeleteStatement(mysqlRequest, project.getName(), project.getPassword(),
                    project.isActive(), timestamp, project.getAuthorEmail(),
                    project.getPhase() == null ? Phase.GroupFormation : project.getPhase(), project.getDescription(),
                    project.getSurvey(), project.getGroupWorkContext().toString());
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
        String mysql = "INSERT IGNORE INTO groupfindingmechanismselected (`projectName`, `gfmSelected`) values (?,?)";
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
        connect.connect();
        //get all projectNames with the Student in GroupFormation Phase
        String mysqlRequest = "" +
                "SELECT * FROM projects " +
                    "LEFT JOIN " +
                        "(SELECT p.name as studentParticipatesIn FROM projects p " +
                            "LEFT JOIN " +
                            "projectuser pu on p.name = pu.projectName WHERE userEmail=?" +
                        ") as j1 " +
                    "on name=studentParticipatesIn " +
                "WHERE studentParticipatesIn IS NULL AND phase='GroupFormation' " +
                "AND context='FL';";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, user.getEmail());
        while (vereinfachtesResultSet.next()) {
            Project projectFromResultSet = getProjectFromResultSet(vereinfachtesResultSet);
            List<String> tagsList = getTags(projectFromResultSet);
            String[] tags = new String[tagsList.size()];
            projectFromResultSet.setTags(tagsList.toArray(tags));
            projects.add(projectFromResultSet);
        }
        connect.close();
        return projects;
    }

    public List<Project> getSurveyProjects() {
        connect.connect();
        String query = "Select * from projects where issurvey = ?";

        ArrayList<Project> projects = new ArrayList<>();
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query, true);
        while(vereinfachtesResultSet.next()) {
            Project projectFromResultSet = getProjectFromResultSet(vereinfachtesResultSet);
            projects.add(projectFromResultSet);
        }
        connect.close();
        return projects;
    }

    public String getActiveSurveyProject(String projectContext) {
        connect.connect();

        String query = "select * from projects where context = ? and phase = ?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(query, projectContext, Phase.GroupFormation);

        boolean next = vereinfachtesResultSet.next();
        String result = null;
        if (next) {
            result = vereinfachtesResultSet.getString("name");
        }

        connect.close();

        return result;
    }
}

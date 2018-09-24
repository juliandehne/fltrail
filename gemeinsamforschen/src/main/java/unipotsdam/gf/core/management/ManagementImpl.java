package unipotsdam.gf.core.management;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.modules.groupfinding.Group;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.project.ProjectConfiguration;
import unipotsdam.gf.core.management.project.ProjectConfigurationDAO;
import unipotsdam.gf.core.management.project.ProjectDAO;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.user.UserDAO;
import unipotsdam.gf.core.management.user.UserInterests;
import unipotsdam.gf.core.management.user.UserProfile;
import unipotsdam.gf.modules.groupfinding.service.GroupDAO;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.FileInputStream;
import java.sql.Blob;

/**
 * Created by dehne on 31.05.2018.
 */
@ManagedBean
@Resource
@Singleton
public class ManagementImpl implements Management {
        String mysqlRequest =
                "INSERT INTO users (`name`, `password`, `email`, `token`,`isStudent`," + "`rocketChatId`,`rocketChatAuthToken`) values (?,?,?,?,?,?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, user.getName(), user.getPassword(), user.getEmail(), token,
                user.getStudent(), user.getRocketChatId(), user.getRocketChatAuthToken());
    public String create(Project project) {
                "INSERT INTO projects (`id`, `password`, `active`, `timecreated`, `author`, " + "`adminPassword`, `token`, `phase`) values (?,?,?,?,?,?,?,?)";
                project.getTimecreated(), project.getAuthor(), project.getAdminPassword(), token,
                project.getPhase() == null ? ProjectPhase.CourseCreation : project.getPhase());

        String mysql2Request = "INSERT INTO tags (`projectId`, `tag`) values (?,?)";
        String[] tags = project.getTags();
        for (String tag : tags) {
            connect.issueInsertOrDeleteStatement(mysql2Request, project.getId(), tag);
        }
     /*   VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement("Select * from projects a where a.id=?", project.getId());
        while (vereinfachtesResultSet.next()) {
            System.out.println(vereinfachtesResultSet.getString("id"));
        }*/
        return token;

    private UserDAO userDAO;
    private GroupDAO groupDAO;
    private ProjectDAO projectDAO;

    private MysqlConnect connect;

    @Inject
    public ManagementImpl(UserDAO userDAO, GroupDAO groupDAO, ProjectDAO projectDAO, MysqlConnect connect) {
        this.userDAO = userDAO;
        this.groupDAO = groupDAO;
        this.projectDAO = projectDAO;
        this.connect = connect;
    }

    @Override
    public void register(User user, Project project, UserInterests interests) {
        connect.connect();
        String mysqlRequest = "INSERT INTO projectuser (`projectId`, `userId`) values (?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, project.getId(), user.getId());
        connect.close();
    }

    public void delete(User user) {
        userDAO.delete(user);
        String mysqlRequest =
                "UPDATE `users` SET `name`=?,`password`=?,`email`=?,`token`=?,`isStudent`=?," + "`rocketChatId`=?,`rocketChatAuthToken`=? WHERE email=? LIMIT 1";
        connect.issueUpdateStatement(mysqlRequest, user.getName(), user.getPassword(), user.getEmail(), user.getToken(),
                user.getStudent(), user.getRocketChatId(), user.getRocketChatAuthToken(), user.getEmail());
    }

    @Override
    public void create(User user, UserProfile profile) {
        userDAO.persist(user, profile);
    }

    @Override
    public void update(User user) {
        userDAO.update(user);
                "SELECT * FROM users u " + " JOIN projectuser pu ON u.email=pu.userId" + " JOIN projects p ON pu.projectId = p.id" + " WHERE pu.projectId = ?";
        Project project =
                new Project(id, password, active, timestamp, author, adminPassword, token, ProjectPhase.valueOf(phase));
    private void fillGroupFromResultSet(
            VereinfachtesResultSet vereinfachtesResultSet, HashMap<Integer, Group> existingGroups) {
    }

    @Override
    public Boolean exists(User user) {
        return userDAO.exists(user);
    }

    @Override
    public void create(Project project) {
        projectDAO.persist(project);
    }

    @Override
    public User getUserByName(String studentId) {
        return getUserByField("name", studentId);    }

    @Override
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, value);
    }

    @Override
    public Boolean exists(Project project) {
        if (id == null) {
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, id);
    }

    @Override
    public void create(Group group) {
        groupDAO.persist(group);
    }

    @Override
    public void update(Group group) {
        groupDAO.update(group);
    }

    @Override
    public Boolean exists(Group group) {
        return groupDAO.exists(group);
        String mysqlRequest =
                "SELECT * FROM groups g " + "JOIN groupuser gu ON g.id=gu.groupId " + "JOIN users u ON gu.userEmail=u.email" + "where g.projectId = ?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, projectId);
    }

    @Override
    public void create(ProjectConfiguration projectConfiguration, Project project) {
        ProjectConfigurationDAO projectConfigurationDAO = new ProjectConfigurationDAO();
        projectConfigurationDAO.persistProjectConfiguration(projectConfiguration, project);
    }

    @Override
    public ProjectConfiguration getProjectConfiguration(Project project) {
        ProjectConfigurationDAO projectConfigurationDAO = new ProjectConfigurationDAO();
        return projectConfigurationDAO.loadProjectConfiguration(project);
    }

    @Override
    public String getProjectToken(String projectName, String password) {
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        if (password != null && !password.trim().equals("")) {
            String query = "SELECT a.token from projects a where a.password = ? and a.id = ?";
            VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(projectName, password);

            String result = "";
            while (vereinfachtesResultSet.next()) {
                result = vereinfachtesResultSet.getString("token");
            }
            connect.close();
            return result;
        } else {
            String query = "SELECT a.token from projects a where a.id = ?";
            VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(projectName);

            String result = "";
            while (vereinfachtesResultSet.next()) {
                result = vereinfachtesResultSet.getString("token");
            }
            connect.close();
            return result;
        }

    }

    @Override
    public Project getProjectByToken(String projectToken) {
        if (projectToken == null) {
            return null;
        }
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "SELECT * FROM projects where token = ?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, projectToken);
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

    @Override
    public List<String> getProjects(String authorToken) {
        if (authorToken == null) {
            return null;
        }
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest =
                "SELECT p.id FROM users u " +
                        " JOIN projects p ON u.email = p.author" +
                        " WHERE u.token = ?";

        //49c6eeda-62d2-465e-8832-dc2db27e760c

        List<String> result = new ArrayList<>();
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, authorToken);
        while (vereinfachtesResultSet.next()) {
            String project = vereinfachtesResultSet.getString("id");
            result.add(project);
        }
        connect.close();
        return result;
    }

    public String saveProfilePicture(FileInputStream image, String studentId) {
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        Blob blobbedImage = (Blob) image;
        String mysqlRequest = "INSERT INTO `profilepicture`(`studentId`, `image`) VALUES (?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, studentId, blobbedImage);
        connect.close();
        return "success";
    }
}

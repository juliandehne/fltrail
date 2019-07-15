package unipotsdam.gf.modules.project;

import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.modules.user.UserInterests;
import unipotsdam.gf.modules.user.UserProfile;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.modules.group.GroupDAO;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.FileInputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dehne on 31.05.2018.
 */
@ManagedBean
@Resource
@Singleton
public class ManagementImpl implements Management {

    @Inject
    private UserDAO userDAO;

    @Inject
    private GroupDAO groupDAO;

    @Inject
    private ProjectDAO projectDAO;

    @Inject
    private MysqlConnect connect;

    @Inject
    private ProjectConfigurationDAO projectConfigurationDAO;


    @Override
    public void delete(User user) {
        userDAO.delete(user);
    }

    @Override
    public void create(User user) {
        userDAO.persist(user);
    }

    @Override
    public void create(Project project) {
        projectDAO.persist(project);
    }

    @Override
    public void create(Project project, Integer groupSize){
        projectDAO.persist(project, groupSize);
    }

    @Override
    public void delete(Project project) {
       projectDAO.delete(project);
    }

    @Override
    public void register(User user, Project project, UserInterests interests) {
        connect.connect();
        String mysqlRequest = "INSERT INTO projectuser (`projectName`, `userEmail`) values (?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, project.getName(), user.getEmail());
        connect.close();
    }

    @Override
    public void update(User user) {
        userDAO.updateRocketChatUserName(user);
    }

    @Override
    public void update(Group group) {
        groupDAO.update(group);
    }

    @Override
    public Boolean exists(User user) {
        return userDAO.exists(user);
    }

    @Override
    public Boolean exists(Project project) {
        return projectDAO.exists(project);
    }

    @Override
    public Boolean exists(Group group) {
        return groupDAO.exists(group);
    }

    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    @Override
    public void create(Group group) {
        groupDAO.persist(group);
    }


    @Override
    public void create(ProjectConfiguration projectConfiguration, Project project) {

        projectConfigurationDAO.persistProjectConfiguration(projectConfiguration, project);
    }

    @Override
    public ProjectConfiguration getProjectConfiguration(Project project) {
        return projectConfigurationDAO.loadProjectConfiguration(project);
    }

       @Override
    public List<Project> getProjects(String authorEmail) {
        if (authorEmail == null) {
            return null;
        }
        connect.connect();
        String mysqlRequest =
                "SELECT * FROM projects where author = ?";

        //49c6eeda-62d2-465e-8832-dc2db27e760c

        List<Project> result = new ArrayList<>();
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, authorEmail);
        while (vereinfachtesResultSet.next()) {
            Project project = null;
            try {
                project = projectDAO.getProjectByName(vereinfachtesResultSet.getString("name"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            result.add(project);
        }
        connect.close();
        return result;
    }

    @Override
    public List<Project> getProjectsStudent(User user) {
        if (user == null) {
            return null;
        }
        connect.connect();
        String mysqlRequest =
                "SELECT projectName FROM projectuser WHERE userEmail=?";
        List<Project> result = new ArrayList<>();
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, user.getEmail());
        while (vereinfachtesResultSet.next()) {
            String project = vereinfachtesResultSet.getString("projectName");
            try {
                result.add(projectDAO.getProjectByName(project));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        connect.close();
        return result;
    }

    @Override
    public List<Project> getAllProjects() {
        connect.connect();
        String mysqlRequest =
                "SELECT name FROM projects WHERE phase=?";
        List<Project> result = new ArrayList<>();
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, Phase.GroupFormation);
        while (vereinfachtesResultSet.next()) {
            String project = vereinfachtesResultSet.getString("name");
            try {
                result.add(projectDAO.getProjectByName(project));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        connect.close();
        return result;
    }

    public String saveProfilePicture(FileInputStream image, String userName) {
        connect.connect();
        Blob blobbedImage = (Blob) image;
        String mysqlRequest = "INSERT INTO `profilepicture`(`userName`, `image`) VALUES (?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, userName, blobbedImage);
        connect.close();
        return "success";    }

    @Override
    public List<String> getTags(Project project) {
        return projectDAO.getTags(project);
    }

    @Override
    public Project getProjectByName(String projectName) {
        try {
            return projectDAO.getProjectByName(projectName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}

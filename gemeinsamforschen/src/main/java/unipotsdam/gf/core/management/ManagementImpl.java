package unipotsdam.gf.core.management;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.core.management.group.Group;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.project.ProjectConfiguration;
import unipotsdam.gf.core.management.project.ProjectConfigurationDAO;
import unipotsdam.gf.core.management.project.ProjectDAO;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.user.UserDAO;
import unipotsdam.gf.core.management.user.UserInterests;
import unipotsdam.gf.core.management.user.UserProfile;
import unipotsdam.gf.core.management.util.ResultSetUtil;
import unipotsdam.gf.core.states.model.ProjectPhase;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.groupfinding.service.GroupDAO;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by dehne on 31.05.2018.
 */
@ManagedBean
@Resource
@Singleton
public class ManagementImpl implements Management {

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
    public void delete(User user) {
        userDAO.delete(user);
    }

    @Override
    public void create(User user, UserProfile profile) {
        userDAO.persist(user, profile);
    }

    @Override
    public String create(Project project) {
        return projectDAO.persist(project);
    }

    @Override
    public void delete(Project project) {
       projectDAO.delete(project);
    }

    @Override
    public void register(User user, Project project, UserInterests interests) {
        connect.connect();
        String mysqlRequest = "INSERT INTO projectuser (`projectId`, `userId`) values (?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, project.getId(), user.getId());
        connect.close();
    }

    @Override
    public void update(User user) {
        userDAO.update(user);
    }

    @Override
    public void update(Group group) {

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
        return null;
    }

    @Override
    public User getUserByName(String studentId) {
        return null;
    }


    public List<User> getUsers(Project project) {
        return userDAO.getUsersByProjectId(project.getId());
    }

    private User getUserFromResultSet(VereinfachtesResultSet vereinfachtesResultSet) {
        return ResultSetUtil.getUserFromResultSet(vereinfachtesResultSet);
    }

    private void fillGroupFromResultSet(
            VereinfachtesResultSet vereinfachtesResultSet, HashMap<Integer, Group> existingGroups) {
        int id = vereinfachtesResultSet.getInt("id");
        if (existingGroups.containsKey(id)) {
            existingGroups.get(id).addMember(getUserFromResultSet(vereinfachtesResultSet));
        } else {
            String projectId = vereinfachtesResultSet.getString("projectId");
            User user = getUserFromResultSet(vereinfachtesResultSet);
            String chatRoomId = vereinfachtesResultSet.getString("chatRoomId");
            ArrayList<User> userList = new ArrayList<>(Collections.singletonList(user));
            Group group = new Group(userList, projectId, chatRoomId);
            existingGroups.put(id, group);
        }
    }


    public String getUserToken(User user) {
        return userDAO.getUserToken(user);
    }


    public User getUserByToken(String token) {
        return userDAO.getUserByToken(token);
    }


    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    public Project getProjectById(String id) {
        return projectDAO.getProjectById(id);
    }

    @Override
    public void create(Group group) {

        connect.connect();

        String mysqlRequestGroup = "INSERT INTO groups (`projectId`,`chatRoomId`) values (?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequestGroup, group.getProjectId(), group.getChatRoomId());

        for (User groupMember : group.getMembers()) {
            String mysqlRequest2 = "INSERT INTO groupuser (`userEmail`, `groupId`) values (?,?)";
            connect.issueInsertOrDeleteStatement(mysqlRequest2, groupMember.getEmail(), group.getProjectId());
        }
        connect.close();
    }


    public List<Group> getGroupsByProjectId(String projectId) {

        connect.connect();
        String mysqlRequest =
                "SELECT * FROM groups g " + "JOIN groupuser gu ON g.id=gu.groupId " + "JOIN users u ON gu.userEmail=u.email" + "where g.projectId = ?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, projectId);
        HashMap<Integer, Group> groupHashMap = new HashMap<>();
        while (vereinfachtesResultSet.next()) {
            fillGroupFromResultSet(vereinfachtesResultSet, groupHashMap);
        }
        ArrayList<Group> groups = new ArrayList<>();
        groupHashMap.forEach((key, group) -> groups.add(group));
        if (groups.isEmpty()) {
            return null;
        }

        return groups;
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
        return projectDAO.getProjectByToken(projectToken);
    }

    @Override
    public List<String> getProjects(String authorToken) {
        if (authorToken == null) {
            return null;
        }
        connect.connect();
        String mysqlRequest =
                "SELECT p.id FROM users u " +
                        " JOIN projects p ON u.email = p.authorEmail" +
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

    @Override
    public List<String> getProjectsStudent(String studentToken) {
        if (studentToken == null) {
            return null;
        }
        connect.connect();
        String mysqlRequest =
                "SELECT projectId FROM projectuser WHERE userId=?";

        //49c6eeda-62d2-465e-8832-dc2db27e760c

        List<String> result = new ArrayList<>();
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, studentToken);
        while (vereinfachtesResultSet.next()) {
            String project = vereinfachtesResultSet.getString("projectId");
            result.add(project);
        }
        connect.close();
        return result;
    }

    public String saveProfilePicture(FileInputStream image, String studentId) {
        connect.connect();
        Blob blobbedImage = (Blob) image;
        String mysqlRequest = "INSERT INTO `profilepicture`(`studentId`, `image`) VALUES (?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, studentId, blobbedImage);
        connect.close();
        return "success";    }

    @Override
    public List<String> getTags(Project project) {
        return projectDAO.getTags(project);
    }
}

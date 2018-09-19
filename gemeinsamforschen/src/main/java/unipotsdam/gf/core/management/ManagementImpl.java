package unipotsdam.gf.core.management;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.modules.groupfinding.Group;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.project.ProjectConfiguration;
import unipotsdam.gf.core.management.project.ProjectConfigurationDAO;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.user.UserInterests;
import unipotsdam.gf.core.management.user.UserProfile;
import unipotsdam.gf.core.states.model.ProjectPhase;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
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
    @Override
    public void delete(StudentIdentifier identifier) {
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "DELETE FROM users where email = (?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, identifier.getStudentId());
        connect.close();
    }

    @Override
    public void create(User user, UserProfile profile) {
        UUID uuid = UUID.randomUUID();
        String token = uuid.toString();

        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest =
                "INSERT INTO users (`name`, `password`, `email`, `token`,`isStudent`," + "`rocketChatId`,`rocketChatAuthToken`) values (?,?,?,?,?,?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, user.getName(), user.getPassword(), user.getEmail(), token,
                user.getStudent(), user.getRocketChatId(), user.getRocketChatAuthToken());
        connect.close();

        // TODO implmement UserProfile @Mar
    }

    @Override
    public String create(Project project) {
        UUID uuid = UUID.randomUUID();
        String token = uuid.toString();


        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest =
                "INSERT INTO projects (`id`, `password`, `active`, `timecreated`, `author`, " + "`adminPassword`, `token`, `phase`) values (?,?,?,?,?,?,?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, project.getId(), project.getPassword(), project.isActive(),
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
        connect.close();
        return token;
    }

    @Override
    public void delete(Project project) {
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "DELETE FROM projects where id = (?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, project.getId());

        // TODO: delete all groups of project?

        connect.close();
    }

    @Override
    public void register(User user, Project project, UserInterests interests) {
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "INSERT INTO projectuser (`projectId`, `userId`) values (?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, project.getId(), user.getId());
        connect.close();
    }

    @Override
    public void update(User user) {
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest =
                "UPDATE `users` SET `name`=?,`password`=?,`email`=?,`token`=?,`isStudent`=?," + "`rocketChatId`=?,`rocketChatAuthToken`=? WHERE email=? LIMIT 1";
        //TODO: maybe add handling if a line is actually updated
        //TODO: if user is updated, it also must update all other tables which includes some information about the user, for example project user

        connect.issueUpdateStatement(mysqlRequest, user.getName(), user.getPassword(), user.getEmail(), user.getToken(),
                user.getStudent(), user.getRocketChatId(), user.getRocketChatAuthToken(), user.getEmail());
        connect.close();
    }

    @Override
    public Boolean exists(User user) {
        Boolean result;
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "SELECT * FROM users where email = ? and password = ?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, user.getEmail(), user.getPassword());
        result = vereinfachtesResultSet.next();
        connect.close();
        return result;
    }

    @Override
    public Boolean exists(Project project) {
        Boolean result;
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "SELECT * FROM projects where id = ? and adminPassword = ?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, project.getId(), project.getAdminPassword());
        result = vereinfachtesResultSet.next();
        connect.close();
        return result;
    }

    @Override
    public List<User> getUsers(Project project) {
        String query =
                "SELECT * FROM users u " + " JOIN projectuser pu ON u.email=pu.userId" + " JOIN projects p ON pu.projectId = p.id" + " WHERE pu.projectId = ?";

        ArrayList<User> result = new ArrayList<>();
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query, project.getId());
        while (!vereinfachtesResultSet.isLast()) {
            Boolean next = vereinfachtesResultSet.next();
            if (next) {
                User user = getUserFromResultSet(vereinfachtesResultSet);
                String token = vereinfachtesResultSet.getString("token");
                user.setToken(token);
                result.add(user);
            } else {
                break;
            }
        }
        connect.close();
        return result;
    }

    private User getUserFromResultSet(VereinfachtesResultSet vereinfachtesResultSet) {
        String name = vereinfachtesResultSet.getString("name");
        String password = vereinfachtesResultSet.getString("password");
        String email = vereinfachtesResultSet.getString("email");
        String token = vereinfachtesResultSet.getString("token");
        String rocketChatId = vereinfachtesResultSet.getString("rocketChatId");
        String rocketChatAuthToken = vereinfachtesResultSet.getString("rocketChatAuthToken");
        Boolean isStudent = vereinfachtesResultSet.getBoolean("isStudent");
        return new User(name, password, email, token, rocketChatId, rocketChatAuthToken, isStudent);
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
        Project project =
                new Project(id, password, active, timestamp, author, adminPassword, token, ProjectPhase.valueOf(phase));
        ProjectPhase projectPhase = ProjectPhase.valueOf(phase);
        project.setPhase(projectPhase);
        return project;
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

    @Override
    public String getUserToken(User user) {
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "SELECT * FROM users where email = ? and password = ?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, user.getEmail(), user.getPassword());
        boolean next = vereinfachtesResultSet.next();
        if (!next) {
            connect.close();
            return null;
        }
        String token = vereinfachtesResultSet.getString("token");
        connect.close();
        return token;
    }

    @Override
    public User getUserByToken(String token) {
        return getUserByField("token", token);
    }

    @Override
    public User getUserByEmail(String email) {
        return getUserByField("email", email);
    }


    /**
     * @param value
     * @return
     */
    private User getUserByField(String field, String value) {
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "SELECT * FROM users where " + field + " = ?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, value);
        boolean next = vereinfachtesResultSet.next();
        if (next) {
            User user = getUserFromResultSet(vereinfachtesResultSet);
            connect.close();
            return user;
        } else {
            connect.close();
            return null;
        }
    }

    @Override
    public Project getProjectById(String id) {
        if (id == null) {
            return null;
        }
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "SELECT * FROM projects where id = ?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, id);
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
    public void create(Group group) {
        MysqlConnect connect = new MysqlConnect();
        connect.connect();

        String mysqlRequestGroup = "INSERT INTO groups (`projectId`,`chatRoomId`) values (?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequestGroup, group.getProjectId(), group.getChatRoomId());

        for (User groupMember : group.getMembers()) {
            String mysqlRequest2 = "INSERT INTO groupuser (`userEmail`, `groupId`) values (?,?)";
            connect.issueInsertOrDeleteStatement(mysqlRequest2, groupMember.getEmail(), group.getProjectId());
        }
        connect.close();
    }

    @Override
    public void addGroupMember(User groupMember, int groupId) {
        // TODO: implement
    }

    @Override
    public void deleteGroupMember(User groupMember, int groupId) {
        // TODO: implement
    }

    @Override
    public List<Group> getGroupsByProjectId(String projectId) {
        MysqlConnect connect = new MysqlConnect();
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

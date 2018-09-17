package unipotsdam.gf.core.management;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.core.management.group.Group;
import unipotsdam.gf.core.management.group.GroupDAO;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.project.ProjectConfiguration;
import unipotsdam.gf.core.management.project.ProjectConfigurationDAO;
import unipotsdam.gf.core.management.project.ProjectDAO;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.user.UserDAO;
import unipotsdam.gf.core.management.user.UserInterests;
import unipotsdam.gf.core.management.user.UserProfile;
import unipotsdam.gf.modules.assessment.controller.model.Quiz;
import unipotsdam.gf.core.states.model.ProjectPhase;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.ArrayList;

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
    public void register(User user, Project project, UserInterests interests) {
        connect.connect();
        String mysqlRequest = "INSERT INTO projectuser (`projectId`, `userId`) values (?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, project.getId(), user.getId());
        connect.close();
    }

    @Override
    public void update(User user) {
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "UPDATE `users` SET `name`=?,`password`=?,`email`=?,`token`=?,`isStudent`=?," +
                "`rocketChatId`=?,`rocketChatAuthToken`=? WHERE email=? LIMIT 1";
        //TODO: maybe add handling if a line is actually updated
        //TODO: if user is updated, it also must update all other tables which includes some information about the user, for example project user

        connect.issueUpdateStatement(mysqlRequest, user.getName(), user.getPassword(), user.getEmail(),
                user.getToken(), user.getStudent(), user.getRocketChatId(), user.getRocketChatAuthToken(), user.getEmail());
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
                "SELECT * FROM users u "
                        + " JOIN projectuser pu ON u.email=pu.userId"
                        + " JOIN projects p ON pu.projectId = p.id"
                        + " WHERE pu.projectId = ?";

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
        Project project = new Project(id, password, active, timestamp, author, adminPassword, token, ProjectPhase.valueOf(phase));
        ProjectPhase projectPhase = ProjectPhase.valueOf(phase);
        project.setPhase(projectPhase);
        return project;
    }

    private void fillGroupFromResultSet(VereinfachtesResultSet vereinfachtesResultSet, HashMap<Integer, Group> existingGroups) {
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
     *
     *
     * @param value
     * @return
     */
    @Override
    public Quiz getQuizByProjectGroupId(String projectId, String quizId) {
        connect.connect();
        String mysqlRequest = "SELECT * FROM quiz where projectId=" + projectId + " , question=" + quizId;
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, "");
        boolean next = vereinfachtesResultSet.next();
        String question = "";
        ArrayList<String> correctAnswers = new ArrayList<String>();
        ArrayList<String> incorrectAnswers = new ArrayList<String>();
        String answer = "";
        Boolean correct = false;
        String mcType = "";
        while (next) {
            mcType = vereinfachtesResultSet.getString("mcType");
            question = vereinfachtesResultSet.getString("question");
            answer = vereinfachtesResultSet.getString("answer");
            correct = vereinfachtesResultSet.getBoolean("correct");
            if (correct) {
                correctAnswers.add(answer);
            } else {
                incorrectAnswers.add(answer);
            }
            next = vereinfachtesResultSet.next();
        }
        Quiz quiz = new Quiz(mcType, question, correctAnswers, incorrectAnswers);
        connect.close();
        return quiz;
    }

    private User getUserByField(String field, String value) {
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "SELECT * FROM users where " + field + " = ?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, value);
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
        if (id == null){
            return null;
        }
        MysqlConnect connect = new MysqlConnect();
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
    public void delete(User user) {
        userDAO.delete(user);
    }

    @Override
    public void create(User user, UserProfile profile) {
        userDAO.persist(user, profile);
    }

    @Override
    public void update(User user) {
        userDAO.update(user);
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
    public void delete(Project project) {
        projectDAO.delete(project);
    }

    @Override
    public Boolean exists(Project project) {
        return projectDAO.exists(project);
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

    public String saveProfilePicture(FileInputStream image, String studentId){
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        Blob blobbedImage= (Blob) image;
        String mysqlRequest = "INSERT INTO `profilepicture`(`studentId`, `image`) VALUES (?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, studentId, blobbedImage);
        return "success";
    }
}

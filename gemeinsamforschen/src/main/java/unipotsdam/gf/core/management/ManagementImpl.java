package unipotsdam.gf.core.management;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.core.management.group.Group;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.user.UserInterests;
import unipotsdam.gf.core.management.user.UserProfile;
import unipotsdam.gf.modules.assessment.controller.model.Quiz;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Singleton;
import java.sql.Timestamp;
import java.util.ArrayList;
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
        String mysqlRequest = "INSERT INTO users (`name`, `password`, `email`, `token`,`isStudent`," +
                "`rocketChatId`,`rocketChatAuthToken`) values (?,?,?,?,?,?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, user.getName(), user.getPassword(), user.getEmail(),
                token, user.getStudent(), user.getRocketChatId(), user.getRocketChatAuthToken());
        connect.close();

        // TODO implmement UserProfile @Mar
    }

    // TODO: naming convention discussion? all is named create, but group is named createGroup
    @Override
    public void create(Project project) {
        UUID uuid = UUID.randomUUID();
        String token = uuid.toString();

        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest =
                "INSERT INTO projects (`id`, `password`, `active`, `timecreated`, `author`, "
                        + "`adminPassword`, `token`) values (?,?,?,?,?,?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, project.getId(), project.getPassword(), project.isActive(),
                project.getTimecreated(), project.getAuthor(), project.getAdminPassword(), token);
        connect.close();
    }

    @Override
    public void delete(Project project) {
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "DELETE FROM projects where id = (?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, project.getId());
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
        String mysqlRequest = "UPDATE `users` SET `name`=?,`password`=?,`email`=?,`token`=?,`isStudent`=?, `rocketChatId`=?,`rocketChatAuthToken`=? WHERE email=? LIMIT 1";
        //TODO: maybe add handling if a line is actually updated
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
            vereinfachtesResultSet.next();
            User user = getUserFromResultSet(vereinfachtesResultSet);
            String token = vereinfachtesResultSet.getString("token");
            user.setToken(token);
            result.add(user);
        }
        connect.close();
        return result;
    }

    private User getUserFromResultSet(VereinfachtesResultSet vereinfachtesResultSet) {
        String name = vereinfachtesResultSet.getString("name");
        String password = vereinfachtesResultSet.getString("password");
        String email = vereinfachtesResultSet.getString("email");
        String rocketChatId = vereinfachtesResultSet.getString("rocketChatId");
        String rocketChatAuthToken = vereinfachtesResultSet.getString("rocketChatAuthToken");
        Boolean isStudent = vereinfachtesResultSet.getBoolean("isStudent");
        return new User(name, password, email, rocketChatId, rocketChatAuthToken, isStudent);
    }

    private Project getProjectFromResultSet(VereinfachtesResultSet vereinfachtesResultSet) {
        String id = vereinfachtesResultSet.getString("id");
        String password = vereinfachtesResultSet.getString("password");
        boolean active = vereinfachtesResultSet.getBoolean("active");
        Timestamp timestamp = vereinfachtesResultSet.getTimestamp("timecreated");
        String author = vereinfachtesResultSet.getString("author");
        String adminPassword = vereinfachtesResultSet.getString("adminpassword");
        String token = vereinfachtesResultSet.getString("token");

        return new Project(id, password, active, timestamp, author, adminPassword, token);
    }

    private Group getGroupFromResultSet(VereinfachtesResultSet vereinfachtesResultSet) {
        int id = vereinfachtesResultSet.getInt("id");
        String projectId = vereinfachtesResultSet.getString("projectId");
        String chatRoomId = vereinfachtesResultSet.getString("chatRoomId");
        // TODO: determine how to get all User
        return new Group(id, new ArrayList<>(), projectId, chatRoomId);
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

    public Quiz getQuizByProjectGroupId(String projectId, String quizId){
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "SELECT * FROM quiz where projectId=" + projectId + " , question="+quizId;
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
            if (correct){
                correctAnswers.add(answer);
            }else{
                incorrectAnswers.add(answer);
            }
            next = vereinfachtesResultSet.next();
        }
        Quiz quiz = new Quiz(mcType,question, correctAnswers, incorrectAnswers);
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
    }


    @Override
    public void createGroup(Group group, String projectId) {
        MysqlConnect connect = new MysqlConnect();
        connect.connect();

        Project project = getProjectById(projectId);

        for (User groupMember : group.getMembers()) {
            String mysqlRequest2 = "INSERT INTO groupuser (`userEmail`, `groupId`) values (?,?)";
            connect.issueInsertOrDeleteStatement(mysqlRequest2, groupMember.getEmail(), project.getId());
        }
        connect.close();
    }

    @Override
    public void addGroupMember(User groupMember, int groupId) {

    }

    @Override
    public void deleteGroupMember(User groupMember, int groupId) {

    }

    @Override
    public List<Group> getGroupsByProjectId(String projectId) {
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        // TODO: implement correct join and finish implementation
        String mysqlRequest = "SELECT * FROM groups g " +
                "JOIN groupuser gu u ON g.id=gu.groupId " + "JOIN users u ON gu.userEmail=u.email" +
                "where g.projectId = ?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, projectId);
        ArrayList<Group> groups = new ArrayList<>();
        while (vereinfachtesResultSet.next()) {
            //groups.add()
        }
        if (groups.isEmpty()) {
            return null;
        } else {
            return groups;
        }
    }
}

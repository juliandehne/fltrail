package unipotsdam.gf.core.management;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.core.management.group.Group;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.user.UserInterests;
import unipotsdam.gf.core.management.user.UserProfile;
import unipotsdam.gf.modules.assessment.controller.StudentIdentifier;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Singleton;
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

    @Override
    public void create(Project project) {
        UUID uuid = UUID.randomUUID();
        String token = uuid.toString();

        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest =
                "INSERT INTO projects (`id`, `password`, `activ`, `timecreated`, `author`, "
                        + "`adminpassword`, `token`) values (?,?,?,?,?,?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, project.getId(), project.getPassword(), project.getActiv(),
                project.getTimecreated(), project.getAuthor(), project.getAdminpassword(), token);
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
        if (result == null) {
            return false;
        }
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
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "SELECT * FROM users where token = ?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, token);
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
    public void createGroup(List<User> groupMembers, String projectId) {

        for (User groupMember : groupMembers) {
            UUID uuid = UUID.randomUUID();
            String token = uuid.toString();

            MysqlConnect connect = new MysqlConnect();
            connect.connect();
            String mysqlRequest =
                    "INSERT INTO projects (`projectId`) values (?)";
            VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, projectId);
            vereinfachtesResultSet.next();
            int id = vereinfachtesResultSet.getInt("id");

            String mysqlRequest2 =
                    "INSERT INTO groupuser (`userEmail`, `groupId`) values (?,?)";
            connect.issueInsertOrDeleteStatement(mysqlRequest2, groupMember.getEmail(), id);
            connect.close();
        }


    }

    @Override
    public void addGroupMember(User groupMember, int groupId) {

    }

    @Override
    public void deleteGroupMember(User groupMember, int groupId) {

    }

    @Override
    public List<Group> getGroups(String projectId) {
        return null;
    }
}

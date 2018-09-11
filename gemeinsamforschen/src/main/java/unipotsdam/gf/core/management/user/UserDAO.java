package unipotsdam.gf.core.management.user;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.util.ResultSetUtil;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ManagedBean
@Resource
@Singleton
public class UserDAO {


    private MysqlConnect connect;

    @Inject
    public UserDAO(MysqlConnect connect) {
        this.connect = connect;
    }

    public void persist(User user, UserProfile profile) {
        UUID uuid = UUID.randomUUID();
        String token = uuid.toString();

        connect.connect();
        String mysqlRequest = "INSERT INTO users (`name`, `password`, `email`, `token`,`isStudent`," +
                "`rocketChatId`,`rocketChatAuthToken`) values (?,?,?,?,?,?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, user.getName(), user.getPassword(), user.getEmail(),
                token, user.getStudent(), user.getRocketChatId(), user.getRocketChatAuthToken());
        connect.close();

        // TODO implmement UserProfile @Mar
    }

    public void delete(User user) {
        connect.connect();
        String mysqlRequest = "DELETE FROM users where email = (?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, user.getEmail());
        connect.close();
    }

    public void update(User user) {
        connect.connect();
        String mysqlRequest = "UPDATE `users` SET `name`=?,`password`=?,`email`=?,`token`=?,`isStudent`=?," +
                "`rocketChatId`=?,`rocketChatAuthToken`=? WHERE email=? LIMIT 1";
        //TODO: maybe add handling if a line is actually updated
        //TODO: if user is updated, it also must update all other tables which includes some information about the user, for example project user

        connect.issueUpdateStatement(mysqlRequest, user.getName(), user.getPassword(), user.getEmail(),
                user.getToken(), user.getStudent(), user.getRocketChatId(), user.getRocketChatAuthToken(), user.getEmail());
        connect.close();
    }

    public boolean exists(User user) {
        boolean result;
        connect.connect();
        String mysqlRequest = "SELECT * FROM users where email = ? and password = ?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, user.getEmail(), user.getPassword());
        result = vereinfachtesResultSet.next();
        connect.close();
        return result;
    }

    public List<User> getUsers(Project project) {
        String query =
                "SELECT * FROM users u "
                        + " JOIN projectuser pu ON u.email=pu.userId"
                        + " JOIN projects p ON pu.projectId = p.id"
                        + " WHERE pu.projectId = ?";

        ArrayList<User> result = new ArrayList<>();
        connect.connect();
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query, project.getId());
        while (!vereinfachtesResultSet.isLast()) {
            boolean next = vereinfachtesResultSet.next();
            if (next) {
                User user = ResultSetUtil.getUserFromResultSet(vereinfachtesResultSet);
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

    public String getUserToken(User user) {
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

    public User getUserByToken(String token) {
        return getUserByField("token", token);
    }

    public User getUserByEmail(String email) {
        return getUserByField("email", email);
    }

    private User getUserByField(String field, String value) {
        connect.connect();
        String mysqlRequest = "SELECT * FROM users where " + field + " = ?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, value);
        boolean next = vereinfachtesResultSet.next();
        if (next) {
            User user = ResultSetUtil.getUserFromResultSet(vereinfachtesResultSet);
            connect.close();
            return user;
        } else {
            connect.close();
            return null;
        }
    }
}

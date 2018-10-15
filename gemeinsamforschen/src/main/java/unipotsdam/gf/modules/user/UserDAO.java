package unipotsdam.gf.modules.user;

import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;
import unipotsdam.gf.util.ResultSetUtil;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@Resource
@Singleton
public class UserDAO {



    @Inject
    private MysqlConnect connect;


    public UserDAO() {

    }

    public void persist(User user, UserProfile profile) {
        connect.connect();
        String mysqlRequest = "INSERT INTO users (`name`, `password`, `email`, `isStudent`," +
                "`rocketChatId`,`rocketChatAuthToken`) values (?,?,?,?,?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, user.getName(), user.getPassword(), user.getEmail(),
                 user.getStudent(), user.getRocketChatId(), user.getRocketChatAuthToken());
        connect.close();
        // TODO implmement UserProfile @Mar
    }

    public void delete(User user) {
        String mysqlRequest = "DELETE FROM users where email = (?)";
        connect.connect();
        connect.issueInsertOrDeleteStatement(mysqlRequest, user.getEmail());
        connect.close();
    }

    public void update(User user) {
        String mysqlRequest = "UPDATE `users` SET `name`=?,`password`=?,`email`=?,`isStudent`=?," +
                "`rocketChatId`=?,`rocketChatAuthToken`=? WHERE email=? LIMIT 1";
        //TODO: maybe add handling if a line is actually updated
        //TODO: if user is updated, it also must updateForUser all other tables which includes some information about the user, for example project user
        connect.connect();
        connect.issueUpdateStatement(mysqlRequest, user.getName(), user.getPassword(), user.getEmail(), user
                .getStudent(), user.getRocketChatId(), user.getRocketChatAuthToken(), user.getEmail());
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

    public List<User> getUsersByProjectName(String projectName) {
        connect.connect();
        String query =
                "SELECT * FROM users u "
                        + " JOIN projectuser pu ON u.email=pu.userEmail"
                        + " JOIN projects p ON pu.projectName = p.name"
                        + " WHERE pu.projectName = ?";

        ArrayList<User> result = new ArrayList<>();
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query, projectName);
        while (!vereinfachtesResultSet.isLast()) {
            boolean next = vereinfachtesResultSet.next();
            if (next) {
                User user = ResultSetUtil.getUserFromResultSet(vereinfachtesResultSet);
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

    public User getUserByEmail(String email) {
        return getUserByField("email", email);
    }

    public User getUserByToken(String token) {
        return getUserByField("token", token);
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

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
    public void persist(User user) {
        connect.connect();
        String mysqlRequest =
                "INSERT INTO users (`name`, `password`, `email`, `isStudent`,`rocketChatUsername`, `discordid`) values (?,?,?,?,?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, user.getName(), user.getPassword(), user.getEmail(),
                user.getStudent(), user.getRocketChatUsername(), user.getDiscordid());
        connect.close();


    }

    public void delete(User user) {
        String mysqlRequest = "DELETE FROM users where email = (?)";
        connect.connect();
        connect.issueInsertOrDeleteStatement(mysqlRequest, user.getEmail());
        connect.close();
    }

    public void updateRocketChatUserName(User user) {
        String mysqlRequest = "UPDATE `users` SET" + "`rocketChatUsername`=? WHERE email=? LIMIT 1";
        connect.connect();
        connect.issueUpdateStatement(mysqlRequest, user.getRocketChatUsername(), user.getEmail());
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

    public boolean existsByRocketChatUsername(String rocketChatUsername) {
        connect.connect();
        String mysqlRequest = "SELECT * FROM users where rocketChatUsername = ?";
        VereinfachtesResultSet resultSet = connect.issueSelectStatement(mysqlRequest, rocketChatUsername);
        if (resultSet == null) {
            return false;
        }
        boolean result = resultSet.next();
        connect.close();
        return result;
    }

    public List<User> getUsersByProjectName(String projectName) {
        connect.connect();
        String query =
                "SELECT * FROM users u " + " JOIN projectuser pu ON u.email=pu.userEmail" + " JOIN projects p ON pu.projectName = p.name" + " WHERE pu.projectName = ?";

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

    public User getUserByEmail(String email) {
        return getUserByField("email", email);
    }


    private User getUserByField(String field, String value) {
        connect.connect();
        String mysqlRequest = "SELECT * FROM users where " + field + " = ?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, value);
        boolean next = vereinfachtesResultSet.next();
        User user = null;
        if (next) {
            user = ResultSetUtil.getUserFromResultSet(vereinfachtesResultSet);
        }
        connect.close();
        return user;
    }

    public User getUserById(String id) {
        return getUserByField("id", id);
    }


}

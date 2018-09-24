package unipotsdam.gf.core.management.util;

import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.core.management.user.User;

public class ResultSetUtil {

    public static User getUserFromResultSet(VereinfachtesResultSet vereinfachtesResultSet) {
        String name = vereinfachtesResultSet.getString("name");
        String password = vereinfachtesResultSet.getString("password");
        String email = vereinfachtesResultSet.getString("email");
        String token = vereinfachtesResultSet.getString("token");
        String rocketChatUsername = vereinfachtesResultSet.getString("rocketChatUsername");
        String rocketChatAuthToken = vereinfachtesResultSet.getString("rocketChatAuthToken");
        String rocketChatPersonalAccessToken = vereinfachtesResultSet.getString("rocketChatPersonalAccessToken");
        String rocketChatUserId = vereinfachtesResultSet.getString("rocketChatUserId");
        Boolean isStudent = vereinfachtesResultSet.getBoolean("isStudent");
        return new User(name, password, email, token, rocketChatUsername, rocketChatAuthToken,
                rocketChatPersonalAccessToken, rocketChatUserId, isStudent);
    }
}

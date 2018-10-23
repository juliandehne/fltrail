package unipotsdam.gf.util;

import unipotsdam.gf.mysql.VereinfachtesResultSet;
import unipotsdam.gf.modules.user.User;

public class ResultSetUtil {

    public static User getUserFromResultSet(VereinfachtesResultSet vereinfachtesResultSet) {
        String name = vereinfachtesResultSet.getString("name");
        String password = vereinfachtesResultSet.getString("password");
        String email = vereinfachtesResultSet.getString("email");
        String rocketChatId = vereinfachtesResultSet.getString("rocketChatId");
        String rocketChatAuthToken = vereinfachtesResultSet.getString("rocketChatAuthToken");
        String rocketChatPersonalAccessToken = vereinfachtesResultSet.getString("rocketChatPersonalAccessToken");
        String rocketChatUserId = vereinfachtesResultSet.getString("rocketChatUserId");
        Boolean isStudent = vereinfachtesResultSet.getBoolean("isStudent");
        return new User(name, password, email, rocketChatUsername, rocketChatAuthToken,
                rocketChatPersonalAccessToken, rocketChatUserId, isStudent);
    }
}

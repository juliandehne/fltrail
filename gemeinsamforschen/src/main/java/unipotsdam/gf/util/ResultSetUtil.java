package unipotsdam.gf.util;

import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.mysql.VereinfachtesResultSet;

public class ResultSetUtil {

    public static User getUserFromResultSet(VereinfachtesResultSet vereinfachtesResultSet) {
        String name = vereinfachtesResultSet.getString("name");
        String password = vereinfachtesResultSet.getString("password");
        String email = vereinfachtesResultSet.getString("email");
        String rocketChatId = vereinfachtesResultSet.getString("rocketChatUserId");
        String rocketChatAuthToken = vereinfachtesResultSet.getString("rocketChatAuthToken");
        String rocketChatPersonalAccessToken = vereinfachtesResultSet.getString("rocketChatPersonalAccessToken");
        String rocketChatUserId = vereinfachtesResultSet.getString("rocketChatUserId");
        Boolean isStudent = vereinfachtesResultSet.getBoolean("isStudent");
        return new User(name, password, email, rocketChatId, rocketChatAuthToken,
                rocketChatPersonalAccessToken, rocketChatUserId, isStudent);
    }
}

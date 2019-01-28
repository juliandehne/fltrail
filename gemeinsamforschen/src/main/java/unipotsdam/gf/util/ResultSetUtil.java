package unipotsdam.gf.util;

import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.mysql.VereinfachtesResultSet;

public class ResultSetUtil {

    public static User getUserFromResultSet(VereinfachtesResultSet vereinfachtesResultSet) {
        String name = vereinfachtesResultSet.getString("name");
        String password = vereinfachtesResultSet.getString("password");
        String email = vereinfachtesResultSet.getString("email");
        String discordId = vereinfachtesResultSet.getString("discordid");
        String rocketChatUserName = vereinfachtesResultSet.getString("rocketChatUserName");
        Boolean isStudent = vereinfachtesResultSet.getBoolean("isStudent");
        int id = vereinfachtesResultSet.getInt("id");
        User result =  new User(name, password, email, rocketChatUserName, isStudent, discordId);
        result.setId(id);
        return result;
    }
}

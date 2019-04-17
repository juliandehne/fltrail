package unipotsdam.gf.modules.fileManagement;

import unipotsdam.gf.config.GFDatabaseConfig;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@ManagedBean
@Resource
@Singleton
class FileManagementDAO {

    static void writePDFMetaToDB(User user, Project project, String fileLocation, FileRole fileRole, String fileName)
            throws SQLException {
        String url = GFDatabaseConfig.DB_URL+"/fltrail";
        String dbUser = GFDatabaseConfig.USER;
        String dbPassword = GFDatabaseConfig.PASS;
        Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
        String mysqlRequest =
                "INSERT INTO `largefilestorage`(`userEmail`, `projectName`, `filelocation`, `filerole`, `filename`) VALUES (?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(mysqlRequest);
        statement.setString(1, user.getEmail());
        statement.setString(2, project.getName());
        statement.setString(3, fileLocation);
        statement.setString(4, fileRole.toString());
        statement.setString(5, fileName);
        statement.executeUpdate();
    }
}

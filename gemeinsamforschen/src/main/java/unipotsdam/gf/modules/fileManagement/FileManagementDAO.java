package unipotsdam.gf.modules.fileManagement;

import unipotsdam.gf.config.GFDatabaseConfig;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.mysql.MysqlConnect;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@ManagedBean
@Resource
@Singleton
public class FileManagementDAO {

    public static void saveInputStreamToDB(User user, Project project, PDFFile pdfFile) throws SQLException {
        String url = GFDatabaseConfig.DB_URL+"/fltrail";
        String dbUser = GFDatabaseConfig.USER;
        String dbPassword = GFDatabaseConfig.PASS;
        Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
        String mysqlRequest =
                "INSERT INTO `largefilestorage`(`userEmail`, `projectName`, `PDFBLOB`, `filename`, `filerole`) VALUES (?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(mysqlRequest);
        statement.setString(1, user.getEmail());
        statement.setString(2, project.getName());
        statement.setBlob(3, pdfFile.getBLOB());
        statement.setString(4, pdfFile.getNameOfFile());
        statement.setString(5, pdfFile.getFileRole().toString());
        //todo: execution throws error. Probably blob is too large. Increase Timeout and filesize!?
        statement.executeUpdate();
    }
}

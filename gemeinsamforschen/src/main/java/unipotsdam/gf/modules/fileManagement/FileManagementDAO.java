package unipotsdam.gf.modules.fileManagement;

import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@ManagedBean
@Resource
@Singleton
public class FileManagementDAO {

    @Inject
    private MysqlConnect connect;

    @Inject
    private GroupDAO groupDAO;

    void writeFileMetaToDB(User user, Project project, String fileLocation, FileRole fileRole, String fileName) {

        Integer groupId = null;

        switch (fileRole) {
            case FINAL_REPORT:
            case PRESENTATION:
            case DOSSIER:
                groupId = groupDAO.getGroupByStudent(project, user);
                break;
        }

        connect.connect();
        if (groupId != null) {
            String mysqlRequest =
                    "INSERT INTO `largefilestorage`(`userEmail`, `projectName`, `filelocation`, `filerole`, " +
                            "`filename`, `groupId`) VALUES (?,?,?,?,?,?)";
            connect.issueInsertOrDeleteStatement(
                    mysqlRequest, user.getEmail(), project.getName(), fileLocation, fileRole.toString(), fileName);
        } else {
            String mysqlRequest =
                    "INSERT INTO `largefilestorage`(`userEmail`, `projectName`, `filelocation`, `filerole`, `filename`) VALUES (?,?,?,?,?)";
            connect.issueInsertOrDeleteStatement(
                    mysqlRequest, user.getEmail(), project.getName(), fileLocation, fileRole.toString(), fileName);
        }
        connect.close();
    }

    Map<String, String> getListOfFiles(User user, Project project) {
        connect.connect();
        String mysqlRequest =
                "SELECT * FROM `largefilestorage` lfs JOIN `groupuser` gu " + "ON gu.userEmail=lfs.userEmail JOIN groups g on g.id=gu.groupId WHERE g.id " + "IN (SELECT gu2.groupId FROM `groupuser` gu2 WHERE gu2.userEmail=?) AND g.projectName=? " + "AND g.projectName=lfs.projectName";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, user.getEmail(), project.getName());
        boolean next = vereinfachtesResultSet.next();
        Map<String, String> result = new HashMap<>();
        while (next) {
            result.put(vereinfachtesResultSet.getString("filelocation"), vereinfachtesResultSet.getString("fileName"));
            next = vereinfachtesResultSet.next();
        }
        connect.close();
        return result;
    }

    void deleteMetaOfFile(String fileLocation) {
        connect.connect();
        String mysqlRequest = "DELETE FROM `largefilestorage` WHERE filelocation=?";
        connect.issueInsertOrDeleteStatement(mysqlRequest, fileLocation);
        connect.close();
    }
}

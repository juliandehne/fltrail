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
import java.util.ArrayList;
import java.util.List;

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
                    mysqlRequest, user.getEmail(), project.getName(), fileLocation, fileRole.toString(), fileName, groupId);
        } else {
            String mysqlRequest =
                    "INSERT INTO `largefilestorage`(`userEmail`, `projectName`, `filelocation`, `filerole`, `filename`) VALUES (?,?,?,?,?)";
            connect.issueInsertOrDeleteStatement(
                    mysqlRequest, user.getEmail(), project.getName(), fileLocation, fileRole.toString(), fileName);
        }
        connect.close();
    }

    public List<String> getFileLocation(Project project, User user, FileRole fileRole) {
        List<String> result = new ArrayList<>();
        connect.connect();
        String mysqlRequest =
                "SELECT * FROM `largefilestorage` WHERE userEmail = ? AND projectName = ? AND filerole = ?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(
                mysqlRequest, user.getEmail(), project.getName(), fileRole.toString());
        while (vereinfachtesResultSet.next()) {
            result.add(vereinfachtesResultSet.getString("fileLocation"));
        }
        connect.close();
        return result;
    }

    List<ContributionStorage> getListOfFiles(User user, Project project, boolean isAuthor) {
        connect.connect();
        String mysqlRequest =
                "SELECT * FROM `largefilestorage` lfs JOIN `groupuser` gu " +
                        "ON gu.userEmail=lfs.userEmail JOIN groups g on g.id=gu.groupId WHERE (g.id " +
                        "IN (SELECT gu2.groupId FROM `groupuser` gu2 WHERE gu2.userEmail=?) OR ?) AND g.projectName=? " +
                        "AND g.projectName=lfs.projectName";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, user.getEmail(), isAuthor, project.getName());
        boolean next = vereinfachtesResultSet.next();
        List<ContributionStorage> result = new ArrayList<>();
        while (next) {
            ContributionStorage shuttle = new ContributionStorage();
            shuttle.setFileLocation(vereinfachtesResultSet.getString("filelocation"));
            shuttle.setFileName(vereinfachtesResultSet.getString("fileName"));
            shuttle.setFileRole(FileRole.valueOf(vereinfachtesResultSet.getString("filerole")));
            shuttle.setProjectName(project.getName());
            shuttle.setUserEmail(vereinfachtesResultSet.getString("userEmail"));
            shuttle.setGroup(groupDAO.getGroupByGroupId(vereinfachtesResultSet.getInt("groupId")));
            result.add(shuttle);
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

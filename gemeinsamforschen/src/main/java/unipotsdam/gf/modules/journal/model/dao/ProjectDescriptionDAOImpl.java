package unipotsdam.gf.modules.journal.model.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.journal.model.ProjectDescription;
import unipotsdam.gf.modules.journal.util.JournalUtils;

import java.util.ArrayList;
import java.util.UUID;

public class ProjectDescriptionDAOImpl implements ProjectDescriptionDAO {

    private final Logger log = LoggerFactory.getLogger(ProjectDescriptionDAOImpl.class);


    @Override
    public void createDescription(ProjectDescription projectDescription) {
        // create a new id
        String uuid = UUID.randomUUID().toString();
        while (JournalUtils.existsId(uuid,"projectdescription")) {
            uuid = UUID.randomUUID().toString();
        }

        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "INSERT INTO projectdescription(`id`, `author`, `project`, `text`, `open`) VALUES (?,?,?,?,?);";
        connection.issueInsertOrDeleteStatement(request, uuid, projectDescription.getStudent().getStudentId(),projectDescription.getStudent().getProjectId(),projectDescription.getDescriptionMD(),true);

        //close connection
        connection.close();

    }

    @Override
    public void updateDescription(ProjectDescription projectDescription) {
        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "UPDATE projectdescription SET text=? WHERE id = ?";
        connection.issueUpdateStatement(request, projectDescription.getDescriptionMD(), projectDescription.getId());

        //close connection
        connection.close();
    }

    @Override
    public ProjectDescription getDescription(StudentIdentifier studentIdentifier) {
        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "SELECT * FROM projectdescription WHERE author = ? AND project = ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, studentIdentifier.getStudentId(),studentIdentifier.getProjectId());

        if (rs != null && rs.next()) {

            // save journal
            ProjectDescription description = getDescriptionFromResultSet(rs);

            // close connection
            connection.close();

            return description;
        } else {

            // close connection
            connection.close();

            return null;
        }
    }

    @Override
    public ProjectDescription getDescription(String id) {
        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "SELECT * FROM projectdescription WHERE id = ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, id);

        if (rs != null && rs.next()) {

            // save journal
            ProjectDescription description = getDescriptionFromResultSet(rs);

            // close connection
            connection.close();

            return description;
        } else {

            // close connection
            connection.close();

            return null;
        }
    }

    @Override
    public void deleteDescription(StudentIdentifier studentIdentifier) {
        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "DELETE FROM projectdescription WHERE WHERE author = ? AND project = ?;";
        connection.issueInsertOrDeleteStatement(request, studentIdentifier.getStudentId(),studentIdentifier.getProjectId());

        // close connection
        connection.close();


    }

    @Override
    public void closeDescription(String id) {
        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "UPDATE projectdescription SET open=? WHERE id = ?";
        connection.issueUpdateStatement(request, false, id);

        //close connection
        connection.close();
    }

    @Override
    public ArrayList<String> getOpenDescriptions(Project project) {
        ArrayList<String> userIds = new ArrayList<>();

        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "SELECT * FROM projectdescription WHERE project = ? AND open = ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, project, true);

        while (rs.next()) {
            userIds.add(getDescriptionFromResultSet(rs).getStudent().getStudentId());
        }

        // close connection
        connection.close();

        return userIds;
    }

    private ProjectDescription getDescriptionFromResultSet(VereinfachtesResultSet rs) {
        String id = rs.getString("id");
        long timestamp = rs.getTimestamp(2).getTime();
        String author = rs.getString("author");
        String project = rs.getString("project");
        String text = rs.getString("text");
        boolean open = rs.getBoolean("open");

        return new ProjectDescription(id,author,text,project,new ArrayList<>(),new ArrayList<>(),timestamp, open);
    }

}

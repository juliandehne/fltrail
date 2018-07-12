package unipotsdam.gf.modules.journal.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.modules.journal.util.JournalUtils;
import unipotsdam.gf.modules.journal.view.ProjectDescriptionView;

import java.util.UUID;

public class ProjectDescriptionDAOImpl implements ProjectDescriptionDAO {

    private Logger log = LoggerFactory.getLogger(ProjectDescriptionDAOImpl.class);


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
    public ProjectDescription getDescription(String projectDescription) {
        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "SELECT * FROM projectdescription WHERE id = ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, projectDescription);

        if (rs.next()) {

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
    public void deleteDescription(String projectDescription) {
        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "DELETE FROM projectdescription WHERE id = ?;";
        connection.issueInsertOrDeleteStatement(request, projectDescription);

        // close connection
        connection.close();


    }

    @Override
    public void closeDescription(String projectDescription) {
        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "UPDATE projectdescription SET open=? WHERE id = ?";
        connection.issueUpdateStatement(request, false, projectDescription);

        //close connection
        connection.close();
    }

    private ProjectDescription getDescriptionFromResultSet(VereinfachtesResultSet rs) {
        String id = rs.getString("id");
        long timestamp = rs.getTimestamp(2).getTime();
        String author = rs.getString("author");
        String project = rs.getString("project");
        String text = rs.getString("text");
        String open = rs.getString("open");

        return null;
    }

}

package unipotsdam.gf.modules.journal.model.dao;

import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.journal.model.ProjectDescription;
import unipotsdam.gf.modules.journal.util.JournalUtils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.UUID;

public class ProjectDescriptionDAOImpl implements ProjectDescriptionDAO {


    @Inject
    MysqlConnect connection;


    @Inject
    JournalUtils utils;



    @Override
    public void createDescription(ProjectDescription projectDescription) {
        // create a new id
        String uuid = UUID.randomUUID().toString();
        while (utils.existsId(uuid, "projectdescription")) {
            uuid = UUID.randomUUID().toString();
        }

        // establish connection

        connection.connect();

        // build and execute request
        String request = "INSERT INTO projectdescription(`id`, `userName`, `projectName`, `text`, `open`) VALUES (?,?,?,?,?);";
        connection.issueInsertOrDeleteStatement(request, uuid, projectDescription.getStudent().getUserEmail(),
                projectDescription.getStudent().getProjectName(), projectDescription.getDescriptionMD(), true);

        //close connection
        connection.close();

    }

    @Override
    public void updateDescription(ProjectDescription projectDescription) {
        // establish connection

        connection.connect();

        // build and execute request
        String request = "UPDATE projectdescription SET text=? WHERE id = ?";
        connection.issueUpdateStatement(request, projectDescription.getDescriptionMD(), projectDescription.getId());

        //close connection
        connection.close();
    }

    @Override
    public ProjectDescription getDescription(StudentIdentifier userNameentifier) {
        // establish connection

        connection.connect();

        // build and execute request
        String request = "SELECT * FROM projectdescription WHERE userName = ? AND projectName = ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, userNameentifier.getUserEmail(),
                userNameentifier.getProjectName());

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
    public void deleteDescription(StudentIdentifier userNameentifier) {
        // establish connection

        connection.connect();

        // build and execute request
        String request = "DELETE FROM projectdescription WHERE userName = ? AND projectName = ?;";
        connection.issueInsertOrDeleteStatement(request, userNameentifier.getUserEmail(), userNameentifier.getProjectName());

        // close connection
        connection.close();


    }

    @Override
    public void closeDescription(String id) {
        // establish connection

        connection.connect();

        // build and execute request
        String request = "UPDATE projectdescription SET open=? WHERE id = ?";
        connection.issueUpdateStatement(request, false, id);

        //close connection
        connection.close();
    }

    @Override
    public ArrayList<String> getOpenDescriptions(Project project) {
        ArrayList<String> userEmails = new ArrayList<>();

        // establish connection

        connection.connect();

        // build and execute request
        String request = "SELECT * FROM projectdescription WHERE projectName = ? AND open = ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, project.getName(), true);

        while (rs.next()) {
            userEmails.add(getDescriptionFromResultSet(rs).getStudent().getUserEmail());
        }

        // close connection
        connection.close();

        return userEmails;
    }

    private ProjectDescription getDescriptionFromResultSet(VereinfachtesResultSet rs) {
        String id = rs.getString("id");
        long timestamp = rs.getTimestamp(2).getTime();
        String author = rs.getString("userName");
        String project = rs.getString("projectName");
        String text = rs.getString("text");
        boolean open = rs.getBoolean("open");

        return new ProjectDescription(id, author, text, project, new ArrayList<>(), timestamp, open);
    }

}

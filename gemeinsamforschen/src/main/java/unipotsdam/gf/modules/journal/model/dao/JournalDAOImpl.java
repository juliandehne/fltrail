package unipotsdam.gf.modules.journal.model.dao;

import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.journal.model.Journal;
import unipotsdam.gf.modules.journal.model.JournalFilter;
import unipotsdam.gf.modules.journal.util.JournalUtils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.UUID;

public class JournalDAOImpl implements JournalDAO {

    @Inject
    MysqlConnect connection;


    JournalUtils utils;

    @Override
    public void createJournal(Journal journal) {
        // create a new id if we found no id.
        String uuid = UUID.randomUUID().toString();
        while (utils.existsId(uuid, "journals")) {
            uuid = UUID.randomUUID().toString();
        }

        // establish connection
        connection.connect();

        // build and execute request
        String request = "INSERT INTO journals (`id`, `userName`, `projectName`, `text`, `visibility`,`category`, `open` ) VALUES (?,?,?,?,?,?,?);";
        connection.issueInsertOrDeleteStatement(request, uuid, journal.getStudentIdentifier().getUserEmail(),
                journal.getStudentIdentifier().getProjectName(), journal.getEntryMD(), journal.getVisibility(), journal.getCategory(), true);

        //close connection
        connection.close();

    }

    @Override
    public void updateJournal(Journal journal) {

        // establish connection

        connection.connect();

        // build and execute request
        String request = "UPDATE journals SET text=?, visibility=?, category=? WHERE id = ?";
        connection.issueUpdateStatement(request, journal.getEntryMD(), journal.getVisibility(), journal.getCategory(), journal.getId());

        //close connection
        connection.close();

    }

    @Override
    public void deleteJournal(String id) {

        // establish connection

        connection.connect();

        // build and execute request
        String request = "DELETE FROM journals WHERE id = ?;";
        connection.issueInsertOrDeleteStatement(request, id);

        // close connection
        connection.close();

    }

    @Override
    public Journal getJournal(String id) {
        // establish connection

        connection.connect();

        // build and execute request
        String request = "SELECT * FROM journals WHERE id = ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, id);

        if (rs.next()) {

            // save journal
            Journal journal = getJournalFromResultSet(rs);

            // close connection
            connection.close();

            return journal;
        } else {

            // close connection
            connection.close();

            return null;
        }
    }

    @Override
    public ArrayList<Journal> getAllByProject(String project, String student) {

        ArrayList<Journal> journals = new ArrayList<>();

        // establish connection

        connection.connect();

        // build and execute request
        String request = "SELECT * FROM journals WHERE projectName= ? AND (userName = ? OR visibility = \"ALL\" or visibility = \"GROUP\");";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, project, student);

        while (rs.next()) {
            journals.add(getJournalFromResultSet(rs));
        }

        // close connection
        connection.close();

        return journals;

    }

    private ArrayList<Journal> getAllByStudent(String student) {

        ArrayList<Journal> journals = new ArrayList<>();

        // establish connection

        connection.connect();

        // build and execute request
        String request = "SELECT * FROM journals WHERE userName= ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, student);

        while (rs.next()) {
            journals.add(getJournalFromResultSet(rs));
        }

        // close connection
        connection.close();

        return journals;

    }

    @Override
    public ArrayList<Journal> getAllByProjectAndFilter(String project, String student, JournalFilter filter) {
        if (filter == JournalFilter.ALL) {
            return getAllByProject(project, student);
        } else {
            return getAllByStudent(student);
        }

    }

    @Override
    public void closeJournal(String id) {
        // establish connection

        connection.connect();

        // build and execute request
        String request = "UPDATE journals SET open=? WHERE id = ?";
        connection.issueUpdateStatement(request, false, id);

        //close connection
        connection.close();
    }

    @Override
    public ArrayList<String> getOpenJournals(Project project) {
        ArrayList<String> userEmails = new ArrayList<>();

        // establish connection

        connection.connect();

        // build and execute request
        String request = "SELECT * FROM journals WHERE projectName = ? AND open = ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, project.getName(), true);

        while (rs.next()) {
            userEmails.add(getJournalFromResultSet(rs).getStudentIdentifier().getUserEmail());
        }

        // close connection
        connection.close();

        return userEmails;

    }

    /**
     * extracts a journal from VereinfachtesResultSet
     *
     * @param rs VereinfachtesResultSet
     * @return journal
     */
    private Journal getJournalFromResultSet(VereinfachtesResultSet rs) {

        String id = rs.getString("id");
        long timestamp = rs.getTimestamp(2).getTime();
        String student = rs.getString("userName");
        String project = rs.getString("projectName");
        String text = rs.getString("text");
        String visibility = rs.getString("visibility");
        String category = rs.getString("category");
        boolean open = rs.getBoolean("open");

        return new Journal(id, new StudentIdentifier(project, student), text, timestamp, utils.stringToVisibility
                (visibility), utils.stringToCategory(category), open);

    }
}

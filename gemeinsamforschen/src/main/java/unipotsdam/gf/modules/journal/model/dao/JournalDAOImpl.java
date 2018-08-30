package unipotsdam.gf.modules.journal.model.dao;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.journal.model.Journal;
import unipotsdam.gf.modules.journal.model.JournalFilter;
import unipotsdam.gf.modules.journal.util.JournalUtils;

import java.util.ArrayList;
import java.util.UUID;

public class JournalDAOImpl implements JournalDAO {

    @Override
    public void createJournal(Journal journal) {
        // create a new id if we found no id.
        String uuid = UUID.randomUUID().toString();
        while (JournalUtils.existsId(uuid,"journals")) {
            uuid = UUID.randomUUID().toString();
        }

        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "INSERT INTO journals (`id`, `author`, `project`, `text`, `visibility`,`category`, `open` ) VALUES (?,?,?,?,?,?,?);";
        connection.issueInsertOrDeleteStatement(request, uuid, journal.getStudentIdentifier().getStudentId(),
                journal.getStudentIdentifier().getProjectId(), journal.getEntryMD(), journal.getVisibility(), journal.getCategory(), true);

        //close connection
        connection.close();

    }

    @Override
    public void updateJournal(Journal journal) {

        // establish connection
        MysqlConnect connection = new MysqlConnect();
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
        MysqlConnect connection = new MysqlConnect();
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
        MysqlConnect connection = new MysqlConnect();
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
    public ArrayList<Journal> getAllByProject(String project) {

        ArrayList<Journal> journals = new ArrayList<>();

        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "SELECT * FROM journals WHERE project= ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, project);

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
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "SELECT * FROM journals WHERE author= ?;";
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
            return getAllByProject(project);
        } else {
            return getAllByStudent(student);
        }

    }

    @Override
    public void closeJournal(String id) {
        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "UPDATE journals SET open=? WHERE id = ?";
        connection.issueUpdateStatement(request, false, id);

        //close connection
        connection.close();
    }

    @Override
    public ArrayList<String> getOpenJournals(Project project) {
        ArrayList<String> userIds = new ArrayList<>();

        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "SELECT * FROM journals WHERE project = ? AND open = ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, project, true);

        while (rs.next()) {
            userIds.add(getJournalFromResultSet(rs).getStudentIdentifier().getStudentId());
        }

        // close connection
        connection.close();

        return userIds;

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
        String student = rs.getString("author");
        String project = rs.getString("project");
        String text = rs.getString("text");
        String visibility = rs.getString("visibility");
        String category = rs.getString("category");
        boolean open = rs.getBoolean("open");

        return new Journal(id, new StudentIdentifier(project, student), text, timestamp, JournalUtils.stringToVisibility(visibility), JournalUtils.stringToCategory(category), open);

    }
}

package unipotsdam.gf.modules.journal.model.dao;

import org.junit.Test;
import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.journal.model.Journal;
import unipotsdam.gf.modules.journal.model.JournalFilter;
import unipotsdam.gf.modules.journal.model.Visibility;
import unipotsdam.gf.modules.journal.util.JournalUtils;
import unipotsdam.gf.modules.peer2peerfeedback.Category;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class JournalDAOImplTest {

    private final JournalDAO journalDAO = new JournalDAOImpl();
    private final MysqlConnect connection = new MysqlConnect();
    private final String testId = "-1";
    private final String testStudent = "testStudent";
    private final String testProject = "testProject";
    private final String testEntry = "This is a Test";
    private final Visibility testVisibility = Visibility.ALL;
    private final Category testCategory = Category.TITEL;
    private final Journal testJournal = new Journal(testId, new StudentIdentifier(testProject, testStudent), testEntry, testVisibility, testCategory);

    @Test
    public void createJournal() {

        // Create Journal
        journalDAO.createJournal(testJournal);

        connection.connect();

        // Get that Journal
        ArrayList<Journal> resultJournals = getJournals();

        //Only one Journal should exist
        assertEquals(1, resultJournals.size());

        Journal resultJournal = resultJournals.get(0);

        //check if data correct
        assertEquals(resultJournal.getStudentIdentifier().getStudentId(), testStudent);
        assertEquals(resultJournal.getStudentIdentifier().getProjectId(), testProject);
        assertEquals(resultJournal.getEntryMD(), testEntry);

        //Journal should get real id while create
        assertFalse(resultJournal.getId().equals(testId));

        assertEquals(resultJournal.getVisibility(), testVisibility);
        assertEquals(resultJournal.getCategory(), testCategory);

        //cleanup
        cleanup(resultJournal.getId());

        connection.close();
    }

    @Test
    public void updateJournal() {

        Journal updateJournal = testJournal;
        //createJournal

        connection.connect();

        // build and execute request
        create(updateJournal);

        //change Data
        Visibility newVisibility = Visibility.MINE;
        Category newCategory = Category.DURCHFUEHRUNG;
        String newEntry = "Still a test";

        updateJournal.setVisibility(newVisibility);
        updateJournal.setCategory(newCategory);
        updateJournal.setEntry(newEntry);

        //update that Journal
        journalDAO.updateJournal(updateJournal);

        //check if update successful
        ArrayList<Journal> resultJournals = getJournals();

        //Only one Journal should exist
        assertEquals(1, resultJournals.size());

        Journal resultJournal = resultJournals.get(0);

        //check if data correct
        assertEquals(resultJournal.getStudentIdentifier().getStudentId(), testStudent);
        assertEquals(resultJournal.getStudentIdentifier().getProjectId(), testProject);
        assertEquals(resultJournal.getEntryMD(), newEntry);
        assertEquals(resultJournal.getId(), testId);
        assertEquals(resultJournal.getVisibility(), newVisibility);
        assertEquals(resultJournal.getCategory(), newCategory);

        //cleanup
        cleanup(updateJournal.getId());
        connection.close();

    }

    @Test
    public void deleteJournal() {

        //createJournal
        connection.connect();

        // build and execute request
        create(testJournal);

        //check if Journal was added
        ArrayList<Journal> resultJournals = getJournals();

        assertEquals(1, resultJournals.size());

        Journal resultJournal = resultJournals.get(0);


        //delete Journal
        journalDAO.deleteJournal(resultJournal.getId());

        //check if deleted
        resultJournals = getJournals();

        assertEquals(0, resultJournals.size());

        connection.close();
    }

    @Test
    public void getJournal() {


        //createJournal

        connection.connect();

        // build and execute request
        create(testJournal);

        //get that Journal
        Journal resultJournal = journalDAO.getJournal(testId);

        //check data
        assertEquals(resultJournal.getStudentIdentifier().getStudentId(), testStudent);
        assertEquals(resultJournal.getStudentIdentifier().getProjectId(), testProject);
        assertEquals(resultJournal.getEntryMD(), testEntry);
        assertEquals(resultJournal.getId(), testId);
        assertEquals(resultJournal.getVisibility(), testVisibility);
        assertEquals(resultJournal.getCategory(), testCategory);

        //cleanup
        cleanup(resultJournal.getId());

        connection.close();

    }

    @Test
    public void getAllByProject() {
        connection.connect();

        //add Some Journals
        Journal j1 = testJournal;
        create(j1);
        j1.setId("-2");
        create(j1);
        j1.setId("-3");
        j1.getStudentIdentifier().setProjectId("otherProject");
        create(j1);

        //get for project
        ArrayList<Journal> resultJournals = journalDAO.getAllByProject(testProject, testStudent);

        //should be 2 Journals
        assertEquals(2, resultJournals.size());

        //should be j1 and j2
        for (Journal j : resultJournals) {
            assertTrue(j.getId().equals("-1") || j.getId().equals("-2"));
        }

        //cleanup
        cleanup("-1", "-2", "-3");
    }

    @Test
    public void getAllByProjectAndFilter() {

        connection.connect();

        //Create some journals
        Journal j1 = testJournal;
        create(j1);
        j1.getStudentIdentifier().setStudentId("otherStudent");
        j1.setId("-2");
        create(j1);
        j1.setId("-3");
        j1.setVisibility(Visibility.MINE);
        create(j1);

        //all should return 2 Journals
        assertEquals(2, journalDAO.getAllByProjectAndFilter(testProject, testStudent, JournalFilter.ALL).size());

        //Own should return 1 Journal
        assertEquals(1, journalDAO.getAllByProjectAndFilter(testProject, testStudent, JournalFilter.OWN).size());

        //Cleanup
        cleanup("-1", "-2", "-3");
        connection.close();
    }

    @Test
    public void closeJournal() {
        connection.connect();

        //create Journal
        create(testJournal);

        Journal resultJournal = getJournals().get(0);

        //check if open
        assertTrue(resultJournal.isOpen());

        //close Journal
        journalDAO.closeJournal(resultJournal.getId());

        //check if closed
        resultJournal = getJournals().get(0);
        assertFalse(resultJournal.isOpen());

        //cleanup
        cleanup(resultJournal.getId());
    }

    @Test
    public void getOpenJournals() {
        connection.connect();

        Project openProject = new Project();
        openProject.setId(testProject);

        //create some Journals
        Journal j1 = testJournal;
        create(j1);
        j1.setOpen(false);
        j1.setId("-2");
        create(j1);
        j1.setId("-3");
        j1.getStudentIdentifier().setProjectId("otherProject");
        create(j1);

        //getOpenJournals
        ArrayList<String> resultJournals = journalDAO.getOpenJournals(openProject);

        //should be 1 Journal
        assertEquals(1, resultJournals.size());

        //should be journal -1
        assertEquals(testStudent, resultJournals.get(0));

        cleanup("-1", "-2", "-3");
        connection.close();
    }


    //utility

    private ArrayList<Journal> getJournals() {
        String request = "SELECT * FROM journals WHERE projectId= ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, testProject);

        ArrayList<Journal> resultJournals = new ArrayList<>();
        while (rs.next()) {
            resultJournals.add(getJournalFromResultSet(rs));
        }
        return resultJournals;
    }

    private void create(Journal getJournal) {
        String createRequest = "INSERT INTO journals (`id`, `studentId`, `projectId`, `text`, `visibility`,`category`, `open` ) VALUES (?,?,?,?,?,?,?);";
        connection.issueInsertOrDeleteStatement(createRequest, getJournal.getId(), getJournal.getStudentIdentifier().getStudentId(),
                getJournal.getStudentIdentifier().getProjectId(), getJournal.getEntryMD(), getJournal.getVisibility(), getJournal.getCategory(), getJournal.isOpen());
    }

    private void cleanup(String... ids) {
        for (String id : ids) {
            String deleteRequest = "DELETE FROM journals WHERE id = ?;";
            connection.issueInsertOrDeleteStatement(deleteRequest, id);
        }
    }

    private Journal getJournalFromResultSet(VereinfachtesResultSet rs) {

        String id = rs.getString("id");
        long timestamp = rs.getTimestamp(2).getTime();
        String student = rs.getString("studentId");
        String project = rs.getString("projectId");
        String text = rs.getString("text");
        String visibility = rs.getString("visibility");
        String category = rs.getString("category");
        boolean open = rs.getBoolean("open");

        return new Journal(id, new StudentIdentifier(project, student), text, timestamp, JournalUtils.stringToVisibility(visibility), JournalUtils.stringToCategory(category), open);

    }
}
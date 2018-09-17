package unipotsdam.gf.modules.journal.service;

import org.junit.After;
import org.junit.Test;
import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.user.UserDAO;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.journal.model.Journal;
import unipotsdam.gf.modules.journal.model.Visibility;
import unipotsdam.gf.modules.journal.model.dao.JournalDAO;
import unipotsdam.gf.modules.journal.model.dao.JournalDAOImpl;
import unipotsdam.gf.modules.peer2peerfeedback.Category;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class JournalServiceImplTest {

    private final String testId = "-1";
    private final String testStudent = "testStudent";
    private final String testProject = "testProject";
    private final String testEntry = "This is a Test";
    private final Visibility testVisibility = Visibility.ALL;
    private final Category testCategory = Category.TITEL;
    private final Journal testJournal = new Journal(testId, new StudentIdentifier(testProject, testStudent), testEntry, testVisibility, testCategory);
    private JournalService journalService = new JournalServiceImpl();
    private JournalDAO journalDAO = new JournalDAOImpl();
    private UserDAO userDAO = new UserDAO(new MysqlConnect());

    @After
    public void cleanUp() {

        ArrayList<Journal> deleteJournals = journalDAO.getAllByProject(testProject, testStudent);

        for (Journal j : deleteJournals) {
            journalDAO.deleteJournal(j.getId());
        }
    }

    @Test
    public void getJournal() {

        journalDAO.createJournal(testJournal);

        //get id of added Journal
        String journalID = journalDAO.getAllByProject(testProject, testStudent).get(0).getId();

        Journal resultJournal = journalService.getJournal(journalID);

        assertNotNull(resultJournal);

        assertEquals(resultJournal.getStudentIdentifier().getStudentId(), testStudent);
        assertEquals(resultJournal.getStudentIdentifier().getProjectId(), testProject);
        assertEquals(resultJournal.getEntryMD(), testEntry);
        assertEquals(resultJournal.getId(), journalID);
        assertEquals(resultJournal.getVisibility(), testVisibility);
        assertEquals(resultJournal.getCategory(), testCategory);

    }

    @Test
    public void getAllJournalsSi() {

        journalDAO.createJournal(testJournal);
        journalDAO.createJournal(testJournal);
        journalDAO.createJournal(testJournal);
        testJournal.setStudentIdentifier(new StudentIdentifier("a", "b"));
        journalDAO.createJournal(testJournal);

        ArrayList<Journal> resultJurnals = journalService.getAllJournals(testStudent, testProject);

        assertEquals(3, resultJurnals.size());

        //Delete extra Journal
        String jId = journalDAO.getAllByProject("a", "b").get(0).getId();
        journalDAO.deleteJournal(jId);
    }

    @Test
    public void getAllJournalsFilter() {
        journalDAO.createJournal(testJournal);
        testJournal.setVisibility(Visibility.MINE);
        journalDAO.createJournal(testJournal);
        testJournal.setVisibility(Visibility.GROUP);
        journalDAO.createJournal(testJournal);
        testJournal.setStudentIdentifier(new StudentIdentifier(testProject, "b"));
        testJournal.setVisibility(Visibility.ALL);
        journalDAO.createJournal(testJournal);

        ArrayList<Journal> resultJournals = journalService.getAllJournals("b", testProject);

        assertEquals(3, resultJournals.size());

        //Delete extra Journal
        String jId = journalDAO.getAllByProject(testProject, "b").get(0).getId();
        journalDAO.deleteJournal(jId);

    }

    @Test
    public void saveJournal() {
        //Create new

        journalService.saveJournal("0", testStudent, testProject, testEntry, testVisibility.toString(), testVisibility.toString());

        String journalID = journalDAO.getAllByProject(testProject, testStudent).get(0).getId();

        Journal resultJournal = journalDAO.getJournal(journalID);

        assertNotNull(resultJournal);

        assertEquals(resultJournal.getStudentIdentifier().getStudentId(), testStudent);
        assertEquals(resultJournal.getStudentIdentifier().getProjectId(), testProject);
        assertEquals(resultJournal.getEntryMD(), testEntry);
        assertEquals(resultJournal.getId(), journalID);
        assertEquals(resultJournal.getVisibility(), testVisibility);
        assertEquals(resultJournal.getCategory(), testCategory);


        //Update

        journalService.saveJournal(journalID, testStudent, testProject, testEntry + testEntry, Visibility.MINE.toString(), Category.AUSWERTUNG.toString());

        resultJournal = journalDAO.getJournal(journalID);

        assertNotNull(resultJournal);

        assertEquals(resultJournal.getStudentIdentifier().getStudentId(), testStudent);
        assertEquals(resultJournal.getStudentIdentifier().getProjectId(), testProject);
        assertEquals(resultJournal.getEntryMD(), testEntry + testEntry);
        assertEquals(resultJournal.getId(), journalID);
        assertEquals(resultJournal.getVisibility(), Visibility.MINE);
        assertEquals(resultJournal.getCategory(), Category.AUSWERTUNG);


    }

    @Test
    public void deleteJournal() {

        journalDAO.createJournal(testJournal);

        ArrayList<Journal> resultJournals = journalDAO.getAllByProject(testProject, testStudent);
        assertEquals(1, resultJournals.size());

        String jId = resultJournals.get(0).getId();

        journalService.deleteJournal(jId);

        resultJournals = journalDAO.getAllByProject(testProject, testStudent);
        assertEquals(0, resultJournals.size());

    }

    @Test
    public void closeJournal() {

        journalDAO.createJournal(testJournal);

        Journal resultJournal = journalDAO.getAllByProject(testProject, testStudent).get(0);

        assertTrue(resultJournal.isOpen());

        journalService.closeJournal(resultJournal.getId());
        resultJournal = journalDAO.getAllByProject(testProject, testStudent).get(0);

        assertFalse(resultJournal.isOpen());
    }

    @Test
    public void checkIfAllJournalClosed() {

        Project project = new Project();
        project.setId(testProject);

        journalDAO.createJournal(testJournal);
        journalDAO.createJournal(testJournal);

        ArrayList<Journal> resultJournals = journalDAO.getAllByProject(testProject, testStudent);

        assertFalse(journalService.checkIfAllJournalClosed(project));

        for (Journal j : resultJournals) {
            journalDAO.closeJournal(j.getId());
        }

        assertTrue(journalService.checkIfAllJournalClosed(project));

    }

    @Test
    public void getOpenUserByProject() {

        User user = new User("Test", "Test", "test@test.de", true);
        String token = userDAO.getUserToken(user);

        Project project = new Project();

        testJournal.getStudentIdentifier().setStudentId(token);
        project.setId(testProject);

        journalDAO.createJournal(testJournal);

        ArrayList<User> resultUsers = journalService.getOpenUserByProject(project);

        assertEquals(1, resultUsers.size());
        assertEquals(user.getEmail(), resultUsers.get(0).getId());
    }
}
package unipotsdam.gf.modules.journal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.user.UserDAO;
import unipotsdam.gf.core.states.model.Constraints;
import unipotsdam.gf.core.states.model.ConstraintsMessages;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.journal.model.Journal;
import unipotsdam.gf.modules.journal.model.JournalFilter;
import unipotsdam.gf.modules.journal.model.Visibility;
import unipotsdam.gf.modules.journal.model.dao.JournalDAO;
import unipotsdam.gf.modules.journal.model.dao.JournalDAOImpl;
import unipotsdam.gf.modules.journal.util.JournalUtils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class JournalServiceImpl implements JournalService {

    private final Logger log = LoggerFactory.getLogger(JournalServiceImpl.class);
    private final JournalDAO journalDAO = new JournalDAOImpl();

    //@Inject TODO injected userDAO = null
    private UserDAO userDAO = new UserDAO(new MysqlConnect());

    @Override
    public Journal getJournal(String id) {
            return journalDAO.getJournal(id);
    }

    @Override
    public ArrayList<Journal> getAllJournals(String student, String project, JournalFilter filter) {
        log.debug(">> get all journals(" + student + "," + project + "," + filter + ")");

        ArrayList<Journal> result = new ArrayList<>();

        ArrayList<Journal> dbJournals = journalDAO.getAllByProjectAndFilter(project, student, filter);
        for (Journal j : dbJournals) {
            //always show own Journals
            if (j.getStudentIdentifier().getStudentId().equals(student)) {
                addJournal(j, result);
            } else {

                // if Visibility All, show if Filter allows it
                if (j.getVisibility() == Visibility.ALL && filter == JournalFilter.ALL) {
                    addJournal(j, result);
                }

                //If Visibility Group, show if student is in group and filter allows it
                if (j.getVisibility() == Visibility.GROUP && j.getStudentIdentifier().getProjectId().equals(project) && filter == JournalFilter.ALL) {
                    addJournal(j, result);
                }

            }

        }
        log.debug("<< get all journals(" + student, "," + project + ")");

        return result;
    }

    private void addJournal(Journal j, ArrayList<Journal> result) {
        j.setName(userDAO.getUserByToken(j.getStudentIdentifier().getStudentId()).getName());
        result.add(j);
    }

    @Override
    public ArrayList<Journal> getAllJournals(String student, String project) {
        log.debug(">> get all journals(" + student + "," + project + ")");

        return journalDAO.getAllByProject(project, student);
    }

    @Override
    public void saveJournal(String id, String student, String project, String text, String visibility, String category) {
        log.debug(">> save journal(" + id + "," + student + "," + project + "," + text + "," + visibility + "," + category + ")");

        Journal journal = new Journal(id, new StudentIdentifier(project, student), text, JournalUtils.stringToVisibility(visibility), JournalUtils.stringToCategory(category));

        //if id = 0 new Journal else update
        if (id.equals("")) {

            log.debug("save journal: create new");
            journalDAO.createJournal(journal);
        } else {
            log.debug("save journal: update" + journal.getId());
            journalDAO.updateJournal(journal);
        }
        log.debug("<<< save journal");

    }

    @Override
    public void deleteJournal(String journal) {
        log.debug(">>> delete journal:" + journal);
        journalDAO.deleteJournal(journal);
        log.debug("<<< delete journal");
    }

    @Override
    public void closeJournal(String journal) {
        log.debug(">>> close journal: " + journal);
        journalDAO.closeJournal(journal);
        log.debug("<<< close journal");
    }

    @Override
    public Map<StudentIdentifier, ConstraintsMessages> checkIfAllJournalClosed(Project project) {
        Map<StudentIdentifier, ConstraintsMessages> result = new HashMap<>();
        for (String studentId : journalDAO.getOpenJournals(project)) {
            StudentIdentifier student = new StudentIdentifier(project.getId(), studentId);
            result.put(student, new ConstraintsMessages(Constraints.JournalOpen, student));
        }
        return result;
    }

    @Override
    public ArrayList<User> getOpenUserByProject(Project project) {

        ArrayList<String> userId = journalDAO.getOpenJournals(project);
        ArrayList<User> users = new ArrayList<>();

        for (String id : userId) {
            users.add(userDAO.getUserByToken(id));
        }
        return users;
    }

}
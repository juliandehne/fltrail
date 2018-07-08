package unipotsdam.gf.modules.journal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.journal.model.*;
import unipotsdam.gf.modules.journal.util.JournalUtils;

import java.util.ArrayList;

public class JournalServiceImpl implements JournalService {

    private final Logger log = LoggerFactory.getLogger(JournalServiceImpl.class);
    JournalDAO journalDAO = new JournalDAOImpl();

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
                result.add(j);
            } else {

                // if Visibility All, show if Filter allows it
                if (j.getVisibility() == Visibility.ALL && filter == JournalFilter.ALL) {
                    result.add(j);
                }

                //If Visibility Group, show if student is in group and filter allows it
                //TODO: project != Group, for testing ok, change for real Service
                if (j.getVisibility() == Visibility.GROUP && j.getStudentIdentifier().getProjectId().equals(project) && filter == JournalFilter.ALL) {
                    result.add(j);
                }

                //TODO if Dozent
            }

        }
        log.debug("<< get all journals(" + student, "," + project + ")");

        return result;
    }

    @Override
    public ArrayList<Journal> getAllJournals(String student, String project) {
        return journalDAO.getAllByProject(project);
    }

    @Override
    public void saveJournal(String id, String student, String project, String text, String visibility, String category) {
        Journal journal = new Journal(id, new StudentIdentifier(student, project), text, JournalUtils.stringToVisibility(visibility), JournalUtils.stringToCategory(category));

        //if id = 0 new Journal else update
        if (id.equals("0")) {
            journalDAO.createJournal(journal);
        } else {
            journalDAO.updateJournal(journal);
        }

    }

    @Override
    public void deleteJournal(String id) {
    }

    @Override
    public void closeJournal(String journal) {
        journalDAO.closeJournal(journal);
    }


}


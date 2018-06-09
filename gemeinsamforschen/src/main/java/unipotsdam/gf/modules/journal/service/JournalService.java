package unipotsdam.gf.modules.journal.service;

import unipotsdam.gf.modules.journal.model.Journal;
import unipotsdam.gf.modules.journal.model.JournalFilter;

import java.util.ArrayList;

/**
 * Service for learning Journal
 */

public interface JournalService {

    /**
     * Returns a specific Journal
     * @param id id of Journal
     * @return JSON of Journal
     */
    Journal getJournal(String id);

    /**
     * Returns all Journals for a student, filtered whether only own or all Journals are requested
     * @param student the requested student
     * @param project the requested project
     * @param filter OWN or ALL
     * @return Json of all Journals
     */
    ArrayList<Journal> getAllJournals(String student, String project, JournalFilter filter);

    /**
     * Returns all Journals for a student
     * @param student the requested student
     * @param project the requested project
     * @return Json of all Journals
     */
    ArrayList<Journal> getAllJournals(String student, String project);

    /**
     * Saves or edits a Journal
     * @param id id, -1 if new Journal
     * @param student owner of the Journal
     * @param project associated Project
     * @param text content of the Journal
     * @param visibility visibility of the Journal
     * @param category category of the Journal
     * @return true if success
     */
     boolean saveJournal(long id, String student, String project, String text, String visibility, String category);

    /**
     * deletes a Journal
     * @param id id of the Journal
     * @return true if success
     */
     boolean deleteJournal(long id);


}

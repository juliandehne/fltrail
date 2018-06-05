package unipotsdam.gf.interfaces;

import unipotsdam.gf.modules.journal.JournalEntry;

/**
 * Interface for learning journal entry
 */
public interface IJournal {

    /**
     * Enum for visibility
     */
    enum Visibility{ALL, STUDENT, DOZENT, NONE}


    /**
     * Add a new journal entry
     *
     * @param text text of the entry
     * @param visibility visibility of the entry
     */
    void addJournal(String text, Visibility visibility);

    /**
     * Change an existing journal entry
     *
      * @param newText the new text
     */
    void editJournal(String newText);

    /**
     * Delete a journal entry
     *
     * @param journaId id of the entry
     */
    void deleteJournal(long journaId);

    /**
     * change visibility of an entry
     * @param journaId id of the entry
     * @param visibility new visibility
     */
    void setVisibility(long journaId, Visibility visibility);

    /**

     * Get specific journal entry
     *
     * @param journaId id of the entry
     * @return JournalEntry from database
     */
    JournalEntry getJournal(long journaId);

    /**
     * Get all JournalEntry for a project
     *
     * @param projectId id of project
     * @return all JournalEnrys for that project
     */
    JournalEntry getAllJournalEntrysProject(long projectId);


}

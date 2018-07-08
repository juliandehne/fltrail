package unipotsdam.gf.modules.journal.model;

import java.util.ArrayList;

public interface JournalDAO {


    void createJournal(Journal journal);

    void updateJournal(Journal journal);

    void deleteJournal(String id);

    Journal getJournal(String id);

    ArrayList<Journal> getAllByProject(String project);

    ArrayList<Journal> getAllByProjectAndFilter(String project, String student, JournalFilter filter);

    void closeJournal(String id);

}

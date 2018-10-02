package unipotsdam.gf.modules.journal.model.dao;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.journal.model.Journal;
import unipotsdam.gf.modules.journal.model.JournalFilter;

import java.util.ArrayList;

public interface JournalDAO {


    void createJournal(Journal journal);

    void updateJournal(Journal journal);

    void deleteJournal(String id);

    Journal getJournal(String id);

    ArrayList<Journal> getAllByProject(String project, String student);

    ArrayList<Journal> getAllByProjectAndFilter(String project, String student, JournalFilter filter);

    void closeJournal(String id);

    ArrayList<String> getOpenJournals(Project project);
}

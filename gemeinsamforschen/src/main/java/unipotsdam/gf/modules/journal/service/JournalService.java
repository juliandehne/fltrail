package unipotsdam.gf.modules.journal.service;

import unipotsdam.gf.modules.journal.model.Journal;

import java.util.ArrayList;

public interface JournalService {
    ArrayList<Journal> getAllJournals(String student, String project);
}

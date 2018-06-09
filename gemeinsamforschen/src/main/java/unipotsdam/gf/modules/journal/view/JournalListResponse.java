package unipotsdam.gf.modules.journal.view;

import unipotsdam.gf.modules.journal.model.Journal;

import java.util.List;

/**
 * Wrapper Class to return a List in REST
 */
public class JournalListResponse {

    private List<Journal> journals;

    public void setJournals(List<Journal> journals) {
        this.journals = journals;
    }

    public List<Journal> getJournals() {
        return journals;


    }
}

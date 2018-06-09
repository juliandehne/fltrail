package unipotsdam.gf.interfaces;

import unipotsdam.gf.modules.assessment.controller.StudentIdentifier;

/**
 * Interface for learning journal
 */

public interface IJournal {

    /**
     * Exports the learning journal
     * @param student StudentIdentifier
     * @return the journal as String (may change)
     */
    String exportJournal (StudentIdentifier student);

}

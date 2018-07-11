package unipotsdam.gf.interfaces;


import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;

import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;

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

    /**
     * check if all students have prepared their portfolios to be evaluated
     * @return
     * @param project
     */
    Boolean getPortfoliosForEvaluationPrepared(Project project);

    /**
     * find out, who hasn't prepared their portfolio for evaluation and send message or highlight in view
     * @param project
     */
    void assignMissingPortfolioTasks(Project project);
}

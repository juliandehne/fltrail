package unipotsdam.gf.interfaces;


import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.assignments.NotImplementedLogger;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.journal.model.EPorfolio;
import unipotsdam.gf.modules.journal.model.Journal;
import unipotsdam.gf.modules.researchreport.ResearchReport;

import javax.swing.text.html.HTML;
import java.io.File;
import java.util.List;

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
    String exportJournal (StudentIdentifier student) ;

    /**
     * check if all students have prepared their portfolios to be evaluated
     * @return
     * @param project
     */
    Boolean getPortfoliosForEvaluationPrepared(Project project) ;

    /**
     * find out, who hasn't prepared their portfolio for evaluation and send message or highlight in view
     * @param project
     */
    void assignMissingPortfolioTasks(Project project) ;

    /**
     * after user has uploaded a journal entry this function is called
     * @param journalEntry
     * @param student
     */
    void uploadJournalEntry(Journal journalEntry, User student) ;

    /**
     * persist final portfolio for assessment
     *
     * Maybe create a class for the collected portfolio
     * @param journalEntries
     * @param finalResearchReport
     * @param presentation
     */

    void uploadFinalPortfolio(Project project, List<Journal> journalEntries, ResearchReport finalResearchReport, File
            presentation, User user) ;

    /**
     * Gets EPortfolio for assesment
     * @param project
     * @return EPortfolio (containing Report, ProjectDescription and Journal)
     */

    EPorfolio getFinalPortfolioForAssessment(Project project, User user) ;
}

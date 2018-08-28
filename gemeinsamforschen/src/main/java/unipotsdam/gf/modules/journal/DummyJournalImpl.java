package unipotsdam.gf.modules.journal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.assignments.Assignee;
import unipotsdam.gf.assignments.NotImplementedLogger;
import unipotsdam.gf.interfaces.IJournal;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.journal.model.Journal;
import unipotsdam.gf.modules.researchreport.ResearchReport;

import javax.swing.text.html.HTML;
import java.io.File;
import java.util.List;

public class DummyJournalImpl implements IJournal {


    private Logger log = LoggerFactory.getLogger(DummyJournalImpl.class);

    @Override
    public String exportJournal(StudentIdentifier student)  {
        NotImplementedLogger.logAssignment(Assignee.THOMAS, IJournal.class);
        return null;
    }

    @Override
    public Boolean getPortfoliosForEvaluationPrepared(Project project)  {
        NotImplementedLogger.logAssignment(Assignee.THOMAS, IJournal.class);
        return false;
    }

    @Override
    public void assignMissingPortfolioTasks(Project project) {
        NotImplementedLogger.logAssignment(Assignee.THOMAS, IJournal.class);
    }

    @Override
    public void uploadJournalEntry(Journal journalEntry, User student) {
        NotImplementedLogger.logAssignment(Assignee.THOMAS, IJournal.class);
    }

    @Override
    public void uploadFinalPortfolio(
            Project project, List<Journal> journalEntries, ResearchReport finalResearchReport, File presentation,
            User user)  {
        NotImplementedLogger.logAssignment(Assignee.THOMAS, IJournal.class);
    }

    @Override
    public HTML getFinalPortfolioForAssessment(
            Project project, User user)  {
        NotImplementedLogger.logAssignment(Assignee.THOMAS, IJournal.class);
        return null;
    }
}

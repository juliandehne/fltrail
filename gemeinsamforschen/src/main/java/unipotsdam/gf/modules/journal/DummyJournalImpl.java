package unipotsdam.gf.modules.journal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.interfaces.IJournal;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.journal.service.DummyJournalService;

public class DummyJournalImpl implements IJournal {


    private Logger log = LoggerFactory.getLogger(DummyJournalImpl.class);

    @Override
    public String exportJournal(StudentIdentifier student) {
        // TODO Impl was macht das hier?

        return null;
    }

    @Override
    public Boolean getPortfoliosForEvaluationPrepared(Project project) {

        log.debug("checking fake constraints for the portfolio evaluation");
        return false;
    }

    @Override
    public void assignMissingPortfolioTasks(Project project) {
        log.debug("assigning fake MissingPortfolioTasks");
    }
}

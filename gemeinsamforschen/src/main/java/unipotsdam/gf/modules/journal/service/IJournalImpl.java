package unipotsdam.gf.modules.journal.service;

import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.interfaces.IJournal;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.journal.model.EPorfolio;
import unipotsdam.gf.modules.journal.model.Journal;
import unipotsdam.gf.modules.researchreport.ResearchReport;

import javax.swing.text.html.HTML;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class IJournalImpl implements IJournal {

    JournalService journalService = new JournalServiceImpl();
    ProjectDescriptionService descriptionService = new ProjectDescriptionImpl();

    @Override
    public String exportJournal(StudentIdentifier student) {
        return null;
    }

    @Override
    public Boolean getPortfoliosForEvaluationPrepared(Project project) {
        //Teilnehmer suchen

        //Hat jeder Teilnehmer ein geschlossenenes Portfolio

        //Hat jeder die nötigen Journal

        return null;
    }

    @Override
    public void assignMissingPortfolioTasks(Project project) {
        //Teilnehmer suchen

        //Hat jeder Teilnehmer ein geschlossenenes Portfolio

        //Hat jeder die nötigen Journal

    }

    @Override
    public void uploadJournalEntry(Journal journalEntry, User student) {

    }

    @Override
    public void uploadFinalPortfolio(Project project, List<Journal> journalEntries, ResearchReport finalResearchReport, File presentation, User user) {

    }

    @Override
    public EPorfolio getFinalPortfolioForAssessment(Project project, User user) {

        EPorfolio result = new EPorfolio();
        StudentIdentifier studentIdentifier = new StudentIdentifier(project.getId(),user.getId());

        result.setDescrition(descriptionService.getProjectbyStudent(studentIdentifier));
        result.setJournals(journalService.getAllJournals(user.getId(),project.getId()));
        //TODO result.setReport(...);

        return result;
    }
}

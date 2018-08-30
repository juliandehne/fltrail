package unipotsdam.gf.modules.journal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.interfaces.IJournal;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.journal.model.EPorfolio;
import unipotsdam.gf.modules.journal.model.Journal;
import unipotsdam.gf.modules.researchreport.ResearchReport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class IJournalImpl implements IJournal {

    private Logger log = LoggerFactory.getLogger(IJournalImpl.class);

    JournalService journalService = new JournalServiceImpl();
    ProjectDescriptionService descriptionService = new ProjectDescriptionImpl();


    @Override
    public Boolean getPortfoliosForEvaluationPrepared(Project project) {

        if(!descriptionService.checkIfAllDescriptionsClosed(project) || !journalService.checkIfAllJournalClosed(project)){
            //assignMissingPortfolioTasks()?
            return false;
        }

        //TODO check Constrains (5? Journals...)...

        return true;
    }


    @Override
    public void assignMissingPortfolioTasks(Project project) {

        ArrayList<User> descUser = descriptionService.getOpenUserByProject(project);

        for(User user : descUser){

            log.debug("Send close description message to user {}", user.getId());
            //TODO send message when implemented

        }

        ArrayList<User> journalUser = journalService.getOpenUserByProject(project);

        for(User user : journalUser){

            log.debug("Send close journal message to user {}", user.getId());
            //TODO send message when implemented

        }


    }

    @Override
    public void uploadJournalEntry(Journal journalEntry, User student) {
        //TODO
    }

    @Override
    public void uploadFinalPortfolio(Project project, List<Journal> journalEntries, ResearchReport finalResearchReport, File presentation, User user) {
        //TODO
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

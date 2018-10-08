package unipotsdam.gf.modules.journal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.states.ConstraintsMessages;
import unipotsdam.gf.interfaces.IJournal;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.journal.model.EPortfolio;
import unipotsdam.gf.modules.journal.model.Journal;
import unipotsdam.gf.modules.researchreport.ResearchReport;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IJournalImpl implements IJournal {

    private final Logger log = LoggerFactory.getLogger(IJournalImpl.class);

    private final JournalService journalService = new JournalServiceImpl();
    private final ProjectDescriptionService descriptionService = new ProjectDescriptionImpl();


    @Override
    public Map<StudentIdentifier, ConstraintsMessages> getPortfoliosForEvaluationPrepared(Project project) {
        Map<StudentIdentifier, ConstraintsMessages> result =new HashMap<>();
        result.putAll(descriptionService.checkIfAllDescriptionsClosed(project));
        result.putAll(journalService.checkIfAllJournalClosed(project));
        return result;
    }


    @Override
    public void assignMissingPortfolioTasks(Project project) {

        ArrayList<User> descUser = descriptionService.getOpenUserByProject(project);

        for (User user : descUser) {

            log.debug("Send close description message to user {}", user.getEmail());
            //TODO send message when implemented

        }

        ArrayList<User> journalUser = journalService.getOpenUserByProject(project);

        for (User user : journalUser) {

            log.debug("Send close journal message to user {}", user.getEmail());
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
    public EPortfolio getFinalPortfolioForAssessment(Project project, User user) {

        EPortfolio result = new EPortfolio();
        StudentIdentifier userNameentifier = new StudentIdentifier(project.getName(), user.getEmail());

        result.setDescription(descriptionService.getProjectByStudent(userNameentifier));
        result.setJournals(journalService.getAllJournals(user.getEmail(), project.getName()));
        //TODO result.setReport(...);

        return result;
    }

    @Override
    public EPortfolio getPortfolio(String project, String user) {
        return null;
    }

    @Override
    public byte[] exportPortfolioToPdf(EPortfolio ePortfolio) {
        return new byte[0];
    }


}

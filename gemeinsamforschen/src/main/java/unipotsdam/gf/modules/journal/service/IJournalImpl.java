package unipotsdam.gf.modules.journal.service;

import com.qkyrie.markdown2pdf.Markdown2PdfConverter;
import com.qkyrie.markdown2pdf.internal.exceptions.ConversionException;
import com.qkyrie.markdown2pdf.internal.exceptions.Markdown2PdfLogicException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.states.model.ConstraintsMessages;
import unipotsdam.gf.interfaces.IJournal;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.journal.model.EPortfolio;
import unipotsdam.gf.modules.journal.model.Journal;
import unipotsdam.gf.modules.peer2peerfeedback.Category;
import unipotsdam.gf.modules.researchreport.ResearchReport;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class IJournalImpl implements IJournal {

    private final Logger log = LoggerFactory.getLogger(IJournalImpl.class);

    private final JournalService journalService = new JournalServiceImpl();
    private final ProjectDescriptionService descriptionService = new ProjectDescriptionImpl();

    private final SimpleDateFormat jdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");

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

            log.debug("Send close description message to user {}", user.getId());
            //TODO send message when implemented

        }

        ArrayList<User> journalUser = journalService.getOpenUserByProject(project);

        for (User user : journalUser) {

            log.debug("Send close journal message to user {}", user.getId());
            //TODO send message when implemented

        }


    }

    @Override
    public void uploadJournalEntry(Journal journalEntry, User student) {
        journalService.saveJournal("0",student.getId(),journalEntry.getStudentIdentifier().getProjectId(),journalEntry.getEntryMD(),journalEntry.getVisibility().toString(),journalEntry.getCategory().toString());
    }

    @Override
    public void uploadFinalPortfolio(Project project, List<Journal> journalEntries, ResearchReport finalResearchReport, File presentation, User user) {
        //TODO for version 2
    }

    @Override
    public EPortfolio getFinalPortfolioForAssessment(Project project, User user) {

        return getPortfolio(project.getId(), user.getId());
    }

    @Override
    public EPortfolio getPortfolio(String project, String user) {

        EPortfolio result = new EPortfolio();
        StudentIdentifier studentIdentifier = new StudentIdentifier(project, user);

        result.setDescription(descriptionService.getProjectByStudent(studentIdentifier));
        result.setJournals(journalService.getAllJournals(user, project));
        //TODO result.setReport(...);

        return result;
    }

    @Override
    public byte[] exportPortfolioToPdf(EPortfolio ePortfolio) {

        //IntelliJs solution for lambda statement
        final byte[][] res = new byte[1][1];

        //Build String
        String input = ePortfolioToString(ePortfolio);

        //Convert
        try {
            Markdown2PdfConverter
                    .newConverter()
                    .readFrom(() -> input)
                    .writeTo(out -> {
                        res[0] = out;
                    })
                    .doIt();
        } catch (ConversionException | Markdown2PdfLogicException e) {
            e.printStackTrace();
        }

        return res[0];
    }

    private String ePortfolioToString(EPortfolio ePortfolio) {

        StringBuilder result = new StringBuilder();

        //If Description exists, add to pdf
        if (ePortfolio.getDescription() != null) {
            result.append("#Portfolio# \n\n");
            result.append(ePortfolio.getDescription().getDescriptionMD()).append("\n");
        }

        //If Journals and Report exists combine
        if (ePortfolio.getReport() != null && ePortfolio.getJournals() != null && !ePortfolio.getJournals().isEmpty()) {

            ArrayList<Journal> journals = ePortfolio.getJournals();
            ResearchReport researchReport = ePortfolio.getReport();

            result.append(researchReport.getTitle()).append("\n");
            result.append(journalStringByCategory(journals, Category.TITEL));

            result.append(researchReport.getResearchQuestion()).append("\n");
            result.append(journalStringByCategory(journals, Category.FORSCHUNGSFRAGE));


            for (String s : researchReport.getLearningGoals()) {
                result.append(s).append("\n");
            }

            result.append(researchReport.getMethod()).append("\n");

            result.append(journalStringByCategory(journals, Category.UNTERSUCHUNGSKONZEPT));
            result.append(journalStringByCategory(journals, Category.METHODIK));

            result.append(researchReport.getResearch()).append("\n");
            result.append(journalStringByCategory(journals, Category.RECHERCHE));

            result.append(researchReport.getResearchResult()).append("\n");
            result.append(journalStringByCategory(journals, Category.DURCHFUEHRUNG));


            result.append(researchReport.getEvaluation()).append("\n");
            result.append(journalStringByCategory(journals, Category.AUSWERTUNG));

            //TODO Extract String when implemented
            result.append(researchReport.getBibliography()).append("\n");
            result.append(journalStringByCategory(journals, Category.LITERATURVERZEICHNIS));


        }

        //if Report but no Journals, add Report
        else if (ePortfolio.getReport() != null) {
            ResearchReport report = ePortfolio.getReport();

            result.append(report.getTitle()).append("\n");

            result.append(report.getResearchQuestion()).append("\n");

            for (String s : report.getLearningGoals()) {
                result.append(s).append("\n");
            }

            result.append(report.getMethod()).append("\n");

            result.append(report.getResearch()).append("\n");

            result.append(report.getResearchResult()).append("\n");

            result.append(report.getEvaluation()).append("\n");

            //TODO Extract String when implemented
            result.append(report.getBibliography()).append("\n");

        }

        //If Journal but no Report
        else if (ePortfolio.getJournals() != null) {
            result.append("##Lerntagebuch## \n\n");

            for (Journal j : ePortfolio.getJournals()) {
                result.append(journalAsString(j));
            }
        }

        return result.toString();
    }

    private String journalStringByCategory(ArrayList<Journal> journals, Category c) {
        StringBuilder str = new StringBuilder();
        for (Journal j : journals) {
            if (j.getCategory().equals(c)) {
                str.append(journalAsString(j));
            }
        }
        return str.toString();
    }

    private String journalAsString(Journal j) {
        return jdf.format(new Date(j.getTimestamp())) + " " + j.getCategory() + "\n" + j.getEntryMD() + "\n";
    }
}

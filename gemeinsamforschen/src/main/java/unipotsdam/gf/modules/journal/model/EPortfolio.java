package unipotsdam.gf.modules.journal.model;

import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.researchreport.ResearchReport;

import java.util.ArrayList;

/**
 * Class for passing e-portfolio to assessment
 */

public class EPortfolio {

    private StudentIdentifier student;
    private ProjectDescription description;
    private ArrayList<Journal> journals;
    private ResearchReport report;



    public EPortfolio() {
    }

    public EPortfolio(StudentIdentifier student, ProjectDescription description, ArrayList<Journal> journals, ResearchReport report) {
        this.student = student;
        this.description = description;
        this.journals = journals;
        this.report = report;
    }

    public StudentIdentifier getStudent() {
        return student;
    }

    public void setStudent(StudentIdentifier student) {
        this.student = student;
    }

    public ProjectDescription getDescription() {
        return description;
    }

    public void setDescription(ProjectDescription description) {
        this.description = description;
    }

    public ArrayList<Journal> getJournals() {
        return journals;
    }

    public void setJournals(ArrayList<Journal> journals) {
        this.journals = journals;
    }

    public ResearchReport getReport() {
        return report;
    }

    public void setReport(ResearchReport report) {
        this.report = report;
    }

}

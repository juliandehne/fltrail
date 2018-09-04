package unipotsdam.gf.modules.journal.model;

import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.researchreport.ResearchReport;

import java.util.ArrayList;

/**
 * Class for passing e-portfolio to assessment
 */

public class EPorfolio {

    StudentIdentifier student;
    ProjectDescription descrition;
    ArrayList<Journal> journals;
    ResearchReport report;
    //TODO mehr?


    public EPorfolio() {
    }

    public EPorfolio(StudentIdentifier student, ProjectDescription descrition, ArrayList<Journal> journals, ResearchReport report) {
        this.student = student;
        this.descrition = descrition;
        this.journals = journals;
        this.report = report;
    }

    public StudentIdentifier getStudent() {
        return student;
    }

    public void setStudent(StudentIdentifier student) {
        this.student = student;
    }

    public ProjectDescription getDescrition() {
        return descrition;
    }

    public void setDescrition(ProjectDescription descrition) {
        this.descrition = descrition;
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

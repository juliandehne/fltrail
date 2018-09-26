package unipotsdam.gf.modules.assessment.controller.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import unipotsdam.gf.core.database.mysql.MysqlConnect;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;

@XmlRootElement

public class Assessment {
    private StudentIdentifier student;// gemeint als Ziel der Bewertung, kann auch gruppe sein
    @JsonIgnore
    private Performance performance;

    private StudentIdentifier bewertender;
    private String projektId;
    private int bewertung;
    private boolean adressat;
    private Date deadline;

    public Assessment(StudentIdentifier student, Performance performance) {
        this.student = student;
        this.performance = performance;
    }

    public Assessment(boolean adressat, StudentIdentifier student, Date deadline, StudentIdentifier bewertender, String projektId, int bewertung) {
        this.student = student;
        this.deadline = deadline;
        this.bewertender = bewertender;
        this.projektId = projektId;
        this.bewertung = bewertung;
        this.adressat = adressat;
    }

    public Assessment() {
    }

    public ArrayList<Performance> getTotalAssessment() {
        return null;
    }

    public StudentIdentifier getStudent() {
        return student;
    }

    public StudentIdentifier getBewertender() {
        return bewertender;
    }

    public void setBewertender(StudentIdentifier bewertender) {
        this.bewertender = bewertender;
    }

    public void setStudent(StudentIdentifier student) {
        this.student = student;
    }

    @JsonIgnore
    public Performance getPerformance() {
        return performance;
    }

    @JsonIgnore
    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

    @Override
    public String toString() {
        return "Assessment{" +
                "student=" + student +
                ", performance=" + performance +
                '}';
    }

    public int getBewertung() {
        return bewertung;
    }

    public void setBewertung(int bewertung) {
        this.bewertung = bewertung;
    }

    public String getProjektId() {
        return projektId;
    }

    public void setProjektId(String projektId) {
        this.projektId = projektId;
    }


    public void setAssessment(Assessment assessment) {
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "INSERT INTO assessments ( `adressat`, `deadline`, `erstellerId`,`empfaengerId`, `projektId`, `bewertung`) values (?,?,?,?,?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest,
                assessment.isAdressat(),
                assessment.getDeadline(),
                assessment.getBewertender().getStudentId(),
                assessment.getStudent().getStudentId(),
                assessment.getProjektId(),
                assessment.getBewertung()
        );
        connect.close();
    }

    public boolean isAdressat() {
        return adressat;
    }

    public void setAdressat(boolean adressat) {
        this.adressat = adressat;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
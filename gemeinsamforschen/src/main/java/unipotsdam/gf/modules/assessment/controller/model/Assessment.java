package unipotsdam.gf.modules.assessment.controller.model;

import unipotsdam.gf.core.management.user.User;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement

public class Assessment {
    private StudentIdentifier student;
    private Performance performance;

    public Assessment(StudentIdentifier student, Performance performance) {
        this.student = student;
        this.performance = performance;
    }

    public Assessment(){}

    public ArrayList<Performance> getTotalAssessment() { return null; }

    public StudentIdentifier getStudent() {
        return student;
    }

    public void setStudent(StudentIdentifier student) {
        this.student = student;
    }

    public Performance getPerformance() {
        return performance;
    }

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

    public void setAssessment(User tim, Assessment assessment) {


    }
}

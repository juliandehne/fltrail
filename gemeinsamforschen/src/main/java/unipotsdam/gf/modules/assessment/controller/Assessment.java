package unipotsdam.gf.modules.assessment.controller;
package unipotsdam.gf.modules.assessment.controller.model;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.User;

import javax.xml.bind.annotation.XmlRootElement;
import java.security.acl.Group;
import java.util.ArrayList;

@XmlRootElement

public class Assessment {
    private StudentIdentifier student;
    private Performance performance;
    private StudentIdentifier bewertender;

    public Assessment(StudentIdentifier student, Performance performance) {
        this.student = student;
        this.performance = performance;
    }

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

    public void setAssessment(User user, Assessment assessment){
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "INSERT INTO assessments ( `BewertenderId`, `BewerteterId`, `Bewertung`,`StuoGrp`) values (?,?,?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, "tom" ,user.getName() , assessment.getPerformance().getWorkRating()[0], user.istStudent());
        connect.close();
    }
    @Override
    public String toString() {
        return "Assessment{" +
                "student=" + student +
                ", performance=" + performance +
                '}';
    }
}

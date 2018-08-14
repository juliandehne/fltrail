package unipotsdam.gf.modules.assessment.controller.service;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.modules.assessment.controller.model.*;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean
@Resource
@Singleton
class AssessmentDBCommunication {
    ArrayList<Map<String, Double>> getWorkRating(StudentIdentifier student) {
        ArrayList<Map<String, Double>> result = new ArrayList<>();
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "SELECT * FROM `workrating` WHERE `projectId`=? AND `studentId`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, student.getProjectId(), student.getStudentId());
        boolean next = vereinfachtesResultSet.next();
        while (next) {
            Map workRating = new HashMap();
            for (String category: Categories.workRatingCategories){
                workRating.put(category, (double) vereinfachtesResultSet.getInt(category));
            }
            result.add(workRating);
            next = vereinfachtesResultSet.next();
        }
        return result;
    }

    List<String> getStudents(String projectID){
        List<String> result = new ArrayList<>();
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "SELECT * FROM `projectuser` WHERE `projectId`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, projectID);
        boolean next = vereinfachtesResultSet.next();
        while (next) {
            result.add(vereinfachtesResultSet.getString("userID"));
            next = vereinfachtesResultSet.next();
        }
        return result;
    }

    ArrayList<Map<String, Double>> getContributionRating(StudentIdentifier student) {
        ArrayList<Map<String, Double>> result = new ArrayList<>();
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "SELECT * FROM `contributionrating` WHERE `projectId`=? AND `studentId`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, student.getProjectId(), student.getStudentId());
        boolean next = vereinfachtesResultSet.next();
        while (next) {
            Map<String, Double> contributionRating = new HashMap<>();
            for (String category: Categories.contributionRatingCategories){
                contributionRating.put(category, (double) vereinfachtesResultSet.getInt(category));
            }
            result.add(contributionRating);
            next = vereinfachtesResultSet.next();
        }
        return result;
    }

    ArrayList<Integer> getAnsweredQuizzes(StudentIdentifier student) {
        ArrayList<Integer> result = new ArrayList<>();
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "SELECT * FROM `answeredquiz` WHERE `projectId`=? AND `studentId`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, student.getProjectId(), student.getStudentId());
        boolean next = vereinfachtesResultSet.next();
        while (next) {
            result.add(vereinfachtesResultSet.getInt("correct"));
            next = vereinfachtesResultSet.next();
        }
        return result;
    }
}

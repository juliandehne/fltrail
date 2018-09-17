package unipotsdam.gf.modules.groupfinding.service;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;

import java.util.ArrayList;

public class GroupDAO {

    public ArrayList<String> getStudentsInSameGroupAs(StudentIdentifier student) {
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        ArrayList<String> result = new ArrayList<>();
        Integer groupId;
        String mysqlRequest1 = "SELECT groupId FROM `groupuser` WHERE `projectId`=? AND `studentId`=?";
        VereinfachtesResultSet vereinfachtesResultSet1 =
                connect.issueSelectStatement(mysqlRequest1, student.getProjectId(), student.getStudentId());
        vereinfachtesResultSet1.next();
        groupId = vereinfachtesResultSet1.getInt("groupId");
        String mysqlRequest2 = "SELECT * FROM `groupuser` WHERE `groupId`=?";
        VereinfachtesResultSet vereinfachtesResultSet2 =
                connect.issueSelectStatement(mysqlRequest2, groupId);
        boolean next2 = vereinfachtesResultSet2.next();
        while (next2) {
            String peer = vereinfachtesResultSet2.getString("studentId");
            if (!peer.equals(student.getStudentId()))
                result.add(peer);
            next2 = vereinfachtesResultSet2.next();
        }
        connect.close();
        return result;
    }
}

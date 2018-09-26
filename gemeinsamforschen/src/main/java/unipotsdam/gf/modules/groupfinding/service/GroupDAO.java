package unipotsdam.gf.modules.groupfinding.service;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.core.management.group.Group;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.util.ResultSetUtil;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;

@ManagedBean
@Resource
@Singleton
public class GroupDAO {

    private MysqlConnect connect;

    @Inject
    public GroupDAO(MysqlConnect connect) {
        this.connect = connect;
    }

    public ArrayList<String> getStudentsInSameGroupAs(StudentIdentifier student) {
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

    public void persist(Group group) {
        connect.connect();
        List<Group> existingGroups = getExistingEntriesOfGroups(group.getProjectId());

        String mysqlRequestGroup = "INSERT INTO groups (`projectId`,`chatRoomId`) values (?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequestGroup, group.getProjectId(), group.getChatRoomId());

        List<Group> existingGroupsWithNewEntry = getExistingEntriesOfGroups(group.getProjectId());
        existingGroupsWithNewEntry.removeAll(existingGroups);
        group.setId(existingGroupsWithNewEntry.get(0).getId());
        for (User groupMember : group.getMembers()) {
            String mysqlRequest2 = "INSERT INTO groupuser (`studentId`, `projectId`, `groupId`) values (?,?,?)";
            connect.issueInsertOrDeleteStatement(mysqlRequest2, groupMember.getEmail(), group.getProjectId(), group.getId());
        }
        connect.close();

    }

    public void update(Group group) {
        connect.connect();
        String mysqlRequest = "UPDATE groups SET projectId = ?, chatRoomId = ? where id = ?";
        connect.issueUpdateStatement(mysqlRequest, group.getProjectId(), group.getChatRoomId(), group.getId());
        connect.close();
        // TODO: implement update of groupuser if needed later (if member list need to be updated)
    }


    public Boolean exists(Group group) {
        List<Group> existingGroups = getGroupsByProjectId(group.getProjectId());
        return existingGroups.contains(group);
    }

    public void addGroupMember(User groupMember, int groupId) {
        // TODO: implement
    }

    public void deleteGroupMember(User groupMember, int groupId) {
        // TODO: implement
    }

    public List<Group> getGroupsByProjectId(String projectId) {
        connect.connect();
        String mysqlRequest = "SELECT * FROM groups g " +
                "JOIN groupuser gu ON g.id=gu.groupId " + "JOIN users u ON gu.studentId=u.email " +
                "where g.projectId = ?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, projectId);
        if (Objects.isNull(vereinfachtesResultSet)) {
            connect.close();
            return Collections.emptyList();
        }
        HashMap<Integer, Group> groupHashMap = new HashMap<>();
        while (vereinfachtesResultSet.next()) {
            fillGroupFromResultSet(vereinfachtesResultSet, groupHashMap);
        }
        ArrayList<Group> groups = new ArrayList<>();
        groupHashMap.forEach((key, group) -> groups.add(group));
        connect.close();
        return groups;
    }

    private void fillGroupFromResultSet(VereinfachtesResultSet vereinfachtesResultSet, HashMap<Integer, Group> existingGroups) {
        int id = vereinfachtesResultSet.getInt("id");
        if (existingGroups.containsKey(id)) {
            existingGroups.get(id).addMember(ResultSetUtil.getUserFromResultSet(vereinfachtesResultSet));
        } else {
            String projectId = vereinfachtesResultSet.getString("projectId");
            User user = ResultSetUtil.getUserFromResultSet(vereinfachtesResultSet);
            String chatRoomId = vereinfachtesResultSet.getString("chatRoomId");
            ArrayList<User> userList = new ArrayList<>(Collections.singletonList(user));
            Group group = new Group(id, userList, projectId, chatRoomId);
            existingGroups.put(id, group);
        }
    }

    private List<Group> getExistingEntriesOfGroups(String projectId) {
        String mysqlExistingGroup = "SELECT * FROM groups WHERE projectId = ?";
        VereinfachtesResultSet resultSet = connect.issueSelectStatement(mysqlExistingGroup, projectId);
        ArrayList<Group> existingGroups = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            Group existingGroup = new Group(id, projectId);
            existingGroups.add(existingGroup);
        }
        return existingGroups;
    }

    public int getGroupIdByStudentIdentifier(StudentIdentifier studentIdentifier) {
        String mysqlQuery = "SELECT id FROM groups where projectId=? AND studentId=?";
        VereinfachtesResultSet resultSet = connect.issueSelectStatement(mysqlQuery, studentIdentifier.getProjectId()
                , studentIdentifier.getStudentId());
        int id;
        if (resultSet.next()) {
            id = resultSet.getInt("id");
        } else {
            id = 0;
        }
        return id;
    }

}

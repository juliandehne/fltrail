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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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
        String mysqlRequest1 = "SELECT groupId FROM `groupuser` WHERE `projectName`=? AND `userEmail`=?";
        VereinfachtesResultSet vereinfachtesResultSet1 =
                connect.issueSelectStatement(mysqlRequest1, student.getProjectName(), student.getUserEmail());
        vereinfachtesResultSet1.next();
        groupId = vereinfachtesResultSet1.getInt("groupId");
        String mysqlRequest2 = "SELECT * FROM `groupuser` WHERE `groupId`=?";
        VereinfachtesResultSet vereinfachtesResultSet2 =
                connect.issueSelectStatement(mysqlRequest2, groupId);
        boolean next2 = vereinfachtesResultSet2.next();
        while (next2) {
            String peer = vereinfachtesResultSet2.getString("userName");
            if (!peer.equals(student.getUserEmail()))
                result.add(peer);
            next2 = vereinfachtesResultSet2.next();
        }
        connect.close();
        return result;
    }


    // refactor (you get the id as a return value when inserting into the db)
    public void persist(Group group) {
        connect.connect();
        List<Group> existingGroups = getExistingEntriesOfGroups(group.getProjectName());

        String mysqlRequestGroup = "INSERT INTO groups (`projectName`,`chatRoomId`) values (?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequestGroup, group.getProjectName(), group.getChatRoomId());

        List<Group> existingGroupsWithNewEntry = getExistingEntriesOfGroups(group.getProjectName());
        existingGroupsWithNewEntry.removeAll(existingGroups);
        group.setId(existingGroupsWithNewEntry.get(0).getId());
        for (User groupMember : group.getMembers()) {
            String mysqlRequest2 = "INSERT INTO groupuser (`userEmail`, `groupId`) values (?,?)";
            connect.issueInsertOrDeleteStatement(mysqlRequest2, groupMember.getEmail(), group.getId());
        }
        connect.close();

    }

    public void update(Group group) {
        connect.connect();
        String mysqlRequest = "UPDATE groups SET projectName = ?, chatRoomId = ? where id = ?";
        connect.issueUpdateStatement(mysqlRequest, group.getProjectName(), group.getChatRoomId(), group.getId());
        connect.close();
        // TODO: implement update of groupuser if needed later (if member list need to be updated)
    }


    public Boolean exists(Group group) {
        List<Group> existingGroups = getGroupsByProjectName(group.getProjectName());
        return existingGroups.contains(group);
    }

    public void addGroupMember(User groupMember, int groupId) {
        // TODO: implement
    }

    public void deleteGroupMember(User groupMember, int groupId) {
        // TODO: implement
    }

    public List<Group> getGroupsByProjectName(String projectName) {
        connect.connect();
        String mysqlRequest = "SELECT * FROM groups g " +
                "JOIN groupuser gu ON g.id=gu.groupId " +
                "JOIN users u ON gu.userEmail=u.email " +
                "where g.projectName = ?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, projectName);
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
            String projectName = vereinfachtesResultSet.getString("projectName");
            User user = ResultSetUtil.getUserFromResultSet(vereinfachtesResultSet);
            String chatRoomId = vereinfachtesResultSet.getString("chatRoomId");
            ArrayList<User> userList = new ArrayList<>(Collections.singletonList(user));
            Group group = new Group(id, userList, projectName, chatRoomId);
            existingGroups.put(id, group);
        }
    }

    private List<Group> getExistingEntriesOfGroups(String projectName) {
        String mysqlExistingGroup = "SELECT * FROM groups WHERE projectName = ?";
        VereinfachtesResultSet resultSet = connect.issueSelectStatement(mysqlExistingGroup, projectName);
        ArrayList<Group> existingGroups = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            Group existingGroup = new Group(id, projectName);
            existingGroups.add(existingGroup);
        }
        return existingGroups;
    }

}

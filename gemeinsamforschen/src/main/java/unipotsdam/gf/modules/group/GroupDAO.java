package unipotsdam.gf.modules.group;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.util.ResultSetUtil;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@ManagedBean
@Resource
@Singleton
public class GroupDAO {

    @Inject
    private MysqlConnect connect;

    @Inject
    public GroupDAO(MysqlConnect connect) {
        this.connect = connect;
    }

    public ArrayList<String> getStudentsInSameGroupAs(StudentIdentifier student) {
        connect.connect();
        ArrayList<String> result = new ArrayList<>();
        int groupId= getGroupByStudent(student);
        String mysqlRequest = "SELECT * FROM `groupuser` WHERE `groupId`=?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, groupId);
        boolean next2 = vereinfachtesResultSet.next();
        while (next2) {
            String peer = vereinfachtesResultSet.getString("userEmail");
            if (!peer.equals(student.getUserEmail()))
                result.add(peer);
            next2 = vereinfachtesResultSet.next();
        }
        connect.close();
        return result;
    }

    public Integer getGroupByStudent(StudentIdentifier student) {
        Integer result;
        connect.connect();
        String mysqlRequest = "SELECT groupId FROM `groupuser` gu JOIN groups g WHERE g.`projectName`=? AND gu.groupid=g.id AND gu.userEmail=? ";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, student.getProjectName(), student.getUserEmail());
        vereinfachtesResultSet.next();
        result = vereinfachtesResultSet.getInt("groupId");
        return result;
    }

    // refactor (you get the id as a return value when inserting into the db)
    public void persist(Group group) {
        assert group.getProjectName() != null;

        connect.connect();

        String mysqlRequestGroup = "INSERT INTO groups (`projectName`,`chatRoomId`) values (?,?)";
        int groupId = connect.issueInsertStatementWithAutoincrement(mysqlRequestGroup, group.getProjectName(),
                group.getChatRoomId());

        group.setId(groupId);
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
        // TODO: implement updateForUser of groupuser if needed later (if member list need to be updated)
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
        String mysqlRequest =
                "SELECT * FROM groups g " + "JOIN groupuser gu ON g.id=gu.groupId " + "JOIN users u ON gu" + ".userEmail=u.email " + "where g.projectName = ?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, projectName);
        if (Objects.isNull(vereinfachtesResultSet)) {
            connect.close();
            return Collections.emptyList();
        }

        ArrayList<Group> groups = new ArrayList<>();
        fillGroupFromResultSet(groups, vereinfachtesResultSet);
        ArrayList<Group> uniqueGroups = new ArrayList<>();
        for (Group group : groups) {
            // transmuting the table to a map with group as key and members as value
            if (uniqueGroups.contains(group)) {
                Group toComplete = uniqueGroups.get(uniqueGroups.indexOf(group));
                toComplete.addMember(group.getMembers().iterator().next());
                uniqueGroups.remove(group);
                uniqueGroups.add(toComplete);
            } else {
                uniqueGroups.add(group);
            }
        }
        // the groups now contain their members, too
        connect.close();
        return uniqueGroups;
    }

    private void fillGroupFromResultSet(ArrayList<Group> groups, VereinfachtesResultSet vereinfachtesResultSet) {
        Boolean next = vereinfachtesResultSet.next();
        while (next) {
            String projectName = vereinfachtesResultSet.getString("projectName");
            User user = ResultSetUtil.getUserFromResultSet(vereinfachtesResultSet);
            String chatRoomId = vereinfachtesResultSet.getString("chatRoomId");
            ArrayList<User> userList = new ArrayList<>(Collections.singletonList(user));
            Group group = new Group(vereinfachtesResultSet.getInt("groupId"), userList, projectName, chatRoomId);
            groups.add(group);
            next = vereinfachtesResultSet.next();
        }
    }

    public GroupFormationMechanism getGroupFormationMechanism(Project project) {
        GroupFormationMechanism groupFormationMechanism = null;
        connect.connect();
        String query = "Select gfmSelected from groupfindingmechanismselected where projectName = ?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query, project.getName());
        vereinfachtesResultSet.next();
        String gfmSelected = vereinfachtesResultSet.getString("gfmSelected");
        groupFormationMechanism = GroupFormationMechanism.valueOf(gfmSelected);
        connect.close();
        return groupFormationMechanism;
    }
}





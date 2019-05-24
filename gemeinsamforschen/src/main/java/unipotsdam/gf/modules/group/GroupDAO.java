package unipotsdam.gf.modules.group;

import org.apache.logging.log4j.util.Strings;
import unipotsdam.gf.modules.group.preferences.survey.GroupWorkContext;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;

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
    private UserDAO userDAO;

    @Inject
    public GroupDAO(MysqlConnect connect) {
        this.connect = connect;
    }

    public ArrayList<String> getStudentsInSameGroupAs(Project project, User user) {
        connect.connect();
        ArrayList<String> result = new ArrayList<>();
        int groupId= getGroupByStudent(project, user);
        String mysqlRequest = "SELECT * FROM `groupuser` WHERE `groupId`=?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, groupId);
        boolean next2 = vereinfachtesResultSet.next();
        while (next2) {
            String peer = vereinfachtesResultSet.getString("userEmail");
            if (!peer.equals(user.getEmail()))
                result.add(peer);
            next2 = vereinfachtesResultSet.next();
        }
        connect.close();
        return result;
    }

    public Integer getGroupByStudent(Project project, User user) {
        Integer result;
        connect.connect();
        String mysqlRequest = "SELECT groupId FROM `groupuser` gu JOIN groups g WHERE g.`projectName`=? AND gu.groupid=g.id AND gu.userEmail=? ";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, project.getName(), user.getEmail());
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

    public void persistOriginalGroup(Group group, int groupId, GroupFormationMechanism groupFormationMechanism){
        assert group.getProjectName() != null;
        connect.connect();
        for (User groupMember : group.getMembers()) {
            String mysqlRequest2 = "INSERT INTO originalgroups (`userEmail`, `projectName`, `groupId`, " +
                    "`groupFormationMechanism`) values (?,?,?,?)";
            connect.issueInsertOrDeleteStatement(mysqlRequest2, groupMember.getEmail(), group.getProjectName(),
                    groupId, groupFormationMechanism);
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
        String mysqlRequest = "SELECT * FROM groups g " +
                "JOIN groupuser gu ON g.id=gu.groupId " +
                "JOIN users u ON " +
                "gu.userEmail=u.email " +
                "where g.projectName = ?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, projectName);
        List<Group> uniqueGroups = resultSetToGroupList(vereinfachtesResultSet, true);
        connect.close();
        return uniqueGroups;
    }

    public List<Group> getOriginalGroupsByProjectName(String projectName) {
        connect.connect();
        String mysqlRequest = "SELECT * FROM originalgroups where projectName = ?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, projectName);
        List<Group> uniqueGroups = resultSetToGroupList(vereinfachtesResultSet, false);
        connect.close();
        return uniqueGroups;
    }

    public List<Group> getGroupsByContextUser(User user, GroupWorkContext context){
        connect.connect();
        String mysqlRequest = "SELECT p.name as projectName, gu.userEmail, gu.groupId, g.chatroomId  " +
                "FROM `projects` p JOIN " +
                "projectuser pu on pu.projectName=p.name and p.context=? and pu.userEmail=? JOIN " +
                "groups g on pu.projectName=g.projectName JOIN " +
                "groupuser gu on g.id=gu.groupId ";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, context.toString(), user.getEmail());
        List<Group> uniqueGroups = resultSetToGroupList(vereinfachtesResultSet, true);
        connect.close();
        return uniqueGroups;
    }

    public Integer getMyGroupId(User user, Project project) {
        connect.connect();
        String mysqlRequest = "SELECT * FROM `groupuser` gu JOIN groups g on gu.groupId = g.id " +
                "AND g.projectName= ? WHERE userEmail=?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest,
                project.getName(), user.getEmail());
        Integer groupId = null;
        if (vereinfachtesResultSet.next()) {
            groupId = vereinfachtesResultSet.getInt("groupId");
        }
        connect.close();
        return groupId;
    }


    private void fillGroupFromResultSet(ArrayList<Group> groups, VereinfachtesResultSet vereinfachtesResultSet,
                                        Boolean withRocketChatId) {
        boolean next = vereinfachtesResultSet.next();
        while (next) {
            String projectName = vereinfachtesResultSet.getString("projectName");
            User user = userDAO.getUserByEmail(vereinfachtesResultSet.getString("userEmail"));
            String chatRoomId = null;
            if(withRocketChatId)
                chatRoomId = vereinfachtesResultSet.getString("chatRoomId");
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

    public String getGroupChatRoomId(User user, Project project) {
        connect.connect();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT g.chatRoomId FROM groups g ");
        stringBuilder.append("join groupuser gu on g.id=gu.groupId ");
        stringBuilder.append("where g.projectName=? and gu.userEmail=?");

        VereinfachtesResultSet resultSet = connect.issueSelectStatement(stringBuilder.toString(), project.getName(),
                user.getEmail());
        if (Objects.isNull(resultSet)) {
            connect.close();
            return Strings.EMPTY;
        }
        String chatRoomId = Strings.EMPTY;
        if (resultSet.next()) {
            chatRoomId = resultSet.getString("chatRoomId");
        }
        connect.close();
        return chatRoomId;
    }

    public void clearChatRoomIdOfGroup(String chatRoomId) {
        connect.connect();
        String mysqlRequest = "updateRocketChatUserName groups SET chatRoomId = ? where chatRoomId = ?";
        connect.issueUpdateStatement(mysqlRequest, "", chatRoomId);
        connect.close();
    }

    public void deleteGroups(Project project) {
        String query ="DELETE gu FROM groupuser gu INNER JOIN groups g ON gu.groupId=g.id WHERE g.projectName = ?;";
        String query2="DELETE FROM groups WHERE projectName=?;";
        connect.connect();
        connect.issueInsertOrDeleteStatement(query, project.getName());
        connect.issueInsertOrDeleteStatement(query2, project.getName());
        connect.close();
    }

    private List<Group> resultSetToGroupList(VereinfachtesResultSet vereinfachtesResultSet, Boolean withRocketChatId){
        if (Objects.isNull(vereinfachtesResultSet)) {
            connect.close();
            return Collections.emptyList();
        }

        ArrayList<Group> groups = new ArrayList<>();
        fillGroupFromResultSet(groups, vereinfachtesResultSet, withRocketChatId);
        ArrayList<Group> uniqueGroups = new ArrayList<>();
        ArrayList<Integer> groupIds = new ArrayList<>();
        for (Group group : groups) {
            // transmuting the table to a map with group as key and members as value
            if (groupIds.contains(group.getId())) {
                Group toComplete = uniqueGroups.get(groupIds.indexOf(group.getId()));
                toComplete.addMember(group.getMembers().iterator().next());
            } else {
                groupIds.add(group.getId());
                uniqueGroups.add(group);
            }
        }
        return uniqueGroups;
    }
}





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

public class GroupDAO {

    @Inject
    private MysqlConnect connect;

    @Inject
    private UserDAO userDAO;

    @Inject
    public GroupDAO(MysqlConnect connect) {
        this.connect = connect;
    }

    GroupData getGroup(Integer groupId) {
        String groupName = "";
        String projectName = "";
        connect.connect();
        String mysqlRequest = "SELECT gu.userEmail, g.name, g.projectName FROM groupuser gu JOIN groups g ON g.id = gu.groupId " +
                "WHERE gu.groupId = ?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(
                mysqlRequest,
                groupId
        );
        List<String> peers = new ArrayList<>();
        while (vereinfachtesResultSet.next()) {
            String peer = vereinfachtesResultSet.getString("userEmail");
            peers.add(peer);
            groupName = vereinfachtesResultSet.getString("name");
            projectName = vereinfachtesResultSet.getString("projectName");
        }
        connect.close();
        List<User> users = new ArrayList<>();
        for (String peer : peers) {
            users.add(userDAO.getUserByEmail(peer));
        }
        List<Group> groups = new ArrayList<>();
        Group group = new Group(users, projectName);
        group.setName(groupName);
        groups.add(group);
        return new GroupData(groups);
    }

    public Integer getGroupByStudent(Project project, User user) {
        Integer result;
        connect.connect();
        String mysqlRequest = "SELECT groupId FROM `groupuser` gu JOIN groups g WHERE g.`projectName`=? AND gu.groupid=g.id AND gu.userEmail=? ";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, project.getName(), user.getEmail());
        vereinfachtesResultSet.next();
        result = vereinfachtesResultSet.getInt("groupId");
        connect.close();
        return result;
    }

    // refactor (you get the id as a return value when inserting into the db)
    public void persist(Group group) {
        assert group.getProjectName() != null;
        connect.connect();

        String mysqlRequestGroup = "INSERT INTO groups (`projectName`,`chatRoomId`, `name`) values (?,?,?)";
        int groupId = connect.issueInsertStatementWithAutoincrement(mysqlRequestGroup, group.getProjectName(),
                group.getChatRoomId(), group.getName());

        group.setId(groupId);
        for (User groupMember : group.getMembers()) {
            String mysqlRequest2 = "INSERT INTO groupuser (`userEmail`, `groupId`) values (?,?)";
            connect.issueInsertOrDeleteStatement(mysqlRequest2, groupMember.getEmail(), group.getId());
        }
        connect.close();
    }

    void persistOriginalGroup(Group group, int groupId, GroupFormationMechanism groupFormationMechanism) {
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

    public List<Group> getGroupsByProjectName(String projectName) {
        connect.connect();
        String mysqlRequest = "SELECT u.name as userName,  u.email as userEmail, u.discordid, " +
                "g.name, gu.groupId, g.chatRoomId, g.projectName, g.chatRoomId FROM groups g " +
                "JOIN groupuser gu ON g.id=gu.groupId " +
                "JOIN users u ON " +
                "gu.userEmail=u.email " +
                "where g.projectName = ?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, projectName);
        /*vereinfachtesResultSet.next();
        Object projectName1 = vereinfachtesResultSet.getInt(0);*/
        List<Group> uniqueGroups = resultSetToGroupList(vereinfachtesResultSet, true);
        connect.close();
        return uniqueGroups;
    }

    public Group getGroupByGroupId(Integer groupId) {
        connect.connect();
        String mysqlRequest = "SELECT * FROM groups g " +
                "JOIN groupuser gu ON g.id=gu.groupId " +
                "JOIN users u ON " +
                "gu.userEmail=u.email " +
                "where g.id= ?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, groupId);
        List<User> members = new ArrayList<>();
        String projectName = "";
        String rocketChatId = "";
        String groupName = "";
        while (vereinfachtesResultSet.next()) {
            members.add(userDAO.getUserByEmail(vereinfachtesResultSet.getString("userEmail")));
            projectName = vereinfachtesResultSet.getString("projectName");
            rocketChatId = vereinfachtesResultSet.getString("chatRoomId");
            groupName = vereinfachtesResultSet.getString("name");
        }
        Group group = new Group(groupId, members, projectName, rocketChatId);
        group.setName(groupName);
        connect.close();
        return group;
    }

    List<Group> getOriginalGroupsByProjectName(String projectName) {
        connect.connect();
        String mysqlRequest = "SELECT u.name as userName, u.email as userEmail, u.discordid, " +
                "g.name, gu.groupId, g.chatRoomId, g.projectName " +
                "FROM originalgroups g JOIN groupuser gu on gu.groupId=g.groupId " +
                "JOIN users u ON u.email=g.userEmail where projectName = ?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, projectName);
        List<Group> uniqueGroups = resultSetToGroupList(vereinfachtesResultSet, false);
        connect.close();
        return uniqueGroups;
    }

    List<Group> getGroupsByContextUser(User user, GroupWorkContext context) {
        connect.connect();
        String mysqlRequest = "SELECT u.name as userName,  u.email as userEmail, u.discordid, " +
                "g.name, gu.groupId, g.chatRoomId, g.projectName, g.chatRoomId FROM groups g  " +
                "FROM `projects` p JOIN " +
                "projectuser pu on pu.projectName=p.name and p.context=? and pu.userEmail=? JOIN " +
                "groups g on pu.projectName=g.projectName JOIN " +
                "groupuser gu on g.id=gu.groupId JOIN users u on gu.userEmail=u.email";
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

    public Group getMyGroup(User user, Project project) {
        int groupId = getMyGroupId(user, project);
        return getGroupByGroupId(groupId);
    }

    public GroupFormationMechanism getGroupFormationMechanism(Project project) {
        GroupFormationMechanism groupFormationMechanism;
        connect.connect();
        String query = "Select gfmSelected from groupfindingmechanismselected where projectName = ?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query, project.getName());
        vereinfachtesResultSet.next();
        String gfmSelected = vereinfachtesResultSet.getString("gfmSelected");
        groupFormationMechanism = GroupFormationMechanism.valueOf(gfmSelected);
        connect.close();
        return groupFormationMechanism;
    }

    public String getGroupChatRoomId(User user, String projectName) {
        connect.connect();

        String stringBuilder = "SELECT g.chatRoomId FROM groups g " +
                "join groupuser gu on g.id=gu.groupId " +
                "where g.projectName=? and gu.userEmail=?";
        VereinfachtesResultSet resultSet = connect.issueSelectStatement(stringBuilder, projectName,
                user.getEmail());
        if (Objects.isNull(resultSet)) {
            connect.close();
            return Strings.EMPTY;
        }
        String chatRoomId = Strings.EMPTY;
        if (resultSet.next()) {
            String result = resultSet.getString("chatRoomId");
            if (result != null) {
                chatRoomId = result;
            }
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

    void deleteGroups(Project project) {
        String query = "DELETE gu FROM groupuser gu INNER JOIN groups g ON gu.groupId=g.id WHERE g.projectName = ?;";
        String query2 = "DELETE FROM groups WHERE projectName=?;";
        connect.connect();
        connect.issueInsertOrDeleteStatement(query, project.getName());
        connect.issueInsertOrDeleteStatement(query2, project.getName());
        connect.close();
    }

    private List<Group> resultSetToGroupList(VereinfachtesResultSet vereinfachtesResultSet, Boolean withRocketChatId) {
        if (Objects.isNull(vereinfachtesResultSet)) {
            return Collections.emptyList();
        }

        ArrayList<Group> groups = new ArrayList<>();
        boolean next = vereinfachtesResultSet.next();
        while (next) {
            String projectName = vereinfachtesResultSet.getString("projectName");
            String userName = vereinfachtesResultSet.getString("userName");
            String userEmail = vereinfachtesResultSet.getString("userEmail");
            String discordId = vereinfachtesResultSet.getString("discordid");
            User user = new User(userName);
            user.setEmail(userEmail);
            user.setDiscordid(discordId);
            String chatRoomId = null;
            if (withRocketChatId) {
                chatRoomId = vereinfachtesResultSet.getString("chatRoomId");
            }
            ArrayList<User> userList = new ArrayList<>(Collections.singletonList(user));
            Group group = new Group(vereinfachtesResultSet.getInt("groupId"), userList, projectName, chatRoomId);
            group.setName(vereinfachtesResultSet.getString("name"));
            groups.add(group);
            next = vereinfachtesResultSet.next();
        }

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

    public User getRepresentativUser(Group group, Project project) {
        User user;
        connect.connect();
        String query = "SELECT pu.userEmail from groupuser gu " +
                "join projectuser pu on pu.userEmail = gu.userEmail " +
                "where gu.groupId = ? " +
                "and pu.projectName = ? " +
                "LIMIT 1";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(query, group.getId(), project.getName());
        vereinfachtesResultSet.next();
        user = new User(vereinfachtesResultSet.getString("userEmail"));
        connect.close();
        return user;
    }
}





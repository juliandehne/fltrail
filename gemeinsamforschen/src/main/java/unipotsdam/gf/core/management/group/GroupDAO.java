package unipotsdam.gf.core.management.group;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.util.ResultSetUtil;

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

    public void persist(Group group) {
        connect.connect();
        List<Group> existingGroups = getExistingEntriesOfGroups(group.getProjectId());

        String mysqlRequestGroup = "INSERT INTO groups (`projectId`,`chatRoomId`) values (?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequestGroup, group.getProjectId(), group.getChatRoomId());

        List<Group> existingGroupsWithNewEntry = getExistingEntriesOfGroups(group.getProjectId());
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
                "JOIN groupuser gu ON g.id=gu.groupId " + "JOIN users u ON gu.userEmail=u.email " +
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
}

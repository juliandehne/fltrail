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

        String mysqlRequestGroup = "INSERT INTO groups (`projectId`,`chatRoomId`) values (?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequestGroup, group.getProjectId(), group.getChatRoomId());

        for (User groupMember : group.getMembers()) {
            String mysqlRequest2 = "INSERT INTO groupuser (`userEmail`, `groupId`) values (?,?)";
            connect.issueInsertOrDeleteStatement(mysqlRequest2, groupMember.getEmail(), group.getProjectId());
        }
        connect.close();
    }

    public void update(Group group) {
        connect.connect();
        String mysqlRequest = "UPDATE group SET projectId=?,chatRoomid=?";
        connect.issueUpdateStatement(mysqlRequest, group.getProjectId(), group.getChatRoomId());
        connect.close();
        // TODO: implement update of groupuser if needed later (if member list need to be updated)
    }


    public Boolean exists(Group group) {
        List<Group> groups = getGroupsByProjectId(group.getProjectId());
        return groups.contains(group);
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
                "JOIN groupuser gu ON g.id=gu.groupId " + "JOIN users u ON gu.userEmail=u.email" +
                "where g.projectId = ?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, projectId);
        HashMap<Integer, Group> groupHashMap = new HashMap<>();
        while (vereinfachtesResultSet.next()) {
            fillGroupFromResultSet(vereinfachtesResultSet, groupHashMap);
        }
        ArrayList<Group> groups = new ArrayList<>();
        groupHashMap.forEach((key, group) -> groups.add(group));

        connect.close();
        if (groups.isEmpty()) {
            return null;
        }
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
            Group group = new Group(userList, projectId, chatRoomId);
            existingGroups.put(id, group);
        }
    }
}

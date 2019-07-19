package unipotsdam.gf.modules.contributionFeedback.service;

import unipotsdam.gf.modules.contributionFeedback.model.ContributionFeedback;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Resource
@ManagedBean
public class ContributionFeedbackDAO {

    @Inject
    private MysqlConnect connect;

    public ContributionFeedback persist(ContributionFeedback contributionFeedback) {
        connect.connect();
        String uuid = UUID.randomUUID().toString();
        int groupId = contributionFeedback.getGroupId();
        String groupIdString = groupId == 0 ? null : String.valueOf(contributionFeedback.getGroupId());
        String mysqlRequest =
                "INSERT INTO contributionFeedback (`id`, `groupId`, `fullSubmissionId`, `fullSubmissionPartCategory`,`text`,`userEmail`) values (?,?,?,?,?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, uuid, groupIdString, contributionFeedback.getFullSubmissionId(),
                contributionFeedback.getFullSubmissionPartCategory(), contributionFeedback.getText(), contributionFeedback.getUserEmail());
        connect.close();
        return findOneById(uuid);
    }

    public ContributionFeedback findOneById(String id) {
        connect.connect();
        String query = "SELECT * FROM contributionfeedback WHERE id = ?";

        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query, id);
        ContributionFeedback contributionFeedback = getContributionFeedback(vereinfachtesResultSet);
        connect.close();
        return contributionFeedback;
    }

    public List<ContributionFeedback> findAll(String fullSubmissionId) {
        connect.connect();
        String query = "SELECT * FROM contributionfeedback WHERE fullSubmissionId = ? ORDER BY timestamp DESC";

        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query, fullSubmissionId);
        ArrayList<ContributionFeedback> contributionFeedbacks = new ArrayList<>();

        while (vereinfachtesResultSet.next()) {
            contributionFeedbacks.add(convertResultSet(vereinfachtesResultSet));
        }
        connect.close();
        return contributionFeedbacks;
    }

    public ContributionFeedback findOneBy(String fullSubmissionId, String fullSubmissionPartCategory, int groupId) {
        connect.connect();
        String query = "SELECT * FROM contributionfeedback WHERE fullSubmissionId = ? and fullSubmissionPartCategory = ? and groupId = ?";

        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query, fullSubmissionId, fullSubmissionPartCategory, groupId);
        ContributionFeedback contributionFeedback = getContributionFeedback(vereinfachtesResultSet);
        connect.close();
        return contributionFeedback;
    }

    public ContributionFeedback getFeedbackFor(String fullSubmissionId, String fullSubmissionPartCategory) {
        connect.connect();
        String query = "SELECT * FROM contributionfeedback WHERE fullSubmissionId = ? and fullSubmissionPartCategory = ?";

        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query, fullSubmissionId, fullSubmissionPartCategory);
        ContributionFeedback contributionFeedback = getContributionFeedback(vereinfachtesResultSet);
        connect.close();
        return contributionFeedback;
    }

    public void update(ContributionFeedback contributionFeedback) {
        connect.connect();
        String query = "UPDATE contributionFeedback SET `text` = ? WHERE fullSubmissionId = ? and groupId = ? and fullSubmissionPartCategory = ?";

        connect.issueUpdateStatement(query, contributionFeedback.getText(), contributionFeedback.getFullSubmissionId(),
                contributionFeedback.getGroupId(), contributionFeedback.getFullSubmissionPartCategory());
        connect.close();
    }

    private ContributionFeedback convertResultSet(VereinfachtesResultSet resultSet) {
        String id = resultSet.getString("id");
        int groupId = resultSet.getInt("groupId");
        String fullSubmissionId = resultSet.getString("fullSubmissionId");
        String fullSubmissionPartCategory = resultSet.getString("fullSubmissionPartCategory");
        String text = resultSet.getString("text");
        String userEmail = resultSet.getString("userEmail");
        long timestamp = resultSet.getTimestamp("timestamp").getTime();

        return new ContributionFeedback(id, groupId, fullSubmissionId, fullSubmissionPartCategory, text, userEmail, timestamp);
    }

    private ContributionFeedback getContributionFeedback(VereinfachtesResultSet vereinfachtesResultSet) {
        ContributionFeedback contributionFeedback = null;
        if (vereinfachtesResultSet.next()) {
            contributionFeedback = convertResultSet(vereinfachtesResultSet);
        }
        return contributionFeedback;
    }

}

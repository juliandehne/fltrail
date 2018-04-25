package uzuzjmd.competence.algorithms.groups.drools;

/**
 * Created by dehne on 13.12.2017.
 */
public class DLearner {

    private Integer groupId;
    private String learnerId;


    public DLearner(String learnerId) {
        this.learnerId = learnerId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getLearnerId() {
        return learnerId;
    }

    public void setLearnerId(String learnerId) {
        this.learnerId = learnerId;
    }
}

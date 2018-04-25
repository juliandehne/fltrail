package uzuzjmd.competence.algorithms.groups.drools;

/**
 * Created by dehne on 12.12.2017.
 */
public class DGroup {
    private Integer groupId;
    private Integer groupSize;
    private Integer groupFill;


    public DGroup(Integer groupFill, Integer groupSize, Integer groupId) {
        this.groupFill = groupFill;
        this.groupSize = groupSize;
        this.groupId = groupId;
    }

    public Integer getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(Integer groupSize) {
        this.groupSize = groupSize;
    }

    public Integer getGroupFill() {
        return groupFill;
    }

    public void setGroupFill(Integer groupFill) {
        this.groupFill = groupFill;
    }

    public void updateGroupFill(Integer update) {
        this.groupFill = groupFill + update;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}

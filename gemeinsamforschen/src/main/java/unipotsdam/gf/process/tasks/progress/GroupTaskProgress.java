package unipotsdam.gf.process.tasks.progress;

import unipotsdam.gf.process.tasks.Task;

public class GroupTaskProgress extends TaskProgress {

    private int groupId;

    public GroupTaskProgress(Task task, int groupId) {
        super(task);
        this.groupId = groupId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}

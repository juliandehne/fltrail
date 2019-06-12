package unipotsdam.gf.process.progress;

import unipotsdam.gf.modules.group.Group;

import java.util.List;

public class ProgressData {
    private Boolean isAlmostComplete;
    private int numberOfCompletion;
    private int numberNeeded;
    private java.util.List<Group> groupsMissing;

    public ProgressData() {
    }

    public ProgressData(
            Boolean isAlmostComplete, int numberOfCompletion, int numberNeeded, List<Group> groupsMissing) {
        this.isAlmostComplete = isAlmostComplete;
        this.numberOfCompletion = numberOfCompletion;
        this.numberNeeded = numberNeeded;
        this.groupsMissing = groupsMissing;
    }

    public Boolean getAlmostComplete() {
        return isAlmostComplete;
    }

    public void setAlmostComplete(Boolean almostComplete) {
        isAlmostComplete = almostComplete;
    }

    public int getNumberOfCompletion() {
        return numberOfCompletion;
    }

    public void setNumberOfCompletion(int numberOfCompletion) {
        this.numberOfCompletion = numberOfCompletion;
    }

    public int getNumberNeeded() {
        return numberNeeded;
    }

    public void setNumberNeeded(int numberNeeded) {
        this.numberNeeded = numberNeeded;
    }

    public List<Group> getGroupsMissing() {
        return groupsMissing;
    }

    public void setGroupsMissing(List<Group> groupsMissing) {
        this.groupsMissing = groupsMissing;
    }
}

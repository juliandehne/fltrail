package unipotsdam.gf.process.progress;

import unipotsdam.gf.modules.user.User;

import java.util.List;

public class ProgressData {
    private Boolean isAlmostComplete;
    private int numberOfCompletion;
    private int numberNeeded;
    private java.util.List<User> usersMissing;

    public ProgressData() {
    }

    public ProgressData(
            Boolean isAlmostComplete, int numberOfCompletion, int numberNeeded, List<User> usersMissing) {
        this.isAlmostComplete = isAlmostComplete;
        this.numberOfCompletion = numberOfCompletion;
        this.numberNeeded = numberNeeded;
        this.usersMissing = usersMissing;
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

    public List<User> getUsersMissing() {
        return usersMissing;
    }

    public void setUsersMissing(List<User> usersMissing) {
        this.usersMissing = usersMissing;
    }
}

package unipotsdam.gf.modules.assessment;

public class InternalPeerAssessmentProgress {

    public InternalPeerAssessmentProgress() {
    }

    public InternalPeerAssessmentProgress(int numberOfMissing) {
        this.numberOfMissing = numberOfMissing;
    }

    public int getNumberOfMissing() {
        return numberOfMissing;
    }

    public void setNumberOfMissing(int numberOfMissing) {
        this.numberOfMissing = numberOfMissing;
    }

    private int numberOfMissing;

}

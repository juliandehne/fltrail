package unipotsdam.gf.modules.assessment;

public class InternalPeerAssessmentProgress {

    InternalPeerAssessmentProgress(int numberOfMissing) {
        this.numberOfMissing = numberOfMissing;
    }

    public int getNumberOfMissing() {
        return numberOfMissing;
    }

    private int numberOfMissing;

}

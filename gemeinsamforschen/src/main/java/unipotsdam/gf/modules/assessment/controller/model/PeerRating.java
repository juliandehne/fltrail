package unipotsdam.gf.modules.assessment.controller.model;

import java.util.Arrays;

public class PeerRating {
    private String fromPeer;
    private String toPeer;
    private int[] workRating;

    @Override
    public String toString() {
        return "PeerRating{" +
                "fromPeer='" + fromPeer + '\'' +
                ", toPeer='" + toPeer + '\'' +
                ", workRating=" + Arrays.toString(workRating) +
                '}';
    }

    public String getFromPeer() {
        return fromPeer;
    }

    public PeerRating() {
    }

    public void setFromPeer(String fromPeer) {
        this.fromPeer = fromPeer;
    }

    public String getToPeer() {
        return toPeer;
    }

    public void setToPeer(String toPeer) {
        this.toPeer = toPeer;
    }

    public int[] getWorkRating() {
        return workRating;
    }

    public void setWorkRating(int[] workRating) {
        this.workRating = workRating;
    }

    public PeerRating(String fromPeer, String toPeer, int[] workRating) {
        this.fromPeer = fromPeer;
        this.toPeer = toPeer;
        this.workRating = workRating;
    }
}
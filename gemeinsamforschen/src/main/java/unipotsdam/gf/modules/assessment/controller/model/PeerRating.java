package unipotsdam.gf.modules.assessment.controller.model;

import java.util.Arrays;
import java.util.Map;

public class PeerRating {
    private String fromPeer;
    private String toPeer;
    private Map<String, Integer> workRating;

    @Override
    public String toString() {
        return "PeerRating{" +
                "fromPeer='" + fromPeer + '\'' +
                ", toPeer='" + toPeer + '\'' +
                ", workRating=" + workRating +
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

    public Map<String, Integer> getWorkRating() {
        return workRating;
    }

    public void setWorkRating(Map<String, Integer> workRating) {
        this.workRating = workRating;
    }

    public PeerRating(String fromPeer, String toPeer, Map<String, Integer> workRating) {
        this.fromPeer = fromPeer;
        this.toPeer = toPeer;
        this.workRating = workRating;
    }
}

package unipotsdam.gf.modules.assessment.controller.model;

import java.util.Arrays;
import java.util.Map;

public class PeerRating {
    private String fromPeer;
    private String toPeer;
    private Map workRating;

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

    public Map getWorkRating() {
        return workRating;
    }

    public void setWorkRating(Map<String, Number> workRating) {
        this.workRating = workRating;
    }

    public PeerRating(String fromPeer, String toPeer, Map<String, Number> workRating) {
        this.fromPeer = fromPeer;
        this.toPeer = toPeer;
        this.workRating = workRating;
    }
}

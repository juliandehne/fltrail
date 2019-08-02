package unipotsdam.gf.modules.performance;

import java.util.HashMap;

public class PerformanceUtil {
    private static HashMap<PerformanceCandidates, Long> timed = new HashMap<>();
    private static HashMap<PerformanceCandidates, Long> beingTimed = new HashMap<>();

    private static synchronized void updateMap(PerformanceCandidates label, Long time) {
        if (timed.containsKey(label)) {
            Long currentValue = timed.get(label);
            timed.put(label, time + currentValue);
        } else {
            timed.put(label, time);
        }
    }

    public static HashMap<PerformanceCandidates, Long> getStats() {
        return timed;
    }

    public static void start(PerformanceCandidates label) {
        beingTimed.put(label, System.currentTimeMillis());
    }

    public static void stop(PerformanceCandidates label) {
        long delta = System.currentTimeMillis() - beingTimed.get(label);
        updateMap(label, delta);
    }
}

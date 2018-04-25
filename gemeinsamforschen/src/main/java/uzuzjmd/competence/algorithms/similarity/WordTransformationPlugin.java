package uzuzjmd.competence.algorithms.similarity;

import java.util.List;

/**
 * Created by dehne on 13.09.2017.
 */
public abstract class WordTransformationPlugin implements IWordTransformationPlugin {

    public final Double derivedPriority;
    public final Double priority;

    public WordTransformationPlugin(Double currentPriority) {
        this.derivedPriority = getDerivedPriority(currentPriority, getReduction());
        this.priority = getReducedPriority(currentPriority, getReduction());
    }

    public List<String> transform(String input) throws Exception {
        List<String> result = getSimilarWords(input);
        /*TimeCriticalStore timeCriticalStore = new TimeCriticalStore();
        timeCriticalStore.writeDerivedLinks(input, result, this.getClass().getSimpleName(),derivedPriority);*/
        return result;
    }

    public abstract int getReduction();

    private Double getReducedPriority(Double priority, int steps) {
        if (steps== 0) {
            return priority;
        }
        if (priority == null || priority == 0d) {
            return 0d;
        }
        return priority / (steps * MatchPriority.BASE);
    }

    private Double getIncreasedPriority(Double priority, int steps) {
        if (steps== 0) {
            return priority;
        }
        if (priority == null || priority == 0d) {
            return 0d;
        }
        return priority * (steps * MatchPriority.BASE);
    }

    private Double getDerivedPriority(Double priority, int steps) {
        if (priority == null || priority == 0d) {
            return 0d;
        }
        Double increasedPriority = getIncreasedPriority(priority, steps);
        double result = 1 / (Math.pow(MatchPriority.BASE,increasedPriority));
        return result;
    }
}

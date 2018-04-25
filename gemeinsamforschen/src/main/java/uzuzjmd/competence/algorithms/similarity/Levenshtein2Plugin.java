package uzuzjmd.competence.algorithms.similarity;

import java.util.List;

/**
 * Created by dehne on 11.10.2017.
 */
public class Levenshtein2Plugin extends WordTransformationPlugin {
    public Levenshtein2Plugin(Double currentPriority) {
        super(currentPriority);
    }

    @Override
    public List<String> getSimilarWords(String input) throws Exception {
        LevensteinsPlugin levensteinsPlugin = new LevensteinsPlugin(priority);
        return levensteinsPlugin.getSimilarWords2(input);
    }

    @Override
    public int getReduction() {
        return MatchPriority.LEVENSHTEIN2REDUCTION;
    }
}

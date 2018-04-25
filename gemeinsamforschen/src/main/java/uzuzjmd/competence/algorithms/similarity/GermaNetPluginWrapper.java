package uzuzjmd.competence.algorithms.similarity;

import java.util.List;

/**
 * Created by dehne on 13.09.2017.
 */
public class GermaNetPluginWrapper extends WordTransformationPlugin {
    public GermaNetPluginWrapper(Double weight) {
        super(weight);
    }

    @Override
    public int getReduction() {
        return MatchPriority.GERMANETSYNREDUCTION;
    }

    @Override
    public List<String> getSimilarWords(String input) throws Exception {
        List<String> result = GermaNetPlugin.getSynStringsJava(input);
        return result;
    }
}

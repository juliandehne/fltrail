package uzuzjmd.competence.algorithms.similarity;

import java.util.List;

/**
 * Created by dehne on 11.10.2017.
 */
public class GermanetSimPluginWrapper extends WordTransformationPlugin {
    public GermanetSimPluginWrapper(Double weight) {
        super(weight);
    }

    @Override
    public int getReduction() {
        return MatchPriority.GERMANETSIMREDUCTION;
    }

    @Override
    public List<String> getSimilarWords(String input) throws Exception {
        return GermaNetPlugin.getSimStrings(input);
    }
}

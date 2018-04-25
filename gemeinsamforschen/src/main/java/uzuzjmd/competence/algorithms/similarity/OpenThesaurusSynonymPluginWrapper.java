package uzuzjmd.competence.algorithms.similarity;

import java.util.List;

/**
 * Created by dehne on 13.09.2017.
 */
public class OpenThesaurusSynonymPluginWrapper extends WordTransformationPlugin {
    public OpenThesaurusSynonymPluginWrapper(Double currentPriority) {
        super(currentPriority);
    }

    @Override
    public List<String> getSimilarWords(String input) throws Exception {
        return OpenThesaurusSynonymPlugin.getSynonymsAsJava(input);
    }

    @Override
    public int getReduction() {
        return MatchPriority.THESAURUSSYNREDUCTION;
    }
}
